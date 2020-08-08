package firok.tiths.intergration.conarm.traits;

import c4.conarm.lib.traits.AbstractArmorTrait;
import firok.tiths.common.Configs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import static firok.tiths.common.Keys.colorTraitLuxurious;
import static firok.tiths.common.Keys.nameTraitLuxurious;
import static firok.tiths.traits.TraitLuxurious.checkParticle;
import static firok.tiths.traits.TraitLuxurious.spawnParticle;

/**
 * 奢华 - 护甲
 */
public class TraitArmorLuxurious extends AbstractArmorTrait
{
	public TraitArmorLuxurious()
	{
		super(nameTraitLuxurious, colorTraitLuxurious);
	}

	@Override
	public void onArmorTick(ItemStack tool, World world, EntityPlayer player)
	{
		if(checkParticle(world))
		{
			spawnParticle(player);
		}
	}

	@Override
	public int onArmorHeal(ItemStack armor, DamageSource source, int amount, int newAmount, EntityPlayer player, int slot) {
		return (int)(newAmount - amount * Configs.Traits.factor_luxurious_durability);
	}
}
