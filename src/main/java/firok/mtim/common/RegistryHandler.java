package firok.mtim.common;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

@Mod.EventBusSubscriber
public class RegistryHandler
{
	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event)
	{
		;
	}

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event)
	{
		event.getRegistry();
	}

	private static class RegHelper<T extends IForgeRegistryEntry<T>>
	{
		IForgeRegistry<T> registry;
		public RegHelper(IForgeRegistry<T> registry)
		{
			this.registry=registry;
		}
		public boolean reg(IForgeRegistryEntry<T> regEntry)
		{
			try
			{
				;

				return true;
			}
			catch (Exception e)
			{
				e.printStackTrace();
				return false;
			}
		}
	}
}
