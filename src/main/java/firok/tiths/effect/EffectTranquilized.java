package firok.tiths.effect;

import firok.tiths.util.DevUse;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.potion.EffectType;

// 镇定
// https://github.com/351768593/MinecraftTinkersThings/blob/indev1122/src/main/java/firok/tiths/potion/PotionTranquilized.java
// 给怪物降低索敌距离 对怪物来说是debuff
@DevUse(isPlaceholder = true)
public class EffectTranquilized extends TithsEffect
{
	public static final String uuidFollowRange = "8D2B5640-7D56-4FEC-B338-4FF8639B2BCA";
	public EffectTranquilized()
	{
		super(EffectType.HARMFUL, 0xedf02c);
		addAttributesModifier(Attributes.FOLLOW_RANGE, uuidFollowRange, 0.125, AttributeModifier.Operation.MULTIPLY_BASE);
	}
}
