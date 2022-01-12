package firok.tiths.modifier.general;

import firok.tiths.config.ConfigModifier;
import firok.tiths.effect.TithsEffects;
import firok.tiths.util.Colors;
import firok.tiths.util.DevUse;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.Direction;
import net.minecraftforge.event.entity.player.PlayerEvent;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

/**
 * 墨染
 *
 * todo 允许用物品给工具砸modifier
 * https://github.com/351768593/MinecraftTinkersThings/blob/indev1122/src/main/java/firok/tiths/traits/TraitInky.java
 * */
@DevUse
public class ModifierInky extends Modifier
{
	public ModifierInky()
	{
		super(Colors.Black);
	}

	@Override
	public int afterEntityHit(IModifierToolStack tool, int level, ToolAttackContext context, float damageDealt)
	{
		LivingEntity target = context.getLivingTarget();
		if(target != null)
		{
			target.addPotionEffect(new EffectInstance(TithsEffects.EFFECT_INKY.get(), ConfigModifier.factor_inky.get(), 0));
		}
		return 0;
	}

	@Override
	public void onBreakSpeed(IModifierToolStack tool, int level, PlayerEvent.BreakSpeed event, Direction sideHit, boolean isEffective, float miningSpeedModifier)
	{
		event.setNewSpeed(event.getNewSpeed() * 0.9f);
	}

	// todo 降低攻速
}
