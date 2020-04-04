package firok.tiths.intergration.conarm.traits;

import c4.conarm.common.armor.utils.ArmorHelper;
import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import static firok.tiths.common.Keys.colorTraitThermalGathering;
import static firok.tiths.common.Keys.nameTraitThermalGathering;
import static firok.tiths.traits.TraitThermalGathering.canThermalGather;
import static firok.tiths.traits.TraitThermalGathering.checkThermalSource;

/**
 * 热力聚集 - 护甲
 */
public class TraitArmorThermalGathering extends AbstractArmorTrait
{
	public TraitArmorThermalGathering()
	{
		super(nameTraitThermalGathering,colorTraitThermalGathering);
	}

	@Override
	public float onHurt(ItemStack armor, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingHurtEvent evt)
	{
		return source.isFireDamage()? newDamage * 0.5f : newDamage;
	}

	@Override
	public void onArmorTick(ItemStack tool, World world, EntityPlayer player)
	{
		if(canThermalGather(world) && checkThermalSource(player))
		{
			ArmorHelper.healArmor(tool,4,player,0);
		}
	}
}
