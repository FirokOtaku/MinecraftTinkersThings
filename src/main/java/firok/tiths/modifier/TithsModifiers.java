package firok.tiths.modifier;

import firok.tiths.modifier.general.*;
import net.minecraftforge.fml.RegistryObject;
import slimeknights.tconstruct.common.TinkerModule;

public class TithsModifiers extends TinkerModule {
    public static final RegistryObject<ModifierTest> MODIFIER_TEST
            = MODIFIERS.register("test_modifier", ModifierTest::new);

    public static final RegistryObject<ModifierAquatic> MODIFIER_AQUATIC
            = MODIFIERS.register("auqatic_modifier", ModifierAquatic::new);

    public static final RegistryObject<ModifierBirefringent> MODIFIER_BIREFRINGENT
            = MODIFIERS.register("birefringent_modifier", ModifierBirefringent::new);

    public static final RegistryObject<ModifierBlowing> MODIFIER_BLOWING
            = MODIFIERS.register("blowing_modifier", ModifierBlowing::new);

    public static final RegistryObject<ModifierCarbonizing> MODIFIER_CARBONIZING
            = MODIFIERS.register("carbonizing", ModifierCarbonizing::new);

    public static final RegistryObject<ModifierChamping> MODIFIER_CHAMPING
            = MODIFIERS.register("champing", ModifierChamping::new);
    public static final RegistryObject<ModifierChemicalInstable> MODIFIER_CHEMICAL_INSTABLE
            = MODIFIERS.register("chemical_instable", ModifierChemicalInstable::new);

    public static final RegistryObject<ModifierCherishing> MODIFIER_CHERISHING
            = MODIFIERS.register("cherishing", ModifierCherishing::new);
    public static final RegistryObject<ModifierCombustionSupporting> MODIFIER_COMBUSTION_SUPPORTING
            = MODIFIERS.register("combustion_supporting", ModifierCombustionSupporting::new);
    public static final RegistryObject<ModifierCreaky> MODIFIER_CREAKY
            = MODIFIERS.register("creaky", ModifierCreaky::new);
    public static final RegistryObject<ModifierDecoying> MODIFIER_DECOYING
            = MODIFIERS.register("decoying", ModifierDecoying::new);
    public static final RegistryObject<ModifierDegenerating> MODIFIER_DEGENERATING
            = MODIFIERS.register("degenerating", ModifierDegenerating::new);
    public static final RegistryObject<ModifierDichroic> MODIFIER_DICHROIC
            = MODIFIERS.register("dichroic", ModifierDichroic::new);
    public static final RegistryObject<ModifierDragonKiller> MODIFIER_DRAGON_KILLER
            = MODIFIERS.register("dragon_killer", ModifierDragonKiller::new);
}