package firok.tiths.common;

import com.google.gson.JsonArray;
import firok.tiths.util.InnerActions;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import org.apache.commons.io.FileUtils;
import slimeknights.tconstruct.library.utils.ToolHelper;

import javax.annotation.Nullable;
import java.io.File;
import java.util.*;
import java.util.List;

import static firok.tiths.TinkersThings.log;
import static firok.tiths.util.Predicates.canTrigger;

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
//						case "texture":
//						{
////							Ignore_MaterialTextureHandler.gen();
//
//							break SWITCH;
//						}
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
						case "nbt":
						{
							ItemStack stack=player.getHeldItemMainhand();
							if(!stack.hasTagCompound()) break SWITCH;

							NBTTagCompound nbt=stack.getTagCompound();
							System.out.println(nbt);

							break SWITCH;
						}
						case "rand":
						{
							Random rand=player.world.rand;

							final int W = 10000;
							for(float rate=0.95f; rate>0; rate-=0.1)
							{
								int count=0;
								for(int i=0;i<W;i++) count += canTrigger(rand, rate) ? 1 : 0;
								System.out.printf("rate %f = %d/%d (%f)\n",rate,count,W,(count/(float)W));
							}


							break SWITCH;
						}
						case "tic":
						{
							ItemStack stackHeld = player.getHeldItemMainhand();
							System.out.printf("ds %d    dm %d    dc %d\n",
									ToolHelper.getDurabilityStat(stackHeld),
									ToolHelper.getMaxDurability(stackHeld),
									ToolHelper.getCurrentDurability(stackHeld)
									);
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
