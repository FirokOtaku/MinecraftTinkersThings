package firok.tiths.traits;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.traits.AbstractTrait;

import static firok.tiths.common.Keys.*;
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
		if(!world.isRemote && isSelected && canTick(world,4,0) && entity instanceof EntityLivingBase && entity.isSneaking())
		{
			((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.RESISTANCE,80,1));
		}
	}
}
