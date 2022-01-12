package firok.tiths.effect;

import firok.tiths.util.DevUse;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.AttributeModifierManager;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.potion.EffectType;

import static firok.tiths.util.Predicates.canTick;

// 天神下凡
// https://github.com/351768593/MinecraftTinkersThings/blob/5bf9e32008780961f3951e2700317b245a9bf967/src/main/java/firok/tiths/potion/PotionAvatar.java#L12
@DevUse(isPlaceholder = true)
public class EffectAvatar extends TithsEffect
{
	public static final String UUID_MOVEMENT_SPEED = "419C373C-57F9-4D56-817C-EB9FA4C34B35";
	public static final String UUID_MAX_HEALTH = "A0C8A9DC-8174-47A0-9B0F-DCFE414E0678";
	public static final String UUID_ARMOR = "BF25C13E-AB14-4945-B799-F46C965104D5";
	public static final String UUID_ARMOR_TOUGHNESS = "A35A5104-0FAF-4536-9A4D-685D26EFC525";
	public static final String UUID_ATTACK_DAMAGE = "14A0A898-78E1-4086-803D-B2DD23584F48";
	EffectAvatar()
	{
		super(EffectType.BENEFICIAL, 0xffeb11);
		addAttributesModifier(Attributes.MOVEMENT_SPEED, UUID_MOVEMENT_SPEED, -0.2, AttributeModifier.Operation.MULTIPLY_TOTAL);
		addAttributesModifier(Attributes.MAX_HEALTH, UUID_MAX_HEALTH, 20, AttributeModifier.Operation.ADDITION);
		addAttributesModifier(Attributes.ARMOR, UUID_ARMOR, 10, AttributeModifier.Operation.ADDITION);
		addAttributesModifier(Attributes.ARMOR_TOUGHNESS, UUID_ARMOR_TOUGHNESS, 10, AttributeModifier.Operation.ADDITION);
		addAttributesModifier(Attributes.ATTACK_DAMAGE, UUID_ATTACK_DAMAGE, 10, AttributeModifier.Operation.ADDITION);
	}

	@Override
	public boolean isReady(int duration, int level)
	{
		return canTick(duration, 4, 0);
	}

	@Override
	public void performEffect(LivingEntity living, int level)
	{
		living.heal( 4 + level );
	}

	@Override
	public void removeAttributesModifiersFromEntity(LivingEntity entityLivingBaseIn, AttributeModifierManager attributeMapIn, int amplifier) {
		super.removeAttributesModifiersFromEntity(entityLivingBaseIn, attributeMapIn, amplifier);
		if (entityLivingBaseIn.getHealth() > entityLivingBaseIn.getMaxHealth()) {
			entityLivingBaseIn.setHealth(entityLivingBaseIn.getMaxHealth());
		}

	}
}
