package firok.tiths.common;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import firok.tiths.TinkersThings;
import firok.tiths.modding.ModdingFactory;
import firok.tiths.modding.ModdingInfo;
import firok.tiths.util.conf.MaterialInfo;
import firok.tiths.world.Info;

import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import static firok.tiths.TinkersThings.log;
import static firok.tiths.util.InnerActions.*;

/**
 * 这个类用来处理json格式的材料属性文件
 */
public final class ConfigJson
{
	private ConfigJson() {}

	/**
	 * 设定配置文件夹
	 */
	public static void setConfigDir(File configDir)
	{
		ConfigJson.configDir=configDir;
	}
	private static File configDir=null;
	/**
	 * 读取配置文件夹下的配置文件
	 */
	public static void readJsonConfig(String filename, Consumer<JsonObject> callback)
	{
		File[] _fileConfigs=configDir.listFiles((dir,name)->name.equals(filename));
		if(_fileConfigs==null || _fileConfigs.length<1) return;
		File fileConfig=_fileConfigs[0];
		try(FileReader reader=new FileReader(fileConfig))
		{
			JsonElement elementConfig=new JsonParser().parse(reader);
			JsonObject objectConfig=elementConfig.getAsJsonObject();

			callback.accept(objectConfig);
		}
		catch (Exception e)
		{
			log(e);
		}
	}

	public static final String nameConfigMats=TinkersThings.MOD_ID + "_materials.json";
	public static final String nameConfigOres=TinkersThings.MOD_ID + "_ores.json";
	public static final String nameConfigToolCraftFunctions=TinkersThings.MOD_ID+"_tool_craft_functions.json";


	/* ---- 自定义材料属性相关 ---- */
	private static final Map<String,MaterialInfo> mats=new HashMap<>();
	public static MaterialInfo getMat(String name)
	{
		return mats.get(name);
	}
	public static void readMats()
	{
		readJsonConfig(nameConfigMats,objectConfig-> objectConfig.entrySet().forEach(entry->{
			String nameMaterial=entry.getKey();
			MaterialInfo mat=new MaterialInfo();
			mat.name=nameMaterial;
			JsonObject obj=entry.getValue().getAsJsonObject();

			if(isTrue(mat.disable=getBool(obj,"disable")))
			{
				log("disabled material: "+nameMaterial);
				return;
			}

			log("customized material: "+nameMaterial);

			getStrs(obj,"traits_tool",v->mat.traits_tool =v);
			getStrs(obj,"traits_bow",v->mat.traits_bow =v);
			getStrs(obj,"traits_armor",v->mat.traits_armor =v);

			if(isTrue(mat.disableHead=getBool(obj,"disable_head")))
			{
				log("disabled head stats");
			}
			else
			{
				getObj(obj,"head",attrHead->{
					getInteger(attrHead,"durability",v->mat.head_durability=v);
					getFloat(attrHead,"mining_speed",v->mat.head_mining_speed=v);
					getFloat(attrHead,"attack",v->mat.head_attack=v);
					getByte(attrHead,"harvest_level",v->mat.head_harvest_level=v);
					getStrs(attrHead,"traits",v->mat.head_traits =v);
				});
			}

			if(isTrue(mat.disableHandle=getBool(obj,"disable_handle")))
			{
				log("disabled handle stats");
			}
			else
			{
				getObj(obj,"handle",attrHandle->{
					getInteger(attrHandle,"durability",v->mat.handle_durability=v);
					getFloat(attrHandle,"modifier",v->mat.handle_modifier=v);
					getStrs(attrHandle,"traits",v->mat.handle_traits=v);
				});
			}

			if(isTrue(mat.disableExtra=getBool(obj,"disable_extra")))
			{
				log("disabled extra stats");
			}
			else
			{
				getObj(obj,"extra",attrExtra->{
					getInteger(attrExtra,"durability",v->mat.extra_durability=v);
					getStrs(attrExtra,"traits",v->mat.extra_traits=v);
				});
			}

			if(isTrue(mat.disableBow=getBool(obj,"disable_bow")))
			{
				log("disabled bow stats");
			}
			else
			{
				getObj(obj,"bow",attrBow->{
					getFloat(attrBow,"draw_speed",v->mat.bow_draw_speed=v);
					getFloat(attrBow,"range",v->mat.bow_range=v);
					getFloat(attrBow,"bonus_damage",v->mat.bow_bonus_damage=v);
					getStrs(attrBow,"traits",v->mat.bow_traits=v);
				});
			}

			if(isTrue(mat.disableString=getBool(obj,"disable_string")))
			{
				log("disabled string stats");
			}
			else
			{
				getObj(obj,"bowstring",attrString->{
					getFloat(attrString,"modifier",v->mat.string_modifier=v);
					getStrs(attrString,"traits",v->mat.string_traits=v);
				});
			}

			if(isTrue(mat.disableFletching=getBool(obj,"disable_fletching")))
			{
				log("disabled fletching stats");
			}
			else
			{
				getObj(obj,"fletching",attrFletching->{
					getFloat(attrFletching,"accuracy",v->mat.fletching_accuracy=v);
					getFloat(attrFletching,"modifier",v->mat.fletching_modifier=v);
					getStrs(attrFletching,"traits",v->mat.fletching_traits=v);
				});
			}

			if(isTrue(mat.disableShaft=getBool(obj,"disable_shaft")))
			{
				log("disabled shaft stats");
			}
			else
			{
				getObj(obj,"arrowshaft",attrShaft->{
					getFloat(attrShaft,"modifier",v->mat.shaft_modifier=v);
					getInteger(attrShaft,"bonus_ammo",v->mat.shaft_bonus_ammo=v);
					getStrs(attrShaft,"traits",v->mat.shaft_traits=v);
				});
			}

			if(isTrue(mat.disableCore=getBool(obj,"disable_core")))
			{
				log("disabled core stats");
			}
			else
			{
				getObj(obj,"core",attrCore->{
					getFloat(attrCore,"durability",v->mat.core_durability=v);
					getFloat(attrCore,"dense",v->mat.core_dense=v);
					getStrs(attrCore,"traits",v->mat.core_traits=v);
				});
			}

			if(isTrue(mat.disableTrim=getBool(obj,"disable_trim")))
			{
				log("disabled trim stats");
			}
			else
			{
				getObj(obj,"trim",attrTrim->{
					getFloat(attrTrim,"durability",v->mat.trim_durability=v);
					getStrs(attrTrim,"traits",v->mat.trim_traits=v);
				});
			}

			if(isTrue(mat.disablePlate=getBool(obj,"disable_plate")))
			{
				log("disabled plate stats");
			}
			else
			{
				getObj(obj,"plate",attrPlate->{
					getFloat(attrPlate,"modifier",v->mat.plate_modifier=v);
					getFloat(attrPlate,"durability",v->mat.plate_durability=v);
					getFloat(attrPlate,"toughness",v->mat.plate_toughness=v);
					getStrs(attrPlate,"traits",v->mat.plate_traits=v);
				});
			}

			mats.put(mat.name,mat);
		}));
		if(mats.size()>0) log("customized materials: " + mats.keySet());
	}

