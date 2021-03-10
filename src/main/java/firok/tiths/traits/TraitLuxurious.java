package firok.tiths.traits;

import firok.tiths.client.particle.ParticleBuilder;
import firok.tiths.client.particle.ParticleType;
import firok.tiths.common.Configs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.traits.AbstractTrait;

import static firok.tiths.common.Keys.colorTraitLuxurious;
import static firok.tiths.common.Keys.nameTraitLuxurious;
import static firok.tiths.util.Predicates.canTick;

/**
 * 奢华
 */
public class TraitLuxurious extends AbstractTrait
{
	public TraitLuxurious() {
		super(nameTraitLuxurious,colorTraitLuxurious);
	}

	@Override
	public int onToolHeal(ItemStack tool, int amount, int newAmount, EntityLivingBase entity) {
		return (int)(newAmount - amount * Configs.Traits.factor_luxurious_durability); // 降低20%的耐久恢复
	}

	public static boolean checkParticle(World world)
	{
		return world.isRemote && canTick(world,9,0);
	}

	@SuppressWarnings("all")
	public static void spawnParticle(Entity entity)
	{
		ParticleBuilder.create(ParticleType.STAR)
				.pos(
						entity.posX-0.4+ random.nextFloat()*0.8,
						entity.posY-0.35+entity.getEyeHeight(),
						entity.posZ-0.4+random.nextFloat()*0.8
				)
				.clr(0xffda63)
				.scale(0.28f)
				.spawn(entity.world);
	}

	@Override
	public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected)
	{
		if(isSelected && checkParticle(world))
		{
			spawnParticle(entity);
		}
	}
}