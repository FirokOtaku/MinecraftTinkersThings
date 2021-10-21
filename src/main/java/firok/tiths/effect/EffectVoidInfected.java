package firok.tiths.effect;

import firok.tiths.util.DevUse;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.potion.EffectType;

// 虚空侵蚀
// https://github.com/351768593/MinecraftTinkersThings/blob/5bf9e32008780961f3951e2700317b245a9bf967/src/main/java/firok/tiths/potion/PotionVoidInfected.java#L11
@DevUse(isPlaceholder = true)
public class EffectVoidInfected extends TithsEffect
{
	public static final String UUID_MAX_HEALTH = "12CE48F3-561E-4433-A31C-791850821665";
	public EffectVoidInfected()
	{
		super(EffectType.HARMFUL, 0x4a0964, false);
		addAttributesModifier(Attributes.MAX_HEALTH, UUID_MAX_HEALTH, -0.3, AttributeModifier.Operation.MULTIPLY_TOTAL);
	}
}
