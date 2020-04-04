package firok.tiths.common;

/**
 * 开发用自检类
 */
public class SelfChecks
{
	public static void checkAll()
	{
		if(Configs.General.enable_conarm) checkTraits();
	}

	public static void checkTraits()
	{
//		FieldStream.of(Traits.class,null, ITrait.class)
//				.forEach((field,anno,trait)->{
//					;
//				});

//		try
//		{
//			Map<String, ITrait> map=(Map) get(TinkerRegistry.class,"traits",null);
//			Object[] objs=map.values().stream().filter(trait->trait.getIdentifier().endsWith("_armor")).toArray();
//
//			ITrait trait=map.get(nameTraitCarbonizing+suffArmor);
//
//			int a=123;
//			int b=123;
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//		}
	}
}
