package firok.tiths.traits;

import firok.tiths.common.Potions;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.entity.EntityProjectileBase;
import slimeknights.tconstruct.library.events.ProjectileEvent;
import slimeknights.tconstruct.library.traits.AbstractProjectileTrait;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.ToolHelper;
import slimeknights.tconstruct.shared.TinkerCommons;
import slimeknights.tconstruct.shared.block.BlockGlow;

import java.util.Random;

import static firok.tiths.common.Keys.colorTraitIlluminating;
import static firok.tiths.common.Keys.nameTraitAblaze;

/**
 * 灯明
 */
public class TraitAblaze extends AbstractProjectileTrait implements IHitBlockProjectile
{
	public TraitAblaze()
	{
		super(nameTraitAblaze,colorTraitIlluminating);
	}

	@Override
	public void onHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, boolean isCritical)
	{
		target.addPotionEffect(new PotionEffect(Potions.illuminating,500,0));
		if(target.world.isRemote)
		{
			int i=3;
			Random rand=target.world.rand;
			while(i-->0)
			{
				target.world.spawnParticle(
						EnumParticleTypes.VILLAGER_HAPPY,
						target.posX + rand.nextFloat()*2-1,
						target.posY + rand.nextFloat()*2,
						target.posZ + rand.nextFloat()*2-1,
						0,0,0
				);
			}
		}
	}

	@Override
	public void afterHit(EntityProjectileBase projectile, World world, ItemStack ammoStack, EntityLivingBase attacker, Entity target, double impactSpeed)
	{
		if(target.world.isRemote)
		{
			int i=3;
			Random rand=target.world.rand;
			while(i-->0)
			{
				target.world.spawnParticle(
						EnumParticleTypes.VILLAGER_HAPPY,
						target.posX + rand.nextFloat()*2-1,
						target.posY + rand.nextFloat()*2,
						target.posZ + rand.nextFloat()*2-1,
						0,0,0
				);
			}
		}
	}

	@Override
	public void hitBlock(ProjectileEvent.OnHitBlock event, BlockPos pos, World world, IBlockState state, Random rand, ItemStack stackAmmo, Entity shootingEntity) throws Exception
	{
		BlockGlow glow=(BlockGlow) firok.tiths.common.Blocks.blockIcelitGlow;

		for(BlockPos candidate : new BlockPos[]{ pos.up(), pos.north(), pos.east(), pos.south(), pos.west(), pos.down()}) {
			for(EnumFacing facing:EnumFacing.values())
			{
				boolean set=glow.addGlow( world, candidate, facing );
				if(set) break;
			}
		}

		if(world.isRemote)
		{
			int i=3;
			while(i-->0)
			{
				world.spawnParticle(
						EnumParticleTypes.VILLAGER_HAPPY,
						pos.getX() + rand.nextFloat(),
						pos.getY() + rand.nextFloat(),
						pos.getZ() + rand.nextFloat(),
						0,0,0
				);
			}
		}
	}
}
