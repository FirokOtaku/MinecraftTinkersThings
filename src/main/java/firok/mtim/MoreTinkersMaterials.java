package firok.mtim;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(
		modid = MoreTinkersMaterials.MOD_ID,
		name = MoreTinkersMaterials.MOD_NAME,
		version = MoreTinkersMaterials.VERSION
)
public class MoreTinkersMaterials
{

	public static final String MOD_ID = "mtim";
	public static final String MOD_NAME = "MoreTinkersMaterials";
	public static final String VERSION = "0.1.1";

	@Mod.Instance(MOD_ID)
	public static MoreTinkersMaterials INSTANCE;

	/**
	 * This is the first initialization event. Register tile entities here.
	 * The registry events below will have fired prior to entry to this method.
	 */
	@Mod.EventHandler
	public void preinit(FMLPreInitializationEvent event)
	{
//		slimeknights.tconstruct.TConstruct.
	}

	/**
	 * This is the second initialization event. Register custom recipes
	 */
	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{

	}

	/**
	 * This is the final initialization event. Register actions from other mods here
	 */
	@Mod.EventHandler
	public void postinit(FMLPostInitializationEvent event)
	{

	}
}
