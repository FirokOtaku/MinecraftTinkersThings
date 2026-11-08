package firok.tiths.tile;

import firok.tiths.block.BlockCrystallizer;
import firok.tiths.common.Configs;
import firok.tiths.common.Items;
import firok.tiths.common.Keys;
import firok.tiths.recipe.crystallizer.CrystallizerFluidInput;
import firok.tiths.recipe.crystallizer.CrystallizerRecipe;
import firok.tiths.recipe.crystallizer.CrystallizerRecipes;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.Material;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * 结晶器 TE: 按配方注册表接受多种熔融流体, 合成产物并向正面输出.
 */
public class TECrystallizer extends TileEntity
{
    private final List<FluidTank> tanks = new ArrayList<>();
    private final IFluidHandler fluidHandler = new CrystallizerFluidHandler();
    private boolean crafting = false;

    /** 上次扫描时的流体种类哈希; 种类未变则复用候选列表, 不重新查注册表 */
    private long cachedTypeHash = Long.MIN_VALUE;
    private List<CrystallizerRecipe> cachedCandidates = Collections.emptyList();
    private CrystallizerRecipe cachedRecipe = null;

    public TECrystallizer() {}

    public static int getFluidCapacity()
    {
        return Math.max(1, Configs.Gameplay.crystallizer_fluid_capacity);
    }

    public static float getShardDropChance()
    {
        return (float) Configs.Gameplay.crystallizer_shard_drop_chance;
    }

    public List<FluidTank> getTanks()
    {
        return Collections.unmodifiableList(tanks);
    }

    private FluidTank createTank()
    {
        FluidTank tank = new FluidTank(getFluidCapacity())
        {
            @Override
            protected void onContentsChanged()
            {
                TECrystallizer.this.onTankChanged();
            }
        };
        tank.setCanDrain(true);
        tank.setCanFill(true);
        return tank;
    }

    private void onTankChanged()
    {
        markDirty();
        // 合成消耗过程中暂缓整理/匹配, 避免中途清空储罐导致配方缓存失效
        if (crafting) return;

        pruneEmptyTanks();
        sortTanksByFluidId();
        rematchRecipeIfTypesChanged();
        if (world != null && !world.isRemote)
        {
            IBlockState state = world.getBlockState(pos);
            world.notifyBlockUpdate(pos, state, state, 3);
            tryCraft();
        }
    }

    private void pruneEmptyTanks()
    {
        tanks.removeIf(tank -> tank.getFluid() == null || tank.getFluidAmount() <= 0);
    }

    /**
     * 按流体 ResourceLocation 排序虚拟储罐.
     */
    private void sortTanksByFluidId()
    {
        tanks.sort(Comparator.comparing(tank ->
        {
            FluidStack stack = tank.getFluid();
            if (stack == null || stack.getFluid() == null)
            {
                return new ResourceLocation("forge", "");
            }
            return CrystallizerRecipes.fluidId(stack.getFluid());
        }));
    }

    private List<FluidStack> currentFluidStacks()
    {
        List<FluidStack> list = new ArrayList<>(tanks.size());
        for (FluidTank tank : tanks)
        {
            FluidStack stack = tank.getFluid();
            if (stack != null && stack.amount > 0 && stack.getFluid() != null)
            {
                list.add(stack);
            }
        }
        return list;
    }

    private long currentTypeHash()
    {
        List<FluidStack> stacks = currentFluidStacks();
        if (stacks.isEmpty()) return Long.MIN_VALUE;
        return CrystallizerRecipes.hashFluidTypesFromStacks(stacks);
    }

    private void rematchRecipeIfTypesChanged()
    {
        long typeHash = currentTypeHash();
        if (typeHash == cachedTypeHash) return;
        cachedTypeHash = typeHash;
        if (typeHash == Long.MIN_VALUE)
        {
            cachedCandidates = Collections.emptyList();
            cachedRecipe = null;
            return;
        }
        cachedCandidates = CrystallizerRecipes.getByTypeHash(typeHash);
        cachedRecipe = CrystallizerRecipes.findMatch(cachedCandidates, currentFluidStacks());
    }

    private void forceRematch()
    {
        cachedTypeHash = Long.MIN_VALUE + 1; // 强制与当前不同
        rematchRecipeIfTypesChanged();
    }

