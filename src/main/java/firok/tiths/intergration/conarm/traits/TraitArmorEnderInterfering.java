package firok.tiths.intergration.conarm.traits;

import c4.conarm.common.armor.utils.ArmorHelper;
import c4.conarm.lib.traits.AbstractArmorTrait;
import firok.tiths.util.Actions;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

import java.util.Random;

import static firok.tiths.common.Keys.colorTraitEnderInterfering;
import static firok.tiths.common.Keys.nameTraitEnderInterfering;
import static firok.tiths.util.Predicates.canTick;

/**
 * 末影干涉
 */
public class TraitArmorEnderInterfering
	extends AbstractArmorTrait
{
	public TraitArmorEnderInterfering()
	{
		super(nameTraitEnderInterfering,colorTraitEnderInterfering);
	}

	@Override
	public void onArmorTick(ItemStack tool, World world, EntityPlayer player)
	{
		if(!player.isSneaking()) return;

		if(world.isRemote)
		{
			if(canTick(world,35,0))
			{
				Random rand = world.rand;
				for (int i = 0; i < 4; i++)
				{
					world.spawnParticle(EnumParticleTypes.END_ROD,
							player.posX + 0.2 - rand.nextFloat() * 0.4,
							player.posY + player.eyeHeight + rand.nextFloat() * 0.2 - 0.1,
							player.posZ + 0.2 - rand.nextFloat() * 0.4,
							0,0,0);
				}
			}
		}
		else // 服务端
		{
			if(!canTick(world,5,0)) return; // 降低频率 降低性能需求

			int cost = Actions.CauseEnderInterfering(player,1,20,10,2,1);

			if(!player.isCreative())
			{
				ArmorHelper.damageArmor(tool, DamageSource.MAGIC, cost / 4, player);
			}
		}
	}
}
