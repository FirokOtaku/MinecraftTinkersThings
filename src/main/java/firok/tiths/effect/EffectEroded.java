package firok.tiths.effect;

import firok.tiths.util.DevUse;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.potion.EffectType;

/**
 * 侵蚀
 *
 * https://github.com/351768593/MinecraftTinkersThings/blob/5bf9e32008780961f3951e2700317b245a9bf967/src/main/java/firok/tiths/potion/PotionEroded.java#L11
 */
@DevUse
public class EffectEroded extends TithsEffect
{
	public static final String uuidMaxHealth = "86B681A7-2268-409D-A8E3-07ECF49CD905";
	EffectEroded()
	{
		super(EffectType.HARMFUL, 0x8a0d82);
		addAttributesModifier(Attributes.ARMOR, uuidMaxHealth, -1, AttributeModifier.Operation.ADDITION);
	}

	@Override
	public boolean isReady(int tick, int level)
	{
		return true;
	}

	@Override
	public void performEffect(LivingEntity entity, int level)
	{
		if(!entity.isServerWorld()) return;

		entity.setHealth( entity.getHealth() ); // 刷新血量, 避免0血不死亡情况
	}
}