    /**
     * 在已缓存的候选列表里按当前存量刷新可合成配方 (不重新查注册表).
     */
    private void refreshCachedRecipeFromCandidates()
    {
        if (cachedRecipe != null && cachedRecipe.matches(currentFluidStacks())) return;
        cachedRecipe = CrystallizerRecipes.findMatch(cachedCandidates, currentFluidStacks());
    }

    /**
     * 当缓存配方所需流体足够时消耗并向前方输出产物.
     */
    public void tryCraft()
    {
        if (world == null || world.isRemote || crafting) return;

        crafting = true;
        try
        {
            rematchRecipeIfTypesChanged();
            refreshCachedRecipeFromCandidates();
            while (cachedRecipe != null && cachedRecipe.matches(currentFluidStacks()))
            {
                CrystallizerRecipe recipe = cachedRecipe;
                consumeRecipe(recipe);
                for (ItemStack output : recipe.copyOutputs())
                {
                    outputItem(output);
                }
                // 同种类下可能仍有余量可继续合成, 只在候选列表内刷新
                refreshCachedRecipeFromCandidates();
            }
        }
        finally
        {
            crafting = false;
            pruneEmptyTanks();
            sortTanksByFluidId();
            forceRematch();
            if (world != null && !world.isRemote)
            {
                IBlockState state = world.getBlockState(pos);
                world.notifyBlockUpdate(pos, state, state, 3);
            }
        }
    }

    private void consumeRecipe(CrystallizerRecipe recipe)
    {
        for (CrystallizerFluidInput input : recipe.getFluids())
        {
            FluidTank tank = tankFor(CrystallizerRecipes.getFluid(input.getFluidId()));
            if (tank != null)
            {
                tank.drain(input.getAmount(), true);
            }
        }
    }

