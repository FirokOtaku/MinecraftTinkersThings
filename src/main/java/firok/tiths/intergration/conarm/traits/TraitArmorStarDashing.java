package firok.tiths.intergration.conarm.traits;

import c4.conarm.lib.traits.AbstractArmorTrait;
import firok.tiths.util.Actions;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import static firok.tiths.common.Keys.colorTraitStarDashing;
import static firok.tiths.common.Keys.nameTraitStarDashing;

/**
 * 星绽 - 护甲
 */
public class TraitArmorStarDashing extends AbstractArmorTrait
{
	public TraitArmorStarDashing()
	{
		super(nameTraitStarDashing,colorTraitStarDashing);
	}

	@Override
	public float onHurt(ItemStack armor, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingHurtEvent evt)
	{
		if(player.isServerWorld())
		{
			Actions.CauseStarDashing(player.world,player.posX,player.posY,player.posZ,3,0.2f,5);
		}
		return newDamage;
	}
}
