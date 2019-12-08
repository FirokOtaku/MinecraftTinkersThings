package firok.tiths.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class BlockCompressed extends Block
{
	public BlockCompressed()
	{
		this(Material.IRON);
	}
	public BlockCompressed(Material material)
	{
		this(material,material.getMaterialMapColor());
	}
	public BlockCompressed(Material material, MapColor color)
	{
		super(material, color);
	}
}
