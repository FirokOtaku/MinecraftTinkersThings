package firok.tiths.item;

import firok.tiths.common.TithsRarity;
import firok.tiths.common.Villagers;
import firok.tiths.util.InnerActions;
import net.minecraft.block.BlockFence;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Supplier;

/**
 * 指定职业村民刷怪蛋: 右键方块生成对应职业村民; 右键已有村民可改其职业.
 */
public class ItemProfessionSpawnEgg extends ItemCustom
{
    private final Supplier<VillagerRegistry.VillagerProfession> professionSupplier;
    private final int primaryColor;
    private final int secondaryColor;

    public ItemProfessionSpawnEgg(
            Supplier<VillagerRegistry.VillagerProfession> professionSupplier,
            int primaryColor,
            int secondaryColor
    )
    {
        super();
        this.professionSupplier = professionSupplier;
        this.primaryColor = primaryColor & 0xFFFFFF;
        this.secondaryColor = secondaryColor & 0xFFFFFF;
        this.setMaxStackSize(64);
        this.setRarity(TithsRarity.CreativeOnly);
    }

    public int getPrimaryColor()
    {
        return primaryColor;
    }

    public int getSecondaryColor()
    {
        return secondaryColor;
    }

    @Nullable
    public VillagerRegistry.VillagerProfession getProfession()
    {
        return professionSupplier == null ? null : professionSupplier.get();
    }

    @Override
    public EnumActionResult onItemUse(
            EntityPlayer player,
            World world,
            BlockPos pos,
            EnumHand hand,
            EnumFacing facing,
            float hitX,
            float hitY,
            float hitZ
    )
    {
        ItemStack stack = player.getHeldItem(hand);
        if (world.isRemote)
        {
            return EnumActionResult.SUCCESS;
        }

        VillagerRegistry.VillagerProfession profession = getProfession();
        if (profession == null)
        {
            return EnumActionResult.FAIL;
        }

        IBlockState state = world.getBlockState(pos);
        BlockPos spawnPos = pos;
        double yOffset = 0.0D;
        if (facing == EnumFacing.UP && state.getBlock() instanceof BlockFence)
        {
            yOffset = 0.5D;
        }
        else
        {
            spawnPos = pos.offset(facing);
        }

        EntityVillager villager = new EntityVillager(world);
        villager.setLocationAndAngles(
                spawnPos.getX() + 0.5D,
                spawnPos.getY() + yOffset,
                spawnPos.getZ() + 0.5D,
                world.rand.nextFloat() * 360.0F,
                0.0F
        );
        // onInitialSpawn 会随机职业; 之后必须清 Career/Offers 再写入目标职业
        villager.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(villager)), null);
        Villagers.applyProfessionFresh(villager, profession);
        villager.enablePersistence();
        world.spawnEntity(villager);
        villager.playLivingSound();

        if (!player.capabilities.isCreativeMode)
        {
            stack.shrink(1);
        }
        return EnumActionResult.SUCCESS;
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase target, EnumHand hand)
    {
        if (!(target instanceof EntityVillager))
        {
            return false;
        }
        VillagerRegistry.VillagerProfession profession = getProfession();
        if (profession == null)
        {
            return false;
        }
        if (!player.world.isRemote)
        {
            Villagers.applyProfessionFresh((EntityVillager) target, profession);
            if (!player.capabilities.isCreativeMode)
            {
                stack.shrink(1);
            }
        }
        return true;
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
    {
        if (this.isInCreativeTab(tab) && getProfession() != null)
        {
            items.add(new ItemStack(this));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World worldIn, List<String> list, ITooltipFlag flagIn)
    {
        InnerActions.addInformation(this, list, flagIn);
    }
}
