package firok.tiths.intergration.conarm.traits;

import c4.conarm.lib.traits.AbstractArmorTrait;
import firok.tiths.common.Potions;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import static firok.tiths.common.Keys.colorTraitEmpathy;
import static firok.tiths.common.Keys.nameTraitEmpathy;
import static firok.tiths.util.Predicates.canTick;

/**
 * 共感 - 护甲
 */
public class TraitArmorEmpathy extends AbstractArmorTrait
{
	public TraitArmorEmpathy()
	{
		super(nameTraitEmpathy,colorTraitEmpathy);
	}

	@Override
	public void onArmorTick(ItemStack tool, World world, EntityPlayer player)
	{
		if(!world.isRemote && canTick(world,5,0))
		{
			player.addPotionEffect(new PotionEffect(Potions.empathic,80,0));
		}
	}
}
