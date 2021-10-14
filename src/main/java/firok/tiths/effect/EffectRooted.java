package firok.tiths.effect;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

import static firok.tiths.util.Predicates.canTick;
import static firok.tiths.util.Predicates.canTrigger;

public class EffectRooted extends TithsEffect
{
	public static final String uuidMovementSpeed = "132DB19B-F9FF-487D-98E6-E95DF9B770C5";

	protected EffectRooted()
	{
		super(EffectType.HARMFUL, 0x1, false);
		addAttributesModifier(Attributes.MOVEMENT_SPEED, uuidMovementSpeed, 0.05, AttributeModifier.Operation.MULTIPLY_TOTAL);
	}

	@Override
	public boolean isReady(int duration, int level)
	{
		return canTick(duration, 3, 0);
	}

	@Override
	public void performEffect(LivingEntity living, int level)
	{
		if(!living.isServerWorld())
		{
			World world = living.world;
			Random rand = world.rand;
			Vector3d vec = living.getPositionVec();
			BlockPos pos = living.getPosition().down();
			BlockState stateStandOn = world.getBlockState(pos);

			world.addOptionalParticle(
					new BlockParticleData(ParticleTypes.BLOCK, stateStandOn).setPos(pos),
					vec.x - 0.2 + rand.nextFloat() * 0.4,
					vec.y + rand.nextFloat() * living.getEyeHeight() * 0.8 + 0.1,
					vec.z - 0.2 + rand.nextFloat() * 0.4,
					0.0D, -0.04D, 0.0D);
		}
	}
}
