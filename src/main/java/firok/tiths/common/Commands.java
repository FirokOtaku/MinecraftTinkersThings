package firok.tiths.common;

import com.google.gson.JsonArray;
import firok.tiths.TinkersThings;
import firok.tiths.util.InnerActions;
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
import java.io.File;
import java.util.*;

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
						case "gen":
						{
							IBlockState stateAir = AIR.getDefaultState();
							IBlockState stateCOAL_ORE = COAL_ORE.getDefaultState();
							IBlockState stateDIAMOND_ORE = DIAMOND_ORE.getDefaultState();
							IBlockState stateEMERALD_ORE = EMERALD_ORE.getDefaultState();
							IBlockState stateIRON_ORE = IRON_ORE.getDefaultState();
							IBlockState stateGOLD_ORE = GOLD_ORE.getDefaultState();
							IBlockState stateLAPIS_ORE = LAPIS_ORE.getDefaultState();
							IBlockState stateREDSTONE_ORE = REDSTONE_ORE.getDefaultState();
							IBlockState stateStellarium_ORE = oreStellarium.getDefaultState();

							World world = player.world;
							double ppx = player.posX, ppy = player.posY, ppz = player.posZ; // player pos 玩家位置

							int R;
							do {
								int Max = 8; // 最大半径
								R = random.nextInt(Max);
							} while (R <= 4); // 最小半径
							int r = R * 2/3; //内球半径

							int BlockScalar = (int) (Math.PI * (R * R * R) * 3/4);
							class block {
								int ix;
								int iy;
								int iz;

								block(int ix, int iy, int iz) {
									this.ix = ix;
									this.iy = iy;
									this.iz = iz;
								}
							}
							block[] block = new block[BlockScalar]; // 定义类组，存储替换为矿物的坐标

							int i = 0;
							FOR_x:
							for (int Ix = -R; Ix <= R; Ix++) {
								for (int Iy = -R; Iy <= R; Iy++) {
									for (int Iz = -R; Iz <= R; Iz++) {
										if (Ix * Ix + Iy * Iy + Iz * Iz > r * r && Ix * Ix + Iy * Iy + Iz * Iz <= R * R) { // 球壳内方块随机填为矿物
											BlockPos posold = new BlockPos(Ix+ppx,Iy+ppy,Iz+ppz);
											IBlockState stateold = world.getBlockState(posold);
											Block blockold = stateold.getBlock();
											double j=Math.random();
											if (j<0.3 && blockold != AIR){ // 球壳内方块填充为矿物概率 && 非空气方块替换
												block[i] = new block(Ix,Iy,Iz);
												i++;
											}
										}
									}
								}
							}
							for (int Ix = -R; Ix < R; Ix++) {
								for (int Iy = -R; Iy < R; Iy++) {
									for (int Iz = -R; Iz < R; Iz++) {
										if (Ix * Ix + Iy * Iy + Iz * Iz <= R * R) {
											BlockPos posTemp = new BlockPos(Ix+ppx,Iy+ppy,Iz+ppz);
											world.setBlockState(posTemp,stateAir);
										}
									}
								}
							}

							for (int Ix = -R; Ix < R; Ix++) {
								for (int Iy = -R; Iy < R; Iy++) {
									for (int Iz = -R; Iz < R; Iz++) {
										if (Ix * Ix + Iy * Iy + Iz * Iz <= R * R/9){ // 中心生成
											BlockPos posTemp = new BlockPos(Ix+ppx,Iy+ppy-(R-r),Iz+ppz);
											world.setBlockState(posTemp,stateStellarium_ORE);
										}
									}
								}
							}

							for (i=0;i<=BlockScalar;i++){
								BlockPos posTemp = new BlockPos(ppx+block[i].ix,ppy+block[i].iy,ppz+block[i].iz);
								switch (random.nextInt(8)){
									case 1:world.setBlockState(posTemp,stateCOAL_ORE);break;
									case 2:world.setBlockState(posTemp,stateDIAMOND_ORE);break ;
									case 3:world.setBlockState(posTemp,stateEMERALD_ORE);break ;
									case 4:world.setBlockState(posTemp,stateGOLD_ORE);break ;
									case 5:world.setBlockState(posTemp,stateIRON_ORE);break ;
									case 6:world.setBlockState(posTemp,stateLAPIS_ORE);break ;
									case 7:world.setBlockState(posTemp,stateREDSTONE_ORE);break ;
								}
							}

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
