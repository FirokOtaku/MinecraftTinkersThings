package firok.tiths.modding;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.tinkering.IMaterialItem;

import java.util.ArrayList;
import java.util.List;

/**
 * 魔改工具匹配信息类
 */
public class ToolInfo implements Comparable<ToolInfo>
{
	private List<String> materials;
	private List<String> parts;

	public ToolInfo(List<String> materials, List<String> parts)
	{
		this.materials=new ArrayList<>(materials);
		this.parts=new ArrayList<>(parts);
	}
	public ToolInfo(List<ItemStack> toolparts)
	{
		this.materials=new ArrayList<>();
		this.parts=new ArrayList<>();
		for(ItemStack toolpart:toolparts)
		{
			if(toolpart==null || toolpart.isEmpty()) continue;

			Item item=toolpart.getItem();
			if(!(item instanceof IMaterialItem)) continue;
			IMaterialItem imi=(IMaterialItem)item;
			String partType=toolpart.getUnlocalizedName();
			String partMaterial=imi.getMaterialID(toolpart);

			this.materials.add(partMaterial);
			this.parts.add(partType);
		}
	}

	@Override
	public int compareTo(ToolInfo info)
	{
		if(materials.size()!=info.materials.size()) return -1;

		List<String> tempMaterialsInfo = new ArrayList<>(info.materials);
		for(String materialThis:this.materials)
		{
			tempMaterialsInfo.remove(materialThis);
		}
		if(tempMaterialsInfo.size()>0) return 1;

		List<String> tempPartsInfo = new ArrayList<>(info.parts);
		for(String partThis:this.parts)
		{
			tempPartsInfo.remove(partThis);
		}
		if(tempPartsInfo.size()>0) return 1;

		return 0;
	}

	public ToolInfo copy()
	{
		return new ToolInfo(this.materials,this.parts);
	}

	@Override
	public boolean equals(Object obj)
	{
		if(obj==this) return true;
		if(!(obj instanceof ToolInfo)) return false;
		return ((ToolInfo) obj).compareTo(this) == 0;
	}
}
