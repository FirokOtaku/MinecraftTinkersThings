package firok.tiths.util;

import baubles.api.BaublesApi;
import baubles.api.cap.IBaublesItemHandler;
import c4.conarm.lib.armor.ArmorCore;
import firok.tiths.common.Items;
import firok.tiths.item.ISoulGather;
import firok.tiths.item.ISoulStore;
import firok.tiths.item.ISoulWatch;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.tinkering.ITinkerable;
import slimeknights.tconstruct.library.tools.ToolCore;
import slimeknights.tconstruct.library.traits.ITrait;
import slimeknights.tconstruct.library.utils.TinkerUtil;

import java.util.*;

/**
 * 灵魂系统工具类
 */
public class SoulUtil
{
	/**
	 * 获取所有储存灵魂的物品
	 * @param player 玩家
	 */
	public static List<ItemStack> getAllSoulStores(EntityPlayer player)
	{
		List<ItemStack> ret=new ArrayList<>();
		if(player==null) return ret;

		InventoryPlayer invPlayer=player.inventory;
		IBaublesItemHandler invBaubles=BaublesApi.getBaublesHandler(player);

		int tempSizeInv=invBaubles.getSlots();
		for(int i=0;i<tempSizeInv;i++)
		{
			ItemStack stackInv=invBaubles.getStackInSlot(i);

			if(stackInv.isEmpty() || !(stackInv.getItem() instanceof ISoulStore)) continue;

			ret.add(stackInv);
		}
		for(ItemStack stackArmor:invPlayer.armorInventory)
		{
			if(stackArmor.isEmpty() || !(stackArmor.getItem() instanceof ISoulStore)) continue;

			ret.add(stackArmor);
		}
		tempSizeInv=invPlayer.getSizeInventory();
		for(int i=0;i<tempSizeInv;i++)
		{
			ItemStack stackInv=invPlayer.getStackInSlot(i);

			if(stackInv.isEmpty() || !(stackInv.getItem() instanceof ISoulStore)) continue;

			ret.add(stackInv);
		}

		return ret;
	}

	public static int countSoul(EntityPlayer player)
	{
		if(player==null) return 0;
		int ret=0;
		List<ItemStack> iSouls= getAllSoulStores(player);
		for(ItemStack stackSoul:iSouls)
		{
			ISoulStore iSoulStore =(ISoulStore)stackSoul.getItem();
			int pointSoul= iSoulStore.countSoul(stackSoul);
			if(pointSoul>0) ret+=pointSoul;
		}
		return ret;
	}

	/**
	 * @param player 玩家
	 * @param point 消耗量
	 * @param force 如果为真, 则尽力消耗
	 * @param fromDeath 是否来源于
	 * @return
	 */
	public static int costSoul(EntityPlayer player,final int point,final boolean force,final boolean fromDeath)
	{
		if(player==null || point<=0) return 0;
		List<ItemStack> iSouls= getAllSoulStores(player);

		if(force)
		{
			Map<ItemStack,Integer> priorities=new HashMap<>();
			for(ItemStack stackSoul:iSouls)
				priorities.put(stackSoul,((ISoulStore)stackSoul.getItem()).costSoulPriority(stackSoul,player));
			iSouls.sort(Comparator.comparing(i -> priorities.getOrDefault(i, ISoulStore.Common)));

			int ret=point; // 还需要消耗多少

			for(ItemStack stackSoul:iSouls)
			{
				ISoulStore iSoulStore =(ISoulStore)stackSoul.getItem();
				int realCost= iSoulStore.costSoul(stackSoul,player,ret,fromDeath); // 计算能消耗多少

				if(realCost>0) ret-=realCost;
				if(ret<=0) break; // 已经完成消耗
			}

			return point-ret;
		}
		else // force == false
		{
			int ret=point;
			for(ItemStack stackSoul:iSouls)
			{
				ISoulStore iSoulStore =(ISoulStore)stackSoul.getItem();
				int realCost= iSoulStore.countSoul(stackSoul); // 先统计有多少可以消耗

				if(realCost>0) ret-=realCost;
				if(ret<=0) break; // 已经完成消耗
			}

			if(ret>0) return 0;
			ret=point;

			for(ItemStack stackSoul:iSouls)
			{
				ISoulStore iSoulStore =(ISoulStore)stackSoul.getItem();
				int realCost= iSoulStore.costSoul(stackSoul,player,ret,fromDeath); // 先统计有多少可以消耗

				if(realCost>0) ret-=realCost;
				if(ret<=0) break; // 已经完成消耗
			}

			return point;
		}

	}
	public static int chargeSoul(EntityPlayer player,int point)
	{
		if(player==null || point<=0) return 0;
		List<ItemStack> iSouls= getAllSoulStores(player);
		Map<ItemStack,Integer> priorities=new HashMap<>();
		for(ItemStack stackSoul:iSouls)
			priorities.put(stackSoul,((ISoulStore)stackSoul.getItem()).chargeSoulPriority(stackSoul,player));
		iSouls.sort((i1,i2)->{
			Integer p1=priorities.getOrDefault(i1,0),p2=priorities.getOrDefault(i2,0);
			return p1.compareTo(p2);
		});

		int ret=point; // 还需要充能多少
		for(ItemStack stackSoul:iSouls)
		{
			ISoulStore iSoulStore =(ISoulStore)stackSoul.getItem();
			int realCost= iSoulStore.chargeSoul(stackSoul,player,ret); // 计算能充能多少

			if(realCost>0) ret-=realCost;
			if(ret<=0) break; // 已经完成充能
		}

		return point-ret;
	}

