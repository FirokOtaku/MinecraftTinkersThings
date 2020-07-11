package firok.tiths.traits;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.entity.EntityProjectileBase;
import slimeknights.tconstruct.library.traits.AbstractProjectileTrait;

import static firok.tiths.common.Keys.colorTraitFrostJack;
import static firok.tiths.common.Keys.nameTraitFrostJack;

/**
 * 冰霜杰克
 */
public class TraitFrostJack extends AbstractProjectileTrait
{
	public TraitFrostJack()
	{
		super(nameTraitFrostJack,colorTraitFrostJack);
	}

	@Override
	public void afterHit(EntityProjectileBase projectile, World world, ItemStack ammoStack, EntityLivingBase attacker, Entity target, double impactSpeed)
	{
		super.afterHit(projectile, world, ammoStack, attacker, target, impactSpeed);
	}
}
