package firok.tiths.common;

import firok.tiths.intergration.conarm.util.CompoArmorCore;
import firok.tiths.intergration.conarm.util.CompoArmorPlate;
import firok.tiths.intergration.conarm.util.CompoArmorTrim;
import firok.tiths.util.$Material;
import firok.tiths.util.reg.*;
import slimeknights.tconstruct.library.materials.Material;

import static firok.tiths.common.Keys.*;
import static slimeknights.tconstruct.library.materials.Material.VALUE_Ingot;

@SuppressWarnings("all")
public final class TiCMaterials
{
	private TiCMaterials() {}

	@Compo(value = nameBlackrock,traitsTool = {nameTraitCheap,nameTraitCheapskate},traitsBow = {nameTraitCheap,nameTraitCheapskate},traitsArmor = {nameTraitCheap,nameTraitCheapskate})
	@CompoHead(durability=152,miningspeed=4.36,attack=3,harvestLevel=1,traits={  })
	@CompoHandle(modifier=0.82,durability=-20,traits={  })
	@CompoExtra(extraDurability=25,traits={  })
	@CompoArmorCore(durability=9.9,defense=5,traits={  })
	@CompoArmorPlate(modifier=0.53,durability=-3.2,toughness=0,traits={  })
	@CompoArmorTrim(extraDurability=0.77,traits={  })
	public static final Material blackrock=new $Material(nameBlackrock, colorBlackrock)
			.addItemAsIngot(Items.blackrock);

	@Compo(value = nameCoal,traitsTool = {nameTraitCombustionSupporting},traitsBow = {nameTraitCombustionSupporting},traitsArmor={nameTraitCarbonizing})
	@CompoHead(durability=50,miningspeed=0.5,attack=1,harvestLevel=0,traits={ nameTraitCarbonizing })
	@CompoHandle(modifier=0.5,durability=-100,traits={  })
	@CompoExtra(extraDurability=150,traits={  })
	@CompoArrowShaft(modifier=0.4,bonusAmmo=0,traits={  })
	@CompoArmorCore(durability=3.5,defense=2,traits={  })
	@CompoArmorPlate(modifier=0.5,durability=-4,toughness=0.8,traits={  })
	@CompoArmorTrim(extraDurability=0.65,traits={  })
	public static final Material coal=new $Material(nameCoal,colorCoal)
			.addItemAsIngot(net.minecraft.init.Items.COAL);

	@Compo(value = nameTreeRoot,traitsTool = {  }, traitsBow = { nameTraitDense },traitsArmor = {nameTraitEcological,nameTraitDense})
	@CompoHead(durability=165,miningspeed=3.6,attack=3,harvestLevel=1,traits={ nameTraitEcological,nameTraitDense })
	@CompoHandle(modifier=0.9,durability=40,traits={ nameTraitEcological,nameTraitDense })
	@CompoExtra(extraDurability=25,traits={ nameTraitDense })
	@CompoBow(drawSpeed=0.8,range=1.15,bonusDamage=0.5,traits={ nameTraitEcological })
	@CompoArrowShaft(modifier=1.05,bonusAmmo=25,traits={  })
	@CompoArmorCore(durability=4,defense=3,traits={  })
	@CompoArmorPlate(modifier=1.4,durability=2,toughness=6,traits={  })
	@CompoArmorTrim(extraDurability=4.6,traits={  })
	public static final Material treeRoot=new $Material(nameTreeRoot,colorTreeRoot)
			.addItemAsIngot(Items.treeRoot);


	@Compo(value = nameSpiderLeg,traitsTool = {nameTraitSharp, nameTraitArsenicPoisonous},traitsArmor = {nameTraitRough, nameTraitArsenicPoisonous})
	@CompoHead(durability=45,miningspeed=0.55,attack=3,harvestLevel=0,traits={  })
	@CompoArrowShaft(modifier=0.5,bonusAmmo=0,traits={  })
	public static final Material spiderLeg=new $Material(nameSpiderLeg, colorSpiderLeg)
			.addItemAsIngot(Items.spiderLeg);

	@Compo(value = nameHardSpiderLeg,traitsTool = {nameTraitSharp},traitsArmor = {nameTraitRough})
	@CompoHead(durability=280,miningspeed=0.9,attack=4,harvestLevel=0,traits={  })
	@CompoHandle(modifier=0.25,durability=30,traits={  })
	@CompoArrowShaft(modifier=0.65,bonusAmmo=0,traits={  })
	public static final Material hardSpiderLeg=new $Material(nameHardSpiderLeg, colorHardSpiderLeg)
			.addItemAsIngot(Items.hardSpiderLeg);

	@Compo(value = nameRoyalAlloy,traitsTool = {nameTraitLuxurious,nameTraitMagnetic2},traitsArmor = {nameTraitLuxurious,nameTraitMagnetic2})
	@CompoHead(durability=136,miningspeed=8.79,attack=2,harvestLevel=3,traits={  })
	@CompoHandle(modifier=0.9,durability=45,traits={  })
	@CompoExtra(extraDurability=45,traits={  })
	@CompoArmorCore(durability=9,defense=12.5,traits={  })
	@CompoArmorPlate(modifier=0.75,durability=-2,toughness=6.5,traits={  })
	@CompoArmorTrim(extraDurability=5,traits={  })
	public static final Material royalAlloy=new $Material(nameRoyalAlloy, colorRoyalAlloy)
			.addItemAsIngot(Items.ingotRoyalAlloy)
			.setFluid(Fluids.moltenRoyalAlloy);

	@Compo(value = nameConsolidatedGlass,traitsTool = {nameTraitCheap,nameTraitLightweight},traitsArmor = {nameTraitCheap,nameTraitLightweight})
	@CompoHead(durability=60,miningspeed=3.8,attack=5,harvestLevel=1,traits={  })
	@CompoExtra(extraDurability=-50,traits={  })
	@CompoArrowShaft(modifier=0.55,bonusAmmo=0,traits={ nameTraitCheap })
	@CompoArmorCore(durability=8.9,defense=6,traits={  })
	@CompoArmorPlate(modifier=0.6,durability=-2,toughness=0.4,traits={  })
	@CompoArmorTrim(extraDurability=1.2,traits={  })
	public static final Material consolidatedGlass=new $Material(nameConsolidatedGlass, colorConsolidatedGlass)
			.addItemAsIngot(Blocks.blockConsolidatedGlass);

