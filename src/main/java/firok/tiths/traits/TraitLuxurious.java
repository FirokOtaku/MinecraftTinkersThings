package firok.tiths.traits;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
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
		return newAmount - amount / 5; // 降低20%的耐久恢复
	}

	public static boolean checkParticle(World world)
	{
		return world.isRemote && canTick(world,7,0);
	}

	public static void spawnParticle(Entity entity)
	{
		entity.world.spawnParticle(
				EnumParticleTypes.VILLAGER_HAPPY,
				entity.posX-0.4+ random.nextFloat()*0.8,
				entity.posY-0.35+entity.getEyeHeight(),
				entity.posZ-0.4+random.nextFloat()*0.8,
				0,0,0);
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