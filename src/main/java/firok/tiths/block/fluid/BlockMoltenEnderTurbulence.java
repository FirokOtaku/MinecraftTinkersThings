package firok.tiths.block.fluid;

import firok.tiths.util.Actions;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import slimeknights.tconstruct.smeltery.block.BlockTinkerFluid;

/**
 * 末影乱流
 */
public class BlockMoltenEnderTurbulence extends BlockTinkerFluid
{
	public BlockMoltenEnderTurbulence(Fluid fluid)
	{
		super(fluid, Material.WATER);
	}

	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
	{
		super.onEntityCollidedWithBlock(worldIn, pos, state, entityIn);

		if (!worldIn.isRemote && entityIn instanceof EntityLivingBase)
		{
			EntityLivingBase enlb = (EntityLivingBase) entityIn;
			Actions.CauseEnderTeleport(enlb);
		}
	}
}