package firok.tiths.effect;

import firok.tiths.TithsModule;
import firok.tiths.util.DevUse;
import net.minecraft.potion.Effect;
import net.minecraftforge.fml.RegistryObject;

import java.util.function.Supplier;

public class TithsEffects extends TithsModule
{
	@DevUse(isPlaceholder = true)
	public static RegistryObject<EffectAcidWetted> EFFECT_ACID_WETTED = register("acid_wetted", EffectAcidWetted::new);

	@DevUse(isPlaceholder = true)
	public static RegistryObject<EffectArmorSoftened> EFFECT_ARMOR_SOFTENED = register("armor_softened", EffectArmorSoftened::new);

	@DevUse(isPlaceholder = true)
	public static RegistryObject<EffectAvatar> EFFECT_AVATAR = register("avatar", EffectAvatar::new);

	@DevUse(isPlaceholder = true)
	public static RegistryObject<EffectEddying> EFFECT_EDDYING = register("eddying", EffectEddying::new);

	@DevUse(isPlaceholder = true)
	public static RegistryObject<EffectEstrous> EFFECT_ESTROUS = register("estrous", EffectEstrous::new);

	@DevUse(isPlaceholder = true)
	public static RegistryObject<EffectHeavy> EFFECT_HEAVY = register("heavy", EffectHeavy::new);

	@DevUse(isPlaceholder = true)
	public static RegistryObject<EffectHyper> EFFECT_HYPER = register("hyper", EffectHyper::new);

	@DevUse(isPlaceholder = true)
	public static RegistryObject<EffectIcy> EFFECT_ICY = register("icy", EffectIcy::new);

	@DevUse(isPlaceholder = true)
	public static RegistryObject<EffectLionHeart> EFFECT_LION_HEART = register("lion_heart", EffectLionHeart::new);

	@DevUse(isPlaceholder = true)
	public static RegistryObject<EffectParalysed> EFFECT_PARALYSED = register("paralysed", EffectParalysed::new);

	@DevUse(isPlaceholder = true)
	public static RegistryObject<EffectPestilential.Pestilential> EFFECT_PESTILENTIAL = register("Pestilential", EffectPestilential.Pestilential::new);
	@DevUse(isPlaceholder = true)
	public static RegistryObject<EffectPestilential.WeakPestilential> EFFECT_WEAKENED_PESTILENTIAL = register("WeakPestilential", EffectPestilential.WeakPestilential::new);

	@DevUse(isPlaceholder = true)
	public static RegistryObject<EffectRooted> EFFECT_ROOTED = register("rooted", EffectRooted::new);

	@DevUse(isPlaceholder = true)
	public static RegistryObject<EffectSoluble> EFFECT_SOLUBLE = register("soluble", EffectSoluble::new);

	@DevUse(isPlaceholder = true)
	public static RegistryObject<EffectTranquilized> EFFECT_TRANQUILIZED = register("tranquilized", EffectTranquilized::new);

	@DevUse(isPlaceholder = true)
	public static RegistryObject<EffectTurbulent> EFFECT_TURBULENT = register("turbulent", EffectTurbulent::new);

	@DevUse(isPlaceholder = true)
	public static RegistryObject<EffectVoidInfected> EFFECT_VOID_INFECTED = register("void_infected", EffectVoidInfected::new);

	private static <T extends Effect> RegistryObject<T> register(String id, Supplier<T> effect)
	{
		return POTIONS.register(id, effect);
	}
}
