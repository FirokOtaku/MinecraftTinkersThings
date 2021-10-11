package firok.tiths.modifier.general;

import firok.tiths.util.Calculates;
import firok.tiths.util.DevUse;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerEvent;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

// 二色性
// https://github.com/351768593/MinecraftTinkersThings/blob/indev1122/src/main/java/firok/tiths/traits/TraitDichroic.java
@DevUse(isPlaceholder = true)
public class ModifierDichroic extends Modifier
{
	public ModifierDichroic()
	{
		super(0x536f9e);
	}

	final int light_mid = 7;
	final float fac_light = 35;

	@Override
	public void onBreakSpeed(IModifierToolStack tool, int level, PlayerEvent.BreakSpeed event, Direction sideHit, boolean isEffective, float miningSpeedModifier)
	{
		PlayerEntity player = event.getPlayer();
		World world = player.world;
		BlockPos pos = event.getPos();

		int light = world.getLight(pos);
		if(light > light_mid)
		{
			event.setNewSpeed(
					( event.getNewSpeed() * ( 1 + ( light - light_mid ) / fac_light ) )
			);
		}
	}

	@Override
	public float getEntityDamage(IModifierToolStack tool, int level, ToolAttackContext context, float baseDamage, float damage)
	{
		int light = Calculates.getLight(context.getAttacker());
		return light < light_mid ?
				damage * (1 + ((light_mid - light ) / fac_light )):
				damage;
	}
}
