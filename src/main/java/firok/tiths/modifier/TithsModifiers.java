package firok.tiths.modifier;

import net.minecraftforge.fml.RegistryObject;
import slimeknights.tconstruct.common.TinkerModule;

public class TithsModifiers extends TinkerModule {
    public static final RegistryObject<ModifierTest> MODIFIER_TEST = MODIFIERS.register("test_modifier", ModifierTest::new);

    public static final RegistryObject<ModifierAquatic> MODIFIER_AQUATIC = MODIFIERS.register("auqatic_modifier", ModifierAquatic::new);
}
