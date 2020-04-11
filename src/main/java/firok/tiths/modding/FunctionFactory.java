package firok.tiths.modding;

import com.google.gson.JsonObject;
import firok.tiths.TinkersThings;
import firok.tiths.common.Configs;

import static firok.tiths.util.conf.Values.getStr;
import static firok.tiths.util.conf.Values.getStrs;

public class FunctionFactory
{
	private static void checkParamCount(String[] params,int count)
	{
		if(count<=0) return;
		// count>0
		if(params==null||params.length!=count) throw new RuntimeException(
		String.format("params count incorrect ( %d needed but %d given )",count,params!=null?params.length:0)
		);
	}
	public static ModdingFunction create(JsonObject json)
	{
		ModdingFunction ret=null;
		try
		{
			String type=getStr(json,"type");
			String[] params=getStrs(json,"params");

			SWITCH:switch(type)
			{
				case MFRenaming.TYPE:
				{
					checkParamCount(params,MFRenaming.COUNT);
					ret=new MFRenaming(params);
					break SWITCH;
				}
				default:
				{
					throw new RuntimeException("function type not found: "+type);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			String error="error to create a modding function";
			if(Configs.General.enable_strict_mode)
			{
				throw new RuntimeException(error,e);
			}
			TinkersThings.log(error);
		}

		return ret;
	}
}
