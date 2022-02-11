package firok.tiths.modifier.melee;

import firok.tiths.config.ConfigModifier;
import firok.tiths.util.Actions;
import firok.tiths.util.DevUse;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

import static firok.tiths.util.Predicates.canTrigger;

// 吹袭
@DevUse(isPlaceholder = true)
public class ModifierBlowing extends Modifier
{
	public ModifierBlowing()
	{
		super(0x86b0ed);
	}

	@Override
	public float beforeEntityHit(IModifierToolStack tool, int level, ToolAttackContext context, float damage, float baseKnockback, float knockback)
	{
		PlayerEntity player = context.getPlayerAttacker();
		LivingEntity target = context.getLivingTarget();
		if(player == null || target == null) return knockback;

		if (target instanceof PlayerEntity && !ConfigModifier.enable_blow_player.get()) return knockback;

		World world = target.world;
		if (!world.isRemote && canTrigger(world, ConfigModifier.rate_blowing.get()))
		{
			if (ConfigModifier.blocklist_blowing.contains(target.getType())) {return knockback;}

			ItemStack stack2drop;
			stack2drop = target.getHeldItemMainhand();
			if (stack2drop.isEmpty()) // 主手没找到
			{
				stack2drop = target.getHeldItemOffhand();
				target.setHeldItem(Hand.OFF_HAND, ItemStack.EMPTY);
			}
			else if (!stack2drop.isEmpty()) // 主手找到了
			{
				target.setHeldItem(Hand.MAIN_HAND, ItemStack.EMPTY);
			}
			else { // 都没找到
				return knockback;
			}

			// todo play sound
//			world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.effectSwing, SoundCategory.PLAYERS, 1, 1);
			Actions.CauseSpawnItem(target, stack2drop);
		}

		return knockback;
	}
}
