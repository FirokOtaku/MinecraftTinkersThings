package firok.tiths.world;

import net.minecraft.block.Block;
import net.minecraftforge.common.util.Lazy;

public class OreType
{
	public Lazy<Block> block;
	public int maxAmount;
	/**
	 * used for construction of TopSolidRangeConfig
	 * */
	public int bottomOffset, topOffset, topMaximum;

	public OreType(Lazy<Block> block, int maxAmount, int bottomOffset, int topOffset, int topMaximum)
	{
		this.block = block;
		this.maxAmount = maxAmount;
		this.bottomOffset = bottomOffset;
		this.topOffset = topOffset;
		this.topMaximum = topMaximum;
	}
}
