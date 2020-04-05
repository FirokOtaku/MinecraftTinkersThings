package firok.tiths.tile;

import firok.tiths.block.BlockSpecialBrewingStand;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.walkers.ItemStackDataLists;
import net.minecraft.util.math.BlockPos;

import java.util.Arrays;

/**
 * 特殊酿造台
 */
public abstract class TEGemBrewingStand extends TileEntityLockable implements ITickable, ISidedInventory
{
	private static final int[] SLOTS_FOR_UP = new int[] {3};
	private static final int[] SLOTS_FOR_DOWN = new int[] {0, 1, 2, 3};
	private static final int[] OUTPUT_SLOTS = new int[] {0, 1, 2, 4};
	private NonNullList<ItemStack> brewingItemStacks = NonNullList.<ItemStack>withSize(4, ItemStack.EMPTY);
	private int brewTime;
	private boolean[] filledSlots;
	private Item ingredientID;
	private String customName;

	public String getName()
	{
		return this.hasCustomName() ? this.customName : "container.brewing";
	}

	public boolean hasCustomName()
	{
		return this.customName != null && !this.customName.isEmpty();
	}

	public void setName(String name)
	{
		this.customName = name;
	}

	public int getSizeInventory()
	{
		return this.brewingItemStacks.size();
	}

	public boolean isEmpty()
	{
		for (ItemStack itemstack : this.brewingItemStacks)
		{
			if (!itemstack.isEmpty())
			{
				return false;
			}
		}

		return true;
	}

	public void update()
	{
//		ItemStack itemstack = this.brewingItemStacks.get(4);
//		if (this.fuel <= 0 && itemstack.getItem() == Items.BLAZE_POWDER)
//		{
//			this.fuel = 20;
//			itemstack.shrink(1);
//			this.markDirty();
//		}

		boolean canBrew = this.canBrew();
		boolean brewTimeOver0 = this.brewTime > 0;
		ItemStack rawMaterial = this.brewingItemStacks.get(3);

		if (brewTimeOver0)
		{
			--this.brewTime;
			boolean flag2 = this.brewTime == 0;

			if (flag2 && canBrew)
			{
				this.brewPotions();
				this.markDirty();
			}
			else if (!canBrew)
			{
				this.brewTime = 0;
				this.markDirty();
			}
			else if (this.ingredientID != rawMaterial.getItem())
			{
				this.brewTime = 0;
				this.markDirty();
			}
		}
		else if (canBrew /*&& this.fuel > 0*/)
		{
			/*--this.fuel;*/
			this.brewTime = 80;
			this.ingredientID = rawMaterial.getItem();
			this.markDirty();
		}

		if (!this.world.isRemote)
		{
			boolean[] filledSlots = this.createFilledSlotsArray();

			if (!Arrays.equals(filledSlots, this.filledSlots))
			{
				this.filledSlots = filledSlots;
				IBlockState blockstate = this.world.getBlockState(this.getPos());

				if (!(blockstate.getBlock() instanceof BlockSpecialBrewingStand))
				{
					return;
				}

				for (int i = 0; i < BlockSpecialBrewingStand.HAS_BOTTLE.length; ++i)
				{
					blockstate = blockstate.withProperty(BlockSpecialBrewingStand.HAS_BOTTLE[i], filledSlots[i]);
				}

				this.world.setBlockState(this.pos, blockstate, 2);
			}
		}
	}

	public boolean[] createFilledSlotsArray()
	{
		boolean[] ret = new boolean[3];

		for (int i = 0; i < 3; ++i)
		{
			if (!(this.brewingItemStacks.get(i)).isEmpty())
			{
				ret[i] = true;
			}
		}

		return ret;
	}

	private boolean canBrew()
	{
		return net.minecraftforge.common.brewing.BrewingRecipeRegistry.canBrew(brewingItemStacks, brewingItemStacks.get(3), OUTPUT_SLOTS); // divert to VanillaBrewingRegistry
	}

	private void brewPotions()
	{
		if (net.minecraftforge.event.ForgeEventFactory.onPotionAttemptBrew(brewingItemStacks)) return;
		ItemStack itemstack = this.brewingItemStacks.get(3);

		net.minecraftforge.common.brewing.BrewingRecipeRegistry.brewPotions(brewingItemStacks, brewingItemStacks.get(3), OUTPUT_SLOTS);

		itemstack.shrink(1);
		BlockPos blockpos = this.getPos();

		if (itemstack.getItem().hasContainerItem(itemstack))
		{
			ItemStack itemstack1 = itemstack.getItem().getContainerItem(itemstack);

			if (itemstack.isEmpty())
			{
				itemstack = itemstack1;
			}
			else
			{
				InventoryHelper.spawnItemStack(this.world, blockpos.getX(), blockpos.getY(), blockpos.getZ(), itemstack1);
			}
		}

		this.brewingItemStacks.set(3, itemstack);
		this.world.playEvent(1035, blockpos, 0);
		net.minecraftforge.event.ForgeEventFactory.onPotionBrewed(brewingItemStacks);
	}

