package firok.tiths.item;

import firok.tiths.TithsModule;
import slimeknights.mantle.registration.object.ItemObject;

public class TithsItems extends TithsModule
{
	public static ItemObject<ItemMaterial> materialPhaseCrystal
			= ITEMS.register("phase_crystal", ItemMaterial::new);


}
