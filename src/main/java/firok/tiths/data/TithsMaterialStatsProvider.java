package firok.tiths.data;

import firok.tiths.material.TithsMaterials;
import net.minecraft.data.DataGenerator;
import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;
import slimeknights.tconstruct.library.data.material.AbstractMaterialStatsDataProvider;
import slimeknights.tconstruct.library.materials.stats.IMaterialStats;

import java.util.function.Supplier;

public class TithsMaterialStatsProvider extends AbstractMaterialStatsDataProvider {
    public TithsMaterialStatsProvider(DataGenerator gen, AbstractMaterialDataProvider materials) {
        super(gen, materials);
    }

    @Override
    protected void addMaterialStats() {

        TithsMaterials.MATERIALS.stream()
                .map(Supplier::get)
                .forEach(material -> addMaterialStats(
                        material.getId(),
                        material.getModifierStat().keySet().toArray(new IMaterialStats[0])
                ));
    }

    @Override
    public String getName() {
        return "Material Stats Provider";
    }
}