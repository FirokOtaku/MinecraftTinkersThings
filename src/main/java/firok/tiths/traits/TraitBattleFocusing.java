package firok.tiths.traits;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import slimeknights.tconstruct.library.traits.AbstractTrait;

import static firok.tiths.common.Keys.colorTraitBattleFocusing;
import static firok.tiths.common.Keys.nameTraitBattleFocusing;
import static firok.tiths.common.Potions.forcibleFocused;
import static net.minecraft.init.MobEffects.SLOWNESS;

/**
 * 专注
 */
public class TraitBattleFocusing extends AbstractTrait
{
	public TraitBattleFocusing()
	{
		super(nameTraitBattleFocusing,colorTraitBattleFocusing);
	}

	@Override
	public void onHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, boolean isCritical)
	{
		if(target.isEntityAlive())
		{
			player.addPotionEffect(new PotionEffect(forcibleFocused,100,0));
			target.addPotionEffect(new PotionEffect(SLOWNESS,100,0));
		}
	}
}
