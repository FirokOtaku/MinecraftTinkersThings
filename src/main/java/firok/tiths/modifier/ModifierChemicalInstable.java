package firok.tiths.modifier;

import firok.tiths.config.ConfigModifier;
import firok.tiths.util.DevUse;
import firok.tiths.util.Predicates;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.Explosion;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.context.ToolHarvestContext;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

// 化学不稳定
// https://github.com/351768593/MinecraftTinkersThings/blob/indev1122/src/main/java/firok/tiths/traits/TraitChemicalInstable.java
@DevUse(isPlaceholder = true)
public class ModifierChemicalInstable extends Modifier
{
	public ModifierChemicalInstable()
	{
		super(0xedde31);
	}

	public static void boom(LivingEntity player, double posX, double posY, double posZ)
	{
		player.addPotionEffect(new EffectInstance(Effects.RESISTANCE,10,1));
		player.world.createExplosion(null,posX,posY,posZ,
				(float) (double) ConfigModifier.factor_chemical_instable.get(),
				false, Explosion.Mode.BREAK);
	}
	public static void boom(LivingEntity player, LivingEntity target)
	{
		boom(player,target.getPosX(),target.getPosY(),target.getPosZ());
	}

	@Override
	public int afterEntityHit(IModifierToolStack tool, int level, ToolAttackContext context, float damageDealt)
	{
//		if(Predicates.canTrigger(context.getPlayerAttacker().world, ))
		return 0;
	}

	@Override
	public void afterBlockBreak(IModifierToolStack tool, int level, ToolHarvestContext context)
	{

	}
}
