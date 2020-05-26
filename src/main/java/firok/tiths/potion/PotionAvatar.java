package firok.tiths.potion;

import firok.tiths.TinkersThings;
import firok.tiths.common.Keys;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.ResourceLocation;

import static firok.tiths.common.Keys.*;

// 天神下凡
public class PotionAvatar extends BasePotion
{
	public PotionAvatar()
	{
		super(icon("avatar"),false, Keys.colorPotionAvatar);
	}

	@Override
	public boolean isReady(int tick, int level)
	{
		return tick%4==0;
	}

	@Override
	public void performEffect(EntityLivingBase entity, int level)
	{
		entity.heal(4);
	}

	/**
	 * @see firok.tiths.common.Potions
	 */
	@Override
	public void postInit()
	{
		registerPotionAttributeModifier(
				SharedMonsterAttributes.MOVEMENT_SPEED,
				uuidPotionAvatarSpeed,
				0.8D,
				2)
		.registerPotionAttributeModifier(
				SharedMonsterAttributes.MAX_HEALTH,
				uuidPotionAvatarMaxHealth,
				20,
				0
		)
		.registerPotionAttributeModifier(
				SharedMonsterAttributes.ARMOR,
				uuidPotionAvatarArmor,
				10,
				0
		)
		.registerPotionAttributeModifier(
				SharedMonsterAttributes.ARMOR_TOUGHNESS,
				uuidPotionAvatarArmorToughness,
				10,
				0
		)
		.registerPotionAttributeModifier(
				SharedMonsterAttributes.ATTACK_DAMAGE,
				uuidPotionAvatarAttackDamage,
				10,
				0
		)
		.setBeneficial();
	}
}
