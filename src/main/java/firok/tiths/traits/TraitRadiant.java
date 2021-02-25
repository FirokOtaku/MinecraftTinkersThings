package firok.tiths.traits;

import firok.tiths.common.Configs;
import firok.tiths.util.EntityFinders;
import firok.tiths.util.Predicates;
import firok.tiths.util.Selectors;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import slimeknights.tconstruct.common.config.Config;
import slimeknights.tconstruct.library.modifiers.IToolMod;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.ToolHelper;
import slimeknights.tconstruct.shared.TinkerCommons;
import slimeknights.tconstruct.tools.TinkerModifiers;
import slimeknights.tconstruct.tools.TinkerTraits;

import java.util.List;
import java.util.ListIterator;
import java.util.Random;

import static firok.tiths.common.Keys.colorTraitRadiant;
import static firok.tiths.common.Keys.nameTraitRadiant;
import static firok.tiths.util.Predicates.canTick;

/**
 * 辉耀
 */
public class TraitRadiant extends AbstractTrait
{
	public TraitRadiant()
	{
		super(nameTraitRadiant,colorTraitRadiant);
	}

	/**
	 * 用于每tick的灼烧效果检查
	 */
	public static boolean checkBurn(World world)
	{
		return !world.isRemote && canTick(world,20,1);
	}

	/**
	 * 灼烧效果
	 */
	public static void burn(World world,Entity source)
	{
		List<Entity> ens= EntityFinders.Nearby(source, Configs.Traits.range_radiant,Selectors.mobAlive);
		if(ens.size()>0)
			for(Entity en:ens)
			{
				en.setFire(5);
			}
	}

	/**
	 * 用于每tick的粒子效果检查
	 */
	public static boolean checkParticle(World world)
	{
		return world.isRemote && canTick(world,4,1);
	}

	/**
	 * 粒子效果
	 */
	@SuppressWarnings("all")
	public static void particle(World world, Entity entity, Random random)
	{
		world.spawnParticle(EnumParticleTypes.FLAME,
				entity.posX + random.nextDouble() -0.5,
				entity.posY + random.nextDouble(),
				entity.posZ + random.nextDouble() -0.5,
				0.0D, 0.0D, 0.0D);

//		Particles.spawnParticle(Particles.RADIANT,world,
//				entity.posX + random.nextDouble() -0.5,
//				entity.posY + random.nextDouble() + 1,
//				entity.posZ + random.nextDouble() -0.5,
//				0,0,0);
	}
	@Override
	public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected)
	{
		if(isSelected && checkBurn(world))
		{
			burn(world, entity);
		}

		if(isSelected && checkParticle(world))
		{
			particle(world,entity,world.rand);
		}
	}

	@Override
	public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit)
	{
		super.afterHit(tool, player, target, damageDealt, wasCritical, wasHit);
		if(wasHit && player.isServerWorld() && target.isEntityAlive())
		{
			target.setFire(10);
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



	@Override
	public void blockHarvestDrops(ItemStack tool, BlockEvent.HarvestDropsEvent event) {
		if(ToolHelper.isToolEffective2(tool, event.getState())) {
			// go through the drops and replace them with their furnace'd variant if applicable
			ListIterator<ItemStack> iter = event.getDrops().listIterator();
			while(iter.hasNext()) {
				ItemStack drop = iter.next();

				if(Predicates.isAnyStone(drop))
				{
					ItemStack stackSearedBrick=TinkerCommons.searedBrick.copy();
					stackSearedBrick.setCount(drop.getCount());
					iter.set(stackSearedBrick);
				}
				else
				{
					ItemStack smelted = FurnaceRecipes.instance().getSmeltingResult(drop);
					if(!smelted.isEmpty()) {
						smelted = smelted.copy();
						smelted.setCount(drop.getCount());
						int fortune = EnchantmentHelper.getEnchantmentLevel(Enchantments.FORTUNE, tool);
						if(Config.autosmeltlapis && fortune > 0) {
							smelted.setCount(smelted.getCount() * random.nextInt(fortune + 1) + 1);
						}
						iter.set(smelted);

						// drop XP for it
						float xp = FurnaceRecipes.instance().getSmeltingExperience(smelted);
						if(xp < 1 && Math.random() < xp) {
							xp += 1f;
						}
						if(xp >= 1f) {
							event.getState().getBlock().dropXpOnBlockBreak(event.getWorld(), event.getPos(), (int) xp);
						}
					}
				}
			}
		}
	}

	@Override
	public void afterBlockBreak(ItemStack tool, World world, IBlockState state, BlockPos pos, EntityLivingBase player, boolean wasEffective) {
		if(world.isRemote && wasEffective) {
			for(int i = 0; i < 4; i++) {
				world.spawnParticle(EnumParticleTypes.FLAME,
						pos.getX() + random.nextDouble(),
						pos.getY() + random.nextDouble(), pos.getZ() + random.nextDouble(),
						0.0D, 0.0D, 0.0D);
			}
		}
	}
}
