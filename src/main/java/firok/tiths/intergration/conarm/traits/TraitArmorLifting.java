package firok.tiths.intergration.conarm.traits;

import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingFallEvent;

import static firok.tiths.common.Keys.colorTraitLifting;
import static firok.tiths.common.Keys.nameTraitLifting;

/**
 * 扬升 - 护甲
 */
public class TraitArmorLifting extends AbstractArmorTrait
{
	public TraitArmorLifting()
	{
		super(nameTraitLifting,colorTraitLifting);
	}

	@Override
	public void onArmorTick(ItemStack tool, World world, EntityPlayer player)
	{
		if(!player.isSneaking() && player.motionY < 0)
		{
			player.motionY *= 0.7f;
		}
	}

	@Override
	public void onFalling(ItemStack armor, EntityPlayer player, LivingFallEvent evt)
	{
		evt.setDamageMultiplier( evt.getDamageMultiplier() * 0.5f );
	}
}
