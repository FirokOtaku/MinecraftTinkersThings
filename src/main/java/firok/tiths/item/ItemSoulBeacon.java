package firok.tiths.item;

import firok.tiths.util.EntityFinders;
import firok.tiths.util.SoulUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

import static firok.tiths.util.Predicates.canTick;
import static firok.tiths.util.Selectors.mobAlive;

// 灵魂信标
public class ItemSoulBeacon extends ItemCustom
{
	public ItemSoulBeacon()
	{
		super();
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
	{
		ItemStack stackHeld=player.getHeldItem(hand);
		if(!world.isRemote && canTick(world,5,0))
		{
//			int cost= SoulUtil.costSoul(player,1000,false);
			List<Entity> entitiesFacing= EntityFinders.FacingHeight(player,player.getEyeHeight()/2,10,2,mobAlive);

			for(Entity en:entitiesFacing)
			{
				EntityLivingBase enlb=(EntityLivingBase)en;
				enlb.addPotionEffect(new PotionEffect(MobEffects.GLOWING,20,0));
//					enlb.attackEntityFrom(DamageSource.MAGIC,2);
			}
		}
		else
		{
			Random rand=world.rand;
			Vec3d facing=player.getForward();
			Vec3d scale=facing.scale(rand.nextFloat() * 5);

			world.spawnParticle(EnumParticleTypes.FLAME,
					player.posX+scale.x+rand.nextFloat()*0.5-0.25,
					player.posY+scale.y+player.eyeHeight+rand.nextFloat()*0.5-0.25,
					player.posZ+scale.z+rand.nextFloat()*0.5-0.25,
					facing.x * 0.4,facing.y * 0.4,facing.z * 0.4
			);
		}
		return ActionResult.newResult(EnumActionResult.FAIL,stackHeld);
	}
}
