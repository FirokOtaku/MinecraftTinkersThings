package firok.tiths.common;

import firok.tiths.TinkersThings;
import firok.tiths.util.InnerActions;
import gnu.trove.map.hash.THashMap;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.traits.ITrait;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

/**
 * 开发用自检类
 */
public class SelfChecks
{
	public static void checkAll()
	{
//		if(TinkersThings.enableConarm()) checkTraits();
	}

	public static void checkTraits()
	{
		try
		{
			Collection<Material> materials=TinkerRegistry.getAllMaterials();
			Collection<ITrait> traitsUsed=new HashSet<>();
			for(Material material:materials)
			{
				traitsUsed.addAll(material.getAllTraits());
			}

			Map<String, ITrait> traits = (Map) InnerActions.get(TinkerRegistry.class,"traits",null);

			System.out.println("\n\n自检开始\n\n");
			for(ITrait trait: traits.values())
			{
				boolean used = traitsUsed.contains(trait);
				System.out.printf("%s = %s\n",trait.getLocalizedName(),used);
			}
			System.out.println("\n\n自检结束\n\n");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
