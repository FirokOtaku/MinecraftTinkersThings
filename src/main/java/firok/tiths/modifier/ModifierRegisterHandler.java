package firok.tiths.modifier;

import firok.tiths.modifier.impls.TestModifier;
import net.minecraftforge.fml.RegistryObject;
import slimeknights.tconstruct.common.TinkerModule;

public class ModifierRegisterHandler extends TinkerModule {
    public static final RegistryObject<TestModifier> TEST = MODIFIERS.register("test_modifier", TestModifier::new);
}
