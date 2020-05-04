package firok.tiths.traits;

import firok.tiths.TinkersThings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import slimeknights.tconstruct.library.traits.AbstractTrait;

import java.util.ListIterator;

import static firok.tiths.common.Keys.colorTraitMidasDesiring;
import static firok.tiths.common.Keys.nameTraitMidasDesiring;
import static firok.tiths.util.Predicates.canTick;
import static firok.tiths.util.Predicates.canTrigger;

// 迈达斯之欲
public class TraitMidasDesiring extends AbstractTrait
{
	public static final String NBTKey= TinkersThings.MOD_ID+'_'+nameTraitMidasDesiring;
	public TraitMidasDesiring()
	{
		super(nameTraitMidasDesiring, colorTraitMidasDesiring);
	}

	@Override
	public void blockHarvestDrops(ItemStack tool, BlockEvent.HarvestDropsEvent event)
	{
		if(!event.getHarvester().world.isRemote)
		{
			ListIterator<ItemStack> iter=event.getDrops().listIterator();

			while(iter.hasNext())
			{
				ItemStack stackOriginal=iter.next();
				if(stackOriginal.getItem()==Item.getItemFromBlock(Blocks.GOLD_ORE))
				{
					iter.set(new ItemStack(Items.GOLD_INGOT,stackOriginal.getCount() * 2));
				}
				else if(canTrigger(event.getWorld(),0.04f))
				{
					iter.set(new ItemStack(Items.GOLD_INGOT,stackOriginal.getCount()));
				}
			}
		}
	}

	@Override
	public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected)
	{
		if(!world.isRemote && isSelected && entity instanceof EntityPlayer && canTick(world,160,5))
		{
			NBTTagCompound nbt=tool.getTagCompound();
			if(nbt==null) nbt=new NBTTagCompound();

			int lastPoint=nbt.hasKey(NBTKey)?nbt.getInteger(NBTKey):0;
			if(lastPoint<=0)
			{
				EntityPlayer player=(EntityPlayer)entity;
				InventoryPlayer inv=player.inventory;
				// 在背包里找金锭 // todo 以后可能改成吃各种金制物品
				FIND_GOLD_INGOT_TO_REMOVE:for(int slot=0;slot<inv.getSizeInventory();slot++)
				{
					ItemStack stack= inv.getStackInSlot(slot);
					Item item=stack.getItem();
					if(item==Items.GOLD_INGOT)
					{
						int count=stack.getCount();

						if(count>1) stack.setCount(count-1);
						else inv.removeStackFromSlot(slot);

						lastPoint=7; // 如果找不到的话就一直找

						break FIND_GOLD_INGOT_TO_REMOVE;
					}
				}
			}
			else // lastPoint > 0
			{
				lastPoint--;
			}
			nbt.setInteger(NBTKey,lastPoint);

			tool.setTagCompound(nbt);
		}
	}
}
