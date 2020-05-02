package firok.tiths.traits;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.traits.AbstractTrait;

import static firok.tiths.TinkersThings.randClient;
import static firok.tiths.common.Keys.colorTraitStaminaFocusing;
import static firok.tiths.common.Keys.nameTraitStaminaFocusing;
import static firok.tiths.util.Predicates.canTick;

// 精力汇聚
public class TraitStaminaFocusing extends AbstractTrait
{
	public TraitStaminaFocusing()
	{
		super(nameTraitStaminaFocusing, colorTraitStaminaFocusing);
	}

	@Override
	public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected)
	{
		if(isSelected && entity instanceof EntityLivingBase && canTick(world,4,0) && entity.isSneaking())
		{
			if (world.isRemote)
			{
				world.spawnParticle(EnumParticleTypes.SPELL_WITCH,
						entity.posX + randClient.nextFloat() * 0.3,
						entity.posY + entity.getEyeHeight() * 2 / 3,
						entity.posZ + randClient.nextFloat() * 0.3,
						0,0,0);
			}
			else
			{
				((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.RESISTANCE,80,1));
			}

		}
	}
}
