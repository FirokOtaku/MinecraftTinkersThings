package firok.tiths.traits;

import firok.tiths.common.Potions;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import slimeknights.tconstruct.library.traits.AbstractTrait;

import static firok.tiths.common.Keys.colorTraitRepressing;
import static firok.tiths.common.Keys.nameTraitRepressing;

// 压制
public class TraitRepressing extends AbstractTrait
{
	public TraitRepressing()
	{
		super(nameTraitRepressing, colorTraitRepressing);
	}

	@Override
	public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit)
	{
		if(wasHit && target.isServerWorld() && target.isEntityAlive())
		{
			target.addPotionEffect(new PotionEffect(Potions.heavy,80,0));
		}
	}
}
