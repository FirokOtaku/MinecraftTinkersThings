package firok.tiths.intergration.conarm.traits;

import c4.conarm.common.armor.utils.ArmorHelper;
import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import static firok.tiths.common.Keys.colorTraitSunPower;
import static firok.tiths.common.Keys.nameTraitSunPower;
import static firok.tiths.traits.TraitSunPower.checkHealSun;

/**
 * 日之力量 - 护甲
 */
public class TraitArmorSunPower extends AbstractArmorTrait
{
	public TraitArmorSunPower()
	{
		super(nameTraitSunPower,colorTraitSunPower);
	}

	@Override
	public void onArmorTick(ItemStack tool, World world, EntityPlayer player)
	{
		if(checkHealSun(world))
		{
			ArmorHelper.healArmor(tool,1,player,0);
		}
	}
}
