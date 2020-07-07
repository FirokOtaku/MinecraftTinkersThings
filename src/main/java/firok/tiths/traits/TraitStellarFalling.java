package firok.tiths.traits;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.entity.EntityProjectileBase;
import slimeknights.tconstruct.library.traits.AbstractProjectileTrait;

import java.util.Random;

import static firok.tiths.common.Keys.colorTraitStellarFalling;
import static firok.tiths.common.Keys.nameTraitStellarFalling;
import static net.minecraft.init.Blocks.AIR;

/**
 * 金乌坠落 - 投射物
 */
public class TraitStellarFalling extends AbstractProjectileTrait
{
	public TraitStellarFalling()
	{
		super(nameTraitStellarFalling,colorTraitStellarFalling);
	}

	@Override
	public void onProjectileUpdate(EntityProjectileBase projectile, World world, ItemStack toolStack)
	{
		if(!world.isRemote) return;

		int i=2;
		Random rand=world.rand;
		while(i-->0)
		{
			world.spawnParticle(
					EnumParticleTypes.FLAME,
					projectile.posX + rand.nextFloat() -0.5,
					projectile.posY + rand.nextFloat() -0.5,
					projectile.posZ + rand.nextFloat() -0.5,
					projectile.motionX/2,
					projectile.motionY/2,
					projectile.motionZ/2
			);
		}
	}

	@Override
	public void afterHit(EntityProjectileBase projectile, World world, ItemStack ammoStack, EntityLivingBase attacker, Entity target, double impactSpeed)
	{
		if(world==null || world.isRemote || target==null) return;

		BlockPos pos=target.getPosition();

		boolean wasSolid;
		IBlockState stateFire=Blocks.FIRE.getDefaultState();

		FOR_X:for(int ox=-1;ox<=1;ox++)
		{
			FOR_Z:for(int oz=-1;oz<=1;oz++)
			{
				BlockPos posBase=pos.add(ox,-2,oz);
				wasSolid=world.getBlockState(posBase).isSideSolid(world,posBase,EnumFacing.UP);

				FOR_Y:for(int oy = -1; true; oy++)
				{
					BlockPos posTemp=pos.add(ox,oy,oz);

					IBlockState stateTemp=world.getBlockState(posTemp);
					boolean isAirNow=stateTemp.getBlock()==AIR;

					if(isAirNow && wasSolid)
					{
						world.setBlockState(posTemp,stateFire);
						continue FOR_Z;
					}
					else if(isAirNow)
					{
						continue FOR_Z;
					}

					if(oy==1) continue FOR_Z;

					wasSolid=stateTemp.isSideSolid(world,posTemp,EnumFacing.UP);
				}
			}
		}

		target.setFire(5);
	}
}
