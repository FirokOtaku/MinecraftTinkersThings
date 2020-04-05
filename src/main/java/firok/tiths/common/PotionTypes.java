package firok.tiths.common;

import firok.tiths.util.reg.FieldStream;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

import static net.minecraft.init.PotionTypes.WATER;
import static net.minecraft.potion.PotionHelper.addMix;

/**
 * 药水类型
 */
public class PotionTypes
{
	public static final PotionType heavy=new PotionType("heavy",
			new PotionEffect(Potions.heavy,3600,0));

	public static final PotionType estrous=new PotionType("estrous",
			new PotionEffect(Potions.estrous,3600,0));
	public static final PotionType pestilential=new PotionType("pestilential",
			new PotionEffect(Potions.pestilential,3600,0));
	public static final PotionType weakened_pestilential=new PotionType("weakened_pestilential",
			new PotionEffect(Potions.weakenedPestilential,3600,0));

	public static final PotionType sun_power=new PotionType("sun_power",
			new PotionEffect(MobEffects.STRENGTH,3600,0));
	static
	{
		addMix(WATER,Items.sunStone,sun_power);

		addMix(WATER,Items.brokenBedrock,heavy);
	}

	public static void registerPotionTypes()
	{
		IForgeRegistry<PotionType> registry= ForgeRegistries.POTION_TYPES;
		FieldStream.of(PotionTypes.class,null,PotionType.class)
				.forEach(((field, anno, pt) -> {
					pt.setRegistryName(field.getName());

					registry.register(pt);
				}));
	}
}
