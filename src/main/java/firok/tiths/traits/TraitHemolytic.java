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

	@Override
	public float damage(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float newDamage, boolean isCritical)
	{
		return super.damage(tool, player, target, damage, newDamage*1.15f, isCritical);
	}

	@Override
	public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit)
	{
		if(!player.world.isRemote && wasHit && damageDealt>5 && canTrigger(player.world,0.5f))
		{
			ToolHelper.healTool(tool,(int)(damageDealt/5),player);
		}
	}
}
