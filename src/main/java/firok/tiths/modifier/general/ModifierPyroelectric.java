package firok.tiths.modifier.general;

import firok.tiths.DamageSources;
import firok.tiths.config.ConfigModifier;
import firok.tiths.util.Colors;
import firok.tiths.util.DevUse;
import firok.tiths.util.EntityFinders;
import firok.tiths.util.Predicates;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

import java.util.List;

/**
 * 热释电
 *
 * https://github.com/351768593/MinecraftTinkersThings/blob/indev1122/src/main/java/firok/tiths/traits/TraitPyroelectric.java
 */
@DevUse
public class ModifierPyroelectric extends Modifier
{
	public ModifierPyroelectric()
	{
		super(Colors.Navy);
	}

	@Override
	public int afterEntityHit(IModifierToolStack tool, int level, ToolAttackContext context, float damageDealt)
	{
		LivingEntity target = context.getLivingTarget();
		LivingEntity holder = context.getAttacker();
		if(target == null) return 0;

		BlockPos pos = target.getPosition();
		World world = target.world;
		float temp = world.getBiome(pos).getTemperature(pos);
		if(target.isBurning()) temp += 0.3; // new flavor

		if(temp < 0.15) return 0;

		final float damage = temp * ConfigModifier.factor_pyroelectric_damage.get().floatValue() + 0.35f;

		List<Entity> list = EntityFinders.Nearby(target, 2, Predicates::isLivingAlive);
		if(!world.isRemote) // server - deal damage
		{
			for(Entity en : list)
			{
				if(en == holder) continue;

				LivingEntity living = (LivingEntity) en;
				living.hurtResistantTime = 0;
				living.attackEntityFrom(DamageSources.Pyroelectric(holder), damage);
			}
		}
		else // client - play sound
		{
			// todo play sound
		}

		return list.size();
	}
}
