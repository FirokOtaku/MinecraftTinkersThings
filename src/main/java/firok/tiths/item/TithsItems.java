package firok.tiths.item;

import firok.tiths.TithsModule;
import slimeknights.mantle.registration.object.ItemObject;

public class TithsItems extends TithsModule
{
	public static ItemObject<ItemMaterial> materialPhaseCrystal
			= ITEMS.register("phase_crystal", ItemMaterial::new);

	public static ItemObject<ItemEnderResonanceCube> enderResonanceCube
			= ITEMS.register("ender_resonance_cube", ItemEnderResonanceCube::new);

	public static ItemObject<ItemAdvancedMotiaCrystal> advancedMotiaCrystal
			= ITEMS.register("advanced_motia_crystal", ItemAdvancedMotiaCrystal::new);
}
