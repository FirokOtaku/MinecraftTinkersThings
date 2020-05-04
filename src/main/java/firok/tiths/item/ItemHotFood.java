package firok.tiths.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

// 灼热食物
public class ItemHotFood extends ItemFood
{
	public ItemHotFood(int food, float saturation, boolean isWolfFood) {
		super(food,saturation,isWolfFood);
//		this.setCreativeTab(CreativeTabs.FOOD);
	}
	public ItemHotFood(int food, boolean isWolfFood) {
		this(food, 0.6F, isWolfFood);
		this.setPotionEffect(new PotionEffect(MobEffects.HASTE,160,0),1);
	}

	@Override
	protected void onFoodEaten(ItemStack stack, World world, EntityPlayer player)
	{
		super.onFoodEaten(stack, world, player);
		player.setFire(8);
	}
}
