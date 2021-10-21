package firok.tiths.config;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

import java.util.ArrayList;

/**
 * @author DustW
 * @since 0.4.1
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ConfigListenerManager {
    private static final ArrayList<IListenableValue> LISTENERS = new ArrayList<>();

    public static <T extends IListenableValue> T addListener(T value) {
        LISTENERS.add(value);
        return value;
    }

    @SubscribeEvent
    public static void onLoadConfig(ModConfig.Loading event) {
        LISTENERS.forEach(IListenableValue::onLoad);
    }

    @SubscribeEvent
    public static void onReloadConfig(ModConfig.Reloading event) {
        LISTENERS.forEach(IListenableValue::onReload);
    }
}
