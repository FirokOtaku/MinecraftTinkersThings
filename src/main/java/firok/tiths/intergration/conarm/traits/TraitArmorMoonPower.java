package firok.tiths.intergration.conarm.traits;

import c4.conarm.common.armor.utils.ArmorHelper;
import c4.conarm.lib.traits.AbstractArmorTrait;
import firok.tiths.common.Configs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import static firok.tiths.common.Keys.colorTraitMoonPower;
import static firok.tiths.common.Keys.nameTraitMoonPower;
import static firok.tiths.traits.TraitMoonPower.checkHealMoon;

/**
 * 月之力量
 */
public class TraitArmorMoonPower extends AbstractArmorTrait
{
	public TraitArmorMoonPower()
	{
		super(nameTraitMoonPower,colorTraitMoonPower);
	}

	@Override
	public void onArmorTick(ItemStack tool, World world, EntityPlayer player)
	{
		if(checkHealMoon(world))
			{
			ArmorHelper.healArmor(tool, Configs.Traits.factor_moon_power,player,0);
		}
	}
}
