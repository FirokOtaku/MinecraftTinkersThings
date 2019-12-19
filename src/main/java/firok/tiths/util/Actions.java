package firok.tiths.util;

import firok.tiths.TinkersThings;
import firok.tiths.entity.projectile.ProjectileDashingStar;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

// 所有的行为封装
public class Actions
{
	private Actions(){}

	public static void generateTreasureRoom(World world,BlockPos pos)
	{
		final int cx=pos.getX(),cy=pos.getY(),cz=pos.getZ();
		final int mx=5,my=5,mz=5;
		for(byte tx=0;tx<mx;tx++)
		{
			for(byte tz=0;tz<mz;tz++)
			{
				for(byte ty=0;ty<my;ty++)
				{
					BlockPos posTemp=new BlockPos(cx+tx,cy+ty,cz+tz);
					if(tx==0||tx==mx-1||tz==0||tz==mz-1||ty==0||ty==my-1)
					{
						world.setBlockState(posTemp,Blocks.STONEBRICK.getDefaultState());
					}
					else
					{
						world.setBlockToAir(posTemp);

						if(ty==1 && tx==2 && tz==2)
						{
							world.setBlockState(posTemp,Blocks.CHEST.getDefaultState());
							TileEntityChest chest=(TileEntityChest)world.getTileEntity(posTemp);
							if(chest==null)
							{
								chest=new TileEntityChest();
								chest.setWorld(world);
								chest.setPos(posTemp);
								world.setTileEntity(posTemp,chest);
							}
							ResourceLocation listLootTable=null;
							double temp=world.rand.nextDouble();
							// fixme 这个表以后再改吧
							if(temp>0.4) listLootTable=LootTableList.CHESTS_SIMPLE_DUNGEON;
							else if(temp>0.2) listLootTable=LootTableList.CHESTS_VILLAGE_BLACKSMITH;
							else if(temp>0.15) listLootTable=LootTableList.CHESTS_NETHER_BRIDGE;
							else if(temp<0.025) listLootTable=LootTableList.CHESTS_END_CITY_TREASURE;
							chest.setLootTable(listLootTable,world.getTotalWorldTime());
//							{
//								LootTableManager manager = world.getLootTableManager()
//
//								ResourceLocation lootTableLocator = ;
//								LootTable table = manager.getLootTableFromLocation(lootTableLocator);
//// 注：LootContext.Builder 的构造器只接受 WorldServer，但 getLootTableManager 方法是 World 里的。
//								LootContext context = LootContext.Builder(world).withPlayer(player).withDamageSource(...).build();
//								List<ItemStack> loots = table.generateLootForPools(world.rand, context);
//							}
						}
					}
				}
			}
		}
	}

	// 创建一次星绽
	public static ProjectileDashingStar CauseStarDashing(World world,double fromX,double fromY,double fromZ,double toX,double toY,double toZ,float speed,float damage)
	{
		TinkersThings.log("gen star..."+System.currentTimeMillis());
		ProjectileDashingStar star=new ProjectileDashingStar(world,fromX,fromY,fromZ);

		double mod= (fromX-toX)*(fromX-toX)+(fromY-toY)*(fromY-toY)+(fromZ-toZ)*(fromZ-toZ);
		mod= MathHelper.sqrt(mod);

		star.motionX= speed * (toX-fromX) / mod;
		star.motionY= speed * (toY-fromY) / mod;
		star.motionZ= speed * (toZ-fromZ) / mod;

		star.damage=damage;

		world.spawnEntity(star);
		return star;
	}
	public static ProjectileDashingStar CauseStarDashing(Entity from,Entity to,float speed,float damage)
	{
		ProjectileDashingStar star=CauseStarDashing(from.world,from.posX,from.posY+3,from.posZ,to.posX,to.posY+to.getEyeHeight(),to.posZ,speed,damage);
		if(from instanceof EntityPlayer)
		{
			star.shotter=((EntityPlayer) from).getDisplayNameString();
		}
		return star;
	}
}
