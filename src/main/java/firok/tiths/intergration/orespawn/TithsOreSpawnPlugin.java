package firok.tiths.intergration.orespawn;

import com.mcmoddev.orespawn.api.os3.OS3API;
import com.mcmoddev.orespawn.api.plugin.IOreSpawnPlugin;
import com.mcmoddev.orespawn.api.plugin.OreSpawnPlugin;
import firok.tiths.TinkersThings;

@SuppressWarnings("unused")
@OreSpawnPlugin(modid = TinkersThings.MOD_ID, resourcePath = "/worldgens.json")
public class TithsOreSpawnPlugin implements IOreSpawnPlugin {

	@Override
	public void register(final OS3API api) {
		// ohh, so much bugs (x) features (√)
		// bug generator (x) feature generator (√)
		api.addFeature("tiths-crystal-cave",new CrystalCaveFeatureGenerator());
		api.addFeature("tiths-cloud",new CloudFeatureGenerator());

	}
}
