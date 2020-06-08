package firok.tiths.common;

import baubles.api.BaublesApi;
import baubles.api.cap.IBaublesItemHandler;
import firok.tiths.util.SoulUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ITickable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 服务端数据管理器
 */
public class Datas
{
	@SideOnly(Side.SERVER)
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
			;
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
