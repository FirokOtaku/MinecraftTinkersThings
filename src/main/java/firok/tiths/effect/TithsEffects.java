package firok.tiths.effect;

import firok.tiths.TithsModule;
import firok.tiths.util.DevUse;
import net.minecraft.potion.Effect;
import net.minecraftforge.fml.RegistryObject;

import java.util.function.Supplier;

public class TithsEffects extends TithsModule
{
	@DevUse(isPlaceholder = true)
	public static RegistryObject<EffectAvatar> EFFECT_AVATAR = register("avatar", EffectAvatar::new);

	@DevUse(isPlaceholder = true)
	public static RegistryObject<EffectEddying> EFFECT_EDDYING = register("eddying", EffectEddying::new);

	@DevUse(isPlaceholder = true)
	public static RegistryObject<EffectEstrous> EFFECT_ESTROUS = register("estrous", EffectEstrous::new);

	@DevUse(isPlaceholder = true)
	public static RegistryObject<EffectHeavy> EFFECT_HEAVY = register("heavy", EffectHeavy::new);

	@DevUse(isPlaceholder = true)
	public static RegistryObject<EffectPestilential.pestilential> EFFECT_PESTILENTIAL = register("pestilential", EffectPestilential.pestilential::new);
	@DevUse(isPlaceholder = true)
	public static RegistryObject<EffectPestilential.weak_pestilential> EFFECT_WEAKENED_PESTILENTIAL = register("weak_pestilential", EffectPestilential.weak_pestilential::new);

	private static <T extends Effect> RegistryObject<T> register(String id, Supplier<T> effect)
	{
		return POTIONS.register(id, effect);
	}
}
