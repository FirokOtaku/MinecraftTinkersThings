package firok.tiths.intergration.conarm.traits;

import c4.conarm.lib.traits.AbstractArmorTrait;
import firok.tiths.common.Configs;
import firok.tiths.util.EntityFinders;
import net.minecraft.client.particle.Particle;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntitySelectors;
import net.minecraft.world.World;

import java.util.List;

import static firok.tiths.common.Keys.colorTraitSurging;
import static firok.tiths.common.Keys.nameTraitSurging;
import static firok.tiths.util.Predicates.canTick;

/**
 * 浪涌 - 护甲
 */
public class TraitArmorSurging extends AbstractArmorTrait
{
	public TraitArmorSurging()
	{
		super(nameTraitSurging,colorTraitSurging);
	}

	@Override
	public void onArmorTick(ItemStack tool, World world, EntityPlayer player)
	{
		if(!world.isRemote && player.isSprinting())
		{
			List<Entity> list=EntityFinders.Nearby(player, Configs.ArmorTraits.range_surging, EntitySelectors.IS_ALIVE);
			for(Entity en:list)
			{
				if(en==null || en instanceof IProjectile) continue;

				boolean close=en.getDistanceSqToEntity(player) < 9;
				if(close)
				{
					en.motionX=player.motionX * 1.64;
					en.motionY=player.motionY + 0.12;
					en.motionZ=player.motionZ * 1.64;
				}
				else
				{
					en.motionX=player.motionX * 1.3;
					en.motionY=player.motionY * 0.6 + 0.12;
					en.motionZ=player.motionZ * 1.3;
				}
			}
//			if(canTick(world,20,0))
//			{
//				System.out.print("trigger\n");
//			}
		}
	}
}
