package firok.tiths.effect;

import firok.tiths.util.DevUse;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.util.Random;

import static firok.tiths.util.Predicates.canTick;

// 缠绕
// https://github.com/351768593/MinecraftTinkersThings/blob/5bf9e32008780961f3951e2700317b245a9bf967/src/main/java/firok/tiths/potion/PotionArmorSoftened.java#L9
@DevUse(isPlaceholder = true)
public class EffectRooted extends TithsEffect
{
	public static final String uuidMovementSpeed = "132DB19B-F9FF-487D-98E6-E95DF9B770C5";

	EffectRooted()
	{
		super(EffectType.HARMFUL, 0x644209);
		addAttributesModifier(Attributes.MOVEMENT_SPEED, uuidMovementSpeed, -0.95, AttributeModifier.Operation.MULTIPLY_TOTAL);
		setNoCure();
	}

	@Override
	public boolean isReady(int duration, int level)
	{
		return canTick(duration, 3, 0);
	}

	@Override
	public void performEffect(LivingEntity living, int level)
	{
		if(living.world.isRemote)
		{
			World world = living.world;
			Random rand = world.rand;
			Vector3d vec = living.getPositionVec();
			BlockPos pos = living.getPosition().down();
			BlockState stateStandOn = world.getBlockState(pos);
			if(stateStandOn.isAir()) return;

			world.addOptionalParticle(
					new BlockParticleData(ParticleTypes.BLOCK, stateStandOn).setPos(pos),
					vec.x - 0.2 + rand.nextFloat() * 0.4,
					vec.y + rand.nextFloat() * living.getEyeHeight() * 0.8 + 0.1,
					vec.z - 0.2 + rand.nextFloat() * 0.4,
					0.0D, -0.06D, 0.0D);
		}
	}
}
