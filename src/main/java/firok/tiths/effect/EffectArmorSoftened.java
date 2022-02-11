package firok.tiths.effect;

import firok.tiths.util.DevUse;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.potion.EffectType;

// 护甲软化
// https://github.com/351768593/MinecraftTinkersThings/blob/5bf9e32008780961f3951e2700317b245a9bf967/src/main/java/firok/tiths/potion/PotionArmorSoftened.java#L9
@DevUse(isPlaceholder = true)
public class EffectArmorSoftened extends TithsEffect
{
	public static final String UUID_ARMOR = "D3CD5FAC-C690-4E07-B2DB-1151313AFDB3";
	public static final String UUID_ARMOR_TOUGHNESS = "FF99DA03-A7E7-4DEB-8595-D7B4274C9B28";
	EffectArmorSoftened()
	{
		super(EffectType.HARMFUL, 0x644209);
		addAttributesModifier(Attributes.ARMOR, UUID_ARMOR, -0.2, AttributeModifier.Operation.MULTIPLY_TOTAL);
		addAttributesModifier(Attributes.ARMOR_TOUGHNESS, UUID_ARMOR_TOUGHNESS, -0.2, AttributeModifier.Operation.MULTIPLY_TOTAL);
		setNoCure();
	}
}
