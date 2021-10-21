package firok.tiths.effect;

import firok.tiths.util.DevUse;
import firok.tiths.util.Predicates;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectType;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.util.Random;

// 发情
// https://github.com/351768593/MinecraftTinkersThings/blob/5bf9e32008780961f3951e2700317b245a9bf967/src/main/java/firok/tiths/potion/PotionEstrous.java#L14
@DevUse(isPlaceholder = true)
public class EffectEstrous extends TithsEffect
{
	public EffectEstrous()
	{
		super(EffectType.BENEFICIAL, 0xEF94F5);
	}

	@Override
	public boolean isReady(int duration, int level)
	{
		return Predicates.canTick(duration, 15, 0);
	}

	@Override
	public void performEffect(LivingEntity living, int level)
	{
		World world = living.world;
		if(!world.isRemote) {return;}
		Random rand = world.rand;

		Vector3d pos = living.getPositionVec();
		world.addParticle(
				ParticleTypes.HEART,
				pos.x - 0.2 + rand.nextDouble() * 0.4,
				pos.y + living.getEyeHeight() + 0.1,
				pos.z - 0.2 + rand.nextDouble() * 0.4,
				0, 0.1, 1
		);
	}
}
