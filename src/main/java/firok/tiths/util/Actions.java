package firok.tiths.util;

import firok.tiths.TinkersThings;
import firok.tiths.entity.projectile.ProjectileDashingStar;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

// 所有的行为封装
public class Actions
{
	private Actions(){}

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
