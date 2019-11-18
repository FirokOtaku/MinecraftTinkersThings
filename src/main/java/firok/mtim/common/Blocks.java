package firok.mtim.common;

import firok.mtim.block.BlockOre;
import firok.mtim.util.Reg;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

@SuppressWarnings("all")
public class Blocks
{
	// 矿石
	@Reg(tn="ore_cinnabar",un="ore_cinnabar")
	public static final BlockOre oreCinnabar; // 辰砂矿石
	@Reg(tn="ore_ink",un="ore_ink")
	public static final BlockOre oreInk; // 墨粉矿石

	static
	{
		oreCinnabar=new BlockOre(Items.cinnabar,1,5,2,3,5);
		oreInk=new BlockOre(Items.inkPowder,4,8,2,2,7);
	}

	// 矿块
	@Reg(tn="block_cinnabar",un="block_cinnabar")
	public static final Block blockCinnabar; // 辰砂块
	@Reg(tn="block_ink",un="block_ink")
	public static final Block blockInk; // 墨粉块
	@Reg(tn="block_stellar",un="block_stellar")
	public static final Block blockStellar; // 恒星金属块
	static
	{
		blockCinnabar=new Block(Material.IRON);
		blockInk=new Block(Material.IRON);
		blockStellar=new Block(Material.IRON);
	}
}
