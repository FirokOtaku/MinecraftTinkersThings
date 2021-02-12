package firok.tiths.traits;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.events.ProjectileEvent;
import slimeknights.tconstruct.library.traits.AbstractProjectileTrait;

import java.util.Random;

import static firok.tiths.common.Keys.colorTraitNetting;
import static firok.tiths.common.Keys.nameTraitNetting;

public class TraitNetting extends AbstractProjectileTrait implements IHitBlockProjectile
{
	public TraitNetting()
	{
		super(nameTraitNetting,colorTraitNetting);
	}

	@Override
	public float damage(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float newDamage, boolean isCritical)
	{
		return 0;
	}

	public static void setWeb(World world,BlockPos pos)
	{
		IBlockState stateWeb = Blocks.WEB.getDefaultState();

		if(world.isAirBlock(pos))
		{
			world.setBlockState(pos,stateWeb);
		}
		else
		{
			BlockPos posUp = pos.up();
			if(world.isAirBlock(posUp))
			{
				world.setBlockState(posUp,stateWeb);
			}
		}
	}

	@Override
	public void onHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, boolean isCritical)
	{
		if(!target.world.isRemote)
		{
			setWeb(target.world,target.getPosition());
		}
	}

	@Override
	public void hitBlock(ProjectileEvent.OnHitBlock event, BlockPos pos, World world, IBlockState state, Random rand, ItemStack stackAmmo, Entity shootingEntity) throws Exception
	{
		if(event.projectile != null)
		{
			event.projectile.setDead();
		}

		if(!world.isRemote)
		{
			setWeb(world,pos);
		}
	}
}
