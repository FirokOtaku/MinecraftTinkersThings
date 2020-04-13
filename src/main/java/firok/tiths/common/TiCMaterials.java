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
	private TiCMaterials(){}

	@Compo(value = nameImmersedSilver, traitsTool = {nameTraitInsatiable},traitsArmor = {nameTraitSkeletal})
	@CompoHead(durability = 365,miningspeed = 4.47,attack = 6.6,harvestLevel = 2)
	@CompoHandle(modifier = 1.22, durability = 87)
	@CompoExtra(extraDurability = 250)
	@CompoBow(drawSpeed = 1.15,range = 0.95,bonusDamage = 2.5)
	@CompoArrowShaft(modifier = 0.85,bonusAmmo = 15)
	@CompoArmorCore(durability = 1,defense = 1)
	@CompoArmorPlate(modifier = 1,durability = 1,toughness = 1) // info 属性需要改
	@CompoArmorTrim(extraDurability = 1)
	public static final Material immersedSilver=new $Material(nameImmersedSilver, colorImmersedSilver)
			.addItemAsIngot(Items.ingotImmersedSilver)
			.setFluid(Fluids.moltenImmersedSilver);

	@Compo(value = nameMithril ,traitsTool = {nameTraitLightweight},traitsArmor = {nameTraitLightweight})
	@CompoHead(durability = 598,miningspeed = 6.74,attack = 4.24,harvestLevel = 4)
	@CompoHandle(modifier = 0.8, durability = -80)
	@CompoExtra(extraDurability = 207)
	@CompoBow(drawSpeed = 1.65, range=1.05, bonusDamage = 2)
	@CompoArrowShaft(modifier = 1.65,bonusAmmo = 25)
	@CompoArmorCore(durability = 1,defense = 1)
	@CompoArmorPlate(modifier = 1,durability = 1,toughness = 1) // info 属性需要改
	@CompoArmorTrim(extraDurability = 1)
	public static final Material mithril=new $Material(nameMithril, colorMithril)
			.addItemAsIngot(Items.ingotMithril)
			.setFluid(Fluids.moltenMithril);

	@Compo(value = nameAdamantine,traitsTool = {nameTraitUnnatural,nameTraitStonebound},traitsArmor = {nameTraitBouncy})
	@CompoHead(durability = 683,miningspeed = 14.43,attack = 5.42,harvestLevel = 4)
	@CompoHandle(modifier = 0.79, durability = -150)
	@CompoExtra(extraDurability = 350)
	@CompoBow(drawSpeed = 0.8f, range = 1.45, bonusDamage = 3.5)
	@CompoArrowShaft(modifier = 1.55,bonusAmmo = 35)
	@CompoArmorCore(durability = 1,defense = 1)
	@CompoArmorPlate(modifier = 1,durability = 1,toughness = 1) // info 属性需要改
	@CompoArmorTrim(extraDurability = 1)
	public static final Material adamantine=new $Material(nameAdamantine, colorAdamantine)
			.addItemAsIngot(Items.ingotAdamantine)
			.setFluid(Fluids.moltenAdamantine);

	@Compo(value = nameBlackrock,traitsTool = {nameTraitCheap,nameTraitCheapskate},traitsArmor = {nameTraitCheap,nameTraitCheapskate})
	@CompoHead(durability = 152,miningspeed = 4.36,attack = 3.52,harvestLevel = 1)
	@CompoHandle(modifier = 0.82, durability = -20)
	@CompoExtra(extraDurability = 25)
	@CompoArmorCore(durability = 1,defense = 1)
	@CompoArmorPlate(modifier = 1,durability = 1,toughness = 1) // info 属性需要改
	@CompoArmorTrim(extraDurability = 1)
	public static final Material blackrock=new $Material(nameBlackrock, colorBlackrock)
			.addItemAsIngot(Items.blackrock);

	@Compo(value = nameInertWitherium,traitsTool = {nameTraitWitherFlowing},traitsArmor = {nameTraitWitherFlowing})
	@CompoHead(durability = 1030,miningspeed = 2.6,attack = 3.8,harvestLevel = 2)
	@CompoHandle(modifier = 1.05, durability = 370)
	@CompoExtra(extraDurability = 180)
	@CompoBow(drawSpeed = 1.15,range = 1,bonusDamage = 0.5)
	@CompoArrowShaft(modifier = 0.35,bonusAmmo = 90)
	@CompoArmorCore(durability = 1,defense = 1)
	@CompoArmorPlate(modifier = 1,durability = 1,toughness = 1) // info 属性需要改
	@CompoArmorTrim(extraDurability = 1)
	public static final Material inertWitherium=new $Material(nameInertWitherium, colorInertWitherium)
			.addItemAsIngot(Items.ingotInertWitherium)
			.setFluid(Fluids.moltenInertWitherium);

	@Compo(value = nameWitherium,traitsTool = {nameTraitLionheart, nameTraitWitherFlowing},traitsArmor = {nameTraitWitherFlowing})
	@CompoHead(durability = 691,miningspeed = 4.88,attack = 9.46,harvestLevel = 3)
	@CompoHandle(modifier = 0.95, durability = 140)
	@CompoExtra(extraDurability = 40)
	@CompoBow(drawSpeed = 1.25,range = 1.1,bonusDamage = 2.5)
	@CompoArrowShaft(modifier = 0.45,bonusAmmo = 25)
	@CompoArmorCore(durability = 1,defense = 1)
	@CompoArmorPlate(modifier = 1,durability = 1,toughness = 1) // info 属性需要改
	@CompoArmorTrim(extraDurability = 1)
	public static final Material witherium=new $Material(nameWitherium, colorWitherium)
			.addItemAsIngot(Items.ingotWitherium)
			.setFluid(Fluids.moltenWitherium);


	@Compo(value = nameRoyalAlloy,traitsTool = {nameTraitLuxurious,nameTraitMagnetic2},traitsArmor = {nameTraitLuxurious,nameTraitMagnetic2})
	@CompoHead(durability = 436,miningspeed = 8.79,attack = 2.83,harvestLevel = 2)
	@CompoHandle(modifier = 0.63, durability = 45)
	@CompoExtra(extraDurability = 45)
	@CompoArmorCore(durability = 1,defense = 1)
	@CompoArmorPlate(modifier = 1,durability = 1,toughness = 1) // info 属性需要改
	@CompoArmorTrim(extraDurability = 1)
	public static final Material royalAlloy=new $Material(nameRoyalAlloy, colorRoyalAlloy)
			.addItemAsIngot(Items.ingotRoyalAlloy)
			.setFluid(Fluids.moltenRoyalAlloy);

	@Compo(value = nameStellarium,traitsTool = {nameTraitRadiant},traitsArmor = {nameTraitRadiant})
	@CompoHead(durability = 211,miningspeed = 9.55,attack = 8.54,harvestLevel = 4)
	@CompoExtra(extraDurability = 0)
	@CompoBow(drawSpeed = 0.9,range = 0.68,bonusDamage = 5)
	@CompoArrowShaft(modifier = 0.65,bonusAmmo = 0)
	@CompoArmorCore(durability = 1,defense = 1)
	@CompoArmorPlate(modifier = 1,durability = 1,toughness = 1) // info 属性需要改
	@CompoArmorTrim(extraDurability = 1)
	public static final Material stellarium=new $Material(nameStellarium, colorStellarium)
			.addItemAsIngot(Items.ingotStellarium)
			.setFluid(Fluids.moltenStellarium);

	@Compo(value = nameStellariumObsidian,traitsTool = {nameTraitRadiant},traitsArmor = {nameTraitRadiant})
	@CompoHead(durability = 540,miningspeed = 6.3,attack = 7.4,harvestLevel = 4)
	@CompoHandle(modifier = 1,durability = -250)
	@CompoExtra(extraDurability = 20)
	@CompoArrowShaft(modifier = 0.55,bonusAmmo = 10)
	@CompoArmorCore(durability = 1,defense = 1)
	@CompoArmorPlate(modifier = 1,durability = 1,toughness = 1) // info 属性需要改
	@CompoArmorTrim(extraDurability = 1)
	public static final Material stellariumObsidian=new $Material(nameStellariumObsidian, colorStellariumObsidian)
			.addItemAsIngot(Blocks.blockStellariumObsidian);

	@Compo(value = nameHothium,traitsTool = {nameTraitExtremeFreezing},traitsArmor = {nameTraitExtremeFreezing})
	@CompoHead(durability = 760,miningspeed = 6.2,attack = 7.6,harvestLevel = 4)
	@CompoHandle(modifier = 0.85, durability = 145)
	@CompoExtra(extraDurability = 45)
	@CompoBow(drawSpeed = 0.95f,range=1.2,bonusDamage = 3.5)
	@CompoArmorCore(durability = 1,defense = 1)
	@CompoArmorPlate(modifier = 1,durability = 1,toughness = 1) // info 属性需要改
	@CompoArmorTrim(extraDurability = 1)
	public static final Material hothium=new $Material(nameHothium, colorHothium)
			.addItemAsIngot(Items.ingotHothium)
			.setFluid(Fluids.moltenHothium);

	@Compo(value = nameSpiderLeg,traitsTool = {nameTraitSharp,nameTraitPoisonous},traitsArmor = {nameTraitRough,nameTraitPoisonous})
	@CompoHead(durability = 45,miningspeed = 0.55,attack = 3.1,harvestLevel = 0)
	@CompoArmorCore(durability = 1,defense = 1)
	@CompoArmorPlate(modifier = 1,durability = 1,toughness = 1) // info 属性需要改
	@CompoArmorTrim(extraDurability = 1)
	public static final Material spiderLeg=new $Material(nameSpiderLeg, colorSpiderLeg)
			.addItemAsIngot(Items.spiderLeg);

	@Compo(value = nameHardSpiderLeg,traitsTool = {nameTraitSharp},traitsArmor = {nameTraitRough})
	@CompoHead(durability = 75,miningspeed = 0.9,attack = 4.5,harvestLevel = 0)
	@CompoHandle(modifier = 0.25,durability = 30)
	@CompoArmorCore(durability = 1,defense = 1)
	@CompoArmorPlate(modifier = 1,durability = 1,toughness = 1) // info 属性需要改
	@CompoArmorTrim(extraDurability = 1)
	public static final Material hardSpiderLeg=new $Material(nameHardSpiderLeg, colorHardSpiderLeg)
			.addItemAsIngot(Items.hardSpiderLeg);

