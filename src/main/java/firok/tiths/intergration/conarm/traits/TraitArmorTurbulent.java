package firok.tiths.intergration.conarm.traits;

import c4.conarm.lib.traits.AbstractArmorTrait;
import firok.tiths.util.Actions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import static firok.tiths.common.Keys.colorTraitTurbulent;
import static firok.tiths.common.Keys.nameTraitTurbulent;

/**
 * 扰动 - 护甲
 */
public class TraitArmorTurbulent extends AbstractArmorTrait
{
	public TraitArmorTurbulent()
	{
		super(nameTraitTurbulent, colorTraitTurbulent);
	}

	@Override
	public float onHurt(ItemStack armor, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingHurtEvent evt)
	{
		if(player.isInWater())
		{
			PUSH_ATTACKER:if(source instanceof EntityDamageSource)
			{
				EntityDamageSource eds=(EntityDamageSource) source;
				Entity attacker=eds.getTrueSource();
				if(attacker==null || !attacker.isEntityAlive()) break PUSH_ATTACKER;

				Vec3d vec=new Vec3d( player.posX-attacker.posX, player.posY-attacker.posY, player.posZ-attacker.posZ);
				Vec3d unit= Actions.CalcUnitVector(vec);
				if(unit==null) break PUSH_ATTACKER;

				double factor=0.8;
				if(Math.abs(attacker.motionX)<1.5) attacker.motionX = Actions.rabs(attacker.motionX - unit.x * factor,1.53);
				if(Math.abs(attacker.motionY)<1.5) attacker.motionY = Actions.rabs(attacker.motionY - unit.y * factor,1.53);
				if(Math.abs(attacker.motionZ)<1.5) attacker.motionZ = Actions.rabs(attacker.motionZ - unit.z * factor,1.53);
			}

			float ret=newDamage - damage/4;
			return ret>0?ret:0;
		}
		return newDamage;
	}
}
