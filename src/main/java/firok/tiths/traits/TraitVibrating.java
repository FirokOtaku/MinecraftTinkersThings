package firok.tiths.traits;

import firok.tiths.TinkersThings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import slimeknights.tconstruct.library.traits.AbstractTrait;

import static firok.tiths.common.Keys.colorTraitVibrating;
import static firok.tiths.common.Keys.nameTraitVibrating;
import static firok.tiths.util.Predicates.canTick;

// 震动
public class TraitVibrating extends AbstractTrait
{
	public static final String NBTKey= TinkersThings.MOD_ID + '_' +nameTraitVibrating;
	public TraitVibrating()
	{
		super(nameTraitVibrating, colorTraitVibrating);
	}

	public static float addFrequency(ItemStack stack,int count,int max)
	{
		NBTTagCompound nbt=stack.hasTagCompound()?stack.getTagCompound():new NBTTagCompound();
		float frequency=nbt.hasKey(NBTKey)?nbt.getFloat(NBTKey):0;

		frequency+=count;
		if(frequency>max) frequency=max;

		nbt.setFloat(NBTKey,frequency);
		stack.setTagCompound(nbt);

		return frequency;
	}

	public static float subtractFrequency(ItemStack stack,int count,int min)
	{
		NBTTagCompound nbt=stack.hasTagCompound()?stack.getTagCompound():new NBTTagCompound();
		float frequency=nbt.hasKey(NBTKey)?nbt.getFloat(NBTKey):0;

		frequency-=count;
		if(frequency<min) frequency=min;

		nbt.setFloat(NBTKey,frequency);
		stack.setTagCompound(nbt);

		return frequency;
	}

	public static float getFrequency(ItemStack stack,int max,int min)
	{
		NBTTagCompound nbt=stack.hasTagCompound()?stack.getTagCompound():new NBTTagCompound();
		float frequency=nbt.hasKey(NBTKey)?nbt.getFloat(NBTKey):0;
		return frequency>max? max : frequency<min? min: frequency;
	}

	@Override
	public void onHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, boolean isCritical)
	{
		// 每次使用增加12 最大叠加12层
		addFrequency(tool,12, 144);
	}

	@Override
	public void beforeBlockBreak(ItemStack tool, BlockEvent.BreakEvent event)
	{
		// 每次使用增加12 最大叠加12层
		addFrequency(tool,12,144);
	}

	@Override
	public void miningSpeed(ItemStack tool, PlayerEvent.BreakSpeed event)
	{
		// 每层降低20%速度
		float speed=event.getOriginalSpeed();
		float frequency=getFrequency(tool,144,0);
		while(frequency>0)
		{
			speed*=0.8;
			frequency-=12;
		}

		event.setNewSpeed(speed);
	}

	@Override
	public float knockBack(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float knockback, float newKnockback, boolean isCritical)
	{
		// 每层增加8%击退
		float frequency=getFrequency(tool,144,0);
		while(frequency>0)
		{
			newKnockback*=1.08;
			frequency-=12;
		}

		return newKnockback;
	}

	@Override
	public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected)
	{
		// 每秒降低4
		if(canTick(world,5,0))
		{
			subtractFrequency(tool,1,0);
		}
	}

	/* ---- 下面这种是一开始的想法和实现 因为效率低被废弃了 但是可能以后有别的用处 ---- */
