package firok.tiths.traits;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.traits.AbstractTrait;

import static firok.tiths.common.Keys.colorTraitInfernalBlazing;
import static firok.tiths.common.Keys.nameTraitInfernalBlazing;
import static firok.tiths.util.Predicates.canTick;

// 狱炎
public class TraitInfernalBlazing extends AbstractTrait
{
	public TraitInfernalBlazing()
	{
		super(nameTraitInfernalBlazing, colorTraitInfernalBlazing);
	}

	@Override
	public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected)
	{
		if(isSelected && !world.isRemote && canTick(world,80,4))
		{
			entity.setFire(4);
		}
	}
}