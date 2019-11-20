package firok.mtim.util;

public interface Keys
{
	String prefMolten="molten_";
	String prefBlock="block_";
	String prefOre="ore_";
	String prefIngot="ingot_";

	String nameRoyalAlloy="royal_alloy";
	String nameStellar="stellar";
	String nameInkPowder="ink_powder";
	String nameCinnabar="cinnabar";
	String nameSpiderLeg="spider_leg";
	String nameHardSpiderLeg="hard_spider_leg";
	String nameSkyCrystal="sky_crystal";
	String nameStormCrystal="storm_crystal";
	String namePhantomCrystal="phantom_crystal";
	String nameAntiGravCrystal="anti_grav_crystal";
	String nameGlass= "glass";
	String nameConsolidatedGlass= "consolidated_glass";
	String nameBrokenIce="broken_ice";

	String moltenRoyalAlloy=prefMolten+nameRoyalAlloy;
	String moltenStellar=prefMolten+nameStellar;

	String blockStellar=prefBlock+nameStellar;
	String blockInkPowder=prefBlock+nameInkPowder;
	String blockCinnabar=prefBlock+nameCinnabar;

	String oreCinnabar=prefOre+nameCinnabar;
	String oreInkPowder=prefOre+nameInkPowder;

	String ingotStellar=prefIngot+nameStellar;
	String ingotRoyalAlloy=prefIngot+nameRoyalAlloy;

	int colorStellar=Colors.Tomato;
	int colorRoyalAlloy=Colors.Yellow;
	int colorSpiderLeg =Colors.IndianRed;
	int colorHardSpiderLeg=Colors.IndianRed;
	int colorCinnabar=Colors.Silver;
	int colorGlass=Colors.WhiteSmoke;
	int colorConsolidatedGlass=Colors.WhiteSmoke;
	int colorBrokenIce=Colors.Snow;

	String nameTraitIcy="icy";
	String nameTraitLuxurious="luxurious";
	String nameTraitRadiant="radiant";
	String nameTraitSwitching="switching";

	int colorTraitIcy=Colors.Snow;
	int colorTraitLuxurious=Colors.Gold;
	int colorTraitRadiant=Colors.Coral;
	int colorTraitSwitching=Colors.SeaGreen;
}
