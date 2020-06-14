package firok.tiths.potion;

import firok.tiths.common.Blocks;
import firok.tiths.common.Keys;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import slimeknights.tconstruct.shared.block.BlockGlow;

import static firok.tiths.util.Predicates.canTick;

/**
 * 灯明
 */
public class PotionIlluminating extends BasePotion
{
	public PotionIlluminating()
	{
		super(icon("illuminating"),false, Keys.colorPotionIlluminating);
	}

	@Override
	public boolean isReady(int tick, int level)
	{
		return canTick(tick,5,0);
	}

	@Override
	public void performEffect(EntityLivingBase entity, int level)
	{
		World world=entity.world;
		if(!world.isRemote) {

			BlockPos pos = entity.getPosition();
			for(BlockPos candidate : new BlockPos[]{pos, pos.up(), pos.north(), pos.east(), pos.south(), pos.west(), pos.down()}) {
				// addGlow tries all directions if the passed one doesn't work
				if(((BlockGlow)Blocks.blockIcelitGlow).addGlow(world, candidate, EnumFacing.values()[world.rand.nextInt(6)])) {
					return;
				}
			}
		}
	}
}
