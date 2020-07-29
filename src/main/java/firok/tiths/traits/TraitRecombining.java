package firok.tiths.traits;

import firok.tiths.common.Items;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.ToolHelper;

import static firok.tiths.common.Keys.colorTraitRecombining;
import static firok.tiths.common.Keys.nameTraitRecombining;
import static firok.tiths.util.Predicates.canTick;

/**
 * 重组
 */
public class TraitRecombining extends AbstractTrait
{
	public TraitRecombining()
	{
		super(nameTraitRecombining,colorTraitRecombining);
	}

	@Override
	public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected)
	{
		if(!world.isRemote && canTick(world,160,8) && entity instanceof EntityPlayer)
		{
			int durNow= ToolHelper.getCurrentDurability(tool);
			int durMax= ToolHelper.getMaxDurability(tool);

			if(durNow >= durMax / 5 * 4) return;

			EntityPlayer player=(EntityPlayer)entity;
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
