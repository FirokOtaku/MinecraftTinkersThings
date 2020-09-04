package firok.tiths.world;

import firok.tiths.common.Configs;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.Biome;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static firok.tiths.util.Predicates.canTrigger;

/**
 * 世界结构生成器<br>
 * 一般来说, 需要重写的只有这几个方法<br>
 * {@link #genRealPos(World, int, int, Random, int)} 生成真实坐标用于结构生成<br>
 * {@link #genAtRealPos(World, int, int, int, int, int, Random)} 根据真实坐标生成结构<br>
 * {@link #init(java.util.Map,IBlockState[])} 导入玩家在配置文件输入的数据<br>
 * @since 0.3.19.0 第三次世界生成模块修改
 */
public abstract class AbstractChunkGen
{
	/**
	 * 本生成器是否启用
	 */
	protected boolean enable;

	/**
	 * 本生成器主要生成的IBS
	 */
	protected IBlockState[] states;

	/**
	 * 每区块尝试次数
	 */
	protected int timeChunk;
	/**
	 * 每次区块级别尝试成功率
	 */
	protected float rateChunk;

	/**
	 * 每次尝试成功率
	 */
	protected float rateSingle;

	/**
	 * 生成器策略
	 */
	protected Strategy strategyDim,strategyBiome;
	protected int[] dims;
	protected String[] biomes;

	/**
	 * 根据用户输入的配置内容导入数据
	 */
	public void init(Map<String,String> raw,IBlockState...states)
	{
		this.states=states!=null?states.clone():new IBlockState[0]; // fixme 这里可能需要修改

		boolean flagEnable=false,flagRateChunk=false,flagRateSingle=false,flagTimeChunk=false,
				flagStrategyDim=false,flagStrategyBiome=false,
				flagDims=false,flagBiomes=false;

		for(Map.Entry<String,String> entry:raw.entrySet())
		{
			String k=entry.getKey(), v=entry.getValue();

			switch (k)
			{
				case "enable":
					flagEnable=checkParameter(flagEnable,k);
					this.enable=Boolean.parseBoolean(v);
					break;

				case "rateChunk":
					flagRateChunk=checkParameter(flagRateChunk,k);
					this.rateChunk=parseFloat(k,v,0,1);
					break;

				case "rateSingle":
					flagRateSingle=checkParameter(flagRateSingle,k);
					this.rateSingle=parseFloat(k,v,0,1);
					break;

				case "timeChunk":
					flagTimeChunk=checkParameter(flagTimeChunk,k);
					this.timeChunk=parseInt(k,v);
					break;

				case "dims":
					flagDims=checkParameter(flagDims,k);
					this.dims=parseIntArray(k,v);
					break;

				case "biomes":
					flagBiomes=checkParameter(flagBiomes,k);
					this.biomes=parseStringArray(k,v);
					break;

				case "strategy_dim":
					flagStrategyDim=checkParameter(flagStrategyDim,k);
					this.strategyDim=parseStrategy(k,v);
					break;

				case "strategy_biome":
					flagStrategyBiome=checkParameter(flagStrategyBiome,k);
					this.strategyBiome=parseStrategy(k,v);
					break;

//				default: errorInvalidKVP(k); break;
			}
		}
		if(!flagEnable) this.enable=true;
		if(!flagRateChunk) this.rateChunk=1;
		if(!flagRateSingle) this.rateSingle=1;
		if(!flagTimeChunk) this.timeChunk=1;
		if(!flagBiomes) this.biomes=new String[0];
		if(!flagDims) this.dims=new int[0];
		if(!flagStrategyDim) this.strategyDim=Strategy.NONE_BLACKLIST;
		if(!flagStrategyBiome) this.strategyBiome=Strategy.NONE_BLACKLIST;

	}
	protected void errorInvalidKVP(String k)
	{
		throw new IllegalArgumentException("Invalid key-value pair: "+k);
	}
	protected void errorMissingKey(String k)
	{
		throw new IllegalArgumentException("Missing parameter: "+k);
	}
	protected void errorLower(String k,int value,int min)
	{
		throw new IllegalArgumentException("The given value is below the minimum allowed: "+value+" ["+min+",) ("+k+")");
	}
	protected void errorGreater(String k,int value,int max)
	{
		throw new IllegalArgumentException("The given value is higher than the maximum allowed: "+value+" (,"+max+"] ("+k+")");
	}
	protected void errorLower(String k,float value,float min)
	{
		throw new IllegalArgumentException("The given value is below the minimum allowed: "+value+" ["+min+",) ("+k+")");
	}
	protected void errorGreater(String k,float value,float max)
	{
		throw new IllegalArgumentException("The given value is higher than the maximum allowed: "+value+" (,"+max+"] ("+k+")");
	}

	/**
	 * 获取此生成器主要生成矿物的{@link IBlockState}
	 */
	public IBlockState getMainState()
	{
		return states!=null && states.length>0? states[0]: null;
	}

