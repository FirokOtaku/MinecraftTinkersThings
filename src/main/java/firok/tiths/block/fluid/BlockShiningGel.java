package firok.tiths.block.fluid;

import firok.tiths.common.Potions;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.smeltery.block.BlockTinkerFluid;

import javax.annotation.Nonnull;
import java.util.Random;

import static firok.tiths.util.Predicates.canTrigger;

/**
 * 闪光凝胶
 */
public class BlockShiningGel extends BlockTinkerFluid
{
	public BlockShiningGel(Fluid fluid)
	{
		super(fluid, Material.WATER);
		this.setLightLevel(1);
		this.setLightOpacity(0);
		this.setTickRandomly(true);
	}

	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
	{
		super.onEntityCollidedWithBlock(worldIn, pos, state, entityIn);

		if (!worldIn.isRemote && entityIn instanceof EntityLivingBase)
		{
			((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(Potions.illuminating,1200,0));
		}
	}

	@Override
	public void updateTick(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull Random rand)
	{
//		if(world!=null && world.isRemote)
//		{
//			int i=2;
//			while(i-->0)
//			{
//				world.spawnParticle(
//						EnumParticleTypes.CRIT,
//						pos.getX(),pos.getY(),pos.getZ(),
//						0,1,0
//				);
//			}
//		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
	{
		if(worldIn!=null && pos!=null && rand!=null && canTrigger(rand,0.4))
		{
			worldIn.spawnParticle(
					EnumParticleTypes.CRIT,
					pos.getX() + rand.nextFloat(),
					pos.getY() + rand.nextFloat(),
					pos.getZ() + rand.nextFloat(),
					0,0.5,0
			);
		}
	}
}
