package firok.tiths.traits;

import firok.tiths.common.Potions;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.traits.AbstractTrait;

import static firok.tiths.common.Keys.colorTraitTurbulent;
import static firok.tiths.common.Keys.nameTraitTurbulent;

/**
 * 乱流
 */
public class TraitTurbulent extends AbstractTrait
{
	public TraitTurbulent()
	{
		super(nameTraitTurbulent,colorTraitTurbulent);
	}

	@Override
	public void onHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, boolean isCritical)
	{
		World world=target.world;
		if(!world.isRemote && target.isEntityAlive())
		{
			target.addPotionEffect( new PotionEffect(Potions.turbulent, 100, 0));
		}
	}
}
