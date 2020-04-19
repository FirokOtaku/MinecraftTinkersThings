package firok.tiths.traits;

import firok.tiths.common.Configs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;
import slimeknights.tconstruct.library.traits.AbstractTrait;

import static firok.tiths.common.Keys.colorTraitWatery;
import static firok.tiths.common.Keys.nameTraitWatery;

// 水化
public class TraitWatery extends AbstractTrait
{
	public TraitWatery()
	{
		super(nameTraitWatery, colorTraitWatery);
	}

	@Override
	public void miningSpeed(ItemStack tool, PlayerEvent.BreakSpeed event)
	{
		event.setNewSpeed( event.getOriginalSpeed() * (float) ( event.getEntityPlayer().isInWater()? Configs.Traits.factor_watery_in: Configs.Traits.factor_watery_out ) );
	}

	@Override
	public float damage(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float newDamage, boolean isCritical)
	{
		return (float)(player.isInWater() ? newDamage * Configs.Traits.factor_watery_in : newDamage * Configs.Traits.factor_watery_out);
	}

	@Override
	public float knockBack(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float knockback, float newKnockback, boolean isCritical)
	{
		return (float)(player.isInWater() ? newKnockback * Configs.Traits.factor_watery_in : newKnockback * Configs.Traits.factor_watery_out);
	}
}
