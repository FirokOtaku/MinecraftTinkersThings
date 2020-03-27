package firok.tiths.common;

import firok.tiths.TinkersThings;
import net.minecraftforge.common.config.Config;

import static net.minecraftforge.common.config.Config.*;

// 配置文件
@Config(modid= TinkersThings.MOD_ID)
@LangKey("config.tiths.general")
public class Configs
{
	@Comment("Enable features of Tinkers' Armory")
	@RequiresMcRestart
	public static boolean enable_conarm=true;

	@Comment("Enable easy crafting of royal alloy ( enabled : iron + gold ; disabled : iron + gold + milk )")
	@RequiresMcRestart
	public static boolean enable_royal_alloy_easy_crafting=false;
}
