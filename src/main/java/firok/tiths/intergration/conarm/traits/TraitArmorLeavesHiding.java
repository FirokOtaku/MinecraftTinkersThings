package firok.tiths.intergration.conarm.traits;

import c4.conarm.lib.traits.AbstractArmorTrait;
import firok.tiths.common.Potions;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import static firok.tiths.common.Keys.colorTraitLeavesHiding;
import static firok.tiths.common.Keys.nameTraitLeavesHiding;
import static firok.tiths.util.Predicates.canTick;

/**
 * 蔽叶 - 护甲
 */
public class TraitArmorLeavesHiding extends AbstractArmorTrait
{
	public TraitArmorLeavesHiding()
	{
		super(nameTraitLeavesHiding,colorTraitLeavesHiding);
	}

	@Override
	public void onArmorTick(ItemStack tool, World world, EntityPlayer player)
	{
		if(!world.isRemote && canTick(world,80,6))
		{
			player.addPotionEffect(new PotionEffect(Potions.leaves_hiding,85,0,false,false));
		}
	}
}
