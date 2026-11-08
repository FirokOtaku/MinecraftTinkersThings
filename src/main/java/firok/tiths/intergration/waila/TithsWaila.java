package firok.tiths.intergration.waila;

import firok.tiths.TinkersThings;
import net.minecraftforge.fml.common.event.FMLInterModComms;

/**
 * Hwyla / Waila 集成入口. 通过 IMC 注册, 避免主类硬依赖.
 */
public final class TithsWaila
{
    public static final String MODID = "waila";

    private TithsWaila() {}

    public static void register()
    {
        FMLInterModComms.sendMessage(MODID, "register",
                "firok.tiths.intergration.waila.TithsWailaRegistrar.wailaCallback");
        TinkersThings.log("Hwyla: crystallizer provider registered");
    }
}
