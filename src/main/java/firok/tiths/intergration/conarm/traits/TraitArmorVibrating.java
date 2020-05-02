package firok.tiths.intergration.conarm.traits;

import c4.conarm.lib.traits.AbstractArmorTrait;
import firok.tiths.traits.TraitVibrating;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import static firok.tiths.common.Keys.colorTraitVibrating;
import static firok.tiths.common.Keys.nameTraitVibrating;
import static firok.tiths.util.Predicates.canTick;

/**
 * 振动 - 护甲
 */
public class TraitArmorVibrating extends AbstractArmorTrait
{
//	public static final String NBTKey=MOD_ID+'_'+nameTraitVibrating;
	public TraitArmorVibrating()
	{
		super(nameTraitVibrating,colorTraitVibrating);
	}

	@Override
	public float onHurt(ItemStack armor, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingHurtEvent evt)
	{
		float fre=TraitVibrating.addFrequency(armor,12, 144);
		float percent=fre/144 * 0.6f;
		return newDamage * (1-percent);
	}

	@Override
	public void onArmorTick(ItemStack tool, World world, EntityPlayer player)
	{
		if(canTick(world,5,0))
		{
			TraitVibrating.subtractFrequency(tool,1,0);
		}
	}
}
