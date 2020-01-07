package firok.tiths.traits;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.traits.AbstractTrait;

import java.util.List;

import static firok.tiths.TinkersThings.tell;
import static firok.tiths.common.DamageSources.PyroelectricDamage;
import static firok.tiths.common.Keys.colorTraitPyroelectric;
import static firok.tiths.common.Keys.nameTraitPyroelectric;
import static firok.tiths.util.Ranges.Neighbour;
import static firok.tiths.util.Selectors.mobAlive;

// 热释电
public class TraitPyroelectric extends AbstractTrait
{
	public static final float factorDamage=1.8f;
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
			float damage= factorDamage * (temp-0.15f)+0.35f;
			List<Entity> list=world.getEntitiesInAABBexcluding(
					target,
					Neighbour(target,2),
					mobAlive
			);

			tell("list size:"+list.size()+", damage:"+damage);
			for(Entity entity:list)
			{
				EntityLivingBase mob=(EntityLivingBase)entity;
				mob.hurtResistantTime=0;
				mob.attackEntityFrom(PyroelectricDamage,damage);
			}
		}
	}
}
