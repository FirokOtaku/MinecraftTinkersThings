package firok.tiths.common;

import com.google.gson.JsonArray;
import firok.tiths.TinkersThings;
import firok.tiths.util.InnerActions;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import org.apache.commons.io.FileUtils;

import javax.annotation.Nullable;
import java.io.File;
import java.util.*;

public class Commands implements ICommand
{
	private static Commands instance;
	public static Commands getInstance()
	{
		if(instance==null) instance=new Commands();
		return instance;
	}
	@Override
	public String getName()
	{
		return "tiths";
	}

	@Override
	public String getUsage(ICommandSender sender)
	{
		return "/tiths";
	}

	@Override
	public List<String> getAliases()
	{
		return new ArrayList<>();
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args)
	{
		try
		{
			System.out.println(Arrays.toString(args));
			if(sender instanceof EntityPlayer)
			{
				EntityPlayer player=(EntityPlayer)sender;
				if(args.length>=1)
				{
					SWITCH:switch(args[0])
					{
						case "worldgen":
						{
							if("reload".equals(args[1]))
							{
								WorldGens instance=WorldGens.getInstance();
								if(instance.isLoading())
								{
									TinkersThings.tell("generator is reloading now");
								}
								else
								{
									new Thread(() -> {
										TinkersThings.tell("generator now start to reload");
										ConfigJson.readOres();
										WorldGens.getInstance().reload();
										TinkersThings.tell("generator reloading finished");
									}).start();
								}
							}
							break SWITCH;
						}
						case "damage":
						{
							float amount=Float.parseFloat(args[1]);
							player.attackEntityFrom(EntityDamageSource.GENERIC, amount);
							break SWITCH;
						}
						case "heal":
						{
							player.heal(20);
							player.getFoodStats().setFoodLevel(20);
							player.getFoodStats().setFoodSaturationLevel(20);

							break SWITCH;
						}
						case "status":
						{
							System.out.printf("hp:%f, food:%d, foodSat:%f\n",
									player.getHealth(),
									player.getFoodStats().getFoodLevel(),
									player.getFoodStats().getSaturationLevel()
							);
							break SWITCH;
						}
						case "show":
						{
							ItemStack stackHeld=player.getHeldItemMainhand();

							NBTTagCompound nbt=stackHeld.getTagCompound();
							if(nbt!=null)
							{
								Map<String,Object> map= ObfuscationReflectionHelper.getPrivateValue(NBTTagCompound.class,nbt,"field_74784_a"); // (Map) InnerActions.get(NBTTagCompound.class,"tagMap",nbt);
								System.out.println(map);
							}

							break SWITCH;
						}
						case "texture":
						{
//							Ignore_MaterialTextureHandler.gen();

							break SWITCH;
						}
						case "air":
						{
							player.setAir(40);
							break SWITCH;
						}
						case "biomes":
						{
							File fileOutput=new File("./biomes.json");

//							JsonObject jsonObject=new JsonObject();
							Set<ResourceLocation> biomeKeys=Biome.REGISTRY.getKeys();

							JsonArray biomeNames=new JsonArray();
							for(ResourceLocation biomeKey:biomeKeys)
							{
								Biome biome=Biome.REGISTRY.getObject(biomeKey);
								if(biome==null) continue;

								String name=biome.getRegistryName().toString();
								biomeNames.add(name);
							}

							FileUtils.write(fileOutput,biomeNames.toString(),"utf8");

							break SWITCH;
						}
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			TinkersThings.tell(e);
		}
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
	{
		return true;
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos)
	{
		return new ArrayList<>();
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index)
	{
		return false;
	}

	@Override
	public int compareTo(ICommand o)
	{
		return o.getName().compareTo(getName());
	}
}
