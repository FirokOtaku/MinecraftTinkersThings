package firok.tiths.modifier.projectile;

import firok.tiths.util.Colors;
import firok.tiths.util.DevUse;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

/**
 * 织网
 *
 * https://github.com/351768593/MinecraftTinkersThings/blob/indev1122/src/main/java/firok/tiths/traits/TraitNetting.java
 */
@DevUse
public class ModifierNetWeaving extends Modifier
{

	public ModifierNetWeaving()
	{
		super(Colors.White);
	}

	@Override
	public float getEntityDamage(IModifierToolStack tool, int level, ToolAttackContext context, float baseDamage, float damage)
	{
		return 0;
	}

	public static void setWeb(World world, BlockPos pos)
	{
		final BlockState stateWeb = Blocks.COBWEB.getDefaultState();
		final BlockPos posUp = pos.up();
		final BlockPos posFinal = world.isAirBlock(pos) ? pos : world.isAirBlock(posUp) ? posUp : null;
		if(posFinal != null)
		{
			world.setBlockState(posFinal, stateWeb);
		}
	}

	// todo waiting for projectile modifier api
}
