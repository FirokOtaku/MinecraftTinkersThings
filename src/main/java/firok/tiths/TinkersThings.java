package firok.tiths;

import firok.tiths.common.Alloys;
import firok.tiths.common.Craftings;
import firok.tiths.common.Modifiers;
import firok.tiths.common.RegistryHandler;
import firok.tiths.world.WorldGen;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.Material;

import java.util.Collection;

@Mod(
		modid = TinkersThings.MOD_ID,
		name = TinkersThings.MOD_NAME,
		version = TinkersThings.VERSION,
		dependencies = "required-after:tconstruct@[1.12.2-2.13.0.171,);required-after:mantle@[1.12-1.3.3.56,)"
)
public class TinkersThings
{

	public static final String MOD_ID = "tiths";
	public static final String MOD_NAME = "TinkersThings";
	public static final String VERSION = "1.12.2-0.2.18.2";


	{
//		TConstruct.pulseManager.registerPulse(new PulseTools()); // register tools pulse
	}

	@Mod.Instance(MOD_ID)
	public static TinkersThings INSTANCE;

	private static Logger logger;
	public static void log(String content)
	{
		logger.log(Level.INFO,content);
	}

	@Mod.EventHandler
	public void preinit(FMLPreInitializationEvent event)
	{
		logger = event.getModLog();
		RegistryHandler.registerFluids();

		Items.trigger();
		RegistryHandler.registerBlocks(ForgeRegistries.BLOCKS);
		RegistryHandler.registerTileEntities();
		RegistryHandler.registerItems(ForgeRegistries.ITEMS);
		RegistryHandler.registerEntities(ForgeRegistries.ENTITIES);

		RegistryHandler.registerTraits();
//		RegistryHandler.registerModifiers();
		Modifiers.log();

		RegistryHandler.registerPotions(ForgeRegistries.POTIONS);

		//  proxy.initConfig();
		//
		RegistryHandler.registerMaterials();
	}

	public static void tell(Object content)
	{
		try
		{
			Minecraft.getMinecraft().player.sendChatMessage(String.valueOf(content));
		}
		catch (Exception e)
		{
//			e.printStackTrace();
		}
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
		//  proxy.registerModels(); // Registers models on the client side
		//  proxy.regsiterKeyBindings();
		//
		//  Fluids.registerfromItem(); // Registers some special smeltery recipes (not alloying)
		GameRegistry.registerWorldGenerator(WorldGen.getInstance(), 100);
		//  // GameRegistry.registerFuelHandler(new FuelHandler());  Registeres fuels' burn times

		//  Blocks.register(true);
		//
		Craftings.registerAllCraftings();
		Alloys.registerAlloys();
//		TCMaterials.packMaterials();
		//
		//  SmeltingRegistry.register(); // Registers smelting recipes
		//  CraftingRegistry.register(); // Registers crafting recipes
		//
		//  for (MaterialIntegration m : integrateList) {
		//      m.integrate();
		//  }
		RegistryHandler.integrateMaterials();
	}

	static Collection<Material> materials=null;

	/**
	 * This is the final initialization event. Register actions from other mods here
	 */
	@Mod.EventHandler
	public void postinit(FMLPostInitializationEvent event)
	{
//		proxy.registerBookPages();
		materials=TinkerRegistry.getAllMaterials();
	}

	public static void main(String...args)
	{
//		;
	}
}
