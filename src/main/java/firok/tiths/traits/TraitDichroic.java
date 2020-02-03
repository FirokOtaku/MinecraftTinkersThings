package firok.tiths.traits;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;
import slimeknights.tconstruct.library.traits.AbstractTrait;

import static firok.tiths.common.Keys.*;

// 二色性
public class TraitDichroic extends AbstractTrait
{
	public static final float lightMid=7f;
	public static final float lightFactor=35f;

	public TraitDichroic()
	{
		super(nameTraitDichroic, colorTraitDichroic);
	}

	@Override
	public void miningSpeed(ItemStack tool, PlayerEvent.BreakSpeed event)
	{
		EntityPlayer player=event.getEntityPlayer();
		int light=player.world.getLightFromNeighbors(player.getPosition());

		if(light>lightMid)
		{
			event.setNewSpeed( event.getNewSpeed() * ( 1 + ( light - lightMid ) / lightFactor ) );
		}
	}

	@Override
	public float damage(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float newDamage, boolean isCritical)
	{
		int light=player.world.getLightFromNeighbors(player.getPosition());

		return light<lightMid?
				newDamage * (1 + (( lightMid- light ) / lightFactor )):
				newDamage;
	}
}
