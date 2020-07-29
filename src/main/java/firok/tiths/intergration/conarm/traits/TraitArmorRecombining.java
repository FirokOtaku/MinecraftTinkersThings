package firok.tiths.intergration.conarm.traits;

import c4.conarm.lib.traits.AbstractArmorTrait;
import firok.tiths.common.Items;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.utils.ToolHelper;

import static firok.tiths.common.Keys.colorTraitRecombining;
import static firok.tiths.common.Keys.nameTraitRecombining;
import static firok.tiths.util.Predicates.canTick;

/**
 * 重组 - 护甲
 */
public class TraitArmorRecombining extends AbstractArmorTrait
{
	public TraitArmorRecombining()
	{
		super(nameTraitRecombining,colorTraitRecombining);
	}

	@Override
	public void onArmorTick(ItemStack tool, World world, EntityPlayer player)
	{
		if(!world.isRemote && canTick(world,160,8))
		{
			int durNow= ToolHelper.getCurrentDurability(tool);
			int durMax= ToolHelper.getMaxDurability(tool);

			if(durNow >= durMax / 5 * 4) return;

//			System.out.printf("dur now %d dur max %d\n",durNow,durMax);

			InventoryPlayer inv=player.inventory;
			ItemStack stackImitatium=null;
			final int size=inv.getSizeInventory();
			for(int i=0;i<size;i++)
			{
				ItemStack stackInv=inv.getStackInSlot(i);
				if(stackInv.isEmpty() || stackInv.getItem() != Items.nuggetImitatium) continue;
				stackImitatium=stackInv;
			}

			if(stackImitatium != null)
			{
				ToolHelper.healTool(tool,durMax/5,player);
				stackImitatium.shrink(1);
			}
		}
	}
}
