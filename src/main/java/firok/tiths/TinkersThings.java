package firok.tiths;

import firok.tiths.common.*;
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

import java.util.*;

@Mod(
		modid = TinkersThings.MOD_ID,
		name = TinkersThings.MOD_NAME,
		version = TinkersThings.VERSION,
		dependencies = "required-after:tconstruct@[1.12.2-2.13.0.171,);required-after:mantle@[1.12-1.3.3.55,);after:baubles@[1.5.2,)"
)
public class TinkersThings
{

	public static final String MOD_ID = "tiths";
	public static final String MOD_NAME = "Tinkers Things";
	public static final String VERSION = "1.12.2-0.2.49.0";

	@Mod.Instance(MOD_ID)
	public static TinkersThings INSTANCE;

	/**
	 * 一些客户端效果用的random
	 */
	public static Random randClient;

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
		RegistryHandler.registerBlocks();
//		RegistryHandler.registerTileEntities();
		RegistryHandler.registerItems();
		RegistryHandler.registerEntities();
//		RegistryHandler.registerVillagers();

		RegistryHandler.registerTraits();
		Modifiers.log();

		RegistryHandler.registerPotions();

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
		catch (Exception e){}
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

		Craftings.registerAllCraftings();
		Alloys.registerAlloys();
//		TCMaterials.packMaterials();
		//
		//  SmeltingRegistry.register(); // Registers smelting recipes
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
		randClient=new Random();
	}

	public static void main(String...args)
	{
//		;
	}
}
