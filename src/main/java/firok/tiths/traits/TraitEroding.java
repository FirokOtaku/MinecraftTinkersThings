package firok.tiths.traits;

import firok.tiths.common.Potions;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import slimeknights.tconstruct.library.traits.AbstractTrait;

import static firok.tiths.common.Keys.colorTraitEroding;
import static firok.tiths.common.Keys.nameTraitEroding;

/**
 * 侵蚀
 */
public class TraitEroding extends AbstractTrait
{
	public TraitEroding()
	{
		super(nameTraitEroding,colorTraitEroding);
	}

	@Override
	public void onHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, boolean isCritical)
	{
		if(target.isServerWorld())
		{
			PotionEffect peEroded=target.getActivePotionEffect(Potions.eroded);
			int lv=peEroded!=null?peEroded.getAmplifier()+1:0;
			if(lv>9) lv=9;
			target.addPotionEffect(new PotionEffect(Potions.eroded,100,lv));
		}
	}
}
