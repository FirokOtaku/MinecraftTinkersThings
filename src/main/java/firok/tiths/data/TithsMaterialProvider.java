package firok.tiths.data;

import firok.tiths.material.TithsMaterials;
import net.minecraft.data.DataGenerator;
import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;

import java.util.function.Supplier;

public class TithsMaterialProvider extends AbstractMaterialDataProvider
{
    public TithsMaterialProvider(DataGenerator gen) {
        super(gen);
        gen.addProvider(new TithsMaterialStatsProvider(gen, this));
        generator.addProvider(new TithsTraitDataProvider(generator, this));
    }

    @Override
    protected void addMaterials() {
        // addMaterial(TEST, 3, ORDER_GENERAL, true, 0xFFD359);
        TithsMaterials.MATERIALS.stream().map(Supplier::get).forEach(material -> {
            if (material.isDirect()) {
                addMaterial(material.getId(), material.getTier(), material.getOrder(), material.isCraftable(), material.getColor());
            }
            else {
                addMaterial(material.getId(), material.getTier(), material.getOrder(), material.isCraftable(), material.getColor(),
                        material.isHidden(), material.getCondition(), material.getRedirect());
            }
        });
    }

    @Override
    public String getName() {
        return "Material Provider";
    }
}