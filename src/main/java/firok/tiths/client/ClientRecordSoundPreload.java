package firok.tiths.client;

import firok.tiths.TinkersThings;
import net.minecraft.client.audio.SoundManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.sound.SoundLoadEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.lang.reflect.Method;
import java.net.URL;

/**
 * 1.12.2 没有 sounds.json 的 preload 字段.
 * 在 SoundSystem 就绪时把三张唱片 ogg 预载进内存, 避免首次放入唱片机时卡顿.
 */
@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(modid = TinkersThings.MOD_ID, value = Side.CLIENT)
public final class ClientRecordSoundPreload
{
    /** 与 sounds.json 中唱片文件路径对应 (Sound#getSoundAsOggLocation) */
    private static final ResourceLocation[] RECORD_OGGS = {
            new ResourceLocation(TinkersThings.MOD_ID, "sounds/records/tinkers_efforts.ogg"),
            new ResourceLocation(TinkersThings.MOD_ID, "sounds/records/tinkers_will.ogg"),
            new ResourceLocation(TinkersThings.MOD_ID, "sounds/records/tinkers_imagination.ogg"),
    };

    private ClientRecordSoundPreload() {}

    @SubscribeEvent
    public static void onSoundLoad(SoundLoadEvent event)
    {
        try
        {
            SoundManager manager = event.getManager();
            Boolean loaded = ObfuscationReflectionHelper.getPrivateValue(
                    SoundManager.class, manager, "field_148617_f"); // loaded
            if (loaded == null || !loaded) return;

            Object sndSystem = ObfuscationReflectionHelper.getPrivateValue(
                    SoundManager.class, manager, "field_148620_e"); // sndSystem
            if (sndSystem == null) return;

            // 本环境 findMethod(Class, name, returnType, paramTypes...)
            Method getUrl = ObfuscationReflectionHelper.findMethod(
                    SoundManager.class,
                    "getURLForSoundResource",
                    URL.class,
                    ResourceLocation.class);
            Method loadSound = sndSystem.getClass().getMethod("loadSound", URL.class, String.class);

            for (ResourceLocation ogg : RECORD_OGGS)
            {
                URL url = (URL) getUrl.invoke(null, ogg);
                // identifier 需带 .ogg 后缀, 与 SoundManager.playSound 使用的 toString() 一致
                loadSound.invoke(sndSystem, url, ogg.toString());
            }

            TinkersThings.log("preloaded " + RECORD_OGGS.length + " record sound(s)");
        }
        catch (Exception e)
        {
            TinkersThings.log("failed to preload record sounds: " + e);
            e.printStackTrace();
        }
    }
}