	@Compo(value = nameCoagulatedBloodSand,traitsTool = nameTraitHemolytic,traitsArmor = nameTraitHemolytic)
	@CompoHead(durability=45,miningspeed=2.43,attack=6,harvestLevel=0,traits={  })
	@CompoExtra(extraDurability=-95,traits={  })
	@CompoArrowShaft(modifier=0.4,bonusAmmo=0,traits={  })
	@CompoArmorTrim(extraDurability=0.7,traits={  })
	public static final Material coagulatedBloodSand=new $Material(nameCoagulatedBloodSand, colorCoagulatedBloodSand)
			.addItemAsIngot(Blocks.blockCoagulatedBloodSand);

	@Compo(value = nameBrokenIce,traitsTool = {nameTraitCheap,nameTraitIcy},traitsArmor = {nameTraitCheap,nameTraitIcy})
	@CompoHead(durability=20,miningspeed=1.6,attack=1,harvestLevel=0,traits={  })
	@CompoHandle(modifier=0.54,durability=10,traits={  })
	@CompoExtra(extraDurability=15,traits={  })
	@CompoArrowShaft(modifier=0.4,bonusAmmo=0,traits={  })
	@CompoArmorCore(durability=5,defense=2,traits={  })
	@CompoArmorPlate(modifier=1.05,durability=-13,toughness=4,traits={  })
	@CompoArmorTrim(extraDurability=0.5,traits={  })
	public static final Material brokenIce=new $Material(nameBrokenIce,colorBrokenIce);
	static
	{
		brokenIce.addItem(Items.brokenIce,4, VALUE_Ingot);
		brokenIce.setRepresentativeItem(Items.brokenIce);
	}

	@Compo(value = nameCocoa,traitsTool = {nameTraitTasty},traitsBow = {nameTraitTasty},traitsArmor = {nameTraitTasty})
	@CompoHead(durability=30,miningspeed=0.8,attack=1,harvestLevel=0,traits={  })
	@CompoHandle(modifier=0.4,durability=-20,traits={  })
	@CompoExtra(extraDurability=15,traits={  })
	@CompoArrowShaft(modifier=0.45,bonusAmmo=0,traits={  })
	@CompoArmorCore(durability=3.8,defense=2.4,traits={  })
	@CompoArmorPlate(modifier=0.8,durability=-3,toughness=5,traits={  })
	@CompoArmorTrim(extraDurability=2,traits={  })
	public static final Material cocoa=new $Material(nameCocoa,colorCocoa)
			.addItemAsIngot(Items.ingotCocoa)
			.setFluid(Fluids.moltenCocoa);

	@Compo(value = nameNitre,traitsTool = {nameTraitSoluble,nameTraitJagged},traitsBow = {nameTraitSoluble},traitsArmor = {nameTraitSoluble})
	@CompoHead(durability=190,miningspeed=2.3,attack=4,harvestLevel=1,traits={  })
	@CompoHandle(modifier=0.6,durability=-60,traits={  })
	@CompoExtra(extraDurability=40,traits={  })
	@CompoArrowShaft(modifier=0.45,bonusAmmo=0,traits={  })
	@CompoArmorCore(durability=5,defense=5,traits={  })
	@CompoArmorPlate(modifier=0.9,durability=1,toughness=0,traits={  })
	@CompoArmorTrim(extraDurability=3,traits={  })
	public static final Material nitre=new $Material(nameNitre,colorNitre)
			.addItemAsIngot(Items.nitre);

	@Compo(value = nameShell,traitsTool = {})
	@CompoHead(durability=64,miningspeed=4.2,attack=3.9,harvestLevel=1,traits={  })
	@CompoHandle(modifier=0.69,durability=40,traits={  })
	@CompoExtra(extraDurability=23,traits={  })
	@CompoFletching(accuracy=0.6,modifier=1.25,traits={  })
	@CompoArmorCore(durability=3,defense=3.5,traits={  })
	@CompoArmorPlate(modifier=0.49,durability=-4,toughness=0,traits={  })
	@CompoArmorTrim(extraDurability=0.44,traits={  })
	public static final Material shell=new $Material(nameShell,colorShell)
			.addItemAsIngot(Items.shell);

	@Compo(value = nameTorrentialCrystal,traitsArmor = { nameTraitTurbulent })
	@CompoHead(durability = 100,miningspeed = 1,attack = 1,harvestLevel = 4,traits = {nameTraitTorrential})
	@CompoHandle(modifier=0.98,durability=120,traits={ nameTraitEddying })
	@CompoExtra(extraDurability=45,traits={  })
	@CompoArmorCore(durability=5.4,defense=6.8,traits={  })
	@CompoArmorPlate(modifier=0.8,durability=7,toughness=1.4,traits={  })
	@CompoArmorTrim(extraDurability=8,traits={  })
	public static final Material torrentialCrystal=new $Material(nameTorrentialCrystal, colorTorrentialCrystal)
			.addItemAsIngot(Items.torrentialCrystal);

	@Compo(value = nameImmersedSilver, traitsTool = {nameTraitRespecting},traitsBow = { nameTraitInsatiable }, traitsArmor = {nameTraitSkeletal})
	@CompoHead(durability=365,miningspeed=6.7,attack=4,harvestLevel=2,traits={  })
	@CompoHandle(modifier=0.8,durability=-50,traits={  })
	@CompoExtra(extraDurability=55,traits={  })
	@CompoBow(drawSpeed=0.6,range=1.1,bonusDamage=2,traits={  })
	@CompoArrowShaft(modifier=0.9,bonusAmmo=5,traits={  })
	@CompoArmorCore(durability=15,defense=18.6,traits={  })
	@CompoArmorPlate(modifier=0.8,durability=6,toughness=1,traits={  })
	@CompoArmorTrim(extraDurability=5,traits={  })
	public static final Material immersedSilver=new $Material(nameImmersedSilver, colorImmersedSilver)
			.addItemAsIngot(Items.ingotImmersedSilver)
			.setFluid(Fluids.moltenImmersedSilver);

	@Compo(value = nameDecurrium, traitsTool = { nameTraitDegenerating }, traitsArmor = { nameTraitSurging })
	@CompoHead(durability=365,miningspeed=6.7,attack=4,harvestLevel=2,traits={  })
	@CompoHandle(modifier=0.8,durability=-50,traits={  })
	@CompoExtra(extraDurability=55,traits={  })
	@CompoBow(drawSpeed=0.6,range=1.1,bonusDamage=2,traits={  })
	@CompoArmorCore(durability=15,defense=18.6,traits={  })
	@CompoArmorPlate(modifier=0.8,durability=6,toughness=1,traits={  })
	@CompoArmorTrim(extraDurability=5,traits={  })
	public static final Material decurrium=new $Material(nameDecurrium, colorDecurrium)
			.addItemAsIngot(Items.ingotDecurrium)
			.setFluid(Fluids.moltenDecurrium);

	@Compo(value = nameIcelit, traitsTool = {nameTraitAblaze}, traitsArmor = { nameTraitQuickFreezing })
	@CompoHead(durability=365,miningspeed=6.7,attack=4,harvestLevel=2,traits={  })
	@CompoHandle(modifier=0.8,durability=-50,traits={  })
	@CompoExtra(extraDurability=55,traits={  })
	@CompoBow(drawSpeed=0.6,range=1.1,bonusDamage=2,traits={  })
	@CompoArmorCore(durability=15,defense=18.6,traits={  })
	@CompoArmorPlate(modifier=0.8,durability=6,toughness=1,traits={  })
	@CompoArmorTrim(extraDurability=5,traits={  })
	public static final Material icelit=new $Material(nameIcelit, colorIcelit)
			.addItemAsIngot(Items.icelit);

	@Compo(value = nameTanatonium,traitsTool = { nameTraitEroding })
	@CompoHead(durability=365,miningspeed=6.7,attack=4,harvestLevel=2,traits={ nameTraitAnnihilating })
	@CompoHandle(modifier=0.8,durability=-50,traits={  })
	@CompoExtra(extraDurability=55,traits={  })
	public static final Material tanatonium=new $Material(nameTanatonium, colorTanatonium)
			.addItemAsIngot(Items.ingotTanatonium)
			.setFluid(Fluids.moltenTanatonium);

	@Compo(value = nameImitatium,traitsTool = { nameTraitRecombining })
	@CompoHead(durability=365,miningspeed=6.7,attack=4,harvestLevel=2,traits={  })
	@CompoHandle(modifier=0.8,durability=-50,traits={  })
	@CompoExtra(extraDurability=55,traits={  })
	public static final Material imitatium=new $Material(nameImitatium,colorImitatium)
			.addItemAsIngot(Items.ingotImitatium);

	@Compo(value = nameTitanium,traitsTool = {nameTraitSharp,nameTraitLightweight},traitsBow = {nameTraitLightweight},traitsArmor = {nameTraitLightweight})
	@CompoHead(durability=780,miningspeed=4.9,attack=4,harvestLevel=3,traits={  })
	@CompoHandle(modifier=0.95,durability=180,traits={  })
	@CompoExtra(extraDurability=150,traits={  })
	@CompoBow(drawSpeed=0.9,range=1.25,bonusDamage=1.5,traits={  })
	@CompoArrowShaft(modifier=1.2,bonusAmmo=15,traits={  })
	@CompoArmorCore(durability=20.9,defense=17.3,traits={  })
	@CompoArmorPlate(modifier=1.15,durability=9.6,toughness=5.7,traits={  })
	@CompoArmorTrim(extraDurability=14.4,traits={  })
	public static final Material titanium=new $Material(nameTitanium,colorTitanium)
			.addItemAsIngot(Items.ingotTitanium)
			.setFluid(Fluids.moltenTitanium);

	@Compo(value = nameMithril ,traitsTool = {nameTraitLightweight}, traitsBow = { nameTraitLightweight},traitsArmor = {nameTraitSmooth})
	@CompoHead(durability=550,miningspeed=7,attack=7,harvestLevel=4,traits={  })
	@CompoHandle(modifier=0.9,durability=-70,traits={  })
	@CompoExtra(extraDurability=250,traits={  })
	@CompoBow(drawSpeed=0.95,range=1.35,bonusDamage=2.5,traits={  })
	@CompoArrowShaft(modifier=1.3,bonusAmmo=30,traits={  })
	@CompoArmorCore(durability=22,defense=17.2,traits={  })
	@CompoArmorPlate(modifier=1.25,durability=3,toughness=3,traits={  })
	@CompoArmorTrim(extraDurability=13.4,traits={  })
	public static final Material mithril=new $Material(nameMithril, colorMithril)
			.addItemAsIngot(Items.ingotMithril)
			.setFluid(Fluids.moltenMithril);

	@Compo(value = nameAdamantine,traitsTool = {nameTraitUnnatural,nameTraitStonebound}, traitsBow = { nameTraitStonebound },traitsArmor = {nameTraitDevouring})
	@CompoHead(durability=900,miningspeed=14.7,attack=5,harvestLevel=4,traits={  })
	@CompoHandle(modifier=0.95,durability=-90,traits={  })
	@CompoExtra(extraDurability=290,traits={  })
	@CompoBow(drawSpeed=0.55,range=1.8,bonusDamage=6,traits={  })
	@CompoArrowShaft(modifier=1.3,bonusAmmo=30,traits={  })
	@CompoArmorCore(durability=26,defense=13.6,traits={  })
	@CompoArmorPlate(modifier=1.25,durability=-3,toughness=1,traits={  })
	@CompoArmorTrim(extraDurability=17.4,traits={  })
	public static final Material adamantine=new $Material(nameAdamantine, colorAdamantine)
			.addItemAsIngot(Items.ingotAdamantine)
			.setFluid(Fluids.moltenAdamantine);

	@Compo(value = nameInertWitherium,traitsTool = {nameTraitWitherFlowing},traitsBow = {nameTraitWitherFlowing},traitsArmor = {nameTraitWitherFlowing})
	@CompoHead(durability=1030,miningspeed=2.6,attack=3,harvestLevel=2,traits={  })
	@CompoHandle(modifier=1.05,durability=370,traits={  })
	@CompoExtra(extraDurability=180,traits={  })
	@CompoBow(drawSpeed=1.15,range=1,bonusDamage=0.5,traits={  })
	@CompoArrowShaft(modifier=0.35,bonusAmmo=90,traits={  })
	public static final Material inertWitherium=new $Material(nameInertWitherium, colorInertWitherium)
			.addItemAsIngot(Items.ingotInertWitherium)
			.setFluid(Fluids.moltenInertWitherium);

