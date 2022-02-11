package firok.tiths.modifier.melee;

import firok.tiths.util.DevUse;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

import java.util.Collection;
import java.util.Optional;

/**
 * 锥心
 *
 * https://github.com/351768593/MinecraftTinkersThings/blob/indev1122/src/main/java/firok/tiths/traits/TraitPiercing.java
 */
@DevUse
public class ModifierPiercing extends Modifier
{
	public ModifierPiercing()
	{
		super(0);
	}

	@Override
	public float getEntityDamage(IModifierToolStack tool, int level, ToolAttackContext context, float baseDamage, float damage)
	{
		int count = 0;
		LivingEntity target = context.getLivingTarget();
		if(target != null)
		{
			Collection<EffectInstance> listEffect =  target.getActivePotionEffects();
			for(EffectInstance ei : listEffect)
			{
				if(ei.getPotion().getEffectType() == EffectType.HARMFUL)
					count++;
			}
		}
		return damage + count * 0.2f;
	}
}
