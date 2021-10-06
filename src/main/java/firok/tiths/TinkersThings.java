package firok.tiths;

import firok.tiths.material.TithsMaterials;
import firok.tiths.modifier.ModifierRegisterHandler;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(TinkersThings.MOD_ID)
public class TinkersThings
{
    public static final String MOD_ID = "tiths";

    public TinkersThings() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.register(new ModifierRegisterHandler());
        bus.register(new TithsMaterials());
    }
}
