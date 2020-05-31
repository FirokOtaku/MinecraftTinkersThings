package firok.tiths.common;

import firok.tiths.TinkersThings;
import firok.tiths.util.InnerActions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.audio.SoundManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import org.apache.logging.log4j.Level;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = TinkersThings.MOD_ID)
public final class SoundEvents
{
	private SoundEvents() {}

	private static final List<SoundEvent> sounds = new ArrayList<>();

	public static final SoundEvent recordTinkersEfforts=$(Keys.soundTinkersEfforts);
	public static final SoundEvent recordTinkersWill=$(Keys.soundTinkersWill);
	public static final SoundEvent recordTinkersImagination=$(Keys.soundTinkersImagination);
	public static final SoundEvent effectHeal=$(Keys.soundHealEffect);
	public static final SoundEvent effectShake=$(Keys.soundShakeEffect);
	public static final SoundEvent effectFire=$(Keys.soundFireEffect);
	public static final SoundEvent effectSwing=$(Keys.soundSwingEffect);
	public static final SoundEvent effectTransforming=$(Keys.soundTransformingEffect);
	public static final SoundEvent effectForward=$(Keys.soundForwardEffect);

	/**
	 @see net.minecraft.util.SoundCategory
	 */
	public static SoundEvent $(String key)
	{
		ResourceLocation resource=new ResourceLocation(TinkersThings.MOD_ID,key);
		SoundEvent sound=new SoundEvent(resource);
		sound.setRegistryName(TinkersThings.MOD_ID,key);
		sounds.add(sound);
		return sound;
	}

	@SubscribeEvent
	public static void registerSoundEvent(RegistryEvent.Register<SoundEvent> event) {
		IForgeRegistry<SoundEvent> registry = event.getRegistry();

		// SoundManager sndManager;
//		try
//		{
//			SoundManager manager=(SoundManager) InnerActions.get(SoundHandler.class,"sndManager",Minecraft.getMinecraft().getSoundHandler());
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//		}

		sounds.forEach(registry::register);
	}
}
