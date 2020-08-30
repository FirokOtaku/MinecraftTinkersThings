package firok.tiths.world;

import net.minecraft.block.state.IBlockState;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 世界生成器工厂 根据用户输入的配置自动创建生成器
 * @since 0.3.19.0 第三次世界生成模块修改
 */
public final class WorldGeneratorFactory
{
	/**
	 * 根据用户输入的字符串创建世界生成器
	 */
	public static AbstractChunkGen create(String rawConfig, IBlockState... states) throws RuntimeException
	{
		Map<String,String> rawConfigMap=readRawConfig(rawConfig);
		String type=rawConfigMap.getOrDefault("type","null");
		AbstractChunkGen ret;
		switch (type)
		{
			case "common": // 普通矿物
				ret=new WorldGenMinable();
				break;

			case "bedrock": // 基岩层矿物
				ret=new WorldGenMinableBedrock();
				break;

			case "lava_ball": // 岩浆球
				ret=new WorldGenLavaBall();
				break;

			case "seabed": // 海床
				ret=new WorldGenSeabed();
				break;

			case "tree_root": // 树根
				ret=new WorldGenTreeRoot();
				break;

			case "meteo": // 陨石
				throw new IllegalArgumentException("Unimplemented generator type: "+type);

			case "cloud": // 云层
				ret=new WorldGenCloud();
				break;

			default: throw new IllegalArgumentException("Unknown world generator type: "+type);
		}
		ret.init(rawConfigMap,states);

		return ret;
	}

	private static Map<String,String> readRawConfig(String rawConfig)
	{
		Map<String,String> ret=new HashMap<>();

		String[] kvps=rawConfig.trim().split(";");
//		System.out.println(Arrays.toString(kvps));
		for(String kvp:kvps)
		{
			if(kvp.trim().isEmpty()) continue;

			String[] words=kvp.split("=");
//			System.out.println("len:"+words.length);
			if(words.length!=2) throw new IllegalArgumentException("Invalid key-value-pair: "+kvp);

			String k=words[0].trim(), v=words[1].trim();

			ret.put(k,v);
		}

		return ret;
	}
}
