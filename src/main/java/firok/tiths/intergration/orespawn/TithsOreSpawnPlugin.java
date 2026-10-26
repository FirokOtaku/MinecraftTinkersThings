package firok.tiths.intergration.orespawn;

import com.mcmoddev.orespawn.api.os3.OS3API;
import com.mcmoddev.orespawn.api.plugin.IOreSpawnPlugin;
import com.mcmoddev.orespawn.api.plugin.OreSpawnPlugin;
import firok.tiths.TinkersThings;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.Level;

import java.io.File;
import java.io.InputStream;

@SuppressWarnings("unused")
// resourcePath must NOT start with '/'; OreSpawn builds "assets/{modid}/{resourcePath}"
// and a leading slash produces "assets/tiths//..." which ClassLoader cannot resolve.
@OreSpawnPlugin(modid = TinkersThings.MOD_ID, resourcePath = "configs")
public class TithsOreSpawnPlugin implements IOreSpawnPlugin
{

    private static final String EMBEDDED_CONFIG = "/assets/tiths/configs/worldgens.json";

    private static void ensureConfigExported()
    {
        File target = new File("config/orespawn3/tiths.json");
        if (target.exists())
        {
            return;
        }
        InputStream in = null;
        try
        {
            in = TithsOreSpawnPlugin.class.getResourceAsStream(EMBEDDED_CONFIG);
            if (in == null)
            {
                TinkersThings.log("OreSpawn config missing in jar: " + EMBEDDED_CONFIG, Level.WARN);
                return;
            }
            File parent = target.getParentFile();
            if (parent != null && !parent.exists() && !parent.mkdirs())
            {
                TinkersThings.log("Failed to create OreSpawn config dir: " + parent, Level.WARN);
                return;
            }
            FileUtils.copyInputStreamToFile(in, target);
            TinkersThings.log("Exported OreSpawn config to " + target.getPath());
        }
        catch (Exception e)
        {
            TinkersThings.log("Failed to export OreSpawn config: " + e.getMessage(), Level.WARN);
        }
        finally
        {
            IOUtils.closeQuietly(in);
        }
    }

    @Override
    public void register(final OS3API api)
    {
        // Fallback for FG5/source runs where OreSpawn's ClassLoader may miss this mod's assets.
        // Runs after PluginLoader.scanResources and before API.loadConfigFiles.
        ensureConfigExported();

        // ohh, so much bugs (x) features (√)
        // bug generator (x) feature generator (√)
        api.addFeature("tiths-crystal-cave", new CrystalCaveFeatureGenerator());
        api.addFeature("tiths-cloud", new CloudFeatureGenerator());
        api.addFeature("tiths-seabed", new SeabedFeatureGenerator());
        api.addFeature("tiths-tree-root", new TreeRootFeatureGenerator());

    }
}