	/**
	 * 在指定区块生成结构, 包含了对区块概率\区块次数\单次概率等条件的检查
	 * @param world 世界
	 * @param chunkVertexX 区块顶点坐标
	 * @param chunkVertexZ 区块顶点坐标
	 * @param rand 随机数生成器
	 * @return 本次生成所替换的方块坐标
	 * @see #genRealPos(World, int, int, Random)
	 */
	@Deprecated // 尽量不要重写这个方法
	public List<BlockPos> genAtChunk(World world, int chunkVertexX, int chunkVertexZ, Random rand)
	{
		List<BlockPos> ret=new ArrayList<>();
		if(canTrigger(rand,rateChunk)) for(int i = 0; i< timeChunk; i++)
		{
			if(!canTrigger(rand, rateSingle)) continue;

			BlockPos realPos=genRealPos(world, chunkVertexX, chunkVertexZ, rand);
			ret.addAll(genAtRealPos(world, realPos.getX(), realPos.getY(), realPos.getZ(), chunkVertexX, chunkVertexZ, rand));
		}
		return ret;
	}

	/**
	 * 根据区块坐标和其它信息生成一个随机真实坐标
	 * @param world 世界
	 * @param chunkVertexX 区块顶点坐标
	 * @param chunkVertexZ 区块顶点坐标
	 * @param rand 随机数生成器
	 * @param padding 内边距
	 * @return 真实坐标
	 */
	public BlockPos genRealPos(World world, int chunkVertexX, int chunkVertexZ, Random rand, int padding)
	{
		final int posX=chunkVertexX+padding+rand.nextInt(16-padding*2);
		final int posY=rand.nextInt(world.getHeight());
		final int posZ=chunkVertexZ+padding+rand.nextInt(16-padding*2);
		return new BlockPos(posX,posY,posZ);
	}

	/**
	 * 根据区块坐标和其它信息生成一个随机真实坐标, 使用默认区块内边距4
	 * @see firok.tiths.world.AbstractChunkGen#genRealPos(World, int, int, Random, int)
	 */
	public BlockPos genRealPos(World world, int chunkVertexX, int chunkVertexZ, Random rand)
	{
		return genRealPos(world, chunkVertexX, chunkVertexZ, rand, 4);
	}

	/**
	 * 在指定真实坐标生成结构, 这个方法是最主要的方法<br>
	 * 请注意, 请不要直接读写世界数据, 而是调用{@link #setState}和{@link #getState}, 这两个方法会根据配置文件自动限制跨区块读写
	 * @param world 世界
	 * @param posX 真实坐标
	 * @param posY 真实坐标
	 * @param posZ 真实坐标
	 * @param chunkVertexX 区块顶点X坐标
	 * @param chunkVertexZ 区块顶点Z坐标
	 * @param rand 随机数生成器
	 * @return 本次生成所替换的方块坐标
	 * @see #setState
	 * @see #getState
	 */
	// 尽量只重写这个方法
	public List<BlockPos> genAtRealPos(World world, int posX, int posY, int posZ, int chunkVertexX, int chunkVertexZ, Random rand)
	{
		return new ArrayList<>();
	}

	/**
	 * 检查能否在指定维度生成
	 * @param targetDimId 指定维度id
	 * @param world 世界
	 * @param provider 维度元
	 * @return 是否生成
	 */
	@Deprecated // 尽量不要重写这个方法
	public boolean canGenAtDim(int targetDimId, World world, WorldProvider provider)
	{
		if(!enable) return false;
		if(this.strategyDim==null) return true;
		switch (this.strategyDim)
		{
			case NONE: return false;
			case ALWAYS: return true;
			case ONLY_WHITELIST:
			{
				if(this.dims!=null)
				{
					for(int temp:this.dims) if(temp==targetDimId) return true;
				}
				return false;
			}
			case NONE_BLACKLIST: default:
			{
				if(this.dims!=null)
				{
					for(int temp:this.dims) if(temp==targetDimId) return false;
				}
				return true;
			}
		}
	}

	/**
	 * 检查能否在指定生物群系生成
	 * @param targetBiomeId 指定生物群系名称
	 * @param world 世界
	 * @param biome 生物群系
	 * @return 是否生成
	 */
	@Deprecated // 尽量不要重写这个方法
	public boolean canGenAtBiome(String targetBiomeId, World world, Biome biome)
	{
		if(!enable) return false;
		if(this.strategyBiome==null) return true;
		switch (this.strategyBiome)
		{
			case NONE: return false;
			case ALWAYS: return true;
			case ONLY_WHITELIST:
			{
				if(this.biomes!=null)
				{
					for(String temp:this.biomes) if(temp.equals(targetBiomeId)) return true;
				}
				return false;
			}
			case NONE_BLACKLIST: default:
			{
				if(this.biomes!=null)
				{
					for(String temp:this.biomes) if(temp.equals(targetBiomeId)) return false;
				}
				return true;
			}
		}
	}

	/* ---- 一些数据相关的工具方法 ---- */

	static boolean crossChunk(BlockPos pos,int chunkVX,int chunkVZ)
	{
		return pos.getX()< chunkVX || pos.getX()> chunkVX+15 || pos.getZ()<chunkVZ || pos.getZ()>chunkVZ+15;
	}

