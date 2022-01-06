package firok.tiths.tile.pedestal;

import firok.tiths.tile.TithsTiles;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;

public class TilePedestalBase extends TileEntity
{
	private ItemStack stackPedestal = ItemStack.EMPTY;

	public TilePedestalBase()
	{
		super(TithsTiles.tePedestal.get());
	}

	public void setStackPedestal(ItemStack stack)
	{
		if(ItemStack.areItemStacksEqual(stack, stackPedestal)) return;
		this.markDirty();
		if(stack == ItemStack.EMPTY)
		{
			this.stackPedestal = ItemStack.EMPTY;
			return;
		}
		this.stackPedestal = stack.copy();
	}
	public ItemStack getStackPedestal()
	{
		return stackPedestal;
	}

	public static final String KEY_NBT_STACK_PEDESTAL = "stack_pedestal";

	@Override
	public void read(BlockState state, CompoundNBT nbt)
	{
		this.stackPedestal = nbt.contains(KEY_NBT_STACK_PEDESTAL, 10) ?
				ItemStack.read(nbt.getCompound(KEY_NBT_STACK_PEDESTAL)) :
				ItemStack.EMPTY;
	}

	@Override
	public CompoundNBT write(CompoundNBT nbt)
	{
		if(stackPedestal != ItemStack.EMPTY)
			nbt.put(KEY_NBT_STACK_PEDESTAL, ItemStack.EMPTY.write(new CompoundNBT()));

		return nbt;
	}
}
