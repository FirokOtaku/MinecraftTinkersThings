package firok.tiths.intergration.conarm.traits;

import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

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
		if(!world.isRemote && canTick(world,4,0) && player.isSneaking())
		{
			player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE,80,1));
		}
	}
}