	/**
	 * 使用这个方法进行世界数据的读写
	 */
	static IBlockState getState(World world, BlockPos pos, int chunkVX, int chunkVZ)
	{
		return Configs.General.block_cross_chunk_generation && crossChunk(pos, chunkVX, chunkVZ)?
				Blocks.AIR.getDefaultState():
				world.getBlockState(pos);
	}
	/**
	 * 使用这个方法进行世界数据的读写
	 */
	static void setState(World world,BlockPos pos,IBlockState state,int chunkVX,int chunkVZ)
	{
		setState(world, pos, state, 2, chunkVX, chunkVZ);
	}
	static void setState(World world,BlockPos pos,IBlockState state,int flag,int chunkVX,int chunkVZ)
	{
		if (!Configs.General.block_cross_chunk_generation || !crossChunk(pos, chunkVX, chunkVZ))
		{
			world.setBlockState(pos,state,flag);
		}
	}

	static boolean isAirBlock(World world,BlockPos pos,int chunkVX,int chunkVZ)
	{
		IBlockState state=getState(world, pos, chunkVX, chunkVZ);
		return state.getBlock().isAir(state,world,pos);
	}

	/* ---- 一些配置相关的工具方法 ---- */

	/**
	 * 检查某个word是否已经被初始化
	 * @param value 检查标志位
	 * @param key 检查名称
	 */
	protected static boolean checkParameter(boolean value,String key)
	{
		if(value) throw new IllegalArgumentException("Duplicate parameter: "+key); // 重复的word
		return true;
	}

	/**
	 * 转换浮点
	 */
	protected static float parseFloat(String key,String raw)
	{
		if(raw==null) throw new IllegalArgumentException("Missing float value ("+key+")"); // 缺失浮点值
		try
		{
			return Float.parseFloat(raw);
		}
		catch (Exception e)
		{
			throw new IllegalArgumentException("Unexpected float value: "+raw+" ("+key+")"); // 浮点值转换失败
		}
	}

	/**
	 * 转换浮点 带范围判断
	 */
	protected static float parseFloat(String key,String raw,float min,float max)
	{
		float ret=parseFloat(key,raw);

		if(ret>max) throw new IllegalArgumentException("The given value is higher than the maximum allowed: "+ret+" ["+min+","+max+"] ("+key+")");
		if(ret<min) throw new IllegalArgumentException("The given value is below the minimum allowed: "+ret+" ["+min+","+max+"] ("+key+")");

		return ret;
	}

	/**
	 * 转换整型
	 */
	protected static int parseInt(String key,String raw)
	{
		if(raw==null) throw new IllegalArgumentException("Missing integer value ("+key+")"); // 缺失整型值
		try
		{
			return Integer.parseInt(raw);
		}
		catch (Exception e)
		{
			throw new IllegalArgumentException("Unexpected integer value: "+raw+" ("+key+")"); // 整型值转换失败
		}
	}

	/**
	 * 转换整型 带范围判断
	 */
	protected static int parseInt(String key,String raw,int min,int max)
	{
		int ret=parseInt(key,raw);

		if(ret>max) throw new IllegalArgumentException("The given value is higher than the maximum allowed: "+ret+" ["+min+","+max+"] ("+key+")");
		if(ret<min) throw new IllegalArgumentException("The given value is below the minimum allowed: "+ret+" ["+min+","+max+"] ("+key+")");

		return ret;
	}

	/**
	 * 转换策略
	 */
	protected static Strategy parseStrategy(String key, String raw)
	{
		try
		{
			return Strategy.valueOf(raw);
		}
		catch (Exception e)
		{
			throw new IllegalArgumentException("Unexpected strategy value: "+raw+" ("+key+")"); // 转换策略失败
		}
	}

	/**
	 * 转换整型数组
	 */
	protected static int[] parseIntArray(String key,String raw)
	{
		int[] ret;
		List<Integer> value=new ArrayList<>();
		if(raw!=null && raw.length()>0)
		{
			String[] words=raw.split(",");
			for(String word:words) value.add( parseInt(key, word.trim() ) ); // 可能会转换失败
		}
		ret=new int[value.size()];
		for(int i=0;i<ret.length;i++) ret[i]=value.get(i);
		return ret;
	}

	/**
	 * 转换字符串数组
	 */
	protected static String[] parseStringArray(String key,String raw)
	{
		String[] ret;
		List<String> value=new ArrayList<>();
		if(raw!=null && raw.length()>0)
		{
			String[] words=raw.split(",");
			for(String word:words) value.add( word.trim() );
		}
		ret=new String[value.size()];
		for(int i=0;i<ret.length;i++) ret[i]=value.get(i);
		return ret;
	}

	protected static int between(Random rand,int min,int max)
	{
		return min + rand.nextInt(1+max-min);
	}
	protected static float between(Random rand,float min,float max)
	{
		return min + rand.nextFloat() * (max-min);
	}

//	public boolean canReplace(IBlockState state)
//	{
//		return selector != null && selector.test(state);
//	}
}
