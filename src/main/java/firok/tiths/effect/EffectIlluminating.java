package firok.tiths.effect;

import firok.tiths.block.TithsBlocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import slimeknights.tconstruct.shared.block.GlowBlock;

import static firok.tiths.util.Predicates.canTick;

/**
 * 灯明
 * https://github.com/351768593/MinecraftTinkersThings/blob/indev1122/src/main/java/firok/tiths/potion/PotionIlluminating.java
 */
public class EffectIlluminating extends TithsEffect
{
	EffectIlluminating()
	{
		super(EffectType.BENEFICIAL, 0xebf756);
	}

	@Override
	public boolean isReady(int duration, int level)
	{
		return canTick(duration, 5, 0);
	}

	@Override
	public void performEffect(LivingEntity living, int level)
	{
		World world = living.world;
		if(world.isRemote) return;

		BlockPos pos = living.getPosition();
		for(BlockPos candidate : new BlockPos[] { pos, pos.up(), pos.north(), pos.east(), pos.south(), pos.west(), pos.down() } )
		{
			// addGlow tries all directions if the passed one doesn't work
			if(!world.isAirBlock(candidate)) continue;
			GlowBlock block = TithsBlocks.BLOCK_ICELIT_GLOW.get();

			if(block.addGlow(world, candidate, Direction.values()[world.rand.nextInt(6)]))
				return;
		}
	}
}
