package firok.tiths.block;

import net.minecraft.block.BlockSoulSand;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static firok.tiths.common.DamageSources.SanDamage;

// 血沙
public class BlockBloodSand extends BlockSoulSand
{
	@Override
	public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity)
	{
		super.onEntityCollidedWithBlock(world, pos, state, entity);

		if(!world.isRemote && entity instanceof EntityLivingBase && entity.isEntityAlive())
		{
			EntityLivingBase enlb=(EntityLivingBase)entity;
			enlb.attackEntityFrom(SanDamage,2);
		}
	}
}
