package firok.tiths.effect;

import firok.tiths.util.Colors;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.potion.EffectType;

/**
 * 墨染
 */
public class EffectInky extends TithsEffect
{
	public static final String uuidMovementSpeed = "6C4BBA0D-FB71-4E24-B6AA-F8EF8FDAF0CB";
	public static final String uuidAttackSpeed = "F193CD5D-D1F6-4401-B31F-D10506D5A967";
	public static final String uuidFollowRange = "2F0CD4E2-E567-4AA2-8D1D-33082EE62CBF";
	public EffectInky()
	{
		super(EffectType.HARMFUL, Colors.Black);
		addAttributesModifier(Attributes.MOVEMENT_SPEED, uuidMovementSpeed, -0.05, AttributeModifier.Operation.MULTIPLY_BASE);
		addAttributesModifier(Attributes.ATTACK_SPEED, uuidAttackSpeed, -0.05, AttributeModifier.Operation.MULTIPLY_BASE);
		addAttributesModifier(Attributes.FOLLOW_RANGE, uuidFollowRange, 0.25, AttributeModifier.Operation.MULTIPLY_TOTAL);
	}
}