//	/**
//	 * 处理时间点数组, 将过期时间点移除. 同时向时间点数组内追加一个新的时间点
//	 * @param timestampsRaw 原始时间点数组数据
//	 * @param now 当前时间点
//	 * @param interval 最大经过间隔
//	 * @return 处理后时间点数组数据
//	 */
//	public static long[] append(long[] timestampsRaw,long now,int interval)
//	{
//		long[] timestampsTemp=new long[timestampsRaw.length+1];
//		int countNew=0;
//		for (long timestampRaw : timestampsRaw)
//		{
//			long temp=now - timestampRaw;
//			if (temp < interval && timestampRaw>0)
//			{
//				timestampsTemp[countNew] = timestampRaw;
//				countNew++;
//			}
//		}
//		timestampsTemp[countNew]=now;
//		countNew++;
//		long[] timestampsNew=new long[countNew];
//		System.arraycopy(timestampsTemp, 0, timestampsNew, 0, countNew);
//
//		return timestampsNew;
//	}
//
//	/**
//	 * 处理时间点数组, 将过期时间点移除. 若没有变化则返回原数组引用
//	 * @param timestampsRaw 原始时间点数组数据
//	 * @param now 当前时间点
//	 * @param interval 最大经过间隔
//	 * @return 处理后时间点数组数据
//	 */
//	public static long[] trim(long[] timestampsRaw,long now,int interval)
//	{
//		long[] timestampsTemp=new long[timestampsRaw.length];
//		int countNew=0;
//		for (long timestampRaw : timestampsRaw)
//		{
//			long temp=now - timestampRaw;
//			if (temp < interval && timestampRaw>0)
//			{
//				timestampsTemp[countNew] = timestampRaw;
//				countNew++;
//			}
//		}
////		timestampsTemp[countNew]=now;
////		countNew++;
//		if(countNew!=timestampsRaw.length)
//		{
//			long[] timestampsNew=new long[countNew];
//			System.arraycopy(timestampsTemp, 0, timestampsNew, 0, countNew);
//			return timestampsNew;
//		}
//		else
//		{
//			return timestampsRaw;
//		}
//	}
//
//	public static long[] trimTimestamp(ItemStack tool, long now, int interval)
//	{
//		NBTTagCompound nbt=tool.hasTagCompound()?tool.getTagCompound():new NBTTagCompound();
//		long[] timestampsRaw=nbt.hasKey(NBTKey)? Values.toLongArray(nbt.getIntArray(NBTKey)):new long[0];
//		long[] timestampsNew=trim(timestampsRaw,now,interval);
//
//		if(timestampsNew!=timestampsRaw)
//		{
//			nbt.setIntArray(NBTKey,Values.toIntArray(timestampsNew));
//			tool.setTagCompound(nbt);
//		}
//
//		return timestampsNew;
//	}
//
//	public static long[] appendTimestamp(ItemStack tool, long now, int interval)
//	{
//		NBTTagCompound nbt=tool.hasTagCompound()?tool.getTagCompound():new NBTTagCompound();
//
//		long[] timestampsRaw=nbt.hasKey(NBTKey)? Values.toLongArray(nbt.getIntArray(NBTKey)):new long[0];
//		long[] timestampsNew=append(timestampsRaw,now,interval);
//
//		nbt.setIntArray(NBTKey,Values.toIntArray(timestampsNew));
//		tool.setTagCompound(nbt);
//
//		return timestampsNew;
//	}

//	@Override
//	public void miningSpeed(ItemStack tool, PlayerEvent.BreakSpeed event)
//	{
//		float speed=event.getOriginalSpeed();
//		long now=event.getEntityPlayer().world.getTotalWorldTime();
//		long[] timestampsNew=trimTimestamp(tool, now, 200);
//
//		for(int i=0;i<timestampsNew.length;i++)
//		{
//			speed*=0.8;
//		}
//
//		event.setNewSpeed(speed);
//	}
//
//	@Override
//	public float knockBack(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float knockback, float newKnockback, boolean isCritical)
//	{
//		long now=player.world.getTotalWorldTime();
//		long[] timestampsNew=trimTimestamp(tool, now, 200);
//
//		for(int i=0;i<timestampsNew.length;i++)
//		{
//			newKnockback*=1.2;
//		}
//
//		return newKnockback;
//	}
//
//	@Override
//	public void onHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, boolean isCritical)
//	{
//		long now=player.world.getTotalWorldTime();
//		long[] t=appendTimestamp(tool, now, 200);
//		System.out.println(Arrays.toString(t));
//	}
//
//	@Override
//	public void beforeBlockBreak(ItemStack tool, BlockEvent.BreakEvent event)
//	{
//		long now=event.getPlayer().world.getTotalWorldTime();
//		appendTimestamp(tool, now, 200);
//	}
}