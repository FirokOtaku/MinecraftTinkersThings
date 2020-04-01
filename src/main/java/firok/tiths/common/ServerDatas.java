package firok.tiths.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 服务端数据管理器
 */
public class ServerDatas
{
	private ServerDatas(MinecraftServer server)
	{
		Objects.requireNonNull(server);

		this.server=server;
	}
	private static ServerDatas instance; // 单例管理
	public static void init(MinecraftServer server)
	{
		instance=new ServerDatas(server);
	}
	public static void uninit()
	{
		instance=null;
	}
	public static ServerDatas instance()
	{
		return instance;
	}

	MinecraftServer server;

	public Map<String, EntityPlayer> mapPlayer=new HashMap<>();
}
