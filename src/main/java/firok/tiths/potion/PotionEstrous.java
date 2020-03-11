package firok.tiths.potion;

import firok.tiths.TinkersThings;
import firok.tiths.common.Keys;
import firok.tiths.util.Predicates;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.Random;

// 发情
public class PotionEstrous extends BasePotion
{
	public PotionEstrous()
	{
		super(new ResourceLocation(TinkersThings.MOD_ID,"textures/potions/estrous.png"),true, Keys.colorPotionEstrous);
	}

	@Override
	public boolean isReady(int tick, int level)
	{
		return Predicates.canTick(tick,15,0);
	}

	@Override
	public void performEffect(EntityLivingBase entity, int level)
	{
		if(entity.world.isRemote)
		{
			World world=entity.world;
			Random rand=world.rand;
			world.spawnParticle(EnumParticleTypes.HEART,
					entity.posX-0.2+rand.nextDouble()*0.4,
					entity.posY+entity.getEyeHeight()+0.1,
					entity.posZ-0.2+rand.nextDouble()*0.4,
					0,0.1,0);
		}
	}
}
