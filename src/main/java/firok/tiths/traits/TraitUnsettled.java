package firok.tiths.traits;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.tools.ToolCore;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.ToolHelper;

import static firok.tiths.common.Keys.colorTraitUnsettled;
import static firok.tiths.common.Keys.nameTraitUnsettled;
import static firok.tiths.util.Predicates.canTick;

// 不安定
public class TraitUnsettled extends AbstractTrait
{
	public TraitUnsettled()
	{
		super(nameTraitUnsettled, colorTraitUnsettled);
	}

	@Override
	public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected)
	{
		if(!world.isRemote && entity instanceof EntityPlayer && canTick(world,80,5))
		{
			int durability=ToolHelper.getDurabilityStat(tool);
			int durabilityMax=ToolHelper.getMaxDurability(tool);
			if(durability>=durabilityMax) return;

			EntityPlayer player=(EntityPlayer)entity;
			InventoryPlayer inv= player.inventory;
			int size=inv.getSizeInventory();
			while(size-->0)
			{
				ItemStack stack=inv.getStackInSlot(size);
				if(stack.isEmpty()) continue;

				Item item=stack.getItem();

				if(!(item instanceof ToolCore) || ToolHelper.isBroken(stack)) continue;

				ToolHelper.damageTool(stack,2,player);
				durability++;
				if(durability>=durabilityMax) break;
				ToolHelper.healTool(stack,1,player);
			}
		}
	}
}
