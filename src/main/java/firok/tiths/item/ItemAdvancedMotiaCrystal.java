package firok.tiths.item;

import firok.tiths.block.TithsBlocks;
import firok.tiths.block.pedestal.AdvancedMotiaPedestalBlock;
import firok.tiths.tile.pedestal.TilePedestalBase;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
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
		AdvancedMotiaPedestalBlock block = TithsBlocks.ADVANCED_MOTIA_PEDESTAL.get();
		if(!stack.hasTag() || state.getBlock() != block)
			return ActionResultType.PASS;

		TilePedestalBase te = block.getTilePedestalAt(world, pos);
		te.setStackPedestal(stack);
		return ActionResultType.SUCCESS;
	}

	public static final String KEY_NBT_ENTITY = "entity";
	public static final String KEY_NBT_NAME = "name";

	@Override
	public boolean onLeftClickEntity(ItemStack stack, PlayerEntity player, Entity entity)
	{
		CompoundNBT tag = new CompoundNBT();

		CompoundNBT tagEntity = new CompoundNBT();
		entity.writeUnlessRemoved(tagEntity);
		tag.put(KEY_NBT_ENTITY, tagEntity);

		String name = entity.getDisplayName().getString();
		tag.putString(KEY_NBT_NAME, name);
		stack.setTag(tag);
		return true;
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
	{
		if(!stack.hasTag()) return;

	}
}
