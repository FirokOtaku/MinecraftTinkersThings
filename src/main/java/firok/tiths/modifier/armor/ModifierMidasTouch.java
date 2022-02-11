package firok.tiths.modifier.armor;

import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.util.DamageSource;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.library.modifiers.impl.TotalArmorLevelModifier;
import slimeknights.tconstruct.library.tools.capability.TinkerDataCapability;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.helper.ModifierUtil;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

/**
 * 迈达斯之触
 *
 * 有几率将物理投射伤害抵消转化为金粒
 */
public class ModifierMidasTouch extends TotalArmorLevelModifier
{
	private static final TinkerDataCapability.TinkerDataKey<Integer> LEVELS = TConstruct.createKey("midas_touch");
	public ModifierMidasTouch()
	{
		super(0, LEVELS);
	}

	public static boolean canDealWith(DamageSource ds)
	{
		return !ds.isMagicDamage() && !ds.isUnblockable() && !ds.isFireDamage();
	}

	@Override
	public float getProtectionModifier(IModifierToolStack tool, int level, EquipmentContext context, EquipmentSlotType slotType, DamageSource source, float modifierValue)
	{
		return canDealWith(source) ? modifierValue + 2 : 0;
	}

	@Override
	public boolean isSourceBlocked(IModifierToolStack tool, int level, EquipmentContext context, EquipmentSlotType slotType, DamageSource source, float amount)
	{
		if(canDealWith(source))
		{
			LivingEntity entity = context.getEntity();
			int levelTotal = ModifierUtil.getTotalModifierLevel(entity, LEVELS);
			System.out.printf("level %d, total level %d",level,levelTotal);

			return true;
		}
		return false;
	}
}
