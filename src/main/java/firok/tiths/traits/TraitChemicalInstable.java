package firok.tiths.traits;

import firok.tiths.common.Configs;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.traits.AbstractTrait;

import static firok.tiths.common.Keys.colorTraitChemicalInstable;
import static firok.tiths.common.Keys.nameTraitChemicalInstable;
import static firok.tiths.util.Predicates.canTrigger;

// 化学不稳定
public class TraitChemicalInstable extends AbstractTrait
{
	public TraitChemicalInstable()
	{
		super(nameTraitChemicalInstable, colorTraitChemicalInstable);
	}

	public static void boom(EntityLivingBase player,double posX,double posY,double posZ)
	{
		player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE,10,1));
		player.world.createExplosion(null,posX,posY,posZ, (float)Configs.Traits.factor_chemical_instable,true);
	}
	public static void boom(EntityLivingBase player,EntityLivingBase target)
	{
		boom(player,target.posX,target.posY,target.posZ);
	}

	@Override
	public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit)
	{
		if(wasHit && !target.world.isRemote && canTrigger(target.world, Configs.Traits.rate_chemical_instable_attack))
		{
			boom(player,target);
		}
	}

	@Override
	public void afterBlockBreak(ItemStack tool, World world, IBlockState state, BlockPos pos, EntityLivingBase player, boolean wasEffective)
	{
		if(!player.world.isRemote && canTrigger(player.world, Configs.Traits.rate_chemical_instable_break))
		{
			boom(player,pos.getX(),pos.getY(),pos.getZ());
		}
	}
}