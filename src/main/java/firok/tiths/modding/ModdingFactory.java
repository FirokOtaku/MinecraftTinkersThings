package firok.tiths.modding;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import static firok.tiths.TinkersThings.log;
import static firok.tiths.util.Values.getStrs;

/**
 * @author gyy
 * @date 2020/04/11 08:50
 */
public class ModdingFactory
{
	public static ModdingInfo create(String name,JsonObject json)
	{
		ModdingInfo ret=null;
		TRY:try
		{
			if(!json.has("materials") || !json.has("parts"))
			{
				log("trying to create a modding info without material info or part info");
				break TRY;
			}

			String[] materials=getStrs(json,"materials");
			String[] parts=getStrs(json,"parts");

			if(!json.has("functions") || !json.get("functions").isJsonArray())
			{
				log("trying to create a modding info without functions");
				break TRY;
			}
			JsonArray array=json.get("functions").getAsJsonArray();
			for(int i=0;i<array.size();i++)
			{
				JsonObject objFunction=array.get(i).getAsJsonObject();
				ModdingFunction function=FunctionFactory.create(objFunction);
			}

			ToolInfo toolinfo=new ToolInfo(materials,parts);


			ModdingInfo modding=new ModdingInfo(name,toolinfo,null);
		}
		catch (Exception e)
		{
			;
		}
		return null;
	}
}
