package firok.tiths.common;

import com.google.gson.JsonArray;
import firok.tiths.TinkersThings;
import firok.tiths.util.Actions;
import firok.tiths.util.Colors;
import firok.tiths.util.InnerActions;
import firok.tiths.util.Predicates;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.IBlockState;
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
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import org.apache.commons.io.FileUtils;

import javax.annotation.Nullable;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;
import java.util.List;

import static firok.tiths.TinkersThings.log;
import static firok.tiths.common.Blocks.*;
import static net.minecraft.init.Blocks.*;
import static slimeknights.tconstruct.TConstruct.random;

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
			log(Arrays.toString(args));
			if(sender instanceof EntityPlayer)
			{
				EntityPlayer player=(EntityPlayer)sender;
				if(args.length>=1)
				{
					final String arg=args[0];
					SWITCH:switch(arg)
					{
						case "worldgen":
						{
							if("reload".equals(args[1]))
							{
								WorldGens instance=WorldGens.getInstance();
								if(instance.isLoading())
								{
									log("generator is reloading now");
								}
								else
								{
									new Thread(() -> {
										log("generator now start to reload");
										ConfigJson.readOres();
										WorldGens.getInstance().reload();
										log("generator reloading finished");
									}).start();
								}
							}
							break SWITCH;
						}
						case "damage":
						{
							float amount=Float.parseFloat(args[1]);
							player.attackEntityFrom(DamageSources.TestDamage, amount);
							break SWITCH;
						}
						case "heal":
						{
							player.heal(20);
							player.getFoodStats().setFoodLevel(20);

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
							int amount= 300;
							try { if(args.length > 1) amount=Double.valueOf(args[1]).intValue(); } catch (Exception ignored) { }
							if(amount<0) amount=0;
							if(amount>10000) amount=10000;
							player.setAir(amount);
							break SWITCH;
						}
						case "export":
						{
							if(!"biomes".equals(args[1])) break SWITCH;

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
						case "gen":
						{


							break SWITCH;
						}
						case "nbt":
						{
							ItemStack stack=player.getHeldItemMainhand();
							if(!stack.hasTagCompound()) break SWITCH;

							NBTTagCompound nbt=stack.getTagCompound();
							System.out.println(nbt);

							break SWITCH;
						}
						case "text":
						{


							break SWITCH;
						}
					}
				}
			}
		}
		catch (Exception e)
		{
			log("error when running command");
			e.printStackTrace();
		}
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender)
	{
		return sender instanceof EntityPlayer &&
				( Configs.General.enable_command_survival ||
						InnerActions.has(Configs.General.whitelist_command_survival,sender.getName())
				);
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
