package firok.tiths.modifier;

import firok.tiths.util.DevUse;
import net.minecraft.entity.player.PlayerEntity;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

import static firok.tiths.util.Predicates.canTrigger;

// 强袭
// https://github.com/351768593/MinecraftTinkersThings/blob/indev1122/src/main/java/firok/tiths/traits/TraitChamping.java
// fixme 原本这个地方是需要修改暴击率 新版暂时没找到怎么改
@DevUse(isPlaceholder = true)
public class ModifierChamping extends Modifier
{
	public ModifierChamping()
	{
		super(0xffa919);
	}

	@Override
	public float getEntityDamage(IModifierToolStack tool, int level, ToolAttackContext context, float baseDamage, float damage)
	{
		PlayerEntity player = context.getPlayerAttacker();
		if(player == null) return damage;

		boolean isCritical = canTrigger(player.world, level * 0.2);
		return isCritical ? ( damage + damage / 2 ) : damage;
	}
}
