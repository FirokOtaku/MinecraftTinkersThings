package firok.tiths.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fluids.Fluid;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.tools.TinkerMaterials;

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

//	public $Material addCraftableIngot(Item item)
//	{
//		return this.addCraftableItem(item,1,VALUE_Ingot);
//	}
//	public $Material addCraftableIngot(Block block)
//	{
//		return this.addCraftableItem(block,1);
//	}
//	public $Material addCraftableItem(Item item,int amountNeeded,int amountMatched)
//	{
//		this.addItem(item, amountNeeded, amountMatched);
//		this.setIconItem(item);
//		return this;
//	}
//	public $Material addCraftableItem(Block block,int ingotMatched)
//	{
//		this.addItem(block, ingotMatched * Material.VALUE_Ingot);
//		this.setIconItem(block);
//		return this;
//	}

//	private boolean hasSetIconItem=false;
//	public $Material setIconItem(String representativeOre)
//	{
//		this.addItem(representativeOre);
//		super.setRepresentativeItem(representativeOre);
//		return this;
//	}
//	public $Material setIconItem(Item item)
//	{
//		if(hasSetIconItem) return this;
//		hasSetIconItem=true;
//
////		this.addItem(item,1,Material.VALUE_Ingot);
//		super.setRepresentativeItem(item);
//		return this;
//	}
//	public $Material setIconItem(Block block)
//	{
//		if(hasSetIconItem) return this;
//		hasSetIconItem=true;
//
////		this.addItem(block,Material.VALUE_Ingot);
//
//		super.setRepresentativeItem(block);
//		return this;
//	}

//	@Override
//	public void setRepresentativeItem(Item item)
//	{
//		this.setRepresentativeItem(item);
//	}
}