	@Compo(value = nameWitherium,traitsTool = {nameTraitLionheart, nameTraitWitherFlowing},traitsBow = {nameTraitLionheart, nameTraitWitherFlowing},traitsArmor = {nameTraitWitherFlowing})
	@CompoHead(durability=691,miningspeed=4.88,attack=9,harvestLevel=3,traits={  })
	@CompoHandle(modifier=0.95,durability=140,traits={  })
	@CompoExtra(extraDurability=40,traits={  })
	@CompoBow(drawSpeed=1.25,range=1.1,bonusDamage=2.5,traits={  })
	@CompoArrowShaft(modifier=0.45,bonusAmmo=25,traits={  })
	public static final Material witherium=new $Material(nameWitherium, colorWitherium)
			.addItemAsIngot(Items.ingotWitherium)
			.setFluid(Fluids.moltenWitherium);

	@Compo(value = nameStellarium,traitsTool = {nameTraitRadiant},traitsBow = {nameTraitRadiant},traitsArmor = {nameTraitRadiant})
	@CompoHead(durability=211,miningspeed=9.55,attack=8,harvestLevel=4,traits={  })
	@CompoExtra(extraDurability=0,traits={  })
	@CompoBow(drawSpeed=0.9,range=0.68,bonusDamage=5,traits={  })
	@CompoArrowShaft(modifier=0.65,bonusAmmo=0,traits={  })
	public static final Material stellarium=new $Material(nameStellarium, colorStellarium)
			.addItemAsIngot(Items.ingotStellarium)
			.setFluid(Fluids.moltenStellarium);

	@Compo(value = nameStellariumObsidian,traitsTool = {nameTraitRadiant},traitsArmor = {nameTraitRadiant})
	@CompoHead(durability=540,miningspeed=6.3,attack=7,harvestLevel=4,traits={  })
	@CompoHandle(modifier=1,durability=-250,traits={  })
	@CompoExtra(extraDurability=20,traits={  })
	@CompoArrowShaft(modifier=0.55,bonusAmmo=10,traits={  })
	public static final Material stellariumObsidian=new $Material(nameStellariumObsidian, colorStellariumObsidian)
			.addItemAsIngot(Blocks.blockStellariumObsidian);

	@Compo(value = nameHothium,traitsTool = {nameTraitExtremeFreezing},traitsBow = {nameTraitExtremeFreezing},traitsArmor = {nameTraitExtremeFreezing})
	@CompoHead(durability=820,miningspeed=5.5,attack=6,harvestLevel=4,traits={  })
	@CompoHandle(modifier=0.85,durability=145,traits={  })
	@CompoExtra(extraDurability=45,traits={  })
	@CompoBow(drawSpeed=0.4,range=1.2,bonusDamage=3,traits={  })
	@CompoArrowShaft(modifier=1.45,bonusAmmo=20,traits={  })
	@CompoArmorCore(durability=18.4,defense=18.2,traits={  })
	@CompoArmorPlate(modifier=1.3,durability=9.7,toughness=5.5,traits={  })
	@CompoArmorTrim(extraDurability=9,traits={  })
	public static final Material hothium=new $Material(nameHothium, colorHothium)
			.addItemAsIngot(Items.ingotHothium)
			.setFluid(Fluids.moltenHothium);

	@Compo(value = namePolarium,traitsTool = {nameTraitUnnatural},traitsArmor = {nameTraitBouncy})
	@CompoHead(durability=620,miningspeed=4.8,attack=4,harvestLevel=4,traits={  })
	@CompoHandle(modifier=1.1,durability=90,traits={  })
	@CompoExtra(extraDurability=90,traits={  })
	@CompoBow(drawSpeed=0.8,range=1.4,bonusDamage=3,traits={  })
	@CompoArrowShaft(modifier=1.05,bonusAmmo=25,traits={  })
	@CompoArmorCore(durability=19.3,defense=13.1,traits={  })
	@CompoArmorPlate(modifier=1.08,durability=7,toughness=4,traits={  })
	@CompoArmorTrim(extraDurability=9,traits={  })
	public static final Material polarium=new $Material(namePolarium,colorPolarium)
			.addItemAsIngot(Items.ingotPolarium)
			.setFluid(Fluids.moltenPolarium);

	@Compo(value = nameHalleium,traitsTool = {nameTraitJagged,nameTraitSwitching},traitsBow = {nameTraitSwitching},traitsArmor = {nameTraitSwitching})
	@CompoHead(durability=660,miningspeed=7.8,attack=3,harvestLevel=4,traits={  })
	@CompoHandle(modifier=1.05,durability=140,traits={  })
	@CompoExtra(extraDurability=80,traits={  })
	@CompoBow(drawSpeed=0.85,range=1.35,bonusDamage=6,traits={  })
	@CompoArrowShaft(modifier=1.1,bonusAmmo=45,traits={  })
	@CompoArmorCore(durability=25,defense=20,traits={  })
	@CompoArmorPlate(modifier=1.4,durability=5.8,toughness=2.4,traits={  })
	@CompoArmorTrim(extraDurability=12.6,traits={  })
	public static final Material halleium=new $Material(nameHalleium,colorHalleium)
			.addItemAsIngot(Items.ingotHalleium)
			.setFluid(Fluids.moltenHalleium);

	@Compo(value = nameAltairium,traitsTool = {nameTraitStarDashing},traitsBow={ nameTraitStarDashing },traitsArmor = {nameTraitStarDashing})
	@CompoHead(durability=650,miningspeed=5.8,attack=6,harvestLevel=4,traits={  })
	@CompoHandle(modifier=1.25,durability=55,traits={  })
	@CompoExtra(extraDurability=25,traits={  })
	@CompoBow(drawSpeed=0.6,range=1.15,bonusDamage=7.5)
	@CompoArrowShaft(modifier=1,bonusAmmo=40,traits={  })
	@CompoArmorCore(durability=17.3,defense=13.4,traits={  })
	@CompoArmorPlate(modifier=1.2,durability=7.4,toughness=3.2,traits={  })
	@CompoArmorTrim(extraDurability=10.4,traits={  })
	public static final Material altairium=new $Material(nameAltairium,colorAltairium)
			.addItemAsIngot(Items.ingotAltairium)
			.setFluid(Fluids.moltenAltairium);

