package firok.tiths.effect;

import firok.tiths.util.DevUse;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.potion.EffectType;

// 麻痹
// https://github.com/351768593/MinecraftTinkersThings/blob/indev1122/src/main/java/firok/tiths/potion/PotionParalysed.java
@DevUse(isPlaceholder = true)
public class EffectParalysed extends TithsEffect
{
	public static final String UUID_MOVEMENT_SPEED = "E90BFF57-C4CB-419C-939C-666D1F93C1E1";
	public static final String UUID_ATTACK_DAMAGE = "6A871942-58FD-4351-94BD-A9B216E8CC0C";
	EffectParalysed()
	{
		super(EffectType.BENEFICIAL, 0xdbab23);
		addAttributesModifier(Attributes.MOVEMENT_SPEED, UUID_MOVEMENT_SPEED, -0.5, AttributeModifier.Operation.MULTIPLY_TOTAL);
		addAttributesModifier(Attributes.ATTACK_DAMAGE, UUID_ATTACK_DAMAGE, -0.5, AttributeModifier.Operation.MULTIPLY_TOTAL);
	}
}
