package firok.mtim;

import firok.mtim.common.Alloys;
import firok.mtim.common.Fluids;
import firok.mtim.common.RegistryHandler;
import firok.mtim.common.Traits;
import firok.mtim.world.WorldGen;
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
		modid = MoreTinkersMaterials.MOD_ID,
		name = MoreTinkersMaterials.MOD_NAME,
		version = MoreTinkersMaterials.VERSION,
		dependencies = "required-after:tconstruct@[1.12.2-2.13.0.171,);required-after:mantle@[1.12-1.3.3.56,)"
)
public class MoreTinkersMaterials
{

	public static final String MOD_ID = "mtim";
	public static final String MOD_NAME = "MoreTinkersMaterials";
	public static final String VERSION = "1.12.2-0.1.5";

	@Mod.Instance(MOD_ID)
	public static MoreTinkersMaterials INSTANCE;

	private static Logger logger;
	public static void log(String content)
	{
		logger.log(Level.INFO,content);
	}

	@Mod.EventHandler
	public void preinit(FMLPreInitializationEvent event)
	{
		logger = event.getModLog();
		Fluids.register();
		Traits.register();

		RegistryHandler.registerBlocks(ForgeRegistries.BLOCKS);
		RegistryHandler.registerItems(ForgeRegistries.ITEMS);

		//  proxy.initConfig();
		//
		RegistryHandler.registerMaterials();
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
		System.out.println(Integer.class.isAssignableFrom(Object.class));
		System.out.println(Integer.class.isAssignableFrom(Integer.class));
		System.out.println(Object.class.isAssignableFrom(Integer.class));
	}
}
