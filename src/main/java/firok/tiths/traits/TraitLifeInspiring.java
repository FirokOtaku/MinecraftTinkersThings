package firok.tiths.traits;

import firok.tiths.common.Configs;
import firok.tiths.util.SoulUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.ToolHelper;

import static firok.tiths.common.Keys.colorTraitLifeInspiring;
import static firok.tiths.common.Keys.nameTraitLifeInspiring;
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
		if(isSelected && !world.isRemote && canTick(world,20,4) && entity instanceof EntityPlayer && !ToolHelper.isBroken(tool))
		{
			EntityPlayer player=(EntityPlayer) entity;
			float hp=player.getHealth(),hpMax=player.getMaxHealth();

			float percent=hp/hpMax;

			if(percent < Configs.Traits.factor_life_inspiring_low && SoulUtil.costSoul(player,200,false,false) > 0)
			{
				player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION,25,
						percent < Configs.Traits.factor_life_inspiring_danger?2:1));
			}
		}
	}
}
