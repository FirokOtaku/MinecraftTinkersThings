package firok.tiths.world;

import firok.tiths.common.Blocks;
import firok.tiths.common.ConfigJson;
import firok.tiths.util.Predicates;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static firok.tiths.util.Predicates.canTrigger;

/**
 * 云层矿物生成器
 * @since 0.3.19.0 第三次世界生成模块修改
 */
public class WorldGenCloud extends AbstractChunkGen
{
	/**
	 * 高度
	 */
	protected int maxHeight,minHeight;
	/**
	 * 尺寸
	 */
	protected int maxX,minX,maxZ,minZ,maxY,minY;
	/**
	 * 替换为矿物的概率
	 */
	protected float rateOre;

	@Override
	public void init(Map<String,String> raw, IBlockState...states)
	{
		super.init(raw,states);

		boolean flagMinY=false,flagMaxY=false,
				flagMinX=false,flagMaxX=false,
				flagMinZ=false,flagMaxZ=false,
				flagMinHeight=false,flagMaxHeight=false,
				flagRateOre=false
				;

		for(Map.Entry<String,String> entry:raw.entrySet())
		{
			String k=entry.getKey(), v=entry.getValue();

			switch (k)
			{
				case "min_x":
					flagMinX=checkParameter(flagMinX,k);
					this.minX=parseInt(k,v,1,8);
					break;

				case "max_x":
					flagMaxX=checkParameter(flagMaxX,k);
					this.maxX=parseInt(k,v,1,8);
					break;

				case "min_z":
					flagMinZ=checkParameter(flagMinZ,k);
					this.minZ=parseInt(k,v,1,8);
					break;

				case "max_z":
					flagMaxZ=checkParameter(flagMaxZ,k);
					this.maxZ=parseInt(k,v,1,8);
					break;

				case "min_y":
					flagMinY=checkParameter(flagMinY,k);
					this.minY=parseInt(k,v,1,8);
					break;

				case "max_y":
					flagMaxY=checkParameter(flagMaxY,k);
					this.maxY=parseInt(k,v,1,8);
					break;

				case "min_height":
					flagMinHeight=checkParameter(flagMinHeight,k);
					this.minHeight=parseInt(k,v);
					break;

				case "max_height":
					flagMaxHeight=checkParameter(flagMaxHeight,k);
					this.maxHeight=parseInt(k,v);
					break;

				case "rate_ore":
					flagRateOre=checkParameter(flagRateOre,k);
					this.rateOre=parseFloat(k,v,0,1);
					break;

//				default: errorInvalidKVP(k); break;
			}
		}

		if(!flagMinX) this.minX=3;
		if(!flagMaxX) this.maxX=6;
		if(!flagMinY) this.minY=1;
		if(!flagMaxY) this.maxY=2;
		if(!flagMinZ) this.minZ=3;
		if(!flagMaxZ) this.maxZ=6;
		if(!flagRateOre) this.rateOre=0.00225f; // 0.015f 是区块成功概率
		if(!flagMinHeight) this.minHeight=150;
		if(!flagMaxHeight) this.maxHeight=180;
		if(maxX<minX) errorLower("max_x",maxX,minX);
		if(maxY<minY) errorLower("max_y",maxY,minY);
		if(maxZ<minZ) errorLower("max_z",maxZ,minZ);
		if(maxHeight<minHeight) errorLower("max_height",maxHeight,minHeight);
	}

	@Override
	public List<BlockPos> genAtRealPos(World world, final int posX, final int posY, final int posZ, int chunkVertexX, int chunkVertexZ, Random rand)
	{
		// 一种二次曲面，是椭圆在三维空间的推广。
		// 椭球在xyz-笛卡尔坐标系中的方程是：x2 / a2+y2 / b2+z2 / c2=1。

		List<BlockPos> ret=new ArrayList<>();
		final int rx=between(rand,minX,maxX),ry=between(rand,minY,maxY),rz=between(rand,minZ,maxZ);
		final float a2=rx*rx,b2=ry*ry,c2=rz*rz;

		IBlockState cloud=Blocks.blockCloud.getDefaultState();
		IBlockState ore=getMainState();
		if(ore==null) return ret;

		FOR_X: for(int ox=-rx; ox<=rx; ox++)
		{
			int ox2=ox*ox;
			FOR_Z: for(int oz=-rz; oz<=rz; oz++)
			{
				int oz2=oz*oz;
				FOR_Y: for(int oy=-ry; oy<=ry; oy++)
				{
					int oy2=oy*oy;
					final boolean inside=ox2/a2+oy2/b2+oz2/c2<1;

					if(inside)
					{
						BlockPos posTemp=new BlockPos(posX+ox,posY+oy,posZ+oz);
						if(AbstractChunkGen.isAirBlock(world,posTemp,chunkVertexX,chunkVertexZ))
						{
							boolean isOre=canTrigger(rand,rateOre);
							AbstractChunkGen.setState(world, posTemp, isOre?ore:cloud, chunkVertexX,chunkVertexZ);
							ret.add(posTemp);
						}
					}
				}
			}
		}
		return ret;
	}
}
