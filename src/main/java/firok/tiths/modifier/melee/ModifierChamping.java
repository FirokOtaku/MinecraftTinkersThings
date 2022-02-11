package firok.tiths.modifier.melee;

import firok.tiths.util.DevUse;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import static firok.tiths.util.Predicates.canTrigger;

// 强袭
// https://github.com/351768593/MinecraftTinkersThings/blob/indev1122/src/main/java/firok/tiths/traits/TraitChamping.java
@DevUse(isPlaceholder = true)
@Mod.EventBusSubscriber
public class ModifierChamping extends Modifier
{
	public ModifierChamping()
	{
		super(0xffa919);
	}

	/** 新版可能只能这么搞了 */
	@SubscribeEvent
	public static void onCriticalHit(CriticalHitEvent event) {
		PlayerEntity player = event.getPlayer();
		ToolStack tool = ToolStack.from(player.getHeldItemMainhand());

		tool.getModifierList().forEach(modifierEntry -> {
			if (modifierEntry.getModifier() instanceof ModifierChamping) {
				if (canTrigger(player.world, modifierEntry.getLevel() * 0.2)) {
					event.setResult(Event.Result.ALLOW);
				}
			}
		});
	}
}
