package firok.tiths.item;

import firok.tiths.block.TithsBlocks;
import firok.tiths.block.paving.MotiaPavingStoneBlock;
import firok.tiths.block.pedestal.AdvancedMotiaPedestalBlock;
import firok.tiths.tile.TileWithEntityType;
import firok.tiths.tile.pedestal.TilePedestalBase;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ItemAdvancedMotiaCrystal extends ItemMaterial
{
	public ItemAdvancedMotiaCrystal()
	{
		super();
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
	{
		ItemStack stack = playerIn.getHeldItem(handIn);

		if(playerIn.isSneaking() && stack.hasTag())
		{
			stack.setTag(null);
			return ActionResult.resultSuccess(stack);
		}
		else return ActionResult.resultPass(stack);
	}

	@Override
	public ActionResultType onItemUse(ItemUseContext context)
	{
		ItemStack stack = context.getItem();
		World world = context.getWorld();
		BlockPos pos = context.getPos();
		BlockState state = world.getBlockState(pos);

		for(IAdvancedMotiaCrystalUseTarget target : chain)
		{
			if(target.canUse(context, stack, world, pos, state))
			{
				target.onUse(context, stack, world, pos, state);
				return ActionResultType.SUCCESS;
			}
		}

		return ActionResultType.FAIL;
	}

	public interface IAdvancedMotiaCrystalUseTarget
	{
		boolean canUse(ItemUseContext context, ItemStack stack, World world, BlockPos pos, BlockState state);
		void onUse(ItemUseContext context, ItemStack stack, World world, BlockPos pos, BlockState state);
	}
	private static final List<IAdvancedMotiaCrystalUseTarget> chain = new ArrayList<>(3);
	public static void addTarget(IAdvancedMotiaCrystalUseTarget node)
	{
		chain.add(node);
	}
	public static final IAdvancedMotiaCrystalUseTarget targetingAdvancedMotiaPedestal = new IAdvancedMotiaCrystalUseTarget()
	{
		@Override
		public boolean canUse(ItemUseContext context, ItemStack stack, World world, BlockPos pos, BlockState state)
		{
			return state.getBlock() == TithsBlocks.ADVANCED_MOTIA_PEDESTAL.get();
		}

		@Override
		public void onUse(ItemUseContext context, ItemStack stack, World world, BlockPos pos, BlockState state)
		{
			AdvancedMotiaPedestalBlock blockPedestal = TithsBlocks.ADVANCED_MOTIA_PEDESTAL.get();
			TilePedestalBase te = blockPedestal.getTilePedestalAt(world, pos);
			if(stack.hasTag())
			{
				te.setStackPedestal(stack);
			}
			else
			{
				te.setStackPedestal(ItemStack.EMPTY);
			}
		}
	};
	public static final IAdvancedMotiaCrystalUseTarget targetingMotiaPavingStone = new IAdvancedMotiaCrystalUseTarget()
	{
		@Override
		public boolean canUse(ItemUseContext context, ItemStack stack, World world, BlockPos pos, BlockState state)
		{
			return state.getBlock() == TithsBlocks.MOTIA_PAVING_STONE.get();
		}

		@Override
		public void onUse(ItemUseContext context, ItemStack stack, World world, BlockPos pos, BlockState state)
		{
			MotiaPavingStoneBlock blockMotiaPavingStone = TithsBlocks.MOTIA_PAVING_STONE.get();
			TileWithEntityType te = blockMotiaPavingStone.getTileWithTypeAt(world, pos);
			if(stack.hasTag())
			{
				try
				{
					String strEt = stack.getTag().getString(KEY_NBT_ENTITY_TYPE);
					ResourceLocation rlEt = new ResourceLocation(strEt);
					EntityType<?> et = ForgeRegistries.ENTITIES.getValue(rlEt);
					te.setEntityType(et);
				}
				catch (Exception ignored) { }
			}
			else
			{
				te.setEntityType(null);
			}

		}
	};
	static
	{
		addTarget(targetingAdvancedMotiaPedestal);
		addTarget(targetingMotiaPavingStone);
	}

	public static final String KEY_NBT_ENTITY = "entity";
	public static final String KEY_NBT_NAME = "name";
	public static final String KEY_NBT_ENTITY_TYPE = "entity_type";

	@Override
	public boolean onLeftClickEntity(ItemStack stack, PlayerEntity player, Entity entity)
	{
		CompoundNBT tag = new CompoundNBT();

		CompoundNBT tagEntity = new CompoundNBT();
		entity.writeUnlessRemoved(tagEntity);
		tag.put(KEY_NBT_ENTITY, tagEntity);

		String name = entity.getDisplayName().getString();
		tag.putString(KEY_NBT_NAME, name);

		String et = entity.getEntityString();
		if(et != null) tag.putString(KEY_NBT_ENTITY_TYPE, et);

		stack.setTag(tag);
		return true;
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
	{
		if(!stack.hasTag()) return;
		CompoundNBT nbt = stack.getTag();
		assert nbt != null;
		String name = nbt.contains(KEY_NBT_NAME) ? nbt.getString(KEY_NBT_NAME) : "";
		tooltip.add(new StringTextComponent(name));
	}
}
