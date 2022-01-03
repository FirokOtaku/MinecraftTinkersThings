package firok.tiths.tile;

import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;

public class TileMotiaPedestal extends TileEntity
{
	public TileMotiaPedestal()
	{
		super(TithsTiles.teMotiaPedestal.get());
	}

	private ItemStack stackPhoto;
	public void setStackPhoto(ItemStack stack)
	{
		if(ItemStack.areItemStacksEqual(stack, stackPhoto)) return;

		this.markDirty();

		if(stack == ItemStack.EMPTY)
		{
			this.stackPhoto = ItemStack.EMPTY;
			return;
		}

		this.stackPhoto = stack.copy();
	}
	public ItemStack getStackPhoto()
	{
		return stackPhoto;
	}

	@Override
	public void read(BlockState state, CompoundNBT nbt)
	{
		this.stackPhoto = nbt.contains("stack_photo", 10) ?
				ItemStack.read(nbt.getCompound("stack_photo")) :
				ItemStack.EMPTY;
	}

	@Override
	public CompoundNBT write(CompoundNBT nbt)
	{
		if(stackPhoto != ItemStack.EMPTY)
			nbt.put("stack_photo", ItemStack.EMPTY.write(new CompoundNBT()));

		return nbt;
	}
}
