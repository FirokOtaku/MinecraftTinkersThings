package firok.tiths.intergration.conarm.traits;

import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import static firok.tiths.common.Keys.colorTraitExtremeFreezing;
import static firok.tiths.common.Keys.nameTraitExtremeFreezing;
import static firok.tiths.traits.TraitExtremeFreezing.*;

/**
 * 极寒 - 护甲
 */
public class TraitArmorExtremeFreezing extends AbstractArmorTrait
{
	public TraitArmorExtremeFreezing()
	{
		super(nameTraitExtremeFreezing,colorTraitExtremeFreezing);
	}
	@Override
	public void onArmorTick(ItemStack tool, World world, EntityPlayer player)
	{
		if(checkParticle(world)) particle(player);
		if(checkFreeze(world)) freeze(player);
	}
}
