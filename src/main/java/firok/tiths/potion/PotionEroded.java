package firok.tiths.potion;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;

import static firok.tiths.common.Keys.*;

/**
 * 侵蚀
 */
public class PotionEroded extends BasePotion
{
	public PotionEroded()
	{
		super(icon("eroded"),true,colorPotionEroded);
	}

	@Override
	public boolean isReady(int tick, int level)
	{
		return true;
	}

	@Override
	public void performEffect(EntityLivingBase entity, int level)
	{
		if(entity.world.isRemote) return;

		entity.setHealth( entity.getHealth() ); // 刷新血量, 避免0血不死亡情况
	}

	@Override
	public void postInit()
	{
		registerPotionAttributeModifier(
				SharedMonsterAttributes.MAX_HEALTH,
				uuidPotionErodedMaxHealth,
				-1,
				0
		);
	}
}
