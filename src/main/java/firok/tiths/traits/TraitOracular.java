package firok.tiths.traits;

import firok.tiths.common.Configs;
import firok.tiths.common.SoundEvents;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.SoundCategory;
import slimeknights.tconstruct.library.traits.AbstractTrait;

import static firok.tiths.common.Keys.colorTraitOracular;
import static firok.tiths.common.Keys.nameTraitOracular;
import static firok.tiths.util.Predicates.canTrigger;

// 神谕
public class TraitOracular extends AbstractTrait
{
	public TraitOracular()
	{
		super(nameTraitOracular, colorTraitOracular);
	}

	@Override
	public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit)
	{
		if(!player.world.isRemote)
		{
			boolean playSound=false;

			if(Configs.Traits.enable_oracular_remove_target && target.isEntityAlive())
			{
				// 移除目标的增益效果
				for(PotionEffect effect:target.getActivePotionEffects())
				{
					Potion potion=effect.getPotion();
					if(!potion.isBadEffect() || potion.isBeneficial())
					{
						target.removePotionEffect(potion);
						playSound = true;
					}
				}
			}

			// 移除玩家的减益效果
			if(Configs.Traits.enable_oracular_remove_player)
			for(PotionEffect effect:player.getActivePotionEffects())
			{
				Potion potion=effect.getPotion();
				if(potion.isBadEffect())
				{
					player.removePotionEffect(potion);
					playSound = true;
				}
			}

			// 几率回血
			if(canTrigger(player.world, Configs.Traits.rate_oracular_heal))
			{
				player.heal((float)Configs.Traits.factor_oracular_heal);
				playSound = true;
			}

			if(playSound && player instanceof EntityPlayer)
			{
//				TinkersThings.log("play sound!");
//				player.world.playSound((EntityPlayer)player,player.posX,player.posY,player.posZ, SoundEvents.effectHeal, SoundCategory.MASTER, 1, 1);
				player.world.playSound(null,player.getPosition(),SoundEvents.effectHeal,SoundCategory.PLAYERS,1,1);
//				player.playSound(SoundEvents.effectHeal,1,1);
			}
		}
	}
}