	@Compo(value = nameTonium,traitsTool = {nameTraitRepressing},traitsBow = {nameTraitRepressing},traitsArmor = {nameTraitSteady})
	@CompoHead(durability=690,miningspeed=2.5,attack=7.8,harvestLevel=4,traits={  })
	@CompoHandle(modifier=0.9,durability=180,traits={  })
	@CompoExtra(extraDurability=350,traits={  })
	@CompoBow(drawSpeed=0.6,range=1.4,bonusDamage=4,traits={  })
	@CompoArrowShaft(modifier=0.35,bonusAmmo=65,traits={  })
	@CompoArmorCore(durability=22.5,defense=14,traits={  })
	@CompoArmorPlate(modifier=1.03,durability=11,toughness=4,traits={  })
	@CompoArmorTrim(extraDurability=11,traits={  })
	public static final Material tonium=new $Material(nameTonium,colorTonium)
			.addItemAsIngot(Items.ingotTonium)
			.setFluid(Fluids.moltenTonium);

	@Compo(value = nameOraclium,traitsTool = {nameTraitOracular},traitsArmor = {nameTraitThresholdLimiting})
	@CompoHead(durability=890,miningspeed=5.6,attack=6,harvestLevel=4,traits={  })
	@CompoHandle(modifier=1.05,durability=140,traits={  })
	@CompoExtra(extraDurability=45,traits={  })
	@CompoBow(drawSpeed=0.8,range=1.1,bonusDamage=6,traits={  })
	@CompoArrowShaft(modifier=1.35,bonusAmmo=40,traits={  })
	@CompoArmorCore(durability=20,defense=18,traits={  })
	@CompoArmorPlate(modifier=1.38,durability=9.5,toughness=6,traits={  })
	@CompoArmorTrim(extraDurability=16,traits={  })
	public static final Material oraclium=new $Material(nameOraclium, colorOraclium)
			.addItemAsIngot(Items.ingotOraclium)
			.setFluid(Fluids.moltenOraclium);

	@Compo(value = nameSteamium,traitsTool = {  },traitsArmor = {})
	@CompoHead(durability=420,miningspeed=5.5,attack=6,harvestLevel=2,traits={ nameTraitBlowing })
	@CompoHandle(modifier=0.65,durability=100,traits={ nameTraitSteamy })
	@CompoExtra(extraDurability=-80,traits={ nameTraitSteamy })
	@CompoBow(drawSpeed=0.7,range=1.55,bonusDamage=6,traits={ nameTraitBlowing })
	@CompoArrowShaft(modifier=1,bonusAmmo=20,traits={ nameTraitBlowing })
	@CompoArmorCore(durability=13.5,defense=13,traits={ nameTraitBuoyant })
	@CompoArmorPlate(modifier=1.18,durability=6,toughness=3,traits={ nameTraitAquaspeed })
	@CompoArmorTrim(extraDurability=8.6,traits={ nameTraitAquaspeed })
	public static final Material steamium=new $Material(nameSteamium, colorSteamium)
			.addItemAsIngot(Items.ingotSteamium)
			.setFluid(Fluids.moltenSteamium);

	@Compo(value = nameMeteorolite,traitsTool = {nameTraitAlien},traitsBow = {nameTraitAlien},traitsArmor = {nameTraitAlien})
	@CompoHead(durability=700,miningspeed=4.3,attack=4,harvestLevel=3,traits={  })
	@CompoHandle(modifier=0.75,durability=20,traits={  })
	@CompoExtra(extraDurability=140,traits={  })
	@CompoArrowShaft(modifier=0.9,bonusAmmo=20,traits={  })
	@CompoArmorCore(durability=14.2,defense=13.8,traits={  })
	@CompoArmorPlate(modifier=0.96,durability=2,toughness=3.6,traits={  })
	@CompoArmorTrim(extraDurability=9,traits={  })
	public static final Material meteorolite=new $Material(nameMeteorolite,colorMeteorolite)
			.addItemAsIngot(Blocks.blockMeteorolite);

//	@Compo(nameCinnabar)
//	@CompoHead(durability = 100,miningspeed = 0.5f,attack = 1,harvestLevel = 1)
//	@CompoHandle(modifier = 0.8f, durability = 100)
//	@CompoExtra(extraDurability = 100)
//	public static final Material cinnabar=new $Material(nameCinnabar,colorCinnabar)
//			.setIconItem(Items.cinnabar)
//			.setFluid(Fluids.moltenCinnabar)
//			.addTrait(TinkerTraits.poisonous, HEAD);

	@Compo(value = nameCorundum,traitsTool = {nameTraitClustering})
	@CompoHead(durability=450,miningspeed=6.5,attack=5,harvestLevel=3,traits={ nameTraitMaiming })
	@CompoHandle(modifier=1,durability=-50,traits={  })
	@CompoExtra(extraDurability=50,traits={  })
	@CompoArrowShaft(modifier=1,bonusAmmo=20,traits={  })
//	@CompoArmorCore(durability=14,defense=10,traits={  })
//	@CompoArmorPlate(modifier=0.67,durability=7,toughness=0,traits={  })
//	@CompoArmorTrim(extraDurability=5,traits={  })
	public static final Material corundum=new $Material(nameCorundum,colorCorundum)
			.addItemAsIngot(Items.corundum);

	@Compo(value = nameRedins,traitsTool = {nameTraitEstablished,nameTraitDense},traitsBow = {nameTraitDense},traitsArmor = {nameTraitMundane})
	@CompoHead(durability=1060,miningspeed=7,attack=7,harvestLevel=3,traits={  })
	@CompoHandle(modifier=0.85,durability=90,traits={  })
	@CompoExtra(extraDurability=110,traits={  })
	@CompoArrowShaft(modifier=1.2,bonusAmmo=25,traits={  })
//	@CompoArmorCore(durability=18,defense=10,traits={  })
//	@CompoArmorPlate(modifier=0.7,durability=9,toughness=0.5,traits={  })
//	@CompoArmorTrim(extraDurability=6,traits={  })
	public static final Material redins=new $Material(nameRedins,colorRedins)
			.addItemAsIngot(Items.redins);


