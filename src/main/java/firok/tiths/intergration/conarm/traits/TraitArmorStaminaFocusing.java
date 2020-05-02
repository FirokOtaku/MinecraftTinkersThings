package firok.tiths.intergration.conarm.traits;

import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

import static firok.tiths.TinkersThings.randClient;
import static firok.tiths.common.Keys.colorTraitStaminaFocusing;
import static firok.tiths.common.Keys.nameTraitStaminaFocusing;
import static firok.tiths.util.Predicates.canTick;

/**
 * 精力汇聚 - 护甲
 */
public class TraitArmorStaminaFocusing extends AbstractArmorTrait
{
	public TraitArmorStaminaFocusing()
	{
		super(nameTraitStaminaFocusing,colorTraitStaminaFocusing);
	}

	@Override
	public void onArmorTick(ItemStack tool, World world, EntityPlayer player)
	{
		if(canTick(world,4,0) && player.isSneaking())
		{
			if (world.isRemote)
			{
				world.spawnParticle(EnumParticleTypes.SPELL_WITCH,
						player.posX + randClient.nextFloat() * 0.3,
						player.posY + player.getEyeHeight() * 2 / 3,
						player.posZ + randClient.nextFloat() * 0.3,
						0,0,0);
			}
			else
			{
				player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE,80,1));
			}
		}
	}
}
