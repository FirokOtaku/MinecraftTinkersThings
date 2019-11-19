package firok.mtim.common;

import firok.mtim.block.BlockOre;
import firok.mtim.util.Keys;
import firok.mtim.util.Reg;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

@SuppressWarnings("all")
public class Blocks
{
	// 矿石
	@Reg(Keys.oreCinnabar)
	public static final BlockOre oreCinnabar; // 辰砂矿石
	@Reg(Keys.oreInkPowder)
	public static final BlockOre oreInkPowder; // 墨粉矿石

	static
	{
		oreCinnabar=new BlockOre(Items.cinnabar,1,5,2,3,5);
		oreInkPowder =new BlockOre(Items.inkPowder,4,8,2,2,7);
	}

	// 矿块
	@Reg(Keys.blockCinnabar)
	public static final Block blockCinnabar; // 辰砂块
	@Reg(Keys.blockInkPowder)
	public static final Block blockInkPowder; // 墨粉块
	@Reg(Keys.blockStellar)
	public static final Block blockStellar; // 恒星金属块
	static
	{
		blockCinnabar=new Block(Material.IRON);
		blockInkPowder =new Block(Material.IRON);
		blockStellar=new Block(Material.IRON);
	}
}
