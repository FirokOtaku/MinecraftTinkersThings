package firok.tiths.common;

import firok.tiths.TinkersThings;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public class SoundEvents
{
	public static final SoundEvent record1=$("record1");

	public static SoundEvent $(String key)
	{
		SoundEvent ret=new SoundEvent(new ResourceLocation(TinkersThings.MOD_ID,key));
//		SoundEvent.REGISTRY.register(); // 貌似用不着注册就能用
		return ret;
	}
}
