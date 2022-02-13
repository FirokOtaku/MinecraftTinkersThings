package firok.tiths.data;

import firok.tiths.material.TithsMaterials;
import net.minecraft.data.DataGenerator;
import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;
import slimeknights.tconstruct.library.data.material.AbstractMaterialTraitDataProvider;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.library.materials.stats.IMaterialStats;
import slimeknights.tconstruct.library.materials.stats.MaterialStatsId;
import slimeknights.tconstruct.library.modifiers.Modifier;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class TithsTraitDataProvider extends AbstractMaterialTraitDataProvider {
    public TithsTraitDataProvider(DataGenerator gen, AbstractMaterialDataProvider materials) {
        super(gen, materials);
    }

    @Override
    protected void addMaterialTraits() {
        TithsMaterials.MATERIALS.stream()
                .map(Supplier::get)
                .forEach(info -> {
                    MaterialId idMaterial = info.getId();
                    List<Modifier> modifierDefault = info.getModifierDefault();
                    Map<IMaterialStats, Modifier[]> modifierStat = info.getModifierStat();

                    if(modifierDefault.size() > 0)
                    {
                        addDefaultTraits(
                                idMaterial,
                                info.getModifierDefault().toArray(new Modifier[0])
                        );
                    }
                    if(modifierStat.size() > 0)
                    {
                        Collection<IMaterialStats> stats = modifierStat.keySet();
                        for(IMaterialStats stat : stats)
                        {
                            MaterialStatsId idStat = stat.getIdentifier();
                            Modifier[] modifiers = modifierStat.get(stat);
                            addTraits(idMaterial, idStat, modifiers);
                        }
                    }
                });
    }

    @Override
    public String getName() {
        return "Trait Data Provider";
    }
}