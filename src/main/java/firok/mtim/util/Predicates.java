package firok.mtim.util;

import net.minecraft.block.BlockStone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

public final class Predicates
{
	private Predicates(){}

	public static boolean canTick(long time,int interval,int offset)
	{
		return time%interval==offset;
	}
	public static boolean canTick(World world,int interval,int offset)
	{
		return canTick(world.getTotalWorldTime(),interval,offset);
	}

	public static boolean canOreGenWorld(IBlockState state)
	{
		return state!=null && state.getBlock()==Blocks.STONE && state.getValue(BlockStone.VARIANT).isNatural();
	}
	public static boolean canOreGenNether(IBlockState state)
	{
		return state!=null && state.getBlock()==Blocks.NETHERRACK;
	}
	public static boolean canOreGenEnd(IBlockState state)
	{
		return state!=null && state.getBlock()==Blocks.END_STONE;
	}
	public static boolean canOreGenOther(IBlockState state)
	{
		return canOreGenWorld(state);
	}
}
