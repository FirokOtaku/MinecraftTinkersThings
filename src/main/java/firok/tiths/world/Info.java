package firok.tiths.world;

import com.google.gson.JsonObject;
import firok.tiths.common.Keys;
import firok.tiths.util.Predicates;
import net.minecraft.block.state.IBlockState;

import java.util.Objects;
import java.util.function.Predicate;

import static firok.tiths.util.InnerActions.*;

public class Info
{
	/* ---- 通用配置 ---- */
	/**
	 * 是否启用
	 */
	private Boolean enable;

	/**
	 * 维度策略
	 */
	private Strategy strategyDim;
	/**
	 * 维度白名单
	 */
	private int[] whitelistDims;
	/**
	 * 维度黑名单
	 */
	private int[] blacklistDims;

	/**
	 * 生物群系策略
	 */
	private Strategy strategyBiome;
	/**
	 * 生物群系白名单
	 */
	private String[] whitelistBiomes;
	/**
	 * 生物群系黑名单
	 */
	private String[] blacklistBiomes;

	/* ---- 矿物配置 ---- */

	/**
	 * 方块替换选择器
	 */
	private Predicate<IBlockState> selector;

	/**
	 * 矿物方块state
	 */
	private IBlockState state;

	/**
	 * 最小生成高度
	 */
	private Integer minY;
	/**
	 * 最大生成高度
	 */
	private Integer maxY;

	/**
	 * 尝试生成次数
	 */
	private Integer time;
	/**
	 * 尝试成功率
	 */
	private Float rate;
	/**
	 * 最大生成数量
	 */
	private Integer size;

	public static boolean enable(Info info1,Info info2,boolean defaultValue)
	{
		return Objects.nonNull(info2) && Objects.nonNull(info2.enable) ?
				info2.enable :
				Objects.nonNull(info1) && Objects.nonNull(info1.enable) ?
						info1.enable :
						defaultValue ;
	}

	public static Strategy strategyDim(Info info1,Info info2,Strategy defaultValue)
	{
		return Objects.nonNull(info2) && Objects.nonNull(info2.strategyDim) ?
				info2.strategyDim :
				Objects.nonNull(info1) && Objects.nonNull(info1.strategyDim) ?
						info1.strategyDim :
						defaultValue;
	}
	public static int[] whitelistDim(Info info1,Info info2,int[] defaultValue)
	{
		return Objects.nonNull(info2) && Objects.nonNull(info2.whitelistDims) ?
				info2.whitelistDims :
				Objects.nonNull(info1) && Objects.nonNull(info1.whitelistDims) ?
						info1.whitelistDims :
						defaultValue;
	}
	public static int[] blacklistDim(Info info1,Info info2,int[] defaultValue)
	{
		return Objects.nonNull(info2) && Objects.nonNull(info2.blacklistDims) ?
				info2.blacklistDims :
				Objects.nonNull(info1) && Objects.nonNull(info1.blacklistDims) ?
						info1.blacklistDims :
						defaultValue;
	}
	public static Strategy strategyBiome(Info info1,Info info2,Strategy defaultValue)
	{
		return Objects.nonNull(info2) && Objects.nonNull(info2.strategyBiome) ?
				info2.strategyBiome :
				Objects.nonNull(info1) && Objects.nonNull(info1.strategyBiome) ?
						info1.strategyBiome :
						defaultValue;
	}
	public static String[] whitelistBiomes(Info info1, Info info2,String[] defaultValue)
	{
		return Objects.nonNull(info2) && Objects.nonNull(info2.whitelistBiomes) ?
				info2.whitelistBiomes :
				Objects.nonNull(info1) && Objects.nonNull(info1.whitelistBiomes) ?
						info1.whitelistBiomes :
						defaultValue;
	}
	public static String[] blacklistBiomes(Info info1, Info info2,String[] defaultValue)
	{
		return Objects.nonNull(info2) && Objects.nonNull(info2.blacklistBiomes) ?
				info2.blacklistBiomes :
				Objects.nonNull(info1) && Objects.nonNull(info1.blacklistBiomes) ?
						info1.blacklistBiomes :
						defaultValue;
	}

