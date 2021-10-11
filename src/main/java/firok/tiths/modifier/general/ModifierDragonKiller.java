package firok.tiths.modifier.general;

import firok.tiths.DamageSources;
import firok.tiths.config.ConfigModifier;
import firok.tiths.util.DevUse;
import net.minecraft.entity.LivingEntity;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

// 屠龙者
// https://github.com/351768593/MinecraftTinkersThings/blob/indev1122/src/main/java/firok/tiths/traits/TraitDragonKiller.java
@DevUse(isPlaceholder = true)
public class ModifierDragonKiller extends Modifier
{
	public ModifierDragonKiller()
	{
		super(0x9100f3);
	}

	@Override
	public int afterEntityHit(IModifierToolStack tool, int level, ToolAttackContext context, float damageDealt)
	{
		LivingEntity target = context.getLivingTarget();
		if (damageDealt > 0 && target != null)
		{
			String name = target.getName().getString();

			if (target.getHealth() > 0 && name.contains("dragon"))
			{
				target.hurtResistantTime = 0;
				target.attackEntityFrom(
						DamageSources.DragonKiller(context.getAttacker()),
						(float) (ConfigModifier.factor_dragon_killer_damage_base.get() + damageDealt * ConfigModifier.factor_dragon_killer_damage_percent.get())
				);
			}
		}
		return 0;
	}
}
