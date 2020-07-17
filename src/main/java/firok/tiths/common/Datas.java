package firok.tiths.common;

import baubles.api.BaublesApi;
import baubles.api.cap.IBaublesItemHandler;
import firok.tiths.TinkersThings;
import firok.tiths.intergration.conarm.ArmorTraits;
import firok.tiths.util.InnerActions;
import firok.tiths.util.SoulUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.ITickable;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.library.traits.ITrait;

import java.util.*;

import static firok.tiths.util.Predicates.canTick;

/**
 * 服务端数据管理器
 */
public class Datas
{
	public static class Server implements ITickable
	{
		private Server(MinecraftServer server)
		{
			Objects.requireNonNull(server);

			this.server=server;
		}
		private static Server instance; // 单例管理
		public static void init(MinecraftServer server)
		{
			instance=new Server(server);
		}
		public static void uninit()
		{
			instance=null;
		}
		public static Server instance()
		{
			return instance;
		}

		MinecraftServer server;

		@Override
		public void update()
		{
			WorldServer[] worlds;
			long time;
			PlayerList players;
			try
			{
				if(server==null) return;
				worlds=server.worlds;
				if(worlds==null || worlds.length<=0) return;
				time=worlds[0].getTotalWorldTime();
				players=server.getPlayerList();

				if(TinkersThings.enableConarm() && canTick(time,40,2))
				{
					checkTempt(players);
				}
			}
			catch (Exception ignored) {
				ignored.printStackTrace();
			}
		}

		private void checkTempt(PlayerList players)
		{
			for(Map.Entry<UUID,Integer> entry:mapTemptMutex.entrySet())
			{
				UUID uuid=entry.getKey();
				if(uuid==null) continue;

				EntityPlayer player=players.getPlayerByUUID(uuid);
				if(player==null) continue;

				int mutex=0;
				Set<ITrait> traitsArmor= InnerActions.getArmorTraits(player);
				mutex |= traitsArmor.contains(ArmorTraits.seedUpgraded) ? mutexSeed : 0;
				mutex |= traitsArmor.contains(ArmorTraits.fishUpgraded) ? mutexFish : 0;
				mutex |= traitsArmor.contains(ArmorTraits.wheatUpgraded) ? mutexWheat : 0;
				mutex |= traitsArmor.contains(ArmorTraits.carrotUpgraded) ? mutexCarrot : 0;
				mutex |= traitsArmor.contains(ArmorTraits.beetrootUpgraded) ? mutexBeetroot : 0;

				entry.setValue(mutex);
			}
		}
		private final Map<UUID,Integer> mapTemptMutex=new HashMap<>();

		/*
		b 0000 0001 fish
		b 0000 0010 wheat
		b 0000 0100 carrot
		b 0000 1000 beetroot
		b 0001 0000 seed
		*/
		public static final int mutexFish=0b0000_0001;
		public static final int mutexWheat=0b0000_0010;
		public static final int mutexCarrot=0b0000_0100;
		public static final int mutexBeetroot=0b0000_1000;
		public static final int mutexSeed=0b0001_0000;

		public void regCarrotTempt(EntityPlayer player)
		{
			if(player==null) return;
			mapTemptMutex.compute(player.getUniqueID(),(uuid,mutexOld)-> mutexCarrot | (mutexOld == null?0:mutexOld));
		}
		public boolean hasCarrotTempt(EntityPlayer player)
		{
			return player!=null && (mapTemptMutex.getOrDefault(player.getUniqueID(),0) & mutexCarrot ) > 0;
		}
		public void regFishTempt(EntityPlayer player)
		{
			if(player==null) return;
			mapTemptMutex.compute(player.getUniqueID(),(uuid,mutexOld)-> mutexFish | (mutexOld == null?0:mutexOld));
		}
		public boolean hasFishTempt(EntityPlayer player)
		{
			return player!=null && (mapTemptMutex.getOrDefault(player.getUniqueID(),0) & mutexFish ) > 0;

		}
		public void regWheatTempt(EntityPlayer player)
		{
			if(player==null) return;
			mapTemptMutex.compute(player.getUniqueID(),(uuid,mutexOld)-> mutexWheat | (mutexOld == null?0:mutexOld));
		}
		public boolean hasWheatTempt(EntityPlayer player)
		{
			return player!=null && (mapTemptMutex.getOrDefault(player.getUniqueID(),0) & mutexWheat ) > 0;
		}
		public void regBeetrootTempt(EntityPlayer player)
		{
			if(player==null) return;
			mapTemptMutex.compute(player.getUniqueID(),(uuid,mutexOld)-> mutexBeetroot | (mutexOld == null?0:mutexOld));
		}
		public boolean hasBeetrootTempt(EntityPlayer player)
		{
			return player!=null && (mapTemptMutex.getOrDefault(player.getUniqueID(),0) & mutexBeetroot) > 0;
		}
		public void regSeedTempt(EntityPlayer player)
		{
			if(player==null) return;
			mapTemptMutex.compute(player.getUniqueID(),(uuid,mutexOld)-> mutexSeed | (mutexOld == null?0:mutexOld));
		}
		public boolean hasSeedTempt(EntityPlayer player)
		{
			return player!=null && (mapTemptMutex.getOrDefault(player.getUniqueID(),0) & mutexSeed ) > 0;
		}
	}

	@SideOnly(Side.CLIENT)
	public static class Client implements ITickable
	{
		Minecraft mc;
		private Client()
		{
			this.mc = Minecraft.getMinecraft();
		}
		private static Client instance;
		public static void init()
		{
			instance=new Client();
		}
		public static void uninit()
		{
			instance=null;
		}
		public static Client instance()
		{
			return instance;
		}

		long lastRenderTick=-1;

		@Override
		public void update()
		{
			try
			{
				if(mc==null) mc=Minecraft.getMinecraft();
				if(mc.world==null) return;

				long timeNow=mc.world.getTotalWorldTime();

				boolean shouldReloadAll=Math.abs(timeNow - lastRenderTick) >= 20 || timeNow < lastRenderTick;

				if(shouldReloadAll) forAll();
				else
				{
					if(timeNow % 4 ==0) forRender();
				}

				lastRenderTick=timeNow;
			}
			catch (Exception ignored) { }
		}

		private void forAll()
		{
			forRender();
		}

		private void forRender()
		{
			calcHasSoulScanners();
		}


		/**
		 * 能否看见灵魂物品
		 */
		private boolean _hasSoulScanners =false;
		public boolean hasSoulScanners()
		{
			return _hasSoulScanners;
		}
		private void calcHasSoulScanners()
		{
			_hasSoulScanners=false;
			SEARCH_ALL:try
			{
				SEARCH_BAUBLE:{
					IBaublesItemHandler handler= BaublesApi.getBaublesHandler(mc.player);
					if(handler==null) break SEARCH_BAUBLE;
					final int size=handler.getSlots();
					for(int i=0;i<size;i++)
					{
						ItemStack stackBauble=handler.getStackInSlot(i);
						if(SoulUtil.getSoulWatch(stackBauble) != null)
						{
							_hasSoulScanners=true;
							break SEARCH_ALL;
						}
					}
				}
				SEARCH_EQUIPMENT:{
					for(ItemStack stackEquipment:mc.player.getEquipmentAndArmor())
					{
						if(SoulUtil.getSoulWatch(stackEquipment) != null)
						{
							_hasSoulScanners=true;
							break SEARCH_ALL;
						}
					}
				}
			}
			catch (Exception ignored) { _hasSoulScanners =false; }
		}
	}

}
