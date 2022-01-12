package firok.tiths.effect;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.potion.EffectType;

/**
 * 特性 - 振奋 (隐藏状态)
 */
public class EffectHyper extends TithsEffect
{
	public static final String uuidMovementSpeed = "8324C68C-9840-4433-B613-7C1FE5CFBA8C";
	EffectHyper()
	{
		super(EffectType.BENEFICIAL, 0xa022ff, false, true);
		addAttributesModifier(Attributes.MOVEMENT_SPEED, uuidMovementSpeed, 0.05, AttributeModifier.Operation.MULTIPLY_TOTAL);
	}
}
