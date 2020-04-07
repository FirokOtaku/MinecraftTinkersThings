package firok.tiths.intergration.conarm.traits;

import c4.conarm.common.armor.utils.ArmorHelper;
import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import static firok.tiths.common.Keys.colorTraitIcy;
import static firok.tiths.common.Keys.nameTraitIcy;
import static firok.tiths.traits.TraitIcy.calcAmount;
import static firok.tiths.traits.TraitIcy.canEffect;

/**
 * 冰凉 - 护甲
 */
public class TraitArmorIcy extends AbstractArmorTrait
{
	public TraitArmorIcy()
	{
		super(nameTraitIcy,colorTraitIcy);
	}

	@Override
	public void onArmorTick(ItemStack tool, World world, EntityPlayer player)
	{
		if(canEffect(world))
		{
			int amount=(int)calcAmount(player);
			if(amount>0) ArmorHelper.healArmor(tool,amount,player,0);
			else if(amount<0) ArmorHelper.damageArmor(tool, DamageSource.GENERIC,-amount,player,0);
		}

	}
}
