package firok.tiths.traits;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.ToolHelper;

import static firok.tiths.common.Keys.*;
import static firok.tiths.util.Predicates.canTick;

// 生命激发
public class TraitLifeInspiring extends AbstractTrait
{
	public TraitLifeInspiring()
	{
		super(nameTraitLifeInspiring, colorTraitLifeInspiring);
	}

	@Override
	public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected)
	{
		if(isSelected && !world.isRemote && canTick(world,20,4) && entity instanceof EntityLivingBase && !ToolHelper.isBroken(tool))
		{
			EntityLivingBase player=(EntityLivingBase)entity;
			float hp=player.getHealth(),hpMax=player.getMaxHealth();

			float percent=hp/hpMax;

			if(percent<0.35f)
				player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION,25,
						percent<0.15f?2:1));
		}
	}
}
