package firok.tiths.traits;

import firok.tiths.TinkersThings;
import firok.tiths.util.EntityFinders;
import firok.tiths.util.Selectors;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.entity.EntityProjectileBase;
import slimeknights.tconstruct.library.traits.AbstractProjectileTrait;

import java.util.List;
import java.util.Random;

import static firok.tiths.common.Keys.colorTraitCracking;
import static firok.tiths.common.Keys.nameTraitCracking;
import static firok.tiths.util.Predicates.canTrigger;

/**
 * 崩裂
 */
public class TraitCracking extends AbstractProjectileTrait
{
	public TraitCracking()
	{
		super(nameTraitCracking,colorTraitCracking);
	}

	@Override
	public void afterHit(EntityProjectileBase projectile, World world, ItemStack ammoStack, EntityLivingBase attacker, Entity target, double impactSpeed)
	{
		if(projectile==null || world==null || target==null) return;
//		TinkersThings.log("hit !");
		Random rand=world.rand;
		if(world.isRemote)
		{
			int times=8;
			int id=Block.getStateId(Blocks.STONE.getDefaultState());
			while(times-->0)
			{
				world.spawnParticle(
						EnumParticleTypes.BLOCK_CRACK,
						target.posX + rand.nextFloat() * 0.8 -0.4,
						target.posY + target.getEyeHeight()/2 + rand.nextFloat() * 0.8 -0.4,
						target.posZ + rand.nextFloat() * 0.8 -0.4,
						 + rand.nextFloat() * 0.4 - 0.2,
						 + rand.nextFloat() * 0.4 - 0.2,
						 + rand.nextFloat() * 0.4 - 0.2,
						id
				);
			}
		}
		else if(canTrigger(rand,1))
		{
			List<Entity> list=EntityFinders.Facing(
					world,projectile,
					projectile.getPositionVector(),
					new Vec3d(projectile.motionX,projectile.motionY,projectile.motionZ),
					5,1,Selectors.livingBaseAlive);
			for(Entity en:list)
			{
				EntityLivingBase enlb=(EntityLivingBase)en;
				enlb.hurtResistantTime=0;
				enlb.attackEntityFrom(DamageSource.GENERIC, (float)impactSpeed);
			}
		}
	}
}
