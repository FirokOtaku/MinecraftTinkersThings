package firok.tiths.tile.pedestal;

import firok.tiths.tile.TithsTiles;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public class TilePedestalBase extends TileEntity
{
	private ItemStack stackPedestal = ItemStack.EMPTY;

	public TilePedestalBase()
	{
		super(TithsTiles.tePedestal.get());
	}
	protected TilePedestalBase(TileEntityType<?> tet)
	{
		super(tet);
	}

	public void setStackPedestal(ItemStack stack)
	{
		if(ItemStack.areItemStacksEqual(stack, stackPedestal)) return;
		if(stack == ItemStack.EMPTY)
		{
			this.stackPedestal = ItemStack.EMPTY;
			return;
		}
		this.stackPedestal = stack.copy();
		this.markDirty();
	}
	public ItemStack getStackPedestal()
	{
		return stackPedestal;
	}

	public static final String KEY_NBT_STACK_PEDESTAL = "stack_pedestal";

	@Override
	public void read(BlockState state, CompoundNBT nbt)
	{
		super.read(state, nbt);
		ItemStack stack = nbt.contains(KEY_NBT_STACK_PEDESTAL) ?
				ItemStack.read(nbt.getCompound(KEY_NBT_STACK_PEDESTAL)) :
				ItemStack.EMPTY;
		setStackPedestal(stack);
	}

	@Override
	public CompoundNBT write(CompoundNBT nbt)
	{
		super.write(nbt);
		ItemStack stack = getStackPedestal();
		if(stack != ItemStack.EMPTY)
			nbt.put(KEY_NBT_STACK_PEDESTAL, stack.write(new CompoundNBT()));

		return nbt;
	}

	public CompoundNBT getUpdateTag() {
		CompoundNBT compoundnbt = this.write(new CompoundNBT());
		return compoundnbt;
	}
}
