package firok.tiths.entity.special;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;

import java.util.HashMap;
import java.util.Map;

import static firok.tiths.util.Predicates.canTick;

/**
 * 焦黑窑逻辑实体
 */
public class LogicSearing extends Entity
{
	public LogicSearing(World worldIn)
	{
		super(worldIn);
	}

	int processRemain=400;
	int ventX=0,ventY=0,ventZ=0;

	@Override
	public void onEntityUpdate()
	{
		BlockPos posVent=new BlockPos(ventX,ventY,ventZ);
		processRemain--;
		if(processRemain<=0)
		{
			CheckResult result=check(world,posVent);
			if(result.success())
			{
				transform(result,world);
			}
			this.setDead();
		}

		if(canTick(world,5,1))
		{
			if(!check(world,posVent).success())
			{
				this.setDead();
			}
		}

		if(world.isRemote)
		{
			if(canTick(world,2,0))
			{
				world.spawnParticle(EnumParticleTypes.SMOKE_LARGE,
						posVent.getX(),posVent.getY(), posVent.getZ(),
						0,0,0);
			}
			if(canTick(world,3,0))
			{
				world.spawnParticle(EnumParticleTypes.FLAME,
						posVent.getX(),posVent.getY()+1.5,posVent.getZ(),
						0,0,0);
			}
		}
	}

	public boolean initFromPos(BlockPos pos)
	{
		Block blockInit=world.getBlockState(pos).getBlock();
//		System.out.println(blockInit.getUnlocalizedName());
		if(blockInit!=firok.tiths.common.Blocks.blockSearingVent)
		{
			this.setDead();
			return false;
		}

		this.ventX=pos.getX();
		this.ventY=pos.getY();
		this.ventZ=pos.getZ();
		return true;
	}

	@Override
	protected void entityInit()
	{
		this.setSize(0,0);
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt)
	{
		this.ventX=nbt.hasKey("ventX")?nbt.getInteger("ventX"):0;
		this.ventY=nbt.hasKey("ventY")?nbt.getInteger("ventY"):0;
		this.ventZ=nbt.hasKey("ventZ")?nbt.getInteger("ventZ"):0;
		this.processRemain=nbt.hasKey("processRemain")?nbt.getInteger("processRemain"):200;
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt)
	{
		nbt.setInteger("ventX",this.ventX);
		nbt.setInteger("ventY",this.ventY);
		nbt.setInteger("ventZ",this.ventZ);
		nbt.setInteger("processRemain",this.processRemain);
	}

	static class CheckResult
	{
		Map<BlockPos, Block> blocks=new HashMap<>();
		int countCoal=0;
		boolean found=true;

		boolean success()
		{
			return found && countCoal>=4;
		}
	}

	static void transform(CheckResult result,World world)
	{
		if(result.success())
		{
			IBlockState stateSeared= TinkerSmeltery.searedBlock.getDefaultState();

			for(Map.Entry<BlockPos,Block> entry:result.blocks.entrySet())
			{
				BlockPos posTemp=entry.getKey();
				Block blockTemp=entry.getValue();

				if(blockTemp==Blocks.COAL_BLOCK)
				{
					world.setBlockToAir(posTemp);
				}
				else
				{
					world.setBlockState(posTemp,stateSeared);
				}
			}
		}
	}

	static CheckResult check(World world,BlockPos posVent)
	{
		CheckResult result=new CheckResult();

//		System.out.println("开始循环");
		FOR_X:for(int ox=-2;ox<=2;ox++)
		{
			FOR_Z:for(int oz=-2;oz<=2;oz++)
			{
				FOR_Y:for(int oy=0;oy>=-5;oy--)
				{
					final BlockPos posTemp=posVent.add(ox,oy,oz);
					final IBlockState stateTemp= world.getBlockState(posTemp);
					final Block blockTemp=stateTemp.getBlock();
					final Material materialTemp=stateTemp.getMaterial();

					// 最外层必须是石头材质
					if(ox==-2 || ox==2 || oz==-2 || oz==2 || oy==0 || oy==-5)
					{
						if(materialTemp!=Material.ROCK)
						{
//							System.out.println("非石头材质方块:"+blockTemp.getUnlocalizedName());
							result.found=false;
							break FOR_X;
						}
					}
					else // 内部必须不是空气 且必须超过4块煤炭块
					{
						if(materialTemp==Material.AIR)
						{
//							System.out.println("有空气方块");
							result.found=false;
							break FOR_X;
						}
						else
						{
							if(blockTemp == Blocks.COAL_BLOCK)
							{
								result.countCoal++;
							}
							else if(blockTemp==Blocks.STONE)
							{
								;
							}
							else
							{
//								System.out.println("有不是石头或者煤炭的方块");
								result.found=false;
								break FOR_X;
							}
							result.blocks.put(posTemp,blockTemp);
						}
					}
				}
			}
		}
		return result;
	}
}
