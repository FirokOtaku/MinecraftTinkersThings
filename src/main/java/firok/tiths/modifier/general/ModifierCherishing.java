package firok.tiths.modifier.general;

import firok.tiths.util.DevUse;
import firok.tiths.util.Predicates;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

import static firok.tiths.util.Predicates.canTrigger;

/**
 * 缅怀
 * https://github.com/351768593/MinecraftTinkersThings/blob/indev1122/src/main/java/firok/tiths/traits/TraitCherishing.java
 */
@DevUse(isPlaceholder = true)
public class ModifierCherishing extends Modifier
{
	public ModifierCherishing()
	{
		super(0x4bcab3);
	}

	// 找个地方种花
	@SuppressWarnings("UnusedLabel")
	@Override
	public int afterEntityHit(IModifierToolStack tool, int level, ToolAttackContext context, float damageDealt)
	{
		LivingEntity target = context.getLivingTarget();
		World world = target != null ? target.world : null;
		if (target == null || world == null) return 0;

		final BlockState stateFlower1 = Blocks.SUNFLOWER.getDefaultState();
		final BlockState stateFlower2 = Blocks.CORNFLOWER.getDefaultState();
		BlockPos pos = target.getPosition();
		FOR_X: for (int offsetX = -1; offsetX <= 1; offsetX++)
		{
			FOR_Z: for (int offsetZ = -1; offsetZ <= 1; offsetZ++)
			{
				if (canTrigger(world, 0.35)) continue FOR_Z;

				boolean wasDirt = false;
				FOR_Y: for (int offsetY = -3; offsetY <= 1; offsetY++)
				{
					BlockPos posTemp = pos.add(offsetX, offsetY, offsetZ);
					BlockState stateTemp = world.getBlockState(posTemp);

					if (Predicates.isAir(stateTemp) && wasDirt)
					{
						boolean color = world.rand.nextBoolean();
						world.setBlockState(posTemp, color ? stateFlower1 : stateFlower2);
					}

					wasDirt = Predicates.isDirt(stateTemp);
				}
			}
		}

		return 0;
	}
}