    private void outputItem(ItemStack stack)
    {
        IBlockState state = world.getBlockState(pos);
        EnumFacing facing = EnumFacing.NORTH;
        if (state.getBlock() instanceof BlockCrystallizer)
        {
            facing = state.getValue(BlockCrystallizer.FACING);
        }
        BlockPos front = pos.offset(facing);
        TileEntity frontTe = world.getTileEntity(front);

        if (frontTe != null && frontTe.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing.getOpposite()))
        {
            IItemHandler handler = frontTe.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing.getOpposite());
            if (handler != null)
            {
                ItemStack leftover = ItemHandlerHelper.insertItem(handler, stack, false);
                if (leftover.isEmpty()) return;
                stack = leftover;
            }
        }

        ejectItem(stack, facing);
    }

    private void ejectItem(ItemStack stack, EnumFacing facing)
    {
        if (stack.isEmpty()) return;
        double x = pos.getX() + 0.5 + facing.getXOffset() * 0.7;
        double y = pos.getY() + 0.35;
        double z = pos.getZ() + 0.5 + facing.getZOffset() * 0.7;
        EntityItem entity = new EntityItem(world, x, y, z, stack);
        entity.motionX = facing.getXOffset() * 0.12;
        entity.motionY = 0.08;
        entity.motionZ = facing.getZOffset() * 0.12;
        entity.setDefaultPickupDelay();
        world.spawnEntity(entity);
    }

    /**
     * 破坏时按剩余流体量掉落对应材料碎片.
     */
    public void dropResidualShards()
    {
        if (world == null || world.isRemote) return;
        Random rand = world.rand;
        float chance = getShardDropChance();
        for (FluidTank tank : tanks)
        {
            dropShardsForTank(tank, rand, chance);
        }
    }

    private void dropShardsForTank(FluidTank tank, Random rand, float chance)
    {
        FluidStack stack = tank.getFluid();
        if (stack == null || stack.amount <= 0 || stack.getFluid() == null) return;

        int ingotUnits = stack.amount / Material.VALUE_Ingot;
        if (ingotUnits <= 0) return;

        String materialName = stack.getFluid().getName();
        ItemStack shardTemplate = getShardStack(materialName);
        if (shardTemplate.isEmpty()) return;

        for (int i = 0; i < ingotUnits; i++)
        {
            if (rand.nextFloat() < chance)
            {
                ItemStack drop = shardTemplate.copy();
                drop.setCount(1);
                net.minecraft.inventory.InventoryHelper.spawnItemStack(
                        world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, drop);
            }
        }
    }

    private static ItemStack getShardStack(String materialName)
    {
        Material material = TinkerRegistry.getMaterial(materialName);
        if (material != null && material != Material.UNKNOWN)
        {
            ItemStack shard = material.getShard();
            if (shard != null && !shard.isEmpty()) return shard;
        }
        // 艾瑞希亚材料当前未注册时, 回退为粒
        if (Keys.nameIrisia.equals(materialName))
        {
            return new ItemStack(Items.nuggetIrisia);
        }
        return ItemStack.EMPTY;
    }

    @Nullable
    private FluidTank tankFor(@Nullable Fluid fluid)
    {
        if (fluid == null) return null;
        ResourceLocation id = CrystallizerRecipes.fluidId(fluid);
        for (FluidTank tank : tanks)
        {
            FluidStack stack = tank.getFluid();
            if (stack != null && stack.getFluid() != null
                    && id.equals(CrystallizerRecipes.fluidId(stack.getFluid())))
            {
                return tank;
            }
        }
        return null;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing)
    {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) return true;
        return super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
    {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
        {
            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(fluidHandler);
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        tanks.clear();
        if (compound.hasKey("Tanks", Constants.NBT.TAG_LIST))
        {
            NBTTagList list = compound.getTagList("Tanks", Constants.NBT.TAG_COMPOUND);
            for (int i = 0; i < list.tagCount(); i++)
            {
                FluidTank tank = createTank();
                tank.readFromNBT(list.getCompoundTagAt(i));
                if (tank.getFluid() != null && tank.getFluidAmount() > 0)
                {
                    tanks.add(tank);
                }
            }
        }
        else
        {
            // 兼容旧版双储罐 NBT
            readLegacyTank(compound, "TankGlass");
            readLegacyTank(compound, "TankIrisia");
        }
        pruneEmptyTanks();
        sortTanksByFluidId();
        forceRematch();
    }

    private void readLegacyTank(NBTTagCompound compound, String key)
    {
        if (!compound.hasKey(key, Constants.NBT.TAG_COMPOUND)) return;
        FluidTank tank = createTank();
        tank.readFromNBT(compound.getCompoundTag(key));
        if (tank.getFluid() != null && tank.getFluidAmount() > 0)
        {
            tanks.add(tank);
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        NBTTagList list = new NBTTagList();
        for (FluidTank tank : tanks)
        {
            if (tank.getFluid() != null && tank.getFluidAmount() > 0)
            {
                list.appendTag(tank.writeToNBT(new NBTTagCompound()));
            }
        }
        compound.setTag("Tanks", list);
        return compound;
    }

    @Override
    public NBTTagCompound getUpdateTag()
    {
        return writeToNBT(new NBTTagCompound());
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket()
    {
        return new SPacketUpdateTileEntity(this.pos, 0, getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt)
    {
        readFromNBT(pkt.getNbtCompound());
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState)
    {
        return oldState.getBlock() != newState.getBlock();
    }

    private class CrystallizerFluidHandler implements IFluidHandler
    {
        @Override
        public IFluidTankProperties[] getTankProperties()
        {
            if (tanks.isEmpty())
            {
                return new IFluidTankProperties[0];
            }
            IFluidTankProperties[] props = new IFluidTankProperties[tanks.size()];
            for (int i = 0; i < tanks.size(); i++)
            {
                props[i] = tanks.get(i).getTankProperties()[0];
            }
            return props;
        }

        @Override
        public int fill(FluidStack resource, boolean doFill)
        {
            if (resource == null || resource.amount <= 0 || resource.getFluid() == null) return 0;
            if (!CrystallizerRecipes.isAcceptedFluid(resource.getFluid())) return 0;

            FluidTank existing = tankFor(resource.getFluid());
            if (existing != null)
            {
                // 同种流体只能进入已有储罐; 满了也不能再开新槽
                return existing.fill(resource, doFill);
            }

            // 新流体类型: 创建虚拟储罐
            if (!doFill)
            {
                return Math.min(resource.amount, getFluidCapacity());
            }
            FluidTank tank = createTank();
            tanks.add(tank);
            return tank.fill(resource, true);
        }

        @Nullable
        @Override
        public FluidStack drain(FluidStack resource, boolean doDrain)
        {
            if (resource == null || resource.amount <= 0 || resource.getFluid() == null) return null;
            FluidTank tank = tankFor(resource.getFluid());
            if (tank == null) return null;
            return tank.drain(resource, doDrain);
        }

        @Nullable
        @Override
        public FluidStack drain(int maxDrain, boolean doDrain)
        {
            if (maxDrain <= 0) return null;
            for (FluidTank tank : tanks)
            {
                FluidStack drained = tank.drain(maxDrain, doDrain);
                if (drained != null) return drained;
            }
            return null;
        }
    }
}
