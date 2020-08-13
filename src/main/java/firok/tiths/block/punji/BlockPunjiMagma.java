package firok.tiths.block.punji;

import firok.tiths.common.DamageSources;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockPunjiMagma extends BlockPunjiCustom
{
	public BlockPunjiMagma()
	{
		super(Material.ROCK, SoundType.GROUND);
		this.setLightLevel(0.2f);
	}

	@Override
	public void hurtLiving(World world, BlockPos pos, IBlockState state, EntityLivingBase enlb)
	{
		float damage = 3f;
		if(enlb.fallDistance > 0) {
			damage += enlb.fallDistance * 1.5f + 2f;
		}
		enlb.attackEntityFrom(DamageSources.PunjiDamage, damage);
		enlb.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 20, 1));
	}
}