	public static Predicate<IBlockState> selector(Info info1,Info info2,Predicate<IBlockState> defaultValue)
	{
		return Objects.nonNull(info2) && Objects.nonNull(info2.selector) ?
				info2.selector :
				Objects.nonNull(info1) && Objects.nonNull(info1.selector) ?
						info1.selector :
						defaultValue;
	}

	public static IBlockState state(Info info1,Info info2,IBlockState defaultValue)
	{
		return Objects.nonNull(info2) && Objects.nonNull(info2.state) ?
				info2.state :
				Objects.nonNull(info1) && Objects.nonNull(info1.state) ?
						info1.state :
						defaultValue;
	}

	public static int minY(Info info1,Info info2,int defaultValue)
	{
		return Objects.nonNull(info2) && Objects.nonNull(info2.minY) ?
				info2.minY :
				Objects.nonNull(info1) && Objects.nonNull(info1.minY) ?
						info1.minY :
						defaultValue;
	}
	public static int maxY(Info info1,Info info2,int defaultValue)
	{
		return Objects.nonNull(info2) && Objects.nonNull(info2.maxY) ?
				info2.maxY :
				Objects.nonNull(info1) && Objects.nonNull(info1.maxY) ?
						info1.maxY :
						defaultValue;
	}
	public static int time(Info info1,Info info2,int defaultValue)
	{
		return Objects.nonNull(info2) && Objects.nonNull(info2.time) ?
				info2.time :
				Objects.nonNull(info1) && Objects.nonNull(info1.time) ?
						info1.time :
						defaultValue;
	}
	public static float rate(Info info1,Info info2,float defaultValue)
	{
		return Objects.nonNull(info2) && Objects.nonNull(info2.rate) ?
				info2.rate :
				Objects.nonNull(info1) && Objects.nonNull(info1.rate) ?
						info1.rate :
						defaultValue;
	}
	public static int size(Info info1,Info info2,int defaultValue)
	{
		return Objects.nonNull(info2) && Objects.nonNull(info2.size) ?
				info2.size :
				Objects.nonNull(info1) && Objects.nonNull(info1.size) ?
						info1.size :
						defaultValue;
	}

	public boolean canReplace(IBlockState state)
	{
		return selector != null && selector.test(state);
	}
	public boolean canGenAtDim(int targetDimId)
	{
		if(enable==null||!enable) return false;
		if(this.strategyDim==null) return true;
		switch (this.strategyDim)
		{
			case NONE: return false;
			case ALWAYS: return true;
			case ONLY_WHITELIST:
			{
				if(this.whitelistDims!=null)
				{
					for(int temp:this.whitelistDims) if(temp==targetDimId) return true;
				}
				return false;
			}
			case NONE_BLACKLIST: default:
			{
				if(this.blacklistDims!=null)
				{
					for(int temp:this.blacklistDims) if(temp==targetDimId) return false;
				}
				return true;
			}
		}
	}
	public boolean canGenAtBiome(String targetBiomeName)
	{
		if(enable==null||!enable) return false;
		if(this.strategyBiome==null) return true;
		switch (this.strategyBiome)
		{
			case NONE: return false;
			case ALWAYS: return true;
			case ONLY_WHITELIST:
			{
				if(this.whitelistBiomes!=null)
				{
					for(String temp:this.whitelistBiomes) if(temp.equals(targetBiomeName)) return true;
				}
				return false;
			}
			case NONE_BLACKLIST: default:
			{
				if(this.blacklistBiomes!=null)
				{
					for(String temp:this.blacklistBiomes) if(temp.equals(targetBiomeName)) return false;
				}
				return true;
			}
		}
	}

