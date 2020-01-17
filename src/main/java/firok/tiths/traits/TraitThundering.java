package firok.tiths.traits;

import firok.tiths.common.DamageSources;
import firok.tiths.util.Selectors;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.traits.AbstractTrait;


import java.util.List;

import static firok.tiths.common.Keys.*;
import static firok.tiths.util.Predicates.canTrigger;
import static firok.tiths.util.Ranges.Neighbour;
import static firok.tiths.util.Selectors.*;

// 雷鸣
public class TraitThundering extends AbstractTrait
{
	public TraitThundering()
	{
		super(nameTraitThundering, colorTraitThundering);
	}

	@Override
	public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit)
	{
		World world=target.world;
//		super.afterHit(tool, player, target, damageDealt, wasCritical, wasHit);
		if(wasHit && !world.isRemote)
		{
			if(target.isEntityAlive())
			{
				target.hurtResistantTime=0;
				target.attackEntityFrom(DamageSources.ThunderingDamage,4.5f);
			}

			if(canTrigger(world,0.2f))
			{
				List<Entity> entities=world.getEntitiesInAABBexcluding(player,Neighbour(player,5),mobAlive);
				final double px=player.posX,pz=player.posZ;
				for(Entity en:entities)
				{
					final double mx=en.posX,mz=en.posZ;
					final double ox=(mx-px)/4,oz=(mz-pz)/4;

					final double vx= ox > 0.5 ? 0.5 : ox < -0.5 ? -0.5 : ox;
					final double vz= oz > 0.5 ? 0.5 : oz < -0.5 ? -0.5 : oz;

					en.addVelocity(vx,0.1f,vz);

					en.hurtResistantTime=0;
					en.attackEntityFrom(DamageSources.ThunderingDamage,3);
				}
			}
		}
	}
}