//	@Compo(nameCinnabar)
//	@CompoHead(durability = 100,miningspeed = 0.5f,attack = 1,harvestLevel = 1)
//	@CompoHandle(modifier = 0.8f, durability = 100)
//	@CompoExtra(extraDurability = 100)
//	public static final Material cinnabar=new $Material(nameCinnabar,colorCinnabar)
//			.setIconItem(Items.cinnabar)
//			.setFluid(Fluids.moltenCinnabar)
//			.addTrait(TinkerTraits.poisonous, HEAD);

	@Compo(value = nameConsolidatedGlass,traitsTool = {nameTraitCheap,nameTraitLightweight},traitsArmor = {nameTraitCheap,nameTraitLightweight})
	@CompoHead(durability = 60,miningspeed = 3.8,attack = 5.6,harvestLevel = 0)
	@CompoExtra(extraDurability = -50)
	@CompoArrowShaft(modifier = 0.55,bonusAmmo = 0)
	@CompoArmorCore(durability = 1,defense = 1)
	@CompoArmorPlate(modifier = 1,durability = 1,toughness = 1) // info 属性需要改
	@CompoArmorTrim(extraDurability = 1)
	public static final Material consolidatedGlass=new $Material(nameConsolidatedGlass, colorConsolidatedGlass)
			.addItemAsIngot(Blocks.blockConsolidatedGlass);

	@Compo(value = nameCoagulatedBloodSand,traitsTool = nameTraitHemolytic,traitsArmor = nameTraitHemolytic)
	@CompoHead(durability = 45,miningspeed = 2.43,attack = 2.79,harvestLevel = 0)
	@CompoExtra(extraDurability = -95)
	@CompoArmorCore(durability = 1,defense = 1)
	@CompoArmorPlate(modifier = 1,durability = 1,toughness = 1) // info 属性需要改
	@CompoArmorTrim(extraDurability = 1)
	public static final Material coagulatedBloodSand=new $Material(nameCoagulatedBloodSand, colorCoagulatedBloodSand)
			.addItemAsIngot(Blocks.blockCoagulatedBloodSand);

	@Compo(value = nameBrokenIce,traitsTool = {nameTraitCheap,nameTraitIcy},traitsArmor = {nameTraitCheap,nameTraitIcy})
	@CompoHead(durability = 20,miningspeed = 1.6,attack = 1.8,harvestLevel = 0)
	@CompoHandle(modifier = 0.54, durability = 10)
	@CompoExtra(extraDurability = 15)
	@CompoArmorCore(durability = 1,defense = 1)
	@CompoArmorPlate(modifier = 1,durability = 1,toughness = 1) // info 属性需要改
	@CompoArmorTrim(extraDurability = 1)
	public static final Material brokenIce=new $Material(nameBrokenIce,colorBrokenIce);
	static
	{
		brokenIce.addItem(Items.brokenIce,4, VALUE_Ingot);
		brokenIce.setRepresentativeItem(Items.brokenIce);
	}

	@Compo(value = nameShell,traitsTool = {nameTraitSharp},traitsArmor = {nameTraitRough})
	@CompoHead(durability = 64,miningspeed = 4.2,attack = 5.7,harvestLevel = 1)
	@CompoHandle(modifier = 0.69, durability = 121)
	@CompoExtra(extraDurability = 23)
	@CompoArmorCore(durability = 1,defense = 1)
	@CompoArmorPlate(modifier = 1,durability = 1,toughness = 1) // info 属性需要改
	@CompoArmorTrim(extraDurability = 1)
	public static final Material shell=new $Material(nameShell,colorShell)
			.addItemAsIngot(Items.shell);

	@Compo(value = nameRedins,traitsTool = {nameTraitEstablished,nameTraitDense},traitsArmor = {nameTraitDense,nameTraitAmbitious})
	@CompoHead(durability = 1440,miningspeed = 7.68,attack = 7.4,harvestLevel = 3)
	@CompoHandle(modifier = 1.3, durability = 50)
	@CompoExtra(extraDurability = 350)
	@CompoArmorCore(durability = 1,defense = 1)
	@CompoArmorPlate(modifier = 1,durability = 1,toughness = 1) // info 属性需要改
	@CompoArmorTrim(extraDurability = 1)
	public static final Material redins=new $Material(nameRedins,colorRedins)
			.addItemAsIngot(Items.redins);

	@Compo(value = nameCorundum,traitsTool = {nameTraitClustering})
	@CompoHead(durability = 450,miningspeed = 6.4,attack = 5.1,harvestLevel = 3,traits = {nameTraitMaiming})
	@CompoHandle(modifier = 1, durability = -50)
	@CompoExtra(extraDurability = 50)
	@CompoArmorCore(durability = 1,defense = 1)
	@CompoArmorPlate(modifier = 1,durability = 1,toughness = 1) // info 属性需要改
	@CompoArmorTrim(extraDurability = 1)
	public static final Material corundum=new $Material(nameCorundum,colorCorundum)
			.addItemAsIngot(Items.corundum);

	@Compo(value = nameTitanium,traitsTool = {nameTraitSharp,nameTraitLightweight},traitsArmor = {nameTraitLightweight,nameTraitRough})
	@CompoHead(durability = 612,miningspeed = 6.6,attack = 3.6,harvestLevel = 2)
	@CompoHandle(modifier = 0.95, durability = 180)
	@CompoExtra(extraDurability = 150)
	@CompoBow(drawSpeed = 0.45,range = 1.7,bonusDamage = 4)
	@CompoArrowShaft(modifier = 1.35,bonusAmmo = 25)
	@CompoArmorCore(durability = 1,defense = 1)
	@CompoArmorPlate(modifier = 1,durability = 1,toughness = 1) // info 属性需要改
	@CompoArmorTrim(extraDurability = 1)
	public static final Material titanium=new $Material(nameTitanium,colorTitanium)
			.addItemAsIngot(Items.ingotTitanium)
			.setFluid(Fluids.moltenTitanium);

	@Compo(value = namePolarium,traitsTool = {nameTraitUnnatural},traitsArmor = {nameTraitBouncy})
	@CompoHead(durability = 620,miningspeed = 4.8,attack = 4.9,harvestLevel = 4)
	@CompoHandle(modifier = 1.1, durability = 90)
	@CompoExtra(extraDurability = 90)
	@CompoBow(drawSpeed = 0.9,range = 1.25,bonusDamage = 2.5)
	@CompoArrowShaft(modifier = 1.25,bonusAmmo = 35)
	@CompoArmorCore(durability = 1,defense = 1)
	@CompoArmorPlate(modifier = 1,durability = 1,toughness = 1) // info 属性需要改
	@CompoArmorTrim(extraDurability = 1)
	public static final Material polarium=new $Material(namePolarium,colorPolarium)
			.addItemAsIngot(Items.ingotPolarium)
			.setFluid(Fluids.moltenPolarium);

	@Compo(value = nameHalleium,traitsTool = {nameTraitJagged,nameTraitSwitching},traitsArmor = {nameTraitSwitching})
	@CompoHead(durability = 560,miningspeed = 7.8,attack = 3.9,harvestLevel = 4)
	@CompoHandle(modifier = 1.05, durability = 140)
	@CompoExtra(extraDurability = 80)
	@CompoBow(drawSpeed = 1.45,range = 0.65f,bonusDamage = 0.5)
	@CompoArrowShaft(modifier = 1.1,bonusAmmo = 45)
	@CompoArmorCore(durability = 1,defense = 1)
	@CompoArmorPlate(modifier = 1,durability = 1,toughness = 1) // info 属性需要改
	@CompoArmorTrim(extraDurability = 1)
	public static final Material halleium=new $Material(nameHalleium,colorHalleium)
			.addItemAsIngot(Items.ingotHalleium)
			.setFluid(Fluids.moltenHalleium);

	@Compo(value = nameAltairium,traitsTool = {nameTraitStarDashing},traitsArmor = {nameTraitStarDashing})
	@CompoHead(durability = 490,miningspeed = 5.8,attack = 6.5,harvestLevel = 4)
	@CompoHandle(modifier = 1.25, durability = 55)
	@CompoExtra(extraDurability = 25)
	@CompoBow(drawSpeed = 0.85,range=1.15,bonusDamage = 4)
	@CompoArrowShaft(modifier = 1,bonusAmmo = 40)
	@CompoArmorCore(durability = 1,defense = 1)
	@CompoArmorPlate(modifier = 1,durability = 1,toughness = 1) // info 属性需要改
	@CompoArmorTrim(extraDurability = 1)
	public static final Material altairium=new $Material(nameAltairium,colorAltairium)
			.addItemAsIngot(Items.ingotAltairium)
			.setFluid(Fluids.moltenAltairium);

	@Compo(value = nameCocoa,traitsTool = {nameTraitTasty},traitsArmor = {nameTraitTasty})
	@CompoHead(durability = 30,miningspeed = 0.8,attack = 1.2,harvestLevel = 0)
	@CompoHandle(modifier = 0.4, durability = -20)
	@CompoExtra(extraDurability = 15)
	@CompoArmorCore(durability = 1,defense = 1)
	@CompoArmorPlate(modifier = 1,durability = 1,toughness = 1) // info 属性需要改
	@CompoArmorTrim(extraDurability = 1)
	public static final Material cocoa=new $Material(nameCocoa,colorCocoa)
			.addItemAsIngot(Items.ingotCocoa)
			.setFluid(Fluids.moltenCocoa);

	@Compo(value = nameNitre,traitsTool = {nameTraitSoluble,nameTraitJagged},traitsArmor = {nameTraitSoluble})
	@CompoHead(durability = 140,miningspeed = 2.3,attack = 4.4,harvestLevel = 1)
	@CompoHandle(modifier = 0.6, durability = -60)
	@CompoExtra(extraDurability = 40)
	@CompoArmorCore(durability = 1,defense = 1)
	@CompoArmorPlate(modifier = 1,durability = 1,toughness = 1) // info 属性需要改
	@CompoArmorTrim(extraDurability = 1)
	public static final Material nitre=new $Material(nameNitre,colorNitre)
			.addItemAsIngot(Items.nitre);

	@Compo(value = nameIcelandSpar,traitsTool = {nameTraitBirefringent},traitsArmor = {nameTraitDiffuseReflecting})
	@CompoHead(durability = 340,miningspeed = 1.5,attack = 5.6,harvestLevel = 1)
	@CompoHandle(modifier = 0.65, durability = -200)
	@CompoExtra(extraDurability = 80)
	@CompoArmorCore(durability = 1,defense = 1)
	@CompoArmorPlate(modifier = 1,durability = 1,toughness = 1) // info 属性需要改
	@CompoArmorTrim(extraDurability = 1)
	public static final Material icelandSpar=new $Material(nameIcelandSpar,colorIcelandSpar)
			.addItemAsIngot(Items.icelandSpar);

	@Compo(value = namePyrophyllite,traitsTool = {nameTraitEcological},traitsArmor = {nameTraitEcological})
	@CompoHead(durability = 380,miningspeed = 1.9,attack = 4.5,harvestLevel = 1)
	@CompoHandle(modifier = 0.75, durability = 250)
	@CompoExtra(extraDurability = 65)
	@CompoArmorCore(durability = 1,defense = 1)
	@CompoArmorPlate(modifier = 1,durability = 1,toughness = 1) // info 属性需要改
	@CompoArmorTrim(extraDurability = 1)
	public static final Material pyrophyllite=new $Material(namePyrophyllite,colorPyrophyllite)
			.addItemAsIngot(Items.pyrophyllite);

	@Compo(value = nameSpinel,traitsTool = {nameTraitShaking},traitsArmor = {nameTraitLifting})
	@CompoHead(durability = 1250,miningspeed = 6.5,attack = 8,harvestLevel = 3)
	@CompoHandle(modifier = 1.25, durability = 150)
	@CompoExtra(extraDurability = 450)
	@CompoArmorCore(durability = 1,defense = 1)
	@CompoArmorPlate(modifier = 1,durability = 1,toughness = 1) // info 属性需要改
	@CompoArmorTrim(extraDurability = 1)
	public static final Material spinel=new $Material(nameSpinel,colorSpinel)
			.addItemAsIngot(Items.spinel);

	@Compo(value = nameTalcum,traitsTool = {nameTraitWritable1},traitsArmor = {nameTraitSliding})
	@CompoHead(durability = 140,miningspeed = 3.1,attack = 3.3,harvestLevel = 1)
	@CompoExtra(extraDurability = 190)
	@CompoArmorCore(durability = 1,defense = 1)
	@CompoArmorPlate(modifier = 1,durability = 1,toughness = 1) // info 属性需要改
	@CompoArmorTrim(extraDurability = 1)
	public static final Material talcum=new $Material(nameTalcum,colorTalcum)
			.addItemAsIngot(Items.talcum);

	@Compo(value = nameTourmaline,traitsTool = {nameTraitPyroelectric})
	@CompoHead(durability = 420,miningspeed = 5.9,attack = 6.5,harvestLevel = 2)
	@CompoHandle(modifier = 0.45, durability = -250)
	@CompoExtra(extraDurability = 180)
	@CompoArmorCore(durability = 1,defense = 1)
	@CompoArmorPlate(modifier = 1,durability = 1,toughness = 1) // info 属性需要改
	@CompoArmorTrim(extraDurability = 1)
	public static final Material tourmaline=new $Material(nameTourmaline,colorTourmaline)
			.addItemAsIngot(Items.tourmaline);

	@Compo(value = nameTonium,traitsTool = {nameTraitRepressing},traitsArmor = {nameTraitSteady})
	@CompoHead(durability = 1980,miningspeed = 3.5,attack = 9.5,harvestLevel = 4)
	@CompoHandle(modifier = 1.15, durability = 280)
	@CompoExtra(extraDurability = 490)
	@CompoBow(drawSpeed = 0.4,range = 2.85,bonusDamage = 6.5)
	@CompoArrowShaft(modifier = 0.35,bonusAmmo = 65)
	@CompoArmorCore(durability = 1,defense = 1)
	@CompoArmorPlate(modifier = 1,durability = 1,toughness = 1) // info 属性需要改
	@CompoArmorTrim(extraDurability = 1)
	public static final Material tonium=new $Material(nameTonium,colorTonium)
			.addItemAsIngot(Items.ingotTonium);

	@Compo(value = nameMeteorolite,traitsTool = {nameTraitAlien},traitsArmor = {nameTraitAlien})
	@CompoHead(durability = 940,miningspeed = 5.5,attack = 6.5,harvestLevel = 3)
	@CompoHandle(modifier = 0.75f, durability = 20)
	@CompoExtra(extraDurability = 140)
	@CompoArmorCore(durability = 1,defense = 1)
	@CompoArmorPlate(modifier = 1,durability = 1,toughness = 1) // info 属性需要改
	@CompoArmorTrim(extraDurability = 1)
	public static final Material meteorolite=new $Material(nameMeteorolite,colorMeteorolite)
			.addItemAsIngot(Blocks.blockMeteorolite);

	@Compo(value = nameCoal,traitsTool = {nameTraitCarbonizing},traitsArmor={nameTraitCarbonizing})
	@CompoHead(durability = 50,miningspeed = 0.5,attack = 1,harvestLevel = 0)
	@CompoHandle(modifier = 0.5,durability = -100)
	@CompoExtra(extraDurability = 150)
	@CompoArmorCore(durability = 1,defense = 1)
	@CompoArmorPlate(modifier =1,durability = 1,toughness = 1)
	@CompoArmorTrim(extraDurability = 1)
	public static final Material coal=new $Material(nameCoal,colorCoal)
			.addItemAsIngot(net.minecraft.init.Items.COAL);

	@Compo(value = nameSunStone,traitsTool = {nameTraitSunPower},traitsArmor = {nameTraitSunPower})
	@CompoHead(durability = 545,miningspeed = 5.75,attack = 6.25,harvestLevel = 3,traits = {nameTraitFlammable})
	@CompoHandle(modifier = 1.1, durability = 50)
	@CompoExtra(extraDurability = 70)
	@CompoBow(drawSpeed = 2.75,range=1,bonusDamage = 4)
	@CompoArmorCore(durability = 1,defense = 1)
	@CompoArmorPlate(modifier = 1,durability = 1,toughness = 1) // info 属性需要改
	@CompoArmorTrim(extraDurability = 1)
	public static final Material sunStone=new $Material(nameSunStone,colorSunStone)
			.addItemAsIngot(Items.sunStone);

	@Compo(value = nameMoonStone,traitsTool = {nameTraitMoonPower},traitsArmor = {nameTraitMoonPower})
	@CompoHead(durability = 545,miningspeed = 6.25,attack = 5.75,harvestLevel = 3,traits = {nameTraitMoonlight})
	@CompoHandle(modifier = 1.1, durability = 50)
	@CompoExtra(extraDurability = 70)
	@CompoBow(drawSpeed = 2.75,range=1,bonusDamage = 4)
	@CompoArmorCore(durability = 1,defense = 1)
	@CompoArmorPlate(modifier = 1,durability = 1,toughness = 1) // info 属性需要改
	@CompoArmorTrim(extraDurability = 1)
	public static final Material moonStone=new $Material(nameMoonStone,colorMoonStone)
			.addItemAsIngot(Items.moonStone);

	@Compo(value = nameEnderDragonSquama,traitsTool = {nameTraitEnderference,nameTraitDense})
	@CompoHead(durability = 675,miningspeed = 5.8, attack = 6.7,harvestLevel = 2,traits = {nameTraitLionheart})
	@CompoHandle(modifier = 1.05, durability = 40)
	@CompoExtra(extraDurability = 120)
	@CompoFletching(accuracy = 0.8,modifier = 3.5f)
	@CompoArmorCore(durability = 1,defense = 1)
	@CompoArmorPlate(modifier = 1,durability = 1,toughness = 1) // info 属性需要改
	@CompoArmorTrim(extraDurability = 1)
	public static final Material enderDragonSquama=new $Material(nameEnderDragonSquama,colorEnderDragonSquama)
			.addItemAsIngot(Items.enderDragonSquama);
