package firok.mtim;

import firok.mtim.common.Alloys;
import firok.mtim.common.Fluids;
import firok.mtim.common.TCMaterials;
import firok.mtim.common.Traits;
import firok.mtim.world.WorldGen;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

@Mod(
		modid = MoreTinkersMaterials.MOD_ID,
		name = MoreTinkersMaterials.MOD_NAME,
		version = MoreTinkersMaterials.VERSION,
		dependencies = "required-after:tconstruct@[1.12.2-2.13.0.171,);required-after:mantle@[1.12-1.3.3.56,)"
)
public class MoreTinkersMaterials
{

	public static final String MOD_ID = "mtim";
	public static final String MOD_NAME = "MoreTinkersMaterials";
	public static final String VERSION = "1.12.2-0.1.4";

	@Mod.Instance(MOD_ID)
	public static MoreTinkersMaterials INSTANCE;

	private static Logger logger;
	public static void log(String content)
	{
		logger.log(Level.INFO,content);
	}

	/**
	 * This is the first initialization event. Register tile entities here.
	 * The registry events below will have fired prior to entry to this method.
	 */
	@Mod.EventHandler
	public void preinit(FMLPreInitializationEvent event)
	{
		logger = event.getModLog();
		Fluids.register();
		Traits.register();

		//
		//  Blocks.register(false);
		//  Blocks.registerItems();
		//  Items.register();
		//
		//  proxy.initConfig();
		//
		TCMaterials.packMaterials();
	}

	/**
	 * This is the second initialization event. Register custom recipes
	 */
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
		Alloys.registerAlloys();
//		TCMaterials.packMaterials();
		//
		//  SmeltingRegistry.register(); // Registers smelting recipes
		//  CraftingRegistry.register(); // Registers crafting recipes
		//
		//  for (MaterialIntegration m : integrateList) {
		//      m.integrate();
		//  }
		TCMaterials.integrateAll();
	}

	/**
	 * This is the final initialization event. Register actions from other mods here
	 */
	@Mod.EventHandler
	public void postinit(FMLPostInitializationEvent event)
	{
//		proxy.registerBookPages();
	}


}
