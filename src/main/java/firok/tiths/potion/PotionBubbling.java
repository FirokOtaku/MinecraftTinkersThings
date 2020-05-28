package firok.tiths.potion;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

import java.util.Random;

import static firok.tiths.util.Predicates.canTick;

/**
 * 发泡
 */
public class PotionBubbling extends BasePotion
{
	public PotionBubbling()
	{
		super(icon("bubbling"),false,0x86b0ed);
	}

	@Override
	public boolean isReady(int tick, int level)
	{
		return true;
	}

	@Override
	public void performEffect(EntityLivingBase entity, int level)
	{
		World world=entity.world;
		if(entity.isInWater())
		{
			entity.motionY+=0.05;

			int air=entity.getAir();
			if(air<=298 && canTick(world,4,0) )
			{
				entity.setAir(air+2);
			}
			if(world.isRemote && canTick(world,4,0))
			{
				Random rand=world.rand;
				for(int i=0;i<2;i++)
				{
					world.spawnParticle(EnumParticleTypes.WATER_BUBBLE,
							entity.posX + rand.nextFloat() * 0.8 -0.4,
							entity.posY + rand.nextFloat() * 0.8 -0.4,
							entity.posZ + rand.nextFloat() * 0.8 -0.4,
							rand.nextFloat() * 0.2,
							rand.nextFloat() * 0.8,
							rand.nextFloat() * 0.2
							);
				}
			}
		}
	}
}
