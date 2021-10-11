package firok.tiths.material;

import firok.tiths.TinkersThings;
import firok.tiths.util.DevUse;
import net.minecraft.util.ResourceLocation;
import slimeknights.tconstruct.library.materials.definition.MaterialId;

import java.util.function.Supplier;

public class TithsMaterialIds
{
	@DevUse
	private static MaterialId createMaterial(String name) {
		return new MaterialId(new ResourceLocation(TinkersThings.MOD_ID, name));
	}

	/* === material ids === */

	@DevUse(isPlaceholder = true)
	public static final MaterialId ID_TEST = createMaterial("test");

	@DevUse(isPlaceholder = true)
	public static final MaterialId ID_BIREFRINGENT = createMaterial("birefringent");

	@DevUse(isPlaceholder = true)
	public static final MaterialId ID_BLOWING = createMaterial("blowing");

	@DevUse(isPlaceholder = true)
	public static final MaterialId ID_AQUATIC = createMaterial("aquatic");

	@DevUse(isPlaceholder = true)
	public static final MaterialId ID_CARBONIZING = createMaterial("carbonizing");

	@DevUse(isPlaceholder = true)
	public static final MaterialId ID_CHAMPING = createMaterial("champing");

	@DevUse(isPlaceholder = true)
	public static final MaterialId ID_CHEMICAL_INSTABLE = createMaterial("chemical_instable");

	@DevUse(isPlaceholder = true)
	public static final MaterialId ID_CHERISHING = createMaterial("cherishing");

	@DevUse(isPlaceholder = true)
	public static final MaterialId ID_COMBUSTION_SUPPORTING = createMaterial("combustion_supporting");

	@DevUse(isPlaceholder = true)
	public static final MaterialId ID_CREAKY = createMaterial("creaky");

	@DevUse(isPlaceholder = true)
	public static final MaterialId ID_DECOYING = createMaterial("decoying");

	@DevUse(isPlaceholder = true)
	public static final MaterialId ID_DEGENERATING = createMaterial("degenerating");

	@DevUse(isPlaceholder = true)
	public static final MaterialId ID_DICHROIC = createMaterial("dichroic");

	@DevUse(isPlaceholder = true)
	public static final MaterialId ID_DRAGON_KILLER = createMaterial("dragon_killer");





}