	/**
	 * 构造一份生成信息
	 */
	public static Info build(
			IBlockState state,
			Strategy strategyDim,
			int[] whitelistDims,
			int[] blacklistDims,
			Strategy strategyBiome,
			String[] whitelistBiome,
			String[] blacklistBiome,
			Predicate<IBlockState> selector,
			int minY,int maxY,
			int time,float rate,int size)
	{
		Info ret=new Info();

		ret.state=state;
		ret.enable=true;
		ret.strategyDim=strategyDim;
		ret.whitelistDims=whitelistDims;
		ret.blacklistDims=blacklistDims;
		ret.strategyBiome=strategyBiome;
		ret.whitelistBiomes =Keys.getBiomes(whitelistBiome);
		ret.blacklistBiomes =Keys.getBiomes(blacklistBiome);
		ret.selector=selector;
		ret.minY=minY;
		ret.maxY=maxY;
		ret.time=time;
		ret.rate=rate;
		ret.size=size;

		return ret;
	}

	/**
	 * 合并两份生成信息
	 */
	public static Info build(Info info1,Info info2)
	{
		info1.state=Info.state(info1,info2,info1.state);
		info1.enable=Info.enable(info1,info2,info1.enable);
		info1.strategyDim=Info.strategyDim(info1,info2,info1.strategyDim);
		info1.whitelistDims=Info.whitelistDim(info1,info2,info1.whitelistDims);
		info1.blacklistDims=Info.blacklistDim(info1,info2,info1.blacklistDims);
		info1.strategyBiome=Info.strategyBiome(info1,info2,info1.strategyBiome);
		info1.whitelistBiomes=Info.whitelistBiomes(info1,info2,info1.whitelistBiomes);
		info1.blacklistBiomes=Info.blacklistBiomes(info1,info2,info1.blacklistBiomes);
		info1.selector=Info.selector(info1,info2,info1.selector);
		info1.minY=Info.minY(info1,info2,info1.minY);
		info1.maxY=Info.maxY(info1,info2,info1.maxY);
		info1.time=Info.time(info1,info2,info1.time);
		info1.rate=Info.rate(info1,info2,info1.rate);
		info1.size=Info.size(info1,info2,info1.size);

		return info1;
	}

	/**
	 * 构造一份生成信息
	 */
	public static Info build(JsonObject json)
	{
		Info ret=new Info();

		getBool(json,"enable",v->ret.enable=v);
		getStr(json,"strategy_dim",v->ret.strategyDim=Strategy.getStrategy(v,null));
		getIntegers(json,"whitelist_dims",v->ret.whitelistDims=Objects.nonNull(v)?arr(v):null);
		getIntegers(json,"blacklist_dims",v->ret.blacklistDims=Objects.nonNull(v)?arr(v):null);
		getStr(json,"strategy_biome",v->ret.strategyBiome=Strategy.getStrategy(v,null));
		getStrs(json,"whitelist_biomes",v->ret.whitelistBiomes = Keys.getBiomes(v));
		getStrs(json,"blacklist_biomes",v->ret.blacklistBiomes = Keys.getBiomes(v));
		getStr(json,"selector",v->ret.selector=Predicates.getPredicateIBlockState(v,null));
		getInteger(json,"min_y",v->ret.minY=v);
		getInteger(json,"max_y",v->ret.maxY=v);
		getInteger(json,"time",v->ret.time=v);
		getFloat(json,"rate",v->ret.rate=v);
		getInteger(json,"size",v->ret.size=v);

		return ret;
	}

	public boolean complete()
	{
		return Objects.nonNull(enable) &&
		Objects.nonNull(strategyDim) &&
		Objects.nonNull(whitelistDims) &&
		Objects.nonNull(blacklistDims) &&
		Objects.nonNull(strategyBiome) &&
		Objects.nonNull(whitelistBiomes) &&
		Objects.nonNull(blacklistBiomes) &&
		Objects.nonNull(selector) &&
		Objects.nonNull(state) &&
		Objects.nonNull(minY) &&
		Objects.nonNull(maxY) &&
		Objects.nonNull(time) &&
		Objects.nonNull(rate) &&
		Objects.nonNull(size);
	}
}
