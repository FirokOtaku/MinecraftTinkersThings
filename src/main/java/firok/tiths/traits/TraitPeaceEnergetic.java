package firok.tiths.traits;

import firok.tiths.common.Configs;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.traits.AbstractTrait;

import static firok.tiths.common.Keys.colorTraitPeaceEnergetic;
import static firok.tiths.common.Keys.nameTraitPeaceEnergetic;
import static firok.tiths.util.Predicates.canTrigger;

// 平和能量
public class TraitPeaceEnergetic extends AbstractTrait
{
	public TraitPeaceEnergetic()
	{
		super(nameTraitPeaceEnergetic, colorTraitPeaceEnergetic);
	}

	@Override
	public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit)
	{
		if(!player.world.isRemote && canTrigger(player.world, Configs.Traits.rate_peace_energetic_hit))
		{
			player.heal(Configs.Traits.factor_peace_energetic_heal);
		}
	}

	@Override
	public void afterBlockBreak(ItemStack tool, World world, IBlockState state, BlockPos pos, EntityLivingBase player, boolean wasEffective)
	{
		if(!player.world.isRemote && canTrigger(player.world, Configs.Traits.rate_peace_energetic_break))
		{
			player.heal(Configs.Traits.factor_peace_energetic_heal);
		}
	}
}
