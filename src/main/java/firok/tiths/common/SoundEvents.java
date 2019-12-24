package firok.tiths.common;

import firok.tiths.TinkersThings;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public class SoundEvents
{
	public static final SoundEvent recordTinkersEfforts=$(Keys.soundTinkersEfforts);
	public static final SoundEvent recordTinkersWill=$(Keys.soundTinkersWill);

	/**
	 @see net.minecraft.util.SoundCategory
	 */
	public static SoundEvent $(String key)
	{
		ResourceLocation resource=new ResourceLocation(TinkersThings.MOD_ID,key);
		SoundEvent sound=new SoundEvent(resource);
//		SoundEvent.REGISTRY.register(); // 貌似用不着注册就能用
		return sound;
	}
}
