package firok.tiths.common;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import firok.tiths.TinkersThings;
import firok.tiths.util.conf.MaterialInfo;

import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import static firok.tiths.TinkersThings.log;
import static firok.tiths.util.conf.Values.*;

/**
 * 这个类用来处理json格式的材料属性文件
 */
public class ConfigJson
{
	private static boolean hasInit=false;
	private static boolean hasJson=false;
	public static boolean hasInit()
	{
		return hasInit;
	}
	public static final String nameConfigJson=TinkersThings.MOD_ID + "_materials.json";

	private static Map<String,MaterialInfo> mats=new HashMap<>();
	public static MaterialInfo getMat(String name)
	{
		return mats.get(name);
	}
	public static void getMatOr(String name, Consumer<MaterialInfo> callbackSuccess, Runnable callbackFail)
	{
		MaterialInfo mat=getMat(name);
		if(mat!=null) callbackSuccess.accept(mat);
		else callbackFail.run();
	}
	public static void getMatOr(String name, Consumer<MaterialInfo> callbackSuccess, Consumer<String> callbackFail)
	{
		MaterialInfo mat=getMat(name);
		if(mat!=null) callbackSuccess.accept(mat);
		else callbackFail.accept(name);
	}

	public static void init(File dirConfigs)
	{
		mats.clear();

		File[] _fileConfigJson=dirConfigs.listFiles((dir,name)->name.equals(nameConfigJson));
		if(_fileConfigJson==null || _fileConfigJson.length<1) return;

		log("now let's read some json");

		File fileConfigJson=_fileConfigJson[0];

		try(FileReader reader=new FileReader(fileConfigJson))
		{
			JsonElement elementConfig=new JsonParser().parse(reader);
			JsonObject objectConfig=elementConfig.getAsJsonObject();
			objectConfig.entrySet().forEach(entry->{
				String nameMaterial=entry.getKey();
				MaterialInfo mat=new MaterialInfo();
				mat.name=nameMaterial;
				JsonObject obj=entry.getValue().getAsJsonObject();

				log("we found customized material:"+nameMaterial);

				getStrs(obj,"traits_tool",v->mat.tool_traits=v);
				getStrs(obj,"traits_armor",v->mat.armor_traits=v);

				getObj(obj,"head",attrHead->{
					getInteger(attrHead,"durability",v->mat.head_durability=v);
					getFloat(attrHead,"mining_speed",v->mat.head_mining_speed=v);
					getFloat(attrHead,"attack",v->mat.head_attack=v);
					getByte(attrHead,"harvest_level",v->mat.head_harvest_level=v);
					getStrs(attrHead,"traits",v->mat.head_tratis=v);
				});
				getObj(obj,"handle",attrHandle->{
					getInteger(attrHandle,"durability",v->mat.handle_durability=v);
					getFloat(attrHandle,"modifier",v->mat.handle_modifier=v);
					getStrs(attrHandle,"traits",v->mat.handle_traits=v);
				});
				getObj(obj,"extra",attrExtra->{
					getInteger(attrExtra,"durability",v->mat.extra_durability=v);
					getStrs(attrExtra,"traits",v->mat.extra_traits=v);
				});
				getObj(obj,"bow",attrBow->{
					getFloat(attrBow,"draw_speed",v->mat.bow_draw_speed=v);
					getFloat(attrBow,"range",v->mat.bow_range=v);
					getFloat(attrBow,"bonus_damage",v->mat.bow_bonus_damage=v);
					getStrs(attrBow,"traits",v->mat.bow_traits=v);
				});
				getObj(obj,"bowstring",attrString->{
					getFloat(attrString,"modifier",v->mat.string_modifier=v);
					getStrs(attrString,"traits",v->mat.string_traits=v);
				});
				getObj(obj,"fletching",attrFletching->{
					getFloat(attrFletching,"accuracy",v->mat.fletching_accuracy=v);
					getFloat(attrFletching,"modifier",v->mat.fletching_modifier=v);
					getStrs(attrFletching,"traits",v->mat.fletching_traits=v);
				});
				getObj(obj,"arrowshaft",attrShaft->{
					getFloat(attrShaft,"modifier",v->mat.shaft_modifier=v);
					getInteger(attrShaft,"bonus_ammo",v->mat.shaft_bonus_ammo=v);
					getStrs(attrShaft,"traits",v->mat.shaft_traits=v);
				});

				mats.put(mat.name,mat);
			});

		} catch (Exception e) { log(e); }


		hasInit=true;
		hasJson=true;
	}
}