	@Compo(value = nameIcelandSpar,traitsTool = {nameTraitBirefringent},traitsBow = {nameTraitBirefringent},traitsArmor = {nameTraitDiffuseReflecting})
	@CompoHead(durability=340,miningspeed=1.5,attack=6,harvestLevel=1,traits={  })
	@CompoHandle(modifier=0.65,durability=-200,traits={  })
	@CompoExtra(extraDurability=80,traits={  })
	@CompoArrowShaft(modifier=0.8,bonusAmmo=0,traits={  })
	@CompoArmorCore(durability=8,defense=7,traits={  })
	@CompoArmorPlate(modifier=0.9,durability=0,toughness=0,traits={  })
	@CompoArmorTrim(extraDurability=5.5,traits={  })
	public static final Material icelandSpar=new $Material(nameIcelandSpar,colorIcelandSpar)
			.addItemAsIngot(Items.icelandSpar);

	@Compo(value = namePyrophyllite,traitsTool = {nameTraitEcological},traitsBow = {nameTraitEcological},traitsArmor = {nameTraitLeavesHiding})
	@CompoHead(durability=240,miningspeed=1.9,attack=4,harvestLevel=1,traits={  })
	@CompoHandle(modifier=0.75,durability=-250,traits={  })
	@CompoExtra(extraDurability=65,traits={  })
	@CompoArrowShaft(modifier=0.7,bonusAmmo=0,traits={  })
	@CompoArmorCore(durability=5.6,defense=7.5,traits={  })
	@CompoArmorPlate(modifier=0.93,durability=0.53,toughness=0,traits={  })
	@CompoArmorTrim(extraDurability=3.3,traits={  })
	public static final Material pyrophyllite=new $Material(namePyrophyllite,colorPyrophyllite)
			.addItemAsIngot(Items.pyrophyllite);

	@Compo(value = nameSpinel,traitsTool = {nameTraitShaking},traitsBow = {nameTraitShaking},traitsArmor = {nameTraitLifting})
	@CompoHead(durability=590,miningspeed=6.5,attack=8,harvestLevel=3,traits={  })
	@CompoHandle(modifier=1,durability=90,traits={  })
	@CompoExtra(extraDurability=170,traits={  })
	@CompoArrowShaft(modifier=1.2,bonusAmmo=20,traits={  })
	@CompoArmorCore(durability=16,defense=9,traits={  })
	@CompoArmorPlate(modifier=1.2,durability=12,toughness=1.8,traits={  })
	@CompoArmorTrim(extraDurability=15,traits={  })
	public static final Material spinel=new $Material(nameSpinel,colorSpinel)
			.addItemAsIngot(Items.spinel);

	@Compo(value = nameTalcum,traitsTool = {nameTraitWritable1},traitsBow = {nameTraitWritable1},traitsArmor = {nameTraitSliding})
	@CompoHead(durability=220,miningspeed=3.1,attack=3,harvestLevel=1,traits={  })
	@CompoExtra(extraDurability=190,traits={  })
	@CompoArrowShaft(modifier=0.75,bonusAmmo=0,traits={  })
	@CompoArmorCore(durability=6.2,defense=4.5,traits={  })
	@CompoArmorPlate(modifier=0.9,durability=-11,toughness=9,traits={  })
	@CompoArmorTrim(extraDurability=3,traits={  })
	public static final Material talcum=new $Material(nameTalcum,colorTalcum)
			.addItemAsIngot(Items.talcum);

	@Compo(value = nameTourmaline,traitsTool = {nameTraitPyroelectric},traitsBow = {nameTraitPyroelectric},traitsArmor={nameTraitVoltaic})
	@CompoHead(durability=350,miningspeed=3.5,attack=4,harvestLevel=2,traits={  })
	@CompoHandle(modifier=0.6,durability=-150,traits={  })
	@CompoExtra(extraDurability=55,traits={  })
	@CompoArrowShaft(modifier=0.7,bonusAmmo=0,traits={  })
	@CompoArmorCore(durability=6.9,defense=6.8,traits={  })
	@CompoArmorPlate(modifier=0.86,durability=-3,toughness=0.3,traits={  })
	@CompoArmorTrim(extraDurability=4.7,traits={  })
	public static final Material tourmaline=new $Material(nameTourmaline,colorTourmaline)
			.addItemAsIngot(Items.tourmaline);


	@Compo(value = nameSunStone,traitsTool = {nameTraitSunPower},traitsBow = {nameTraitSunPower},traitsArmor = {nameTraitSunPower})
	@CompoHead(durability=545,miningspeed=5.75,attack=6,harvestLevel=3,traits={ nameTraitFlammable })
	@CompoHandle(modifier=1.1,durability=50,traits={  })
	@CompoExtra(extraDurability=70,traits={  })
	@CompoArrowShaft(modifier=1.1,bonusAmmo=25,traits={  })
	@CompoArmorCore(durability=10.5,defense=9.8,traits={  })
	@CompoArmorPlate(modifier=0.78,durability=2,toughness=1,traits={  })
	@CompoArmorTrim(extraDurability=3,traits={  })
	public static final Material sunStone=new $Material(nameSunStone,colorSunStone)
			.addItemAsIngot(Items.sunStone);

	@Compo(value = nameMoonStone,traitsTool = {nameTraitMoonPower},traitsBow = {nameTraitMoonPower},traitsArmor = {nameTraitMoonPower})
	@CompoHead(durability=545,miningspeed=6.25,attack=5,harvestLevel=3,traits={ nameTraitMoonlight })
	@CompoHandle(modifier=1.1,durability=50,traits={  })
	@CompoExtra(extraDurability=70,traits={  })
	@CompoArrowShaft(modifier=1.1,bonusAmmo=25,traits={  })
	@CompoArmorCore(durability=10.5,defense=9.8,traits={  })
	@CompoArmorPlate(modifier=0.78,durability=2,toughness=1,traits={  })
	@CompoArmorTrim(extraDurability=3,traits={  })
	public static final Material moonStone=new $Material(nameMoonStone,colorMoonStone)
			.addItemAsIngot(Items.moonStone);

