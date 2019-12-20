package firok.tiths.traits;

import firok.tiths.util.Actions;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.traits.AbstractTrait;

import static firok.tiths.common.Keys.*;
import static firok.tiths.util.Predicates.canTrigger;

// 亡灵呼唤
public class TraitUndeadCalling extends AbstractTrait
{
	public static final float rate=0.1f;
	public TraitUndeadCalling()
	{
		super(nameTraitUndeadCalling, colorTraitUndeadCalling);
	}

	@Override
	public void afterBlockBreak(ItemStack tool, World world, IBlockState state, BlockPos pos, EntityLivingBase player, boolean wasEffective)
	{
		if(wasEffective && !world.isRemote && canTrigger(world, rate))
		{
			Actions.CauseSpawningUndead(player);
		}
	}

	@Override
	public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit)
	{
		if(!target.isEntityAlive() && !target.getEntityWorld().isRemote && canTrigger(player.world, rate))
		{
			Actions.CauseSpawningUndead(player);
		}
	}
}
