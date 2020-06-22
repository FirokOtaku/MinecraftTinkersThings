package firok.tiths.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import static firok.tiths.util.Predicates.canTick;

/**
 * 氧气面罩
 */
public class ItemOxygenMask extends ItemArmor
{
	public ItemOxygenMask()
	{
		super(ArmorMaterial.IRON,2, EntityEquipmentSlot.HEAD);
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack helmet)
	{
		if(!world.isRemote && canTick(world,20,0))
		{
			InventoryPlayer inv=player.inventory;
			final int size=inv.getSizeInventory();
			for(int i=0;i<size;i++)
			{
				ItemStack stack=inv.getStackInSlot(i);
				if(stack.isEmpty()) continue;

				Item item=stack.getItem();
				if(!(item instanceof IAirSupply)) continue;

				IAirSupply airSupply=(IAirSupply)item;
				if(!airSupply.canAutoSupply(stack,helmet,player)) continue;

				airSupply.onAirSupply(stack,player);
				break;
			}
		}
	}
}
