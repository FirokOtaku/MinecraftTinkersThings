package firok.tiths.intergration.conarm.traits;

import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.utils.ToolHelper;

import static firok.tiths.common.Keys.colorTraitSoluble;
import static firok.tiths.common.Keys.nameTraitSoluble;
import static firok.tiths.util.Predicates.canTick;

/**
 * 可溶 - 护甲
 */
public class TraitArmorSoluble extends AbstractArmorTrait
{
	public TraitArmorSoluble()
	{
		super(nameTraitSoluble,colorTraitSoluble);
	}

	@Override
	public void onArmorTick(ItemStack tool, World world, EntityPlayer player)
	{
		if(!world.isRemote && player.isInWater())
		{
			if( canTick(world,15,2) )
			{
				ToolHelper.damageTool(tool, 20, player);
			}
		}
	}
}