	// high !
	public static void registerFixesBrewingStand(DataFixer fixer)
	{
		fixer.registerWalker(FixTypes.BLOCK_ENTITY, new ItemStackDataLists(TEGemBrewingStand.class, new String[] {"Items"}));
	}

	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		this.brewingItemStacks = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(compound, this.brewingItemStacks);
		this.brewTime = compound.getShort("BrewTime");

		if (compound.hasKey("CustomName", 8))
		{
			this.customName = compound.getString("CustomName");
		}

//		this.fuel = compound.getByte("Fuel");
	}

	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		compound.setShort("BrewTime", (short)this.brewTime);
		ItemStackHelper.saveAllItems(compound, this.brewingItemStacks);

		if (this.hasCustomName())
		{
			compound.setString("CustomName", this.customName);
		}

//		compound.setByte("Fuel", (byte)this.fuel);
		return compound;
	}

	public ItemStack getStackInSlot(int index)
	{
		return index >= 0 && index < this.brewingItemStacks.size() ?
				this.brewingItemStacks.get(index) :
				ItemStack.EMPTY;
	}

	public ItemStack decrStackSize(int index, int count)
	{
		return ItemStackHelper.getAndSplit(this.brewingItemStacks, index, count);
	}

	public ItemStack removeStackFromSlot(int index)
	{
		return ItemStackHelper.getAndRemove(this.brewingItemStacks, index);
	}

	public void setInventorySlotContents(int index, ItemStack stack)
	{
		if (index >= 0 && index < this.brewingItemStacks.size())
		{
			this.brewingItemStacks.set(index, stack);
		}
	}

	public int getInventoryStackLimit()
	{
		return 64;
	}

	public boolean isUsableByPlayer(EntityPlayer player)
	{
		if (this.world.getTileEntity(this.pos) != this)
		{
			return false;
		}
		else
		{
			return player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
		}
	}

	public void openInventory(EntityPlayer player)
	{
	}

	public void closeInventory(EntityPlayer player)
	{
	}

	/**
	 * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot. For
	 * guis use Slot.isItemValid
	 */
	public boolean isItemValidForSlot(int index, ItemStack stack)
	{
		if (index == 3)
		{
			return net.minecraftforge.common.brewing.BrewingRecipeRegistry.isValidIngredient(stack);
		}
		else
		{
			return net.minecraftforge.common.brewing.BrewingRecipeRegistry.isValidInput(stack) && this.getStackInSlot(index).isEmpty();
		}
	}

	public int[] getSlotsForFace(EnumFacing side)
	{
		if (side == EnumFacing.UP)
		{
			return SLOTS_FOR_UP;
		}
		else
		{
			return side == EnumFacing.DOWN ? SLOTS_FOR_DOWN : OUTPUT_SLOTS;
		}
	}

	/**
	 * Returns true if automation can insert the given item in the given slot from the given side.
	 */
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction)
	{
		return this.isItemValidForSlot(index, itemStackIn);
	}

	/**
	 * Returns true if automation can extract the given item in the given slot from the given side.
	 */
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction)
	{
		if (index == 3)
		{
			return stack.getItem() == Items.GLASS_BOTTLE;
		}
		else
		{
			return true;
		}
	}

	public abstract String getGuiID();
//	public String getGuiID()
//	{
//		return "tiths:brewing_stand";
//	}

	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
	{
		return new ContainerBrewingStand(playerInventory, this);
	}

	public int getField(int id)
	{
		switch (id)
		{
			case 0:
				return this.brewTime;
//			case 1:
//				return this.fuel;
			default:
				return 0;
		}
	}

	public void setField(int id, int value)
	{
		switch (id)
		{
			case 0:
				this.brewTime = value;
				break;
//			case 1:
//				this.fuel = value;
		}
	}

	net.minecraftforge.items.IItemHandler handlerInput = new net.minecraftforge.items.wrapper.SidedInvWrapper(this, net.minecraft.util.EnumFacing.UP);
	net.minecraftforge.items.IItemHandler handlerOutput = new net.minecraftforge.items.wrapper.SidedInvWrapper(this, net.minecraft.util.EnumFacing.DOWN);
	net.minecraftforge.items.IItemHandler handlerSides = new net.minecraftforge.items.wrapper.SidedInvWrapper(this, net.minecraft.util.EnumFacing.NORTH);

	@Override
	public <T> T getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @javax.annotation.Nullable net.minecraft.util.EnumFacing facing)
	{
		if (facing != null && capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
		{
			if (facing == EnumFacing.UP)
				return (T) handlerInput;
			else if (facing == EnumFacing.DOWN)
				return (T) handlerOutput;
			else
				return (T) handlerSides;
		}
		return super.getCapability(capability, facing);
	}

	public int getFieldCount()
	{
		return 1;
	}

	public void clear()
	{
		this.brewingItemStacks.clear();
	}
}
