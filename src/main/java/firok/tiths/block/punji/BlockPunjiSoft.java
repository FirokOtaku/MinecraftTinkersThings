package firok.tiths.block.punji;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockPunjiSoft extends BlockPunjiCustom
{
	public BlockPunjiSoft()
	{
		super(Material.CLOTH, SoundType.CLOTH);
	}

	public void hurtLiving(World world, BlockPos pos, IBlockState state, EntityLivingBase enlb)
	{
		enlb.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 20, 1));
	}
}
