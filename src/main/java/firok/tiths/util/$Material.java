package firok.tiths.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fluids.Fluid;
import slimeknights.tconstruct.library.materials.Material;

public class $Material extends Material
{
	public $Material(String identifier, TextFormatting textColor)
	{
		super(identifier, textColor);
	}

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
}