	/* ---- 自定义世界生成相关 ---- */
//	private static final Map<String, OreGenInfo> ores=new HashMap<>();
//	public static OreGenInfo getOre(String name)
//	{
//		return ores.get(name);
//	}
//	public static void readOres()
//	{
//		readJsonConfig(nameConfigOres,objectConfig-> objectConfig.entrySet().forEach(entry->{
//			String name=entry.getKey();
//			OreGenInfo info=new OreGenInfo();
//			info.name=name;
//			JsonObject obj=entry.getValue().getAsJsonObject();
//
//			getInteger(obj,"min_y",v->info.minY=v);
//			getInteger(obj,"max_y",v->info.maxY=v);
//			getInteger(obj,"times",v->info.times=v);
//			getFloat(obj,"rate",v->info.timeRate=v);
//			getInteger(obj,"size",v->info.size=v);
//			getIntegers(obj,"dims",v->info.dims=v);
//
//			getFloat(obj,"meteo_rate_chunk",v->info.meteoRateChunk=v);
//			getFloat(obj,"meteo_rate_ore",v->info.meteoRateOre=v);
//			getIntegers(obj,"meteo_dims",v->info.meteoDims=v);
//
//			ores.put(name,info);
//		}));
//		if(ores.size()>0) log("customized ore gens: "+ ores.keySet());
//	}
	private static final Map<String, Info> ores=new HashMap<>();
	public static Info getOre(String name)
	{
		return ores.get(name);
	}
	public static void readOres()
	{
		ores.clear();
		readJsonConfig(nameConfigOres,objectConfig-> objectConfig.entrySet().forEach(entry->{
			String name=entry.getKey();
			JsonObject obj=entry.getValue().getAsJsonObject();
			Info info=Info.build(obj);

			ores.put(name,info);
		}));
	}

	/* ---- 自定义工具处理方法 ---- */
	public static void readMFs()
	{
		ModdingInfos.clearModdings();
		readJsonConfig(nameConfigToolCraftFunctions,objectConfig-> objectConfig.entrySet().forEach(entry->{
			String name=entry.getKey();
			JsonObject obj=entry.getValue().getAsJsonObject();

			ModdingInfo modding=ModdingFactory.create(name,obj);

			if(modding==null) return;

			ModdingInfos.registerModding(modding);
		}));
	}

//	public static void outputAll()
//	{
//		StringBuilder str=new StringBuilder();
//		for(Material material:TinkerRegistry.getAllMaterials())
//		{
//			str.append('[').append(material.getIdentifier()).append("]\n");
//			Collection<IMaterialStats> stats=material.getAllStats();
//			for(IMaterialStats ims:stats)
//			{
//				str.append(ims.getLocalizedInfo()).append('\n');
//
//				List<ITrait> traits=material.getAllTraitsForStats(ims.getIdentifier());
//				for(ITrait trait:traits)
//				{
//					str.append('{').append(trait.getIdentifier()).append('}');
//				}
//			}
//		}
//		log(str.toString());
//	}
}
