package firok.tiths.traits;

import firok.tiths.common.Configs;
import firok.tiths.util.Actions;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.traits.AbstractTrait;

import static firok.tiths.common.Keys.*;
import static firok.tiths.util.Predicates.canTrigger;

// 喀嚓
public class TraitCreaky extends AbstractTrait
{
	public TraitCreaky()
	{
		super(nameTraitCreaky, colorTraitCreaky);
	}

	@Override
	public void afterBlockBreak(ItemStack tool, World world, IBlockState state, BlockPos pos, EntityLivingBase player, boolean wasEffective)
	{
		if(wasEffective && !world.isRemote && canTrigger(world, Configs.Traits.rate_creaky_use))
		{
			Actions.CauseSpawningSilverfish(player, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, world);
		}
	}

	@Override
	public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit)
	{
		if(!target.isEntityAlive() && !target.getEntityWorld().isRemote && canTrigger(player.world, Configs.Traits.rate_creaky_use))
		{
			Actions.CauseSpawningSilverfish(player, target.posX, target.posY, target.posZ, target.world);
		}
	}

}
