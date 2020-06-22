package firok.tiths.block.fluid;

import firok.tiths.common.Potions;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import slimeknights.tconstruct.smeltery.block.BlockTinkerFluid;

/**
 * 闪光凝胶
 */
public class BlockShiningGel extends BlockTinkerFluid
{
	public BlockShiningGel(Fluid fluid)
	{
		super(fluid, Material.WATER);
		this.setLightLevel(1);
	}

	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
	{
		super.onEntityCollidedWithBlock(worldIn, pos, state, entityIn);

		if (!worldIn.isRemote && entityIn instanceof EntityLivingBase)
		{
			((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(Potions.illuminating,100,0));
		}
	}
}
