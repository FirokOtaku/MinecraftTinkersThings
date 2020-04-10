package firok.tiths.entity.trans;

import firok.tiths.traits.TraitThermalGathering;
import firok.tiths.util.Actions;
import firok.tiths.util.Ranges;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

import static firok.tiths.util.Predicates.canTick;

/**
 * 炼金术实体
 */
public class Alchemy extends Entity
{
	public int last=160;
	public List<ItemStack> stacks=new ArrayList<>();
	public Alchemy(World worldIn)
	{
		super(worldIn);
	}

	public void endup()
	{
		this.setDead();
		for(ItemStack stack:stacks)
		{
			Actions.CauseSpawnItem(this,stack);
		}
	}

	@Override
	public void onEntityUpdate()
	{
		last--;
		if(last<=0)
		{
			endup();
			return;
		}

		if(world.isRemote && canTick(world,3,0))
		{
			world.spawnParticle(EnumParticleTypes.CLOUD,
					posX+rand.nextFloat()*0.3,posY,posZ+rand.nextFloat()*0.3,
					0,0.1,0);
		}

		if(!world.isRemote)
		{
			int time=(int)(world.getTotalWorldTime()%5);
			SWITCH:switch(time)
			{
				case 1: // 吸收周围的物品
				{
					List<EntityItem> eis=world.getEntitiesWithinAABB(EntityItem.class, Ranges.Neighbour(this,1.5));
					FOR_EI:for(EntityItem ei:eis)
					{
						ei.setDead();

						boolean hasCombined=false;
						ItemStack stackEi = ei.getItem();
						FOR_STACK:for(ItemStack stack:stacks)
						{

//					    boolean canStack=ItemHandlerHelper.canItemStacksStack(stack,stackEi); // fixme 以后可能换成这个

							if(stackEi.isEmpty())
							{
								continue FOR_EI;
							}
							if (stackEi.getItem() != stack.getItem())
							{
								continue FOR_STACK;
							}
							else if (stackEi.hasTagCompound() ^ stack.hasTagCompound())
							{
								continue FOR_STACK;
							}
							else if (stackEi.hasTagCompound() && !stackEi.getTagCompound().equals(stack.getTagCompound()))
							{
								continue FOR_STACK;
							}
							else if (stackEi.getItem().getHasSubtypes() && stackEi.getMetadata() != stack.getMetadata())
							{
								continue FOR_STACK;
							}
//					        else if (stackEi.getCount() < stack.getCount())
//					        {
//					        	return other.combineItems(this);
//					        }
//					        else if (stackEi.getCount() + stack.getCount() > stackEi.getMaxStackSize())
//					        {
//					        	return false;
//					        }
							else if (!stack.areCapsCompatible(stackEi))
							{
								continue FOR_STACK;
							}
							else
							{
								stack.grow(stackEi.getCount());
								hasCombined=true;
								break FOR_STACK;
							}
						}
						if(!hasCombined)
						{
							stacks.add(stackEi);
						}
					}
					break SWITCH;
				}
//				case 2: // 合成
//				{
//					if(canTick(world,10,0))
//					{
//						;
//						for(ItemStack stack:stacks)
//						{
//							;
//						}
//					}
//					break SWITCH;
//				}
				case 3: // 检查结构
				{
					int x=(int)posX,y=(int)posY,z=(int)posZ;
					posX=x+0.5;
					posY=y+0.5;
					posZ=z+0.5;
					BlockPos center=new BlockPos(x,y,z);
					if(
							world.getBlockState(center).getBlock() != Blocks.WATER ||
							world.getBlockState(center.east()).getMaterial() != Material.IRON ||
			                world.getBlockState(center.west()).getMaterial() != Material.IRON ||
			                world.getBlockState(center.north()).getMaterial() != Material.IRON ||
			                world.getBlockState(center.west()).getMaterial() != Material.IRON ||
			                world.getBlockState(center.down()).getMaterial() != Material.IRON ||
			                !TraitThermalGathering.isThermalSource(world.getBlockState(center.down(2)).getBlock())
					)
					{
						endup();
					}
					break SWITCH;
				}
				default:
				{
					break SWITCH;
				}
			}
		}
	}

	@Override
	protected void entityInit()
	{
		setSize(0,0);
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt)
	{
		this.last=nbt.hasKey("last")?160:nbt.getInteger("last");
		this.stacks.clear();

	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt)
	{

	}
}
