package firok.mtim.common;

import firok.mtim.block.BlockOre;
import firok.mtim.util.Reg;

@SuppressWarnings("all")
public class Blocks
{
	@Reg(tn="ore_cinnabar",un="ore_cinnabar")
	public static final BlockOre oreCinnabar; // 辰砂矿石
	@Reg(tn="ore_ink",un="ore_ink")
	public static final BlockOre oreInk; // 墨粉矿石
	@Reg(tn="ore_stellar",un="ore_stellar")
	public static final BlockOre oreStellar; // 恒星金属矿石
	static
	{
		oreCinnabar=new BlockOre(Items.cinnabar,1,5,2,3,5);
		oreInk=new BlockOre(Items.inkPowder,4,8,2,2,7);
		oreStellar=new BlockOre();
	}
}
