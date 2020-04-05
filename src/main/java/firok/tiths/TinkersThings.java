package firok.tiths;

import firok.tiths.common.*;
import firok.tiths.gui.Guis;
import firok.tiths.intergration.conarm.ArmorRegistryHandler;
import firok.tiths.world.WorldGen;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.traits.AbstractTrait;

import java.util.Collection;
import java.util.Random;

@Mod(
		modid = TinkersThings.MOD_ID,
		name = TinkersThings.MOD_NAME,
		version = TinkersThings.VERSION,
		dependencies = "required-after:tconstruct@[1.12.2-2.13.0.171,);" +
		               "required-after:mantle@[1.12-1.3.3.55,);" +
		               "after:baubles@[1.5.2,);" +
		               "required-after:conarm@[1.2.5,)"
)
public class TinkersThings
{
	public static final String MOD_ID = "tiths";
	public static final String MOD_NAME = "Tinkers Things";
	public static final String VERSION = "1.12.2-0.2.65.0";
	public static final boolean indev=true;

	@Mod.Instance(MOD_ID)
	public static TinkersThings INSTANCE;

	/**
	 * 一些客户端效果用的random
	 */
	public static Random randClient;

	private static Logger logger;
	public static void log(Object content)
	{
		logger.log(Level.INFO,content);
	}

	@Mod.EventHandler
	public void preinit(FMLPreInitializationEvent event)
	{
		logger = event.getModLog();

		ConfigJson.setConfigDir(Loader.instance().getConfigDir());
		if(Configs.General.enable_material_customization) ConfigJson.readMats();
		if(Configs.General.enable_ore_gen_customization) ConfigJson.readOres();

		Configs.General.enable_conarm=Loader.isModLoaded("conarm");
		if(Configs.General.enable_conarm)
		{
			log("Armor, armor, armor!");
		}
		Configs.General.enable_baubles=Loader.isModLoaded("baubles");
		if(Configs.General.enable_baubles)
		{
			log("Bauble, bauble, bauble!");
		}

		RegistryHandler.registerFluids();

		Items.trigger();
		RegistryHandler.registerBlocks(Blocks.class);
//		RegistryHandler.registerTileEntities();
		RegistryHandler.registerItems(Items.class,Blocks.class);
		RegistryHandler.registerEntities();
//		RegistryHandler.registerVillagers();

		Traits.init();
		Traits.postInit();
		RegistryHandler.registerTraits(Traits.class,AbstractTrait.class);
		if(Configs.General.enable_conarm)
		{
			ArmorRegistryHandler.registerArmorTraits();
		}

		Modifiers.log();

		RegistryHandler.registerPotions();

		//  proxy.initConfig();
		//
		RegistryHandler.registerMaterials();
		if(Configs.General.enable_conarm)
		{
			ArmorRegistryHandler.registerArmorMaterials();
		}
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

		new Guis();

		RegistryHandler.registerMaterialTraits();
		if(Configs.General.enable_conarm)
		{
			ArmorRegistryHandler.registerArmorMaterialTraits();
		}

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
//		materials=TinkerRegistry.getAllMaterials();
		randClient=new Random();

		SelfChecks.checkAll();
	}

	@Mod.EventHandler
	public void onServerStart(FMLServerStartingEvent event)
	{
		ServerDatas.init(event.getServer()); // 初始化服务端数据
	}
	@Mod.EventHandler
	public void onServerStop(FMLServerStoppedEvent event)
	{
		ServerDatas.uninit(); // 销毁服务端数据
	}

	public static void main(String...args)
	{
//		;
	}
}
