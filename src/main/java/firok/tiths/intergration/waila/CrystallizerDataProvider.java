package firok.tiths.intergration.waila;

import firok.tiths.tile.TECrystallizer;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

import java.util.List;

public class CrystallizerDataProvider implements IWailaDataProvider
{
    @Override
    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config)
    {
        return null;
    }

    @Override
    public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
    {
        return currenttip;
    }

    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
    {
        TileEntity te = accessor.getTileEntity();
        if (!(te instanceof TECrystallizer)) return currenttip;

        TECrystallizer crystallizer = (TECrystallizer) te;
        NBTTagCompound nbt = accessor.getNBTData();
        if (nbt != null && nbt.hasKey("Tanks", Constants.NBT.TAG_LIST))
        {
            NBTTagList list = nbt.getTagList("Tanks", Constants.NBT.TAG_COMPOUND);
            int capacity = TECrystallizer.getFluidCapacity();
            for (int i = 0; i < list.tagCount(); i++)
            {
                FluidTank tank = new FluidTank(capacity);
                tank.readFromNBT(list.getCompoundTagAt(i));
                appendTank(currenttip, tank);
            }
        }
        else if (nbt != null && (nbt.hasKey("TankGlass") || nbt.hasKey("TankIrisia")))
        {
            // 兼容旧 NBT
            appendLegacy(currenttip, nbt, "TankGlass");
            appendLegacy(currenttip, nbt, "TankIrisia");
        }
        else
        {
            for (FluidTank tank : crystallizer.getTanks())
            {
                appendTank(currenttip, tank);
            }
        }
        return currenttip;
    }

    private static void appendLegacy(List<String> tip, NBTTagCompound nbt, String key)
    {
        if (!nbt.hasKey(key)) return;
        FluidTank tank = new FluidTank(TECrystallizer.getFluidCapacity());
        tank.readFromNBT(nbt.getCompoundTag(key));
        appendTank(tip, tank);
    }

    private static void appendTank(List<String> tip, FluidTank tank)
    {
        FluidStack stack = tank.getFluid();
        if (stack == null || stack.amount <= 0)
        {
            return;
        }
        tip.add(String.format("%s: %d / %d mB", stack.getLocalizedName(), stack.amount, tank.getCapacity()));
    }

    @Override
    public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config)
    {
        return currenttip;
    }

    @Override
    public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, BlockPos pos)
    {
        if (te instanceof TECrystallizer)
        {
            te.writeToNBT(tag);
        }
        return tag;
    }
}