	@Compo(value = nameEnderDragonSquama,traitsTool = {nameTraitEnderference},traitsBow = {nameTraitEnderference})
	@CompoHead(durability=1090,miningspeed=5.8,attack=6,harvestLevel=2,traits={ nameTraitLionheart,nameTraitDragonKiller })
	@CompoHandle(modifier=1.05,durability=90,traits={  })
	@CompoExtra(extraDurability=120,traits={  })
	@CompoBow(drawSpeed=0.45,range=0.9,bonusDamage=3.5,traits={  })
	@CompoArrowShaft(modifier=1.5,bonusAmmo=45,traits={  })
	@CompoFletching(accuracy=0.8,modifier=3.5,traits={  })
//	@CompoArmorCore(durability=17.2,defense=23,traits={  })
//	@CompoArmorPlate(modifier=0.99,durability=7.9,toughness=8,traits={  })
//	@CompoArmorTrim(extraDurability=9,traits={  })
	public static final Material enderDragonSquama=new $Material(nameEnderDragonSquama,colorEnderDragonSquama)
			.addItemAsIngot(Items.enderDragonSquama);
//			.addToolTrait(enderference)
//			.addToolTrait(dense)
//			.addPartTrait(lionheart,HEAD)
//			.addPartTrait(enderference,HEAD); // fixme

	@Compo(value = nameFulgurite,traitsTool = {nameTraitThunderWaving},traitsBow = {nameTraitThunderWaving})
	@CompoHead(durability=515,miningspeed=5.7,attack=7,harvestLevel=3,traits={  })
	@CompoExtra(extraDurability=120,traits={  })
	@CompoArrowShaft(modifier=0.55,bonusAmmo=5,traits={  })
//	@CompoArmorCore(durability=6.9,defense=14,traits={  })
//	@CompoArmorPlate(modifier=0.9,durability=3.2,toughness=0.6,traits={  })
//	@CompoArmorTrim(extraDurability=5,traits={  })
	public static final Material fulgurite=new $Material(nameFulgurite,colorFulgurite)
			.addItemAsIngot(Blocks.blockFulgurite);

//	@Compo(value = nameIrisia,traitsTool = {nameTraitLightweight})
//	@CompoHead(durability=130,miningspeed=7.7,attack=9,harvestLevel=4,traits={  })
//	@CompoHandle(modifier=0.75,durability=0,traits={  })
//	@CompoExtra(extraDurability=20,traits={  })
//	@CompoArmorCore(durability=9.3,defense=11,traits={  })
//	@CompoArmorPlate(modifier=1.2,durability=9.5,toughness=8,traits={  })
//	@CompoArmorTrim(extraDurability=5.8,traits={  })
//	public static final Material irisia=new $Material(nameIrisia,colorIrisia)
//			.addItemAsIngot(Items.ingotIrisia);

	@Compo(value = nameOpal,traitsTool = {nameTraitGorgeous},traitsBow = {nameTraitGorgeous},traitsArmor = {nameTraitGorgeous})
	@CompoHead(durability=230,miningspeed=2.9,attack=3,harvestLevel=0,traits={  })
	@CompoHandle(modifier=0.85,durability=-45,traits={  })
	@CompoExtra(extraDurability=115,traits={  })
	@CompoArrowShaft(modifier=0.9,bonusAmmo=5,traits={  })
	@CompoArmorCore(durability=6.7,defense=10.3,traits={  })
	@CompoArmorPlate(modifier=0.7,durability=5,toughness=1,traits={  })
	@CompoArmorTrim(extraDurability=3,traits={  })
	public static final Material opal=new $Material(nameOpal, colorOpal)
			.addItemAsIngot(Items.opal);

	@Compo(value = nameTopaz,traitsTool = {nameTraitPeaceEnergetic},traitsBow = {  },traitsArmor = {nameTraitVoltaic})
	@CompoHead(durability=490,miningspeed=5.6,attack=4,harvestLevel=3,traits={  })
	@CompoHandle(modifier=1.15,durability=90,traits={  })
	@CompoExtra(extraDurability=280,traits={  })
	@CompoArrowShaft(modifier=1.15,bonusAmmo=20,traits={  })
	@CompoArmorCore(durability=5.3,defense=8,traits={  })
	@CompoArmorPlate(modifier=1.05,durability=4.8,toughness=0,traits={  })
	@CompoArmorTrim(extraDurability=4.4,traits={  })
	public static final Material topaz=new $Material(nameTopaz, colorTopaz)
			.addItemAsIngot(Items.topaz);

	@Compo(value = nameLizanite,traitsTool = {nameTraitHyper},traitsBow = {nameTraitHyper},traitsArmor = {nameTraitHyper})
	@CompoHead(durability = 730,miningspeed = 6.2,attack = 6.9,harvestLevel = 3)
	@CompoHandle(modifier = 1.15,durability = 90)
	@CompoExtra(extraDurability = 310)
	@CompoArmorCore(durability = 11,defense = 8)
	@CompoArmorPlate(modifier = 0.94,durability = -2,toughness = 0.5) // info 属性需要改
	@CompoArmorTrim(extraDurability = 6.1)
	public static final Material lizanite =new $Material(nameLizanite, colorLizanite)
			.addItemAsIngot(Items.lizanite);

	@Compo(value = nameCordierite,traitsTool = {nameTraitDichroic},traitsBow = {nameTraitDichroic},traitsArmor = {nameTraitDichroic})
	@CompoHead(durability=425,miningspeed=5.9,attack=3,harvestLevel=3,traits={  })
	@CompoHandle(modifier=1.05,durability=35,traits={  })
	@CompoExtra(extraDurability=215,traits={  })
	@CompoArrowShaft(modifier=1.15,bonusAmmo=20,traits={  })
	@CompoArmorCore(durability=9,defense=8,traits={  })
	@CompoArmorPlate(modifier=0.9,durability=7,toughness=0,traits={  })
	@CompoArmorTrim(extraDurability=7.4,traits={  })
	public static final Material cordierite=new $Material(nameCordierite, colorCordierite)
			.addItemAsIngot(Items.cordierite);

	@Compo(value = namePrehnite,traitsTool = {nameTraitLifeInspiring},traitsBow = {  },traitsArmor = {nameTraitLifeInspiring})
	@CompoHead(durability=390,miningspeed=4.1,attack=3,harvestLevel=3,traits={  })
	@CompoHandle(modifier=0.95,durability=20,traits={  })
	@CompoExtra(extraDurability=55,traits={  })
	@CompoArrowShaft(modifier=1.2,bonusAmmo=20,traits={  })
	@CompoArmorCore(durability=8,defense=6,traits={  })
	@CompoArmorPlate(modifier=0.92,durability=5.8,toughness=0,traits={  })
	@CompoArmorTrim(extraDurability=5,traits={  })
	public static final Material prehnite=new $Material(namePrehnite, colorPrehnite)
			.addItemAsIngot(Items.prehnite);

