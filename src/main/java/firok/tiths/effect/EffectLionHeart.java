package firok.tiths.effect;

import firok.tiths.util.DevUse;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.potion.EffectType;

/**
 * 狮心
 * https://github.com/351768593/MinecraftTinkersThings/blob/5bf9e32008780961f3951e2700317b245a9bf967/src/main/java/firok/tiths/potion/PotionLionheart.java#L13
 */
@DevUse(isPlaceholder = true)
public class EffectLionHeart extends TithsEffect
{
	public static final String uuidArmor = "FEFC6FA3-67FF-40F0-9C29-FF40149DB959";
	EffectLionHeart()
	{
		super(EffectType.BENEFICIAL, 0xe3041f);
		addAttributesModifier(Attributes.ARMOR, uuidArmor, 0.4, AttributeModifier.Operation.MULTIPLY_TOTAL);
	}
}
