package firok.tiths.util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

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

	static final Item blockStone=Item.getItemFromBlock(Blocks.STONE);
	static final Item blockCobbleStone=Item.getItemFromBlock(Blocks.COBBLESTONE);
	public static boolean isStone(ItemStack stack)
	{
		Item item;
		return stack != null &&
				stack != ItemStack.EMPTY &&
				((item = stack.getItem()) == blockStone ||
						item == blockCobbleStone);
	}
	public static boolean isStone(IBlockState state)
	{
		return state!=null&&isStone(state.getBlock());
	}
	public static boolean isStone(Item item)
	{
		return item!=null && (item == blockStone || item == blockCobbleStone);
	}
	public static boolean isStone(Block block)
	{
		return block!=null && (block == Blocks.STONE || block == Blocks.COBBLESTONE);
	}

//	public static boolean canReplaceWithOre(IBlockState state, IBlockAccess world, BlockPos pos)
//	{
//		return state!=null && canReplaceWithOre(state.getBlock(), world, pos);
//	}
//	public static boolean canReplaceWithOre(Block block, IBlockAccess world, BlockPos pos)
//	{
//		return block!=null && (
//			block==Blocks.AIR ||
//			block==Blocks.STONE ||
//			block==Blocks.COBBLESTONE ||
//			block==Blocks.DIRT ||
//			block==Blocks.SAND ||
//			block==Blocks.GRASS ||
//			(world!=null && pos !=null && block.isReplaceable(world,pos))
//		);
//	}

	public static boolean canTrigger(World world,float rate)
	{
		return canTrigger(world.rand,rate);
	}
	public static boolean canTrigger(Random rand,float rate)
	{
		return rand.nextDouble()<rate;
	}

	public static boolean canOreGenWorld(IBlockState state)
	{
		if(state==null) return false;
		Block block=state.getBlock();

		return block==Blocks.STONE && state.getValue(BlockStone.VARIANT).isNatural();
	}
	public static boolean canMeteoGen(IBlockState state)
	{
		if(state==null) return false;
		Block block=state.getBlock();

		return block == Blocks.AIR ||
				block == Blocks.STONE ||
				block == Blocks.COBBLESTONE ||
				block == Blocks.DIRT ||
				block == Blocks.SAND ||
				block == Blocks.GRASS;
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
