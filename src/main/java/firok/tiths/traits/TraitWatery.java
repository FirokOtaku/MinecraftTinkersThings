package firok.tiths.traits;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;
import slimeknights.tconstruct.library.traits.AbstractTrait;

import static firok.tiths.common.Keys.*;

// 水化
public class TraitWatery extends AbstractTrait
{
	public static final float rate=0.08f;
	public TraitWatery()
	{
		super(nameTraitWatery, colorTraitWatery);
	}

	@Override
	public void miningSpeed(ItemStack tool, PlayerEvent.BreakSpeed event)
	{
		event.setNewSpeed( event.getOriginalSpeed() * ( event.getEntityPlayer().isInWater()?1.4f:0.6f ) );
	}

	@Override
	public float damage(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float newDamage, boolean isCritical)
	{
		return player.isInWater() ? newDamage * 1.4f : newDamage * 0.6f;
	}

	@Override
	public float knockBack(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float knockback, float newKnockback, boolean isCritical)
	{
		return player.isInWater() ? newKnockback * 1.4f : newKnockback * 0.6f;
	}
}
