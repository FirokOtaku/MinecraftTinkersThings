package firok.tiths.traits;

import firok.tiths.common.DamageSources;
import firok.tiths.util.EntityFinders;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.traits.AbstractTrait;

import java.util.List;

import static firok.tiths.common.Configs.Traits.factor_pyroelectric_damage;
import static firok.tiths.common.Keys.colorTraitPyroelectric;
import static firok.tiths.common.Keys.nameTraitPyroelectric;
import static firok.tiths.util.Selectors.mobAlive;

// 热释电
public class TraitPyroelectric extends AbstractTrait
{
	public TraitPyroelectric()
	{
		super(nameTraitPyroelectric, colorTraitPyroelectric);
	}

	@Override
	public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit)
	{
		super.afterHit(tool, player, target, damageDealt, wasCritical, wasHit);

		if(wasHit && player.isServerWorld())
		{
			World world=target.world;
			BlockPos posPlayer=player.getPosition();

			float temp=world.getBiome(posPlayer).getFloatTemperature(posPlayer);
			if(temp<=0.15f) return;
			double damage= factor_pyroelectric_damage * (temp-0.15f)+0.35f;

			List<Entity> list=EntityFinders.Nearby(target,2,mobAlive);

			for(Entity entity:list)
			{
				EntityLivingBase mob=(EntityLivingBase)entity;
				mob.hurtResistantTime=0;
				mob.attackEntityFrom(DamageSources.Pyroelectric(player),(float)damage);
			}
		}
	}
}
