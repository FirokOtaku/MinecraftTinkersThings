package firok.tiths.tile;

import firok.tiths.block.BlockShakingDispenser;
import firok.tiths.common.Blocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockRedstoneRepeater;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerDispenser;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.util.ITickable;

import static firok.tiths.util.Predicates.canTick;

public class TEShackingDispenser
	extends TileEntityDispenser implements ITickable
{
	@Override
	public void update()
	{
		if(world.isRemote || !canTick(world,15,0)) return;

//		System.out.println("shack");
		IBlockState state = world.getBlockState(pos);
		Block block = state.getBlock();
		if(!(block instanceof BlockShakingDispenser)) return;

//		EnumFacing facing = state.getValue(BlockDispenser.FACING);

		int redstonePower = world.getStrongPower(pos);
		if(redstonePower <= 0) return;

		((BlockShakingDispenser) block).dispense(world,pos);
	}

	@Override
	public String getGuiID()
	{
		return "minecraft:dispenser";
	}

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
	{
		return new ContainerDispenser(playerInventory, this);
	}
}
