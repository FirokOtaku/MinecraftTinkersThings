package firok.tiths.modifier.harvest;

import firok.tiths.util.Colors;
import firok.tiths.util.DevUse;
import firok.tiths.util.Predicates;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraftforge.event.entity.player.PlayerEvent;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

/**
 * 绿意
 *
 * https://github.com/351768593/MinecraftTinkersThings/blob/indev1122/src/main/java/firok/tiths/traits/TraitGreen.java
 * */
@DevUse
public class ModifierGreen extends Modifier
{
	public ModifierGreen()
	{
		super(Colors.Green);
	}

	@Override
	public void onBreakSpeed(IModifierToolStack tool, int level, PlayerEvent.BreakSpeed event, Direction sideHit, boolean isEffective, float miningSpeedModifier)
	{
		BlockState state = event.getState();
		if(Predicates.isPlant(state))
			event.setCanceled(true);
	}
}
