package firok.tiths.item;

import firok.tiths.common.TiCMaterials;
import firok.tiths.util.Actions;
import firok.tiths.util.EntityFinders;
import firok.tiths.util.Selectors;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.Material;

import java.util.List;
import java.util.Random;

public class ItemDebug extends ItemCustom
{
	public ItemDebug()
	{
		super();
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
	{
		return true;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
	{

		TRY:try
		{
			List<Entity> entitiesFacing= EntityFinders.FacingEye(player,5,3, Selectors.mobAlive);


			if(!world.isRemote)
			{
				for(Entity entity: entitiesFacing)
				{
					entity.attackEntityFrom(DamageSource.GENERIC,1);
				}
			}
			else
			{
				Vec3d facing=player.getForward();
				Vec3d facingFactor=facing.scale(0.2);
				Vec3d center=player.getPositionVector().addVector(0,player.getEyeHeight(),0);
				Random rand=player.world.rand;

				for(int i=0;i<5;i++)
				{
					Vec3d pos2spawn=center.add( facing.scale( 5f/i ) );
					world.spawnParticle(EnumParticleTypes.FLAME,
							pos2spawn.x+rand.nextFloat()*0.5-0.25,pos2spawn.y+rand.nextFloat()*0.5-0.25,pos2spawn.z+rand.nextFloat()*0.5-0.25,
							facingFactor.x,facingFactor.y,facingFactor.z
							);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}


		return new ActionResult<>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
	}
}
