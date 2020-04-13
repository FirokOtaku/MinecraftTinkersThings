package firok.tiths.traits;

import firok.tiths.common.Configs;
import firok.tiths.util.EntityFinders;
import firok.tiths.util.Selectors;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Enchantments;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.modifiers.IToolMod;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.tools.TinkerModifiers;
import slimeknights.tconstruct.tools.TinkerTraits;

import java.util.List;

import static firok.tiths.common.Keys.colorTraitExtremeFreezing;
import static firok.tiths.common.Keys.nameTraitExtremeFreezing;
import static firok.tiths.util.Predicates.canTick;

// 极寒
public class TraitExtremeFreezing extends AbstractTrait
{
	public TraitExtremeFreezing()
	{
		super(nameTraitExtremeFreezing, colorTraitExtremeFreezing);
	}

	public static boolean checkFreeze(World world)
	{
		return !world.isRemote && canTick(world,80,1);
	}
	public static void freeze(Entity center)
	{
		List<Entity> ens=EntityFinders.Nearby(center, Configs.Traits.range_extreme_freezing,Selectors.mobAlive);
		if(ens.size()>0)
			for(Entity en:ens)
			{
				if(!(en instanceof EntityLivingBase)) continue;
				EntityLivingBase enlb=(EntityLivingBase)en;
				enlb.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS,85,2));
			}
	}
	public static boolean checkParticle(World world)
	{
		return world.isRemote && canTick(world,4,1);
	}
	public static void particle(Entity entity)
	{
		entity.world.spawnParticle(EnumParticleTypes.CLOUD,
				entity.posX + random.nextDouble() -0.5,
				entity.posY + random.nextDouble(),
				entity.posZ + random.nextDouble() -0.5,
				0.0D, 0.25D, 0.0D);
	}

	@Override
	public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected)
	{
//		super.onUpdate(tool, world, entity, itemSlot, isSelected);
		if(isSelected)
		{
			if(checkFreeze(world)) freeze(entity);
			if(checkParticle(world)) particle(entity);
		}
	}

	@Override
	public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit)
	{
		super.afterHit(tool, player, target, damageDealt, wasCritical, wasHit);
		if(wasHit && player.isServerWorld() && target.isEntityAlive())
		{
			target.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS,165,3));
		}
	}

	@Override
	public boolean canApplyTogether(Enchantment enchantment) {
		return enchantment != Enchantments.SILK_TOUCH;
	}

	@Override
	public boolean canApplyTogether(IToolMod toolmod) {
		return !toolmod.getIdentifier().equals(TinkerTraits.squeaky.getIdentifier())
				&& !toolmod.getIdentifier().equals(TinkerModifiers.modSilktouch.getIdentifier());
	}

}
