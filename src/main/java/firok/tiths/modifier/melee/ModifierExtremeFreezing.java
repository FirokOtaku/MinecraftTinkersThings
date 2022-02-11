package firok.tiths.modifier.melee;

import firok.tiths.config.ConfigModifier;
import firok.tiths.util.DevUse;
import firok.tiths.util.EntityFinders;
import firok.tiths.util.Predicates;
import firok.tiths.util.Ranges;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

import java.util.List;
import java.util.Random;

import static firok.tiths.util.Predicates.*;

/**
 * 极寒
 *
 * https://github.com/351768593/MinecraftTinkersThings/blob/indev1122/src/main/java/firok/tiths/traits/TraitExtremeFreezing.java
 */
@DevUse
public class ModifierExtremeFreezing extends Modifier
{
	public ModifierExtremeFreezing()
	{
		super(0x2a61bc);
	}

	public static boolean checkFreeze(World world)
	{
		return canTickServer(world,80,1);
	}

	public static void freeze(Entity center)
	{
		List<Entity> ens = EntityFinders.Nearby(center, ConfigModifier.range_extreme_freezing.get(), Predicates::isMobAlive);
		if(ens.size()>0)
			for(Entity en:ens)
			{
				if(!(en instanceof LivingEntity)) continue;
				LivingEntity enlb = (LivingEntity)en;
				enlb.addPotionEffect(new EffectInstance(Effects.SLOWNESS,85,2));
			}
	}
	public static boolean checkParticle(World world)
	{
		return canTickClient(world,4,1);
	}
	public static void particle(Entity entity)
	{
		Random random = entity.world.rand;
		entity.world.addParticle(ParticleTypes.CLOUD,
				entity.getPosX() + random.nextDouble() -0.5,
				entity.getPosY() + random.nextDouble(),
				entity.getPosZ() + random.nextDouble() -0.5,
				0.0D, 0.25D, 0.0D);
	}

	@Override
	public void onInventoryTick(IModifierToolStack tool, int level, World world, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack)
	{
		if(isSelected)
		{
			if(checkFreeze(world)) freeze(holder);
			if(checkParticle(world)) particle(holder);
		}
	}

	@Override
	public int afterEntityHit(IModifierToolStack tool, int level, ToolAttackContext context, float damageDealt)
	{
		LivingEntity target = context.getLivingTarget();
		if(target != null && target.isServerWorld() && target.isAlive())
		{
			target.addPotionEffect(new EffectInstance(Effects.SLOWNESS,165,3));
		}
		return 0;
	}

//	@Override
//	public boolean canApplyTogether(Enchantment enchantment) {
//		return enchantment != Enchantments.SILK_TOUCH;
//	}
//
//	@Override
//	public boolean canApplyTogether(IToolMod toolmod) {
//		return !toolmod.getIdentifier().equals(TinkerTraits.squeaky.getIdentifier())
//				&& !toolmod.getIdentifier().equals(TinkerModifiers.modSilktouch.getIdentifier());
//	}
}
