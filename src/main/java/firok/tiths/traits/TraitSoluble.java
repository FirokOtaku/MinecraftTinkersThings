package firok.tiths.traits;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.ToolHelper;

import static firok.tiths.common.Keys.colorTraitSoluble;
import static firok.tiths.common.Keys.nameTraitSoluble;
import static firok.tiths.util.Predicates.canTick;

// 可溶
public class TraitSoluble extends AbstractTrait
{
	public TraitSoluble()
	{
		super(nameTraitSoluble, colorTraitSoluble);
	}

	@Override
	public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected)
	{
		if(!world.isRemote && isSelected && canTick(world,15,2) && entity.isInWater())
		{
			ToolHelper.damageTool(tool, 20, (EntityLivingBase) entity);
		}
	}
}
