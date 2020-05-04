package firok.tiths.common;

import firok.tiths.TinkersThings;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public final class SoundEvents
{
	private SoundEvents() {}

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
//		SoundEvent.REGISTRY.register(); // 貌似用不着注册就能用
		return sound;
	}
}
