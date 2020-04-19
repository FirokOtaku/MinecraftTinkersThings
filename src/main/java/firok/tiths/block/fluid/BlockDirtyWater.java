package firok.tiths.block.fluid;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import slimeknights.tconstruct.smeltery.block.BlockTinkerFluid;

import static firok.tiths.util.Predicates.canTick;

/**
 * 污水
 */
public class BlockDirtyWater extends BlockTinkerFluid
{
	public BlockDirtyWater(Fluid fluid)
	{
		super(fluid, Material.WATER);
	}

	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
	{
		super.onEntityCollidedWithBlock(worldIn, pos, state, entityIn);
		if(canTick(worldIn,4,0) && entityIn instanceof EntityLivingBase)
		{
			EntityLivingBase enlb=(EntityLivingBase)entityIn;
			enlb.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS,400,0));
		}
	}
}
