package firok.tiths;

import firok.tiths.common.*;
import firok.tiths.gui.Guis;
import firok.tiths.intergration.conarm.ArmorModifiers;
import firok.tiths.intergration.conarm.ArmorRegistryHandler;
import firok.tiths.util.VersionPhase;
import firok.tiths.common.WorldGens;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.traits.AbstractTrait;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Collection;
import java.util.Random;

@Mod(
		modid = TinkersThings.MOD_ID,
		name = TinkersThings.MOD_NAME,
		version = TinkersThings.VERSION,
		dependencies = "required-after:tconstruct@[1.12.2-2.13.0.171,);" +
		               "required-after:mantle@[1.12-1.3.3.55,);" +
		               "required-after:baubles@[1.5.2,);" +
		               "after:conarm@[1.2.5,);"+
				       "after:hammercore@[1.12.2-2.0.6.17,);"+
				       "after:lux@[1.12.2,);"+
					   "required-after:orespawn@[3.3.1,)"
)
public class TinkersThings
{
	public static final String MOD_ID = "tiths";
	public static final String MOD_NAME = "Tinkers' Things";
	public static final String VERSION = "1.12.2-0.3.27.0";
	public static final VersionPhase version = VersionPhase.Alpha;

	public static final String CONARM_ID = "conarm";
	public static final String OREWPAWN_ID = "orespawn";

	@Mod.Instance(MOD_ID)
	public static TinkersThings INSTANCE;

	@SidedProxy(
			serverSide = "firok.tiths.CommonProxy",
			clientSide = "firok.tiths.ClientProxy",
			modId = MOD_ID
	)
	public static CommonProxy proxy;

	/**
	 * 一些客户端效果用的random
	 */
	public static Random randClient;

	private static Logger logger;
	public static void log(Object content)
	{
		log(content,Level.INFO);
	}
	public static void log(Object content,Level level)
	{
		logger.log(level,content);
	}

	private static boolean hasConarm=false;
	public static boolean enableConarm()
	{
		return hasConarm && Configs.General.enable_conarm;
	}
	@Mod.EventHandler
	public void preinit(FMLPreInitializationEvent event)
	{
		logger = event.getModLog();

		if(Configs.General.enable_strict_mode)
		{
			log("⚠ strict mode enabled");
		}

		ConfigJson.setConfigDir(Loader.instance().getConfigDir());
		if(Configs.General.enable_material_customization) ConfigJson.readMats();
		if(Configs.General.enable_tool_craft_functions) ConfigJson.readMFs();

		hasConarm=Loader.isModLoaded(CONARM_ID);
		if(enableConarm())
		{
			log("Armor, armor, armor!");
		}
		log("Bauble, bauble, bauble!");

		RegistryHandler.registerFluids();

		Items.trigger();

		RegistryHandler.registerBlocks(Blocks.class,TinkersThings.MOD_ID);
		RegistryHandler.registerTileEntities();
		RegistryHandler.registerItems(Items.class,Blocks.class,TinkersThings.MOD_ID);
		RegistryHandler.registerEntities();
//		RegistryHandler.registerVillagers();

		Traits.init();
		Traits.postInit();
		RegistryHandler.registerTraits(Traits.class,AbstractTrait.class);
		if(enableConarm())
		{
			ArmorRegistryHandler.registerArmorTraits();
		}

		Modifiers.log();
		if(enableConarm())
		{
			ArmorModifiers.log();
		}

		RegistryHandler.registerPotions();
		PotionTypes.registerPotionTypes();

		RegistryHandler.registerMaterials();
		if(enableConarm())
		{
			ArmorRegistryHandler.registerArmorMaterials();
		}
	}

	@SideOnly(Side.CLIENT)
	public static void tell(Object content)
	{
		try
		{
			Minecraft.getMinecraft().player.sendChatMessage(String.valueOf(content));
		} catch (Exception ignored){}
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
		//  proxy.registerModels(); // Registers models on the client side
		//  proxy.regsiterKeyBindings();
		//
		//  Fluids.registerfromItem(); // Registers some special smeltery recipes (not alloying)
//		GameRegistry.registerWorldGenerator(WorldGens.getInstance(), 0); // 临时禁用这边的世界生成器
		//  // GameRegistry.registerFuelHandler(new FuelHandler());  Registeres fuels' burn times

		NetworkRegistry.INSTANCE.registerGuiHandler(TinkersThings.INSTANCE, Guis.getInstance());

		RegistryHandler.registerMaterialTraits();
		if(enableConarm())
		{
			ArmorRegistryHandler.registerArmorMaterialTraits();
		}

		IngredientFactories.trigger();
		Craftings.registerAllCraftings();
		Alloys.registerAlloys();
		//  SmeltingRegistry.register(); // Registers smelting recipes
		RegistryHandler.integrateMaterials();
	}

//	static Collection<Material> materials=null;

	@Mod.EventHandler
	public void postinit(FMLPostInitializationEvent event)
	{
//		proxy.registerBookPages();
//		materials=TinkerRegistry.getAllMaterials();
		randClient=new Random();

		SelfChecks.checkAll();
//		log("Has everything been done? might be!");
	}

	@Mod.EventHandler
	public void onServerStart(FMLServerStartingEvent event)
	{
		event.registerServerCommand(Commands.getInstance());

		Datas.Server.init(event.getServer()); // 初始化服务端数据

//		if(Configs.General.log_chunk_generation)
//		{
//			try
//			{
//				new File("./tiths").mkdirs();
//				File file=new File("./tiths","log_world_generation_"+event.getServer().getFolderName()+"_"+System.currentTimeMillis()+".txt");
//				FileOutputStream ofs=new FileOutputStream(file);
//				WorldGens.setOutStream(ofs);
//			}
//			catch (Exception ignored) { }
//		}
	}
	@Mod.EventHandler
	public void onServerStop(FMLServerStoppedEvent event)
	{
		Datas.Server.uninit(); // 销毁服务端数据

		if(Configs.General.log_chunk_generation)
		{
			WorldGens.closeOutStream();
		}
	}

	public static void main(String[] args)
	{
		System.out.println(1);
	}


}
