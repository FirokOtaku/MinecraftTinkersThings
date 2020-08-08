package firok.tiths.traits;

import firok.tiths.common.Configs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;
import slimeknights.tconstruct.library.traits.AbstractTrait;

import static firok.tiths.common.Keys.colorTraitDichroic;
import static firok.tiths.common.Keys.nameTraitDichroic;
import static firok.tiths.util.Actions.getLight;

// 二色性
public class TraitDichroic extends AbstractTrait
{
	public TraitDichroic()
	{
		super(nameTraitDichroic, colorTraitDichroic);
	}

	final int light_mid = 7;
	final int fac_light = 35;

	@Override
	public void miningSpeed(ItemStack tool, PlayerEvent.BreakSpeed event)
	{
		EntityPlayer player=event.getEntityPlayer();
		int light=getLight(player);

		if(light > light_mid)
		{
			event.setNewSpeed((float)( event.getNewSpeed() * ( 1 + ( light - light_mid ) / fac_light ) ));
		}
	}

	@Override
	public float damage(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float newDamage, boolean isCritical)
	{
		int light=getLight(player);

		return light < light_mid?
				newDamage * (float)(1 + ((light_mid- light ) / fac_light )):
				newDamage;
	}
}