//			.addToolTrait(enderference)
//			.addToolTrait(dense)
//			.addPartTrait(lionheart,HEAD)
//			.addPartTrait(enderference,HEAD); // fixme

	@Compo(value = nameFulgurite,traitsTool = {nameTraitThunderWaving})
	@CompoHead(durability = 880,miningspeed = 5.36, attack = 7.5, harvestLevel = 4)
	@CompoExtra(extraDurability = 50)
	@CompoArmorCore(durability = 1,defense = 1)
	@CompoArmorPlate(modifier = 1,durability = 1,toughness = 1) // info 属性需要改
	@CompoArmorTrim(extraDurability = 1)
	public static final Material fulgurite=new $Material(nameFulgurite,colorFulgurite)
			.addItemAsIngot(Blocks.blockFulgurite);

	@Compo(value = nameIrisia,traitsTool = {nameTraitLightweight})
	@CompoHead(durability = 130,miningspeed = 7.7,attack = 9.3,harvestLevel = 4)
	@CompoHandle(modifier = 0.75,durability = 0)
	@CompoExtra(extraDurability = 20)
	@CompoArmorCore(durability = 1,defense = 1)
	@CompoArmorPlate(modifier = 1,durability = 1,toughness = 1) // info 属性需要改
	@CompoArmorTrim(extraDurability = 1)
	public static final Material irisia=new $Material(nameIrisia,colorIrisia)
			.addItemAsIngot(Items.ingotIrisia);

	@Compo(value = nameTreeRoot,traitsTool = {nameTraitEcological,nameTraitDense},traitsArmor = {nameTraitEcological,nameTraitDense})
	@CompoHead(durability = 165,miningspeed = 3.6,attack = 3.2,harvestLevel = 1)
	@CompoHandle(modifier = 0.9,durability = 40)
	@CompoExtra(extraDurability = 25)
	@CompoBow(drawSpeed = 0.8,range=1.15,bonusDamage = 0.5)
	@CompoArrowShaft(modifier = 1.2,bonusAmmo = 25)
	@CompoArmorCore(durability = 1,defense = 1)
	@CompoArmorPlate(modifier = 1,durability = 1,toughness = 1) // info 属性需要改
	@CompoArmorTrim(extraDurability = 1)
	public static final Material treeRoot=new $Material(nameTreeRoot,colorTreeRoot)
			.addItemAsIngot(Items.treeRoot);

	@Compo(value = nameFlesh,traitsTool = {nameTraitGluttonic},traitsArmor = {nameTraitDeepParasitic})
	@CompoHead(durability = 220,miningspeed = 3.8,attack = 4,harvestLevel = 0)
	@CompoHandle(modifier = 0.8,durability = -50)
	@CompoExtra(extraDurability = 59)
	@CompoArmorCore(durability = 1,defense = 1)
	@CompoArmorPlate(modifier = 1,durability = 1,toughness = 1) // info 属性需要改
	@CompoArmorTrim(extraDurability = 1)
	public static final Material flesh=new $Material(nameFlesh,colorFlesh)
			.addItemAsIngot(Items.flesh);

	@Compo(value = nameBrokenBedrock,traitsTool = {nameTraitDuritos},traitsArmor = {nameTraitHeavy,nameTraitOverHeavy})
	@CompoHead(durability = 1850,miningspeed = 0.5,attack = 1.5,harvestLevel = 4,traits={nameTraitDense})
	@CompoHandle(modifier = 1.35,durability = 160)
	@CompoExtra(extraDurability = 270)
	@CompoArmorCore(durability = 1,defense = 1)
	@CompoArmorPlate(modifier = 1,durability = 1,toughness = 1) // info 属性需要改
	@CompoArmorTrim(extraDurability = 1)
	public static final Material brokenBedrock=new $Material(nameBrokenBedrock, colorBrokenBedrock)
			.addItemAsIngot(Items.brokenBedrock);
