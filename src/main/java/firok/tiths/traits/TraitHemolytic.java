package firok.tiths.traits;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.ToolHelper;

import static firok.tiths.common.Keys.colorTraitHemolytic;
import static firok.tiths.common.Keys.nameTraitHemolytic;
import static firok.tiths.util.Predicates.canTrigger;

// 溶血
public class TraitHemolytic  extends AbstractTrait
{
	public TraitHemolytic()
	{
		super(nameTraitHemolytic, colorTraitHemolytic);
	}

	public static final float factorDamage=1.15f;
	public static final float limitRepairDamage=5;
	public static final float factorRepairDamage=5;
	public static final float rate=0.5f;

	@Override
	public float damage(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float newDamage, boolean isCritical)
	{
		return super.damage(tool, player, target, damage, newDamage * factorDamage, isCritical);
	}

	@Override
	public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit)
	{
		if(!player.world.isRemote && wasHit && damageDealt>limitRepairDamage && canTrigger(player.world,rate))
		{
			ToolHelper.healTool(tool,(int)(damageDealt/factorRepairDamage),player);
		}
	}
}
