package firok.tiths.event;

import firok.tiths.TinkersThings;
import firok.tiths.item.ItemEnderResonanceCube;
import firok.tiths.item.TithsItems;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = TinkersThings.MOD_ID)
public class PlayerEvents
{
	@SubscribeEvent
	public static void onItemPickup(EntityItemPickupEvent event)
	{
		PlayerEntity player = event.getPlayer();
		ItemEntity ie = event.getItem();

		PROCESS_ENDER_RESONANCE_STONE: // 末影谐振之石
		{
			if(player == null || ie == null) break PROCESS_ENDER_RESONANCE_STONE;

			ItemStack stack = ie.getItem();
			Item itemEnderResonanceStone = TithsItems.enderResonanceCube.get();

			PlayerInventory inv = player.inventory;
			for(int step = 0; step < inv.getSizeInventory(); step++)
			{
				ItemStack stackOld = inv.getStackInSlot(step);

				if(stackOld.getItem() != itemEnderResonanceStone) continue;

				stack = ItemEnderResonanceCube.addItem(stack, stackOld, 10);

				if(stack == ItemStack.EMPTY) // 成功叠加
					break;
			}

			if(stack == ItemStack.EMPTY)
			{
				System.out.println("取消事件");
				ie.remove();
				event.setCanceled(true);
				event.setResult(Event.Result.DENY);
			}
			else
				ie.setItem(stack);
		}
	}
}
