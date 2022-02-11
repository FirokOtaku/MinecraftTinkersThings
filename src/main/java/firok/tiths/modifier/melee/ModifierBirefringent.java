package firok.tiths.modifier.melee;

import firok.tiths.DamageSources;
import firok.tiths.util.Colors;
import firok.tiths.util.DevUse;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

import static firok.tiths.util.Predicates.canTrigger;

// 双折射 (双折)
@DevUse(isPlaceholder = true)
public class ModifierBirefringent extends Modifier
{
	public ModifierBirefringent()
	{
		super(Colors.Indigo);
	}

	@Override
	public int afterEntityHit(IModifierToolStack tool, int level, ToolAttackContext context, float damageDealt)
	{
		PlayerEntity player = context.getPlayerAttacker();
		if(player == null) return 0;
		LivingEntity target = context.getLivingTarget();
		if(target == null) return 0;

		World world = player.world;

		if(damageDealt > 0 && player.isServerWorld() && target.isAlive() && canTrigger(world, 0.2))
		{
			target.hurtResistantTime=0;
			target.attackEntityFrom(DamageSources.Birefringent(player),damageDealt);
		}
		return 0;
	}
}
