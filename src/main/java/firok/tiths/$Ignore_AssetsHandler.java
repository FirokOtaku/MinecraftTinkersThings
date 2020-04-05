package firok.tiths;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static firok.tiths.$Ignore_AssetsHandler.Type.*;
import static org.apache.commons.io.FileUtils.write;

public class $Ignore_AssetsHandler
{
	enum Type
	{
		block,
		item,
	}

	static File path=new File("./");
//		System.out.println(path.getAbsolutePath()); // D:\OneDrive\MinecraftTinkersThings\.

	static File pathSource=new File("./temp/"); // 源文件


	/**
	 *
	 * @param source 硝石
	 * @param target nitre
	 * @param type item
	 * @throws Exception
	 */
	public static void $(String source,String target,Type type) throws Exception
	{
		System.out.print(String.format("%s <- -> %s :: %s ",source,target,type));
		String pathFileSource="./temp/"+source+".png";
		String pathFileTarget;

		switch (type)
		{
			case block:
			{
				pathFileTarget="./src/main/resources/assets/tiths/textures/blocks/"+target+".png";
				break;
			}
			case item:
			{
				pathFileTarget="./src/main/resources/assets/tiths/textures/items/"+target+".png";
				break;
			}
			default:
				throw new RuntimeException("类型不支持");
		}

		File fileSourcePng=new File(pathFileSource);
		File fileTargetPng=new File(pathFileTarget);

		FileUtils.copyFile(fileSourcePng,fileTargetPng); // 先复制材质文件

		File of;
		String model;

		// 创建json
		switch (type)
		{
			case block:
			{
				// 方块-物品模型
				of=new File("./src/main/resources/assets/tiths/models/item/"+target+".json");
				model=String.format("{\r\n  \"parent\": \"tiths:block/%s\"\r\n}",target);
				write(of,model,"utf-8");

				// 方块模型
				of=new File("./src/main/resources/assets/tiths/models/block/"+target+".json");
				model=String.format("{\r\n  \"parent\": \"block/cube\",\r\n  \"textures\": {\r\n    \"particle\": \"tiths:blocks/%s\",\r\n    \"down\": \"tiths:blocks/%s\",\r\n    \"up\": \"tiths:blocks/%s\",\r\n    \"north\": \"tiths:blocks/%s\",\r\n    \"south\": \"tiths:blocks/%s\",\r\n    \"west\": \"tiths:blocks/%s\",\r\n    \"east\": \"tiths:blocks/%s\"\r\n  }\r\n}",
						target,target,target,target,target,target,target);
				write(of,model,"utf-8");

				// 方块blockstate
				of=new File("./src/main/resources/assets/tiths/blockstates/"+target+".json");
				model=String.format("{\r\n  \"variants\": {\r\n    \"normal\": {\r\n      \"model\": \"tiths:%s\"\r\n    }\r\n  }\r\n}",target);
				write(of,model,"utf-8");

				break;
			}
			case item:
			{
				// 创建物品自己的模型json
				of=new File("./src/main/resources/assets/tiths/models/item/"+target+".json");
				model=String.format("{\r\n  \"parent\": \"item/generated\",\r\n  \"textures\": {\r\n    \"layer0\": \"tiths:items/%s\"\r\n  }\r\n}",target);

				write(of,model,"utf-8");

				break;
			}
			default:
				break;
		}

		System.out.println("..done");
	}
	public static void main(String...args) throws Exception
	{

		System.out.println("开始");
//		$("葡萄石块","block_prehnite", block);
//		$("坦桑石块","block_tanzanite", block);
//		$("托帕石块","block_topaz", block);
//		$("堇青石块","block_cordierite", block);
//		$("欧珀块","block_opal", block);
//		$("欧珀矿","ore_opal", block);
//		$("欧珀","opal",item);
//		$("谷锭","ingot_grain", item);
//		$("东陵石块","ore_aventurine",block);
//		$("东陵石矿","block_aventurine",block);
//		$("东陵石","aventurine",item);
//		$("硫砷银","proustite",item);
//		$("硫砷银矿","ore_proustite",block);
//		$("岩浆水晶矿","ore_lava_crystal",block);
//		$("振晶块","block_vibrating_crystal",block);
//		$("岩浆水晶块","block_lava_crystal",block);
//		$("云","block_cloud",block);
//		$("硫砷银粒","nugget_proustite",item);
//		$("硫砷银块","block_proustite",block);
//		$("神谕钢粒","nugget_oraclium",item);
//		$("神谕钢锭","ingot_oraclium",item);
//		$("神谕钢块","block_oraclium",block);
//
//		$("气钢粒","nugget_steamium",item);
//		$("气钢锭","ingot_steamium",item);
//		$("气钢块","block_steamium",block);
//		$("气钢矿","ore_steamium",block);
		System.out.println("完成");
	}
}
