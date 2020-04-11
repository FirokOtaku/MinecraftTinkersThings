package firok.tiths.modding;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import scala.actors.threadpool.Arrays;
import slimeknights.tconstruct.library.tinkering.IMaterialItem;

import java.util.ArrayList;
import java.util.List;

/**
 * 魔改工具匹配信息类
 */
public class ToolInfo implements Comparable<ToolInfo>
{

	public ToolInfo(String[] materials,String[] parts)
	{
		this(Arrays.asList(materials==null?new String[]{}:materials),Arrays.asList(parts==null?new String[]{}:parts));
	}
	public ToolInfo(List<String> materials, List<String> parts)
	{
		init(materials,parts);
	}
	public ToolInfo(List<ItemStack> toolparts)
	{
		List<String> materials=new ArrayList<>();
		List<String> parts=new ArrayList<>();
		if(toolparts==null) return;

		for(ItemStack toolpart:toolparts)
		{
			if(toolpart==null || toolpart.isEmpty()) continue;

			Item item=toolpart.getItem();
			if(!(item instanceof IMaterialItem)) continue;
			IMaterialItem imi=(IMaterialItem)item;
			String partType=toolpart.getUnlocalizedName();
			String partMaterial=imi.getMaterialID(toolpart);

			materials.add(partMaterial);
			parts.add(partType);
		}

		init(materials,parts);
	}
	ToolInfo(List<KVP> kvps,boolean flag)
	{
		this.kvps=new ArrayList<>(kvps);
	}

	private void init(List<String> materials, List<String> parts)
	{
		this.kvps=new ArrayList<>();
		if(materials.size()!=parts.size()) throw new RuntimeException("count of materials is not equal to count of parts");

		int size=materials.size();

		for(int i=0;i<size;i++)
		{
			KVP kvp=new KVP(materials.get(i),parts.get(i));
			kvps.add(kvp);
		}
	}

	private List<KVP> kvps;

	@Override
	public int compareTo(ToolInfo out)
	{
		if(kvps.size()!=out.kvps.size()) return -1;
		final int sizeThis=this.kvps.size();
		if(sizeThis==0) return 0;

		List<KVP> kvpsOut=new ArrayList<>(out.kvps); // 外部info的键值对列表

		FOR_THIS: for (KVP kvpTempThis : this.kvps) // 遍历内部info键值对列表
		{
			final int sizeOut = kvpsOut.size(); // 外部info键值对当前size
			FOR_OUT: for (int j = 0; j < sizeOut; j++) // 遍历外部键值对info列表
			{
				KVP kvpTempOut = kvpsOut.get(j); // 第j个外部键值对
				if (kvpTempThis.eq(kvpTempOut)) // 判断相等
				{
					kvpsOut.remove(j); // 移除外部info对应键值对
					continue FOR_THIS;
				}
			}
			return 1; // 如果没找到相应的外部info键值对 则返回不相等
		}
		return 0; // 相等
	}

	private static class KVP
	{
		final String material,part;

		public KVP(String material,String part)
		{
			this.material=material;
			this.part=part;
		}

		public boolean eq(KVP kvp)
		{
			return this.material.equals(kvp.material) && this.part.equals(kvp.part);
		}
	}

	public ToolInfo copy()
	{
		return new ToolInfo(kvps,false);
	}

	@Override
	public boolean equals(Object obj)
	{
		if(obj==this) return true;
		if(!(obj instanceof ToolInfo)) return false;
		return ((ToolInfo) obj).compareTo(this) == 0;
	}
}
