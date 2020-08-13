package firok.tiths.block.punji;

import firok.tiths.common.DamageSources;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockPunjiIron extends BlockPunjiCustom
{
	public BlockPunjiIron()
	{
		super(Material.IRON, SoundType.METAL);
	}

	@Override
	public void hurtLiving(World world, BlockPos pos, IBlockState state, EntityLivingBase enlb)
	{
		float damage = 5f;
		if(enlb.fallDistance > 0) {
			damage += enlb.fallDistance * 1.5f + 2f;
		}
		enlb.attackEntityFrom(DamageSources.PunjiDamage, damage);
	}
}
