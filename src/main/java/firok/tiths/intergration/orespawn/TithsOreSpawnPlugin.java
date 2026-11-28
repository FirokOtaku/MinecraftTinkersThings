package firok.tiths.intergration.orespawn;

import com.mcmoddev.orespawn.api.os3.OS3API;
import com.mcmoddev.orespawn.api.plugin.IOreSpawnPlugin;
import com.mcmoddev.orespawn.api.plugin.OreSpawnPlugin;
import firok.tiths.TinkersThings;
import net.minecraftforge.fml.common.Loader;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * 注册 OreSpawn 兼容生成器和高空雾玉云层。
 * 调整地底矿物的世界生成规则时，请编辑
 * {@code src/main/resources/assets/tiths/orespawn/provider.json}。
 * 如需根据代码重新生成该文件，请运行 {@link firok.tiths.$Ignore_OreSpawnProviderGenerator}
 * 或 Gradle 任务 {@code generateOreSpawnProvider}。
 */
@SuppressWarnings("unused")
// Keep OS3 resource discovery separate from the native OS4 provider directory.
@OreSpawnPlugin(modid = TinkersThings.MOD_ID, resourcePath = "configs")
public class TithsOreSpawnPlugin implements IOreSpawnPlugin
{
    @Override
    public void register(final OS3API api)
    {
        archiveStockOs3Config();

        // Keep the OS3 feature names for existing Forge registry snapshots.
        api.addFeature("tiths-crystal-cave", new CrystalCaveFeatureGenerator());
        api.addFeature("tiths-cloud", new CloudFeatureGenerator());
        api.addFeature("tiths-seabed", new SeabedFeatureGenerator());
        api.addFeature("tiths-tree-root", new TreeRootFeatureGenerator());

        // OS4 ore providers cannot replace air. The bridge schedules custom
        // programmatic spawns without translating them into ordinary ore veins.
        api.addSpawn(api.getSpawnBuilder()
                .setName("tiths:brume_jade_cloud")
                .setEnabled(true)
                .setDimensions(api.getDimensionBuilder().create())
                .setBiomes(api.getBiomeBuilder().setAcceptAll().create())
                .setFeature(api.getFeatureBuilder().setFeature("tiths-cloud")
                        .setParameter("minHeight", 140)
                        .setParameter("maxHeight", 160)
                        .setParameter("radiusX", 10)
                        .setParameter("radiusY", 5)
                        .setParameter("radiusZ", 8)
                        .create())
                .create());
    }

    private static void archiveStockOs3Config()
    {
        Path configDir = Loader.instance().getConfigDir().toPath();
        Path legacy = configDir.resolve("orespawn3").resolve("tiths.json");
        if (!Files.isRegularFile(legacy)) return;

        try
        {
            // Match the exact bundled 0.3.32–0.3.47 defaults, allowing different line endings.
            String json = new String(Files.readAllBytes(legacy), StandardCharsets.UTF_8).replace("\r\n", "\n").trim();
            if (!DigestUtils.sha256Hex(json).equals("ce7b7eb2f2c68ffa2da11726f6ae69959eff26aecec35d36068f138c5858f740")) return;

            Path backupDir = configDir.resolve("orespawn-migration");
            Files.createDirectories(backupDir);
            Path backup = backupDir.resolve("tiths-os3-default.json");
            Files.move(legacy, backup);
            LogManager.getLogger(TinkersThings.MOD_ID).info("Archived the old TITHS ore defaults to {}", backup);
        }
        catch (IOException e)
        {
            LogManager.getLogger(TinkersThings.MOD_ID).warn("Could not archive the old TITHS ore defaults", e);
        }
    }
}
