package firok.tiths.modifier;

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
}
