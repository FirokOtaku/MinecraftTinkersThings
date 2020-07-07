package firok.tiths.intergration.conarm.traits;

import c4.conarm.lib.traits.AbstractArmorTrait;
import firok.tiths.util.SoulUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import static firok.tiths.common.Keys.colorTraitLifeInspiring;
import static firok.tiths.common.Keys.nameTraitLifeInspiring;
import static firok.tiths.util.Predicates.canTick;

/**
 * 生命激发 - 护甲
 */
public class TraitArmorLifeInspiring extends AbstractArmorTrait
{
	public TraitArmorLifeInspiring()
	{
		super(nameTraitLifeInspiring,colorTraitLifeInspiring);
	}

	@Override
	public void onArmorTick(ItemStack tool, World world, EntityPlayer player)
	{
		if(!world.isRemote && canTick(world,20,4))
		{
			float hp=player.getHealth(),hpMax=player.getMaxHealth();

			float percent=hp/hpMax;

			if(percent< 0.35f && player.experienceTotal>=4)
			{
				player.addExperience(-4);
				player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION,25,
						percent<0.15f?2:1));
			}
		}
	}
}
