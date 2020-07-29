package firok.tiths.intergration.conarm.traits;

import c4.conarm.lib.traits.AbstractArmorTrait;
import com.google.common.collect.ImmutableList;
import firok.tiths.common.SoundEvents;
import firok.tiths.util.ITraitData;
import firok.tiths.util.TraitExtraData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

import java.util.List;

import static firok.tiths.common.Keys.colorTraitHydrating;
import static firok.tiths.common.Keys.nameTraitHydrating;
import static firok.tiths.util.Predicates.canDealWith;
import static firok.tiths.util.Predicates.canTick;

/**
 * 水合 - 护甲
 */
public class TraitArmorHydrating extends AbstractArmorTrait implements ITraitData<TraitArmorHydrating.HydratingData>
{
	public TraitArmorHydrating()
	{
		super(nameTraitHydrating,colorTraitHydrating);
	}

	public void regen(ItemStack stack,int point,int max)
	{
		if(stack==null) return;

		HydratingData data=this.readExtraDataFromStack(stack);
		data.point+=point;
		if(data.point>max) data.point=max;
		this.writeExtraDataToStack(stack,data);
	}
	public int cost(ItemStack stack,int cost)
	{
		if(stack==null || cost<0) return 0;

		HydratingData data=this.readExtraDataFromStack(stack);
		if(data.point<cost)
		{
			cost=data.point;
			data.point=0;
		}
		else // point >= cost
		{
			data.point -= cost;
		}
		this.writeExtraDataToStack(stack,data);
		return cost;
	}
	public int get(ItemStack stack)
	{
		return this.readExtraDataFromStack(stack).point;
	}

	/*
	每1点可以降低伤害0.01 = 抵消1点伤害需要消耗100点

	水中每秒恢复10点 恢复上限为500 = 每秒恢复0.1血量
	淋雨每秒恢复7点 500 = 每秒恢复0.07血量
	*/
	@Override
	public void onArmorTick(ItemStack tool, World world, EntityPlayer player)
	{
		if(!world.isRemote && canTick(world,20,0))
		{
			if(player.isInWater())
			{
				regen(tool,10,500);
			}
			else if(world.isRainingAt(player.getPosition()))
			{
				regen(tool,7,500);
			}
		}
	}

	@Override
	public float onDamaged(ItemStack armor, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingDamageEvent evt)
	{
		if(canDealWith(source,true,null,null,null,null))
		{
			int costMax=(int)(newDamage*100);
			int costReal=cost(armor,costMax);

			if(costReal>0)
			{
				newDamage-= costReal/100f;
				if(player.isServerWorld())
				{
					player.world.playSound(null,player.posX,player.posY,player.posZ,
							SoundEvents.effectBubble, SoundCategory.MASTER,1,1);
				}
			}

			return newDamage;
		}
		return newDamage;
	}

	@Override
	public List<String> getExtraInfo(ItemStack tool, NBTTagCompound modifierTag)
	{
		HydratingData data=readExtraDataFromStack(tool);

		if(data.point<=100) return ImmutableList.of();

		StringBuilder info=new StringBuilder().append("§3");
		while(data.point>0)
		{
			if(data.point>=100) info.append("●");
			data.point-=100;
		}
		info.append("§r");

		return ImmutableList.of(info.toString());
	}
	
	public static class HydratingData implements TraitExtraData
	{
		public int point;
	}

	@Override
	public String getDataKey()
	{
		return "HydratingData";
	}

	@Override
	public HydratingData readExtraData(NBTTagCompound nbtExtraData)
	{
		HydratingData data=new HydratingData();
		data.point = nbtExtraData!=null && nbtExtraData.hasKey("point") ? nbtExtraData.getInteger("point") : 0;
		return data;
	}

	@Override
	public NBTTagCompound writeExtraData(HydratingData data)
	{
		NBTTagCompound nbt=new NBTTagCompound();
		nbt.setInteger("point",data.point);
		return nbt;
	}
}
