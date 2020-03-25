package firok.tiths.traits;

import firok.tiths.TinkersThings;
import firok.tiths.util.Actions;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.traits.AbstractTrait;

import static firok.tiths.common.Keys.colorTraitPhantasmic;
import static firok.tiths.common.Keys.nameTraitPhantasmic;

// 异象
public class TraitPhantasmic extends AbstractTrait
{
	public static final float rate=0.05f;
	public TraitPhantasmic()
	{
		super(nameTraitPhantasmic, colorTraitPhantasmic);
	}

	@Override
	public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit)
	{
		if(wasHit && player.world.isRemote && TinkersThings.randClient.nextFloat()<rate)
			Actions.CauseSpawningPhantom(player);
	}

	@Override
	public void afterBlockBreak(ItemStack tool, World world, IBlockState state, BlockPos pos, EntityLivingBase player, boolean wasEffective)
	{
		if(world.isRemote && TinkersThings.randClient.nextFloat()<rate)
			Actions.CauseSpawningPhantom(player);
	}
}
