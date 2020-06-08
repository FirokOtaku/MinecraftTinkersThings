package firok.tiths.traits;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import slimeknights.tconstruct.library.traits.AbstractTrait;

import static firok.tiths.common.Keys.colorTraitEddying;
import static firok.tiths.common.Keys.nameTraitEddying;
import static firok.tiths.common.Potions.eddying;

/**
 * 旋流
 */
public class TraitEddying extends AbstractTrait
{
	public TraitEddying()
	{
		super(nameTraitEddying,colorTraitEddying);
	}

	@Override
	public void onHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, boolean isCritical)
	{
		if(!target.world.isRemote && target.isEntityAlive())
		{
			target.addPotionEffect(new PotionEffect(eddying,200,0));
		}
	}
}