//			.addToolTrait(duritos)
//			.addPartTrait(dense,HEAD);

	@Compo(nameCloud)
	@CompoHead(durability = 20,miningspeed = 0.5,attack = 0,harvestLevel = 0,traits = {nameTraitSqueaky})
	@CompoExtra(extraDurability = -300,traits = {nameTraitLightweight})
	@CompoArmorCore(durability = 1,defense = 1)
	@CompoArmorPlate(modifier = 1,durability = 1,toughness = 1) // info 属性需要改
	@CompoArmorTrim(extraDurability = 1)
	public static final Material cloud=new $Material(nameCloud, colorCloud)
			.addItemAsIngot(Blocks.blockCloud);

	@Compo(value = nameOpal,traitsTool = {nameTraitGorgeous},traitsArmor = {nameTraitGorgeous})
	@CompoHead(durability = 230,miningspeed = 3.1,attack = 3.7,harvestLevel = 0)
	@CompoHandle(modifier = 0.84,durability = -45)
	@CompoExtra(extraDurability = 115)
	@CompoArmorCore(durability = 1,defense = 1)
	@CompoArmorPlate(modifier = 1,durability = 1,toughness = 1) // info 属性需要改
	@CompoArmorTrim(extraDurability = 1)
	public static final Material opal=new $Material(nameOpal, colorOpal)
			.addItemAsIngot(Items.opal);

	@Compo(value = nameTopaz,traitsTool = {nameTraitPeaceEnergetic},traitsArmor = {nameTraitVoltaic})
	@CompoHead(durability = 490,miningspeed = 5.6,attack = 4.4,harvestLevel = 3)
	@CompoHandle(modifier = 1.15,durability = 90)
	@CompoExtra(extraDurability = 280)
	@CompoArmorCore(durability = 1,defense = 1)
	@CompoArmorPlate(modifier = 1,durability = 1,toughness = 1) // info 属性需要改
	@CompoArmorTrim(extraDurability = 1)
	public static final Material topaz=new $Material(nameTopaz, colorTopaz)
			.addItemAsIngot(Items.topaz);

	@Compo(value = nameLizanite,traitsTool = {nameTraitHyper},traitsArmor = {nameTraitHyper})
	@CompoHead(durability = 730,miningspeed = 6.2,attack = 6.9,harvestLevel = 3)
	@CompoHandle(modifier = 1.15,durability = 90)
	@CompoExtra(extraDurability = 310)
	@CompoArmorCore(durability = 1,defense = 1)
	@CompoArmorPlate(modifier = 1,durability = 1,toughness = 1) // info 属性需要改
	@CompoArmorTrim(extraDurability = 1)
	public static final Material lizanite =new $Material(nameLizanite, colorLizanite)
			.addItemAsIngot(Items.lizanite);

	@Compo(value = nameCordierite,traitsTool = {nameTraitDichroic},traitsArmor = {nameTraitDichroic})
	@CompoHead(durability = 425,miningspeed = 5.9,attack = 3.9,harvestLevel = 3)
	@CompoHandle(modifier = 1.05,durability = 35)
	@CompoExtra(extraDurability = 215)
	@CompoArmorCore(durability = 1,defense = 1)
	@CompoArmorPlate(modifier = 1,durability = 1,toughness = 1) // info 属性需要改
	@CompoArmorTrim(extraDurability = 1)
	public static final Material cordierite=new $Material(nameCordierite, colorCordierite)
			.addItemAsIngot(Items.cordierite);

	@Compo(value = namePrehnite,traitsTool = {nameTraitLifeInspiring},traitsArmor = {nameTraitLifeInspiring})
	@CompoHead(durability = 370,miningspeed = 4.1f,attack = 3.3f,harvestLevel = 3)
	@CompoHandle(modifier = 0.95,durability = 20)
	@CompoExtra(extraDurability = 55)
	@CompoArmorCore(durability = 1,defense = 1)
	@CompoArmorPlate(modifier = 1,durability = 1,toughness = 1) // info 属性需要改
	@CompoArmorTrim(extraDurability = 1)
	public static final Material prehnite=new $Material(namePrehnite, colorPrehnite)
			.addItemAsIngot(Items.prehnite);

	@Compo(value = nameProustite,traitsTool = {nameTraitPoisonous},traitsArmor = {nameTraitPoisonous})
	@CompoHead(durability = 290,miningspeed = 3.5,attack = 4.7,harvestLevel = 2)
	@CompoHandle(modifier = 0.85,durability = 80)
	@CompoExtra(extraDurability = 35)
	@CompoArmorCore(durability = 1,defense = 1)
	@CompoArmorPlate(modifier = 1,durability = 1,toughness = 1) // info 属性需要改
	@CompoArmorTrim(extraDurability = 1)
	public static final Material proustite=new $Material(nameProustite, colorProustite);

	@Compo(value = nameOraclium,traitsTool = {nameTraitOracular},traitsArmor = {nameTraitThresholdLimiting})
	@CompoHead(durability = 740,miningspeed = 5.6,attack = 6.8,harvestLevel = 4)
	@CompoHandle(modifier = 1.05,durability = 140)
	@CompoExtra(extraDurability = 45)
	@CompoArmorCore(durability = 1,defense = 1)
	@CompoArmorPlate(modifier = 1,durability = 1,toughness = 1) // info 属性需要改
	@CompoArmorTrim(extraDurability = 1)
	public static final Material oraclium=new $Material(nameOraclium, colorOraclium)
			.addItemAsIngot(Items.ingotOraclium)
			.setFluid(Fluids.moltenOraclium);

	@Compo(value = nameVibratingCrystal,traitsTool = {nameTraitTreasureDetecting},traitsArmor = {nameTraitIndomitable})
	@CompoHead(durability = 800,miningspeed = 7.4,attack = 4.5,harvestLevel = 3)
	@CompoHandle(modifier = 0.7,durability = 230)
	@CompoExtra(extraDurability = 30)
	@CompoArmorCore(durability = 1,defense = 1)
	@CompoArmorPlate(modifier = 1,durability = 1,toughness = 1) // info 属性需要改
	@CompoArmorTrim(extraDurability = 1)
	public static final Material vibratingCrystal=new $Material(nameVibratingCrystal, colorVibratingCrystal)
			.addItemAsIngot(Items.vibratingCrystal);

	@Compo(value = nameLavaCrystal,traitsArmor = {nameTraitThermalGathering})
	@CompoHead(durability = 540,miningspeed = 6.5,attack = 6.4,harvestLevel = 3,traits = {nameTraitFlammable})
	@CompoHandle(modifier = 0.98,durability = 120,traits = {nameTraitThermalGathering})
	@CompoExtra(extraDurability = 45,traits = {nameTraitFlammable})
	@CompoArmorCore(durability = 1,defense = 1)
	@CompoArmorPlate(modifier = 1,durability = 1,toughness = 1) // info 属性需要改
	@CompoArmorTrim(extraDurability = 1)
	public static final Material lavaCrystal=new $Material(nameLavaCrystal, colorLavaCrystal)
			.addItemAsIngot(Items.lavaCrystal);

	@Compo(value = nameSteamium,traitsTool = {nameTraitSteamy},traitsArmor = {nameTraitAquaspeed})
	@CompoHead(durability = 690,miningspeed = 5.5,attack = 6.5,harvestLevel = 2)
	@CompoHandle(modifier = 0.65,durability = 100)
	@CompoExtra(extraDurability = -80)
	@CompoArmorCore(durability = 1,defense = 1)
	@CompoArmorPlate(modifier = 1,durability = 1,toughness = 1) // info 属性需要改
	@CompoArmorTrim(extraDurability = 1)
	public static final Material steamium=new $Material(nameSteamium, colorSteamium)
			.addItemAsIngot(Items.ingotSteamium);

	@Compo(value = nameGrain,traitsTool = {nameTraitDecoying},traitsArmor = {nameTraitCombustible})
	@CompoHead(durability = 240,miningspeed = 3.6,attack = 3.5,harvestLevel = 1)
	@CompoHandle(modifier = 1.05,durability = 10)
	@CompoExtra(extraDurability = 28)
	@CompoArmorCore(durability = 1,defense = 1)
	@CompoArmorPlate(modifier = 1,durability = 1,toughness = 1) // info 属性需要改
	@CompoArmorTrim(extraDurability = 1)
	public static final Material grain=new $Material(nameGrain, colorGrain)
			.addItemAsIngot(Items.ingotGrain);

	@Compo(value = nameAventurine,traitsTool = {nameTraitStaminaFocusing},traitsArmor = {nameTraitStaminaFocusing})
	@CompoHead(durability = 720,miningspeed = 6,attack = 7.3,harvestLevel = 3)
	@CompoHandle(modifier = 0.9,durability = 40)
	@CompoExtra(extraDurability = 50)
	@CompoArmorCore(durability = 1,defense = 1)
	@CompoArmorPlate(modifier = 1,durability = 1,toughness = 1) // info 属性需要改
	@CompoArmorTrim(extraDurability = 1)
	public static final Material aventurine =new $Material(nameAventurine, colorAventurine)
			.addItemAsIngot(Items.aventurine);

//	public static Material cloudStorm=new Material("cloud_storm", Colors.DarkBlue);
//	public static Material skyCrystal=new Material("sky_crystal", Colors.Aqua);
//	public static Material stormCrystal=new Material("storm_crystal", Colors.DarkBlue);
//	public static Material antiGraCrystal=new Material("anti_gra_crystal", Colors.DarkGreen);
//	public static Material phantomCrystal=new Material("phantom_crystal", Colors.Gray);
//	public static Material paradiseMetal=new Material("paradise_metal", Colors.Yellow);

}
