package firok.tiths.traits;

import firok.tiths.common.Potions;
import firok.tiths.util.Actions;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import slimeknights.tconstruct.library.traits.AbstractTrait;

import static firok.tiths.common.Keys.colorTraitLionheart;
import static firok.tiths.common.Keys.nameTraitLionheart;

// 狮心
public class TraitLionheart extends AbstractTrait
{
	public TraitLionheart()
	{
		super(nameTraitLionheart, colorTraitLionheart);
	}

	@Override
	public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit)
	{
		if(wasHit && damageDealt>0 && !player.world.isRemote)
		{
			Actions.CauseAccumEffect(player,new PotionEffect(Potions.lionheart,35,0));
		}
	}
}