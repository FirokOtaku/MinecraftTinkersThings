package firok.tiths.traits;

import firok.tiths.util.EntityFinders;
import firok.tiths.util.Selectors;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.ToolHelper;

import java.util.List;

import static firok.tiths.common.Keys.colorTraitAnnihilating;
import static firok.tiths.common.Keys.nameTraitAnnihilating;

/**
 * 湮灭
 */
public class TraitAnnihilating extends AbstractTrait
{
	public TraitAnnihilating()
	{
		super(nameTraitAnnihilating,colorTraitAnnihilating);
	}

	@Override
	public void onHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, boolean isCritical)
	{
		if(player.isServerWorld())
		{
			ToolHelper.damageTool(tool,10,player);
//			World world=player.world;
			List<Entity> surroundings= EntityFinders.Nearby(target,5, EntitySelectors.IS_ALIVE);
			for(Entity surrounding:surroundings)
			{
				double tx=surrounding.posX,ty=surrounding.posY,tz=surrounding.posZ;
				double mx=(player.posX-tx)/3,my=(player.posY-ty)/3,mz=(player.posZ-tz)/3;

				surrounding.motionX+=mx;
				surrounding.motionY+=my + 0.085;
				surrounding.motionZ+=mz;

				surrounding.attackEntityFrom(DamageSource.MAGIC,4);
			}
		}
	}
}
