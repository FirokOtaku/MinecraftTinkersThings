package firok.tiths.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
/**
 * 虫苔
 */
public class BlockCreepSoil extends BlockCompressed
{
	public BlockCreepSoil()
	{
		super(Material.GROUND);
		this.setHardness(3.0f);

		this.setSoundType(SoundType.SAND);

		setHarvestLevel("shovel", -1);
	}

	@Override
	public void onEntityWalk(World worldIn, BlockPos pos, Entity entity)
	{
		boolean isArthropod=false;
		if(entity instanceof EntityLivingBase)
		{
			EntityLivingBase enlb=(EntityLivingBase)entity;
			if(enlb.getCreatureAttribute() == EnumCreatureAttribute.ARTHROPOD)
			{
				enlb.heal(1);
				isArthropod=true;
			}
		}
		if(!isArthropod)
		{
			entity.motionX *= 0.9;
			entity.motionZ *= 0.9;
		}
	}

	@Override
	public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, IPlantable plantable)
	{
		return true;
	}

	@Override
	public boolean canEntitySpawn(IBlockState state, Entity entity)
	{
		// 禁止非节肢生物的生成
		return entity instanceof EntityLivingBase && ((EntityLivingBase)entity).getCreatureAttribute() == EnumCreatureAttribute.ARTHROPOD;
	}
}
