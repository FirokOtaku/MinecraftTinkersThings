package firok.tiths;

import firok.tiths.block.TithsBlocks;
import firok.tiths.config.ConfigModifier;
import firok.tiths.material.TithsMaterials;
import firok.tiths.modifier.TithsModifiers;
import firok.tiths.tile.TithsTiles;
import firok.tiths.util.VersionPhase;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(TinkersThings.MOD_ID)
public class TinkersThings
{
    public static final String MOD_ID = "tiths";
    public static final VersionPhase VERSION_PHASE = VersionPhase.Indev;

    public TinkersThings() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.register(new TithsModifiers());
        bus.register(new TithsMaterials());
        bus.register(new TithsBlocks());
        bus.register(new TithsTiles());

        TithsModule.initRegisters();

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigModifier.INSTANCE);
    }
}
