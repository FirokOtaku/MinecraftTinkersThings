package firok.mtim.common;

import firok.mtim.MoreTinkersMaterials;
import firok.mtim.util.Reg;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.lang.reflect.Field;

@Mod.EventBusSubscriber
public class RegistryHandler
{
	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event)
	{
		int countItem=0,countItemBlock=0;
		IForgeRegistry<Item> registry=event.getRegistry();
		Field[] fieldsItems=Items.class.getDeclaredFields();
		for(Field field:fieldsItems)
		{
			try
			{
				Reg reg=field.getAnnotation(Reg.class);
				Object obj=field.get(null);
				if(reg!=null && obj instanceof Item)
				{
					String regValue=reg.value();
					String regTn=reg.tn();
					String regUn=reg.un();

					String tn= regValue.length()>0? regValue: regTn;
					String un= regValue.length()>0? regValue: regUn;

					Item item=(Item)obj;
					item.setUnlocalizedName(un);
					item.setRegistryName(MoreTinkersMaterials.MOD_ID,tn);

					registry.register(item);
				}
				countItem++;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		Field[] fieldsBlocks=Blocks.class.getDeclaredFields();
		for(Field field:fieldsBlocks)
		{
			try
			{
				Reg reg=field.getAnnotation(Reg.class);
				Object obj=field.get(null);
				if(reg!=null && obj instanceof Block)
				{
					String regValue=reg.value();
					String regTn=reg.tn();
					String regUn=reg.un();

					String tn= regValue.length()>0? regValue: regTn;
					String un= regValue.length()>0? regValue: regUn;

					Block block=(Block)obj;
					ItemBlock itemBlock=new ItemBlock(block);
					itemBlock.setUnlocalizedName(un);
					itemBlock.setRegistryName(MoreTinkersMaterials.MOD_ID,tn);

					registry.register(itemBlock);
				}
				countItemBlock++;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		MoreTinkersMaterials.log(String.format("register items: item[%d/%d] item_block[%d/%d]",countItem,fieldsItems.length,countItemBlock,fieldsBlocks.length) );
	}

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event)
	{
		IForgeRegistry<Block> registry=event.getRegistry();
		Field[] fields=Blocks.class.getDeclaredFields();
		int countBlock=0;
		for(Field field:fields)
		{
			try
			{
				Reg reg=field.getAnnotation(Reg.class);
				Object obj=field.get(null);
				if(reg!=null && obj instanceof Block)
				{
					String regValue=reg.value();
					String regTn=reg.tn();
					String regUn=reg.un();

					String tn= regValue.length()>0? regValue: regTn;
					String un= regValue.length()>0? regValue: regUn;

					Block block=(Block)obj;
					block.setUnlocalizedName(un);
					block.setRegistryName(MoreTinkersMaterials.MOD_ID,tn);

					registry.register(block);
				}
				countBlock++;
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		MoreTinkersMaterials.log(String.format("register blocks: block[%d/%d]",countBlock,fields.length) );
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
