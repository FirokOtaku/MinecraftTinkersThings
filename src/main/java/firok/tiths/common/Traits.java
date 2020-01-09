package firok.tiths.common;

import firok.tiths.traits.*;
import slimeknights.tconstruct.library.traits.AbstractTrait;

@SuppressWarnings("all")
public class Traits
{
	public static final AbstractTrait maiming=new TraitMaiming(); // 致残
	public static final AbstractTrait luxurious=new TraitLuxurious(); // 奢华
	public static final AbstractTrait retrospective=new TraitRetrospective(); // 后知
	public static final AbstractTrait radiant=new TraitRadiant(); // 辉耀
	public static final AbstractTrait switching=new TraitSwitching(); // 换位
	public static final AbstractTrait icy=new TraitIcy(); // 冰冷
	public static final AbstractTrait clustering=new TraitClustering(); // 群簇
	public static final AbstractTrait starDashing=new TraitStarDashing(); // 星绽
	public static final AbstractTrait soluble=new TraitSoluble(); // 可溶
	public static final AbstractTrait birefringent=new TraitBirefringent(); // 双折
	public static final AbstractTrait pyroelectric=new TraitPyroelectric(); // 热释电
	public static final AbstractTrait shaking=new TraitShaking(); // 撼击
	public static final AbstractTrait inky=new TraitInky(); // 墨染
	static{inky.addItem(Items.inkySlime);}
	public static final AbstractTrait hemolytic=new TraitHemolytic(); // 溶血
	public static final AbstractTrait extremeFreezing=new TraitExtremeFreezing(); // 极寒
	public static final AbstractTrait antiGrav=new TraitAntiGrav(); // 反重力
	public static final AbstractTrait stonePhasing=new TraitStonePhasing(); // 石之相变
	static{stonePhasing.addItem(Items.phasingGem);}
	public static final AbstractTrait treasureDetecting=new TraitTreasureDetecting(); // 宝藏感知
	public static final AbstractTrait creaky=new TraitCreaky(); // 喀嚓
	public static final AbstractTrait undeadCalling=new TraitUndeadCalling(); // 亡灵呼唤
	public static final AbstractTrait repressing=new TraitRepressing(); // 压制
}
