package firok.tiths.common;

import firok.tiths.util.reg.FieldStream;
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
			new PotionEffect(Potions.weakened_pestilential,3600,0));
	public static final PotionType avatar=new PotionType("avatar",
			new PotionEffect(Potions.avatar,3600,0));
	public static final PotionType rooted=new PotionType("rooted",
			new PotionEffect(Potions.rooted,3600,0));
	public static final PotionType armor_softened=new PotionType("armor_softened",
			new PotionEffect(Potions.armor_softened,3600,0));

	public static final PotionType void_infected=new PotionType("void_infected",
			new PotionEffect(Potions.void_infected,3600,0));
	public static final PotionType acid_wetted=new PotionType("acid_wetted",
			new PotionEffect(Potions.acid_wetted,3600,0));
	public static final PotionType lionheart=new PotionType("lionheart",
			new PotionEffect(Potions.lionheart,3600,0));

//	public static final PotionType sun_power=new PotionType("sun_power",
//			new PotionEffect(MobEffects.STRENGTH,3600,0));
	static
	{
		addMix(WATER,Items.brokenBedrock,heavy);

		addMix(WATER,Items.treeRoot,rooted);
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
