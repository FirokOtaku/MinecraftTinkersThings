package firok.tiths.traits;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.entity.EntityProjectileBase;
import slimeknights.tconstruct.library.traits.AbstractProjectileTrait;

import javax.annotation.Nullable;

import static firok.tiths.common.Keys.colorTraitPonderous;
import static firok.tiths.common.Keys.nameTraitPonderous;

/**
 * 重磅
 */
public class TraitPonderous extends AbstractProjectileTrait
{
	public TraitPonderous()
	{
		super(nameTraitPonderous,colorTraitPonderous);
	}

	@Override
	public void onLaunch(EntityProjectileBase projectileBase, World world, @Nullable EntityLivingBase shooter)
	{
		projectileBase.motionX /= 4d;
		projectileBase.motionY /= 4d;
		projectileBase.motionZ /= 4d;
	}

	@Override
	public float knockBack(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float knockback, float newKnockback, boolean isCritical)
	{
		return (newKnockback + 0.5f) * 1.5f;
	}

	@Override
	public float damage(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float newDamage, boolean isCritical)
	{
		return newDamage + damage * 0.5f;
	}
}
