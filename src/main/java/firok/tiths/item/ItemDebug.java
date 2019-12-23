package firok.tiths.item;

import firok.tiths.TinkersThings;
import firok.tiths.common.Potions;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemDebug extends Item
{

	@Override
	public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing face, float x, float y, float z, EnumHand hand)
	{
		world.playSound(player,player.posX,player.posY,player.posZ,
				new SoundEvent(new ResourceLocation(TinkersThings.MOD_ID,"record1")),
				SoundCategory.RECORDS,0.5f,1f);

		return EnumActionResult.SUCCESS;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
	{
		player.addPotionEffect(new PotionEffect(Potions.heavy,180,0));


		return super.onItemRightClick(world, player, hand);
	}
}
