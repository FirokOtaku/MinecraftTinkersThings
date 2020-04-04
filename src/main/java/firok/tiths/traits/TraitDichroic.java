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

// 二色性
public class TraitDichroic extends AbstractTrait
{
	public TraitDichroic()
	{
		super(nameTraitDichroic, colorTraitDichroic);
	}

	/**
	 * 获取亮度
	 */
	public static int getLight(Entity entity)
	{
		return entity.world.getLightFromNeighbors(entity.getPosition());
	}

	@Override
	public void miningSpeed(ItemStack tool, PlayerEvent.BreakSpeed event)
	{
		EntityPlayer player=event.getEntityPlayer();
		int light=getLight(player);

		if(light > Configs.Traits.factor_dichroic_light_mid)
		{
			event.setNewSpeed((float)( event.getNewSpeed() * ( 1 + ( light - Configs.Traits.factor_dichroic_light_mid ) / Configs.Traits.factor_dichroic_light ) ));
		}
	}

	@Override
	public float damage(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float newDamage, boolean isCritical)
	{
		int light=getLight(player);

		return light < Configs.Traits.factor_dichroic_light_mid?
				newDamage * (float)(1 + ((Configs.Traits.factor_dichroic_light_mid- light ) / Configs.Traits.factor_dichroic_light )):
				newDamage;
	}
}
