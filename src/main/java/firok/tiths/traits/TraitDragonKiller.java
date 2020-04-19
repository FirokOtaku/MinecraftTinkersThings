package firok.tiths.traits;

import firok.tiths.common.Configs;
import firok.tiths.common.DamageSources;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.traits.AbstractTrait;

import static firok.tiths.common.Keys.colorTraitDragonKiller;
import static firok.tiths.common.Keys.nameTraitDragonKiller;

// 屠龙者
public class TraitDragonKiller extends AbstractTrait
{
	public TraitDragonKiller()
	{
		super(nameTraitDragonKiller, colorTraitDragonKiller);
	}

	@Override
	public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit)
	{
		if(wasHit && !target.world.isRemote && damageDealt > 0)
		{
			String name=target.getName().toLowerCase();
			if(name.contains("dragon"))
			{
				target.hurtResistantTime=0;
				target.attackEntityFrom(DamageSources.DragonKiller(player), (float)(Configs.Traits.factor_dragon_killer_damage_base + damageDealt * Configs.Traits.factor_dragon_killer_damage_percent));
			}
		}
	}
}
