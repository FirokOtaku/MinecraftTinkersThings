package firok.tiths.traits;

import firok.tiths.util.Actions;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.traits.AbstractTrait;

import static firok.tiths.common.Keys.*;

// 振奋
public class TraitHyper extends AbstractTrait
{
	public TraitHyper()
	{
		super(nameTraitHyper, colorTraitHyper);
	}

	public void tryAddEffects(EntityLivingBase target)
	{
		if(!target.world.isRemote)
		{
			Actions.CauseAccumEffect(target,new PotionEffect(MobEffects.SPEED,30,1));
			Actions.CauseAccumEffect(target,new PotionEffect(MobEffects.HASTE,30,1));
		}
	}

	@Override
	public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit)
	{
		if(wasHit) tryAddEffects(player);
	}

	@Override
	public void afterBlockBreak(ItemStack tool, World world, IBlockState state, BlockPos pos, EntityLivingBase player, boolean wasEffective)
	{
		if(wasEffective) tryAddEffects(player);
	}
}
