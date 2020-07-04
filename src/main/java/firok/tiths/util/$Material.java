package firok.tiths.util;

import c4.conarm.lib.materials.CoreMaterialStats;
import c4.conarm.lib.materials.PlatesMaterialStats;
import c4.conarm.lib.materials.TrimMaterialStats;
import firok.tiths.common.ConfigJson;
import firok.tiths.util.conf.MaterialInfo;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.Optional;
import slimeknights.tconstruct.library.materials.*;

import java.util.Objects;
import java.util.stream.Stream;

import static firok.tiths.TinkersThings.CONARM_ID;

public class $Material extends Material
{
	public $Material(String identifier, int color)
	{
		super(identifier, color);
	}

	public $Material(String identifier, int color, boolean hidden)
	{
		super(identifier, color, hidden);
	}

	public $Material addItemAsIngot(Item item)
	{
		this.addItem(item,1,VALUE_Ingot);
		this.setRepresentativeItem(item);
		return this;
	}
	public $Material addItemAsIngot(Block block)
	{
		this.addItem(block,VALUE_Ingot);
		this.setRepresentativeItem(block);
		return this;
	}

	@Override
	public $Material setFluid(Fluid fluid)
	{
		super.setFluid(fluid);
		return this;
	}

//	/* ---- 整体属性 ---- */
//	String[] compoTraitsTool; // 工具属性
//	String[] compoTraitsBow; // 弓箭属性
//	String[] compoTraitsArmor; // 护甲属性
//	public $Material compo(String[] traitsTool,String[] traitsBow,String[] traitsArmor)
//	{
//		Objects.requireNonNull(traitsTool);
//		Objects.requireNonNull(traitsBow);
//		Objects.requireNonNull(traitsArmor);
//		this.compoTraitsTool=traitsTool;
//		this.compoTraitsBow=traitsBow;
//		this.compoTraitsArmor=traitsArmor;
//		return this;
//	}
//
//	/* ---- 头部属性 ---- */
//	Integer compoHeadDurability;
//	Double compoHeadMiningspeed;
//	Double compoHeadAttack;
//	Integer compoHeadHarvestLevel;
//	String[] compoHeadTraits;
//	public $Material compoHead(int durability,double miningspeed,double attack,int harvestLevel,String...traits)
//	{
//		this.compoHeadDurability=durability;
//		this.compoHeadMiningspeed=miningspeed;
//		this.compoHeadAttack=attack;
//		this.compoHeadHarvestLevel=harvestLevel;
//		this.compoHeadTraits=traits;
//		return this;
//	}
//	private HeadMaterialStats compoHead()
//	{
//		return compoHeadDurability!=null?
//				new HeadMaterialStats(
//						this.compoHeadDurability,
//						this.compoHeadMiningspeed.floatValue(),
//						this.compoHeadAttack.floatValue(),
//						this.compoHeadHarvestLevel)
//				:null;
//	}
//
//	/* ---- 手柄属性 ---- */
//	Double compoHandleModifier;
//	Integer compoHandleDurability;
//	String[] compoHandleTraits;
//	public $Material compoHandle(double modifier,int durability,String...traits)
//	{
//		this.compoHandleModifier=modifier;
//		this.compoHandleDurability=durability;
//		this.compoHandleTraits=traits;
//		return this;
//	}
//	private HandleMaterialStats compoHandle()
//	{
//		return compoHandleModifier!=null?
//				new HandleMaterialStats(
//						this.compoHandleModifier.floatValue(),
//						this.compoHandleDurability)
//				:null;
//	}
//
//	/* ---- 额外属性 ---- */
//	Integer compoExtraDurability;
//	String[] compoExtraTraits;
//	public $Material compoExtra(int durability,String...traits)
//	{
//		this.compoExtraDurability=durability;
//		this.compoExtraTraits=traits;
//		return this;
//	}
//	private ExtraMaterialStats compoExtra()
//	{
//		return compoExtraDurability!=null?
//				new ExtraMaterialStats(this.compoExtraDurability)
//				:null;
//	}
//
//	/* ---- 弓臂属性 ---- */
//	Double compoBowDrawspeed;
//	Double compoBowRange;
//	Double compoBowDamage;
//	String[] compoBowTraits;
//	public $Material compoBow(double drawspeed,double range,double damage,String...traits)
//	{
//		this.compoBowDrawspeed=drawspeed;
//		this.compoBowRange=range;
//		this.compoBowDamage=damage;
//		this.compoBowTraits=traits;
//		return this;
//	}
//	private BowMaterialStats compoBow()
//	{
//		return compoBowDrawspeed!=null?
//				new BowMaterialStats(
//						this.compoBowDrawspeed.floatValue(),
//						this.compoBowRange.floatValue(),
//						this.compoBowDamage.floatValue()
//						):null;
//	}
//
//	/* ---- 弓弦属性 ---- */
//	Double compoStringModifier;
//	String[] compoStringTraits;
//	public $Material compoBowString(double modifier,String[] traits)
//	{
//		this.compoStringModifier=modifier;
//		this.compoStringTraits=traits;
//		return this;
//	}
//	private BowStringMaterialStats compoBowString()
//	{
//		return compoStringModifier!=null?
//				new BowStringMaterialStats(this.compoStringModifier.floatValue())
//				:null;
//	}
//
//	/* ---- 箭羽属性 ---- */
//	Double compoFletchingAccuracy;
//	Double compoFletchingModifier;
//	String[] compoFletchingTraits;
//	public $Material compoFletching(double accuracy,double modifier,String...traits)
//	{
//		this.compoFletchingAccuracy=accuracy;
//		this.compoFletchingModifier=modifier;
//		this.compoFletchingTraits=traits;
//		return this;
//	}
//	private FletchingMaterialStats compoFletching()
//	{
//		return compoFletchingAccuracy!=null?
//				new FletchingMaterialStats(
//						this.compoFletchingAccuracy.floatValue(),
//						this.compoFletchingModifier.floatValue()
//				):null;
//	}
//
//	/* ---- 箭杆属性 ---- */
//	Double compoShaftModifier;
//	Integer compoShaftAmmo;
//	String[] compoShaftTraits;
//	public $Material compoArrowShaft(double modifier,int ammo,String...traits)
//	{
//		this.compoShaftModifier=modifier;
//		this.compoShaftAmmo=ammo;
//		this.compoShaftTraits=traits;
//		return this;
//	}
//	private ArrowShaftMaterialStats compoShaft()
//	{
//		return compoShaftModifier!=null?
//				new ArrowShaftMaterialStats(
//						this.compoShaftModifier.floatValue(),
//						this.compoShaftAmmo
//				):null;
//	}
//
//	/* ---- 护甲基底属性 ---- */
//	Double compoArmorCoreDurability;
//	Double compoArmorCoreDefense;
//	String[] compoArmorCoreTraits;
//	public $Material compoArmorCore(double durability,double defense,String...traits)
//	{
//		compoArmorCoreDurability=durability;
//		compoArmorCoreDefense=defense;
//		compoArmorCoreTraits=traits;
//		return this;
//	}
//	@Optional.Method(modid = CONARM_ID)
//	private CoreMaterialStats compoArmorCore()
//	{
//		return compoArmorCoreDurability!=null?
//				new CoreMaterialStats(
//						this.compoArmorCoreDurability.floatValue(),
//						this.compoArmorCoreDefense.floatValue()
//				):null;
//	}
//
//	/* ---- 护甲板属性 ---- */
//	Double compoArmorPlateModifier;
//	Double compoArmorPlateDurability;
//	Double compoArmorPlateToughness;
//	String[] compoArmorPlateTraits;
//	public $Material compoArmorPlate(double modifier,double durability,double toughness,String...traits)
//	{
//		this.compoArmorPlateModifier=modifier;
//		this.compoArmorPlateDurability=durability;
//		this.compoArmorPlateToughness=toughness;
//		this.compoArmorPlateTraits=traits;
//		return this;
//	}
//	@Optional.Method(modid = CONARM_ID)
//	private PlatesMaterialStats compoArmorPlate()
//	{
//		return this.compoArmorPlateModifier!=null?
//				new PlatesMaterialStats(
//						this.compoArmorPlateModifier.floatValue(),
//						this.compoArmorPlateDurability.floatValue(),
//						this.compoArmorPlateToughness.floatValue()
//				):null;
//	}
//
//	/* ---- 护甲夹板属性 ---- */
//	Double compoArmorTrimDurability;
//	String[] compoArmorTrimTraits;
//	public $Material compoArmorTrim(double durability,String...traits)
//	{
//		this.compoArmorTrimDurability=durability;
//		this.compoArmorTrimTraits=traits;
//		return this;
//	}
//	@Optional.Method(modid = CONARM_ID)
//	private TrimMaterialStats compoArmorTrim()
//	{
//		return this.compoArmorTrimDurability!=null?
//				new TrimMaterialStats(this.compoArmorTrimDurability.floatValue())
//				:null;
//	}
//
//	/**
//	 * 注册工具和弓箭属性
//	 */
//	public void initTools()
//	{
//		Stream.of(
//				compoHead(),
//				compoHandle(),
//				compoExtra(),
//				compoBow(),
//				compoBowString(),
//				compoShaft(),
//				compoFletching()
//		)
//		.filter(Objects::nonNull)
//		.forEach(this::addStats);
//	}
//
//	/**
//	 * 注册护甲属性
//	 */
//	@Optional.Method(modid = CONARM_ID)
//	public void initArmors()
//	{
//		Stream.of(
//				compoArmorCore(),
//				compoArmorPlate(),
//				compoArmorTrim()
//		)
//		.filter(Objects::nonNull)
//		.forEach(this::addStats);
//	}

}