	@Compo(value = nameProustite,traitsTool = {nameTraitArsenicPoisonous},traitsBow = {nameTraitArsenicPoisonous},traitsArmor = {nameTraitArsenicPoisonous})
	@CompoHead(durability=210,miningspeed=3.5,attack=4,harvestLevel=1,traits={  })
	@CompoHandle(modifier=0.85,durability=80,traits={  })
	@CompoExtra(extraDurability=35,traits={  })
	public static final Material proustite=new $Material(nameProustite, colorProustite)
			.addItemAsIngot(Items.proustite);


	@Compo(value = nameVibratingCrystal,traitsTool = {nameTraitVibrating},traitsArmor = {nameTraitVibrating})
	@CompoHead(durability=650,miningspeed=15.5,attack=4,harvestLevel=3,traits={  })
	@CompoHandle(modifier=0.9,durability=190,traits={  })
	@CompoExtra(extraDurability=30,traits={  })
	@CompoArmorCore(durability=5,defense=7.8,traits={  })
	@CompoArmorPlate(modifier=0.6,durability=6.7,toughness=2,traits={  })
	@CompoArmorTrim(extraDurability=7,traits={  })
	public static final Material vibratingCrystal=new $Material(nameVibratingCrystal, colorVibratingCrystal)
			.addItemAsIngot(Items.vibratingCrystal);

	@Compo(value = nameLavaCrystal,traitsArmor = {nameTraitThermalGathering})
	@CompoHead(durability=390,miningspeed=6.5,attack=6,harvestLevel=3,traits={ nameTraitThermalGathering })
	@CompoHandle(modifier=0.98,durability=120,traits={ nameTraitThermalGathering })
	@CompoExtra(extraDurability=45,traits={ nameTraitFlammable })
	@CompoArmorCore(durability=5.4,defense=6.8,traits={  })
	@CompoArmorPlate(modifier=0.8,durability=7,toughness=1.4,traits={  })
	@CompoArmorTrim(extraDurability=8,traits={  })
	public static final Material lavaCrystal=new $Material(nameLavaCrystal, colorLavaCrystal)
			.addItemAsIngot(Items.lavaCrystal);

	@Compo(value = nameGrain,traitsTool = {nameTraitDecoying})
	@CompoHead(durability=240,miningspeed=3.6,attack=3,harvestLevel=1,traits={  })
	@CompoHandle(modifier=1.05,durability=10,traits={  })
	@CompoExtra(extraDurability=28,traits={  })
	public static final Material grain=new $Material(nameGrain, colorGrain)
			.addItemAsIngot(Items.ingotGrain);

	@Compo(value = nameAventurine,traitsTool = {nameTraitStaminaFocusing},traitsBow = {  },traitsArmor = {nameTraitStaminaFocusing})
	@CompoHead(durability=720,miningspeed=6,attack=7,harvestLevel=3,traits={  })
	@CompoHandle(modifier=0.9,durability=40,traits={  })
	@CompoExtra(extraDurability=50,traits={  })
	@CompoArrowShaft(modifier=1.3,bonusAmmo=0,traits={  })
	@CompoArmorCore(durability=8.5,defense=4,traits={  })
	@CompoArmorPlate(modifier=0.68,durability=5,toughness=0.5,traits={  })
	@CompoArmorTrim(extraDurability=5.6,traits={  })
	public static final Material aventurine =new $Material(nameAventurine, colorAventurine)
			.addItemAsIngot(Items.aventurine);

	@Compo(value = nameFlesh,traitsTool = {nameTraitGluttonic},traitsArmor = {nameTraitDeepParasitic})
	@CompoHead(durability=220,miningspeed=3.8,attack=4,harvestLevel=0,traits={  })
	@CompoHandle(modifier=0.8,durability=-50,traits={  })
	@CompoExtra(extraDurability=50,traits={  })
	@CompoArmorCore(durability=2,defense=1,traits={  })
	@CompoArmorPlate(modifier=0.5,durability=1.4,toughness=4,traits={  })
	@CompoArmorTrim(extraDurability=1,traits={  })
	public static final Material flesh=new $Material(nameFlesh,colorFlesh)
			.addItemAsIngot(Items.flesh);

	@Compo(value = nameBrokenBedrock,traitsTool = {nameTraitDuritos},traitsBow = {nameTraitDuritos},traitsArmor = {nameTraitHeavy,nameTraitOverHeavy})
	@CompoHead(durability = 1850,miningspeed = 0.5,attack = 1.5,harvestLevel = 4,traits={nameTraitDense})
	@CompoHandle(modifier = 1.35,durability = 160)
	@CompoExtra(extraDurability = 270)
	@CompoArmorCore(durability = 32.5,defense = 4)
	@CompoArmorPlate(modifier = 0.5,durability = 16,toughness = 0)
	@CompoArmorTrim(extraDurability = 25)
	public static final Material brokenBedrock=new $Material(nameBrokenBedrock, colorBrokenBedrock)
			.addItemAsIngot(Items.brokenBedrock);
//			.addToolTrait(duritos)
//			.addPartTrait(dense,HEAD);

	@Compo(nameCloud)
	@CompoHead(durability = 20,miningspeed = 0.5,attack = 0,harvestLevel = 0,traits = {nameTraitSqueaky})
	@CompoExtra(extraDurability = -300,traits = {nameTraitLightweight})
	public static final Material cloud=new $Material(nameCloud, colorCloud)
			.addItemAsIngot(Blocks.blockCloud);

//	public static Material cloudStorm=new Material("cloud_storm", Colors.DarkBlue);
//	public static Material skyCrystal=new Material("sky_crystal", Colors.Aqua);
//	public static Material stormCrystal=new Material("storm_crystal", Colors.DarkBlue);
//	public static Material antiGraCrystal=new Material("anti_gra_crystal", Colors.DarkGreen);
//	public static Material phantomCrystal=new Material("phantom_crystal", Colors.Gray);
//	public static Material paradiseMetal=new Material("paradise_metal", Colors.Yellow);
}