	/**
	 * 获取所有增加灵魂掉落的物品
	 * @param entity 实体
	 */
	public static Map<ItemStack,ISoulGather> getAllSoulGathers(EntityLivingBase entity)
	{
		HashMap<ItemStack,ISoulGather> ret=new HashMap<>();
		if(entity==null) return ret;

		if(entity instanceof EntityPlayer)
		{
			EntityPlayer player=(EntityPlayer)entity;
			InventoryPlayer invPlayer=player.inventory;
			IBaublesItemHandler invBaubles=BaublesApi.getBaublesHandler(player);

			int tempSizeInv;

			tempSizeInv=invBaubles.getSlots();
			for(int i=0;i<tempSizeInv;i++) // 检查饰品
			{
				ItemStack stackInv=invBaubles.getStackInSlot(i);
				ISoulGather isg=getSoulGather(stackInv);

				if(isg!=null) ret.put(stackInv,isg);
			}
			tempSizeInv=invPlayer.getSizeInventory();
			for(int i=0;i<tempSizeInv;i++) // 检查背包
			{
				ItemStack stackInv=invPlayer.getStackInSlot(i);
				ISoulGather isg=getSoulGather(stackInv);
				if(isg!=null) ret.put(stackInv,isg);
			}
		}

		for(ItemStack stackArmor:entity.getEquipmentAndArmor()) // 检查装备
		{
			ISoulGather isg=getSoulGather(stackArmor);
			if(isg!=null) ret.put(stackArmor,isg);
		}

		return ret;
	}

	public static ISoulGather getSoulGather(ItemStack stack)
	{
		if(stack==null || stack.isEmpty()) return null;

		Item item=stack.getItem();

		// 检查物品类型
		if(item instanceof ISoulGather) return (ISoulGather)item;

		// 检查特性
		if(item instanceof ITinkerable)
		{
			List<ITrait> traits= TinkerUtil.getTraitsOrdered(stack);
			for(ITrait trait:traits)
			{
				if(trait instanceof ISoulGather) return (ISoulGather)trait;
			}
		}

		// 检查附魔
		if(stack.hasTagCompound())
		{
			Map<Enchantment,Integer> enchantments=EnchantmentHelper.getEnchantments(stack);
			for(Map.Entry<Enchantment,Integer> entry:enchantments.entrySet())
			{
				if(entry.getKey() instanceof ISoulGather) return (ISoulGather)entry.getKey();
			}
		}

		return null;
	}
	public static ISoulWatch getSoulWatch(ItemStack stack)
	{
		if(stack==null || stack.isEmpty()) return null;

		Item item=stack.getItem();
		if(item instanceof ISoulWatch) return (ISoulWatch) item;

		// 检查特性
		if(item instanceof ITinkerable)
		{
			List<ITrait> traits= TinkerUtil.getTraitsOrdered(stack);
			for(ITrait trait:traits)
			{
				if(trait instanceof ISoulWatch) return (ISoulWatch) trait;
			}
		}

		// 检查附魔
		if(stack.hasTagCompound())
		{
			Map<Enchantment,Integer> enchantments=EnchantmentHelper.getEnchantments(stack);
			for(Map.Entry<Enchantment,Integer> entry:enchantments.entrySet())
			{
				if(entry.getKey() instanceof ISoulWatch) return (ISoulWatch) entry.getKey();
			}
		}

		return null;
	}

}
