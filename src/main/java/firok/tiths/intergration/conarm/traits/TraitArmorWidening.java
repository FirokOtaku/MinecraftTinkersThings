package firok.tiths.intergration.conarm.traits;

import c4.conarm.lib.traits.AbstractArmorTrait;
import firok.tiths.common.Configs;
import firok.tiths.util.EntityFinders;
import firok.tiths.util.Selectors;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

import java.util.List;

import static firok.tiths.common.Keys.colorTraitWidening;
import static firok.tiths.common.Keys.nameTraitWidening;

/**
 * 广域化 - 护甲
 */
public class TraitArmorWidening extends AbstractArmorTrait
{
	public TraitArmorWidening()
	{
		super(nameTraitWidening,colorTraitWidening);
	}

	public static void widen(EntityLivingBase enlb, ItemStack stack)
	{
		try
		{
			World world=enlb.world;
			Iterable<PotionEffect> effects=PotionUtils.getEffectsFromStack(stack);
			for (PotionEffect potioneffect : effects)
			{
				int amplifier=potioneffect.getAmplifier();
				List<EntityLivingBase> entities= (List)EntityFinders.Nearby(enlb, Configs.ArmorTraits.range_widening, Selectors.livingBaseAlive);
				for(EntityLivingBase entity:entities)
				{
					if(world.isRemote) // 客户端创建粒子效果
					{
						for(int i=0;i<4;i++)
						{
							world.spawnParticle(EnumParticleTypes.DRAGON_BREATH,
									entity.posX-0.4f+ random.nextFloat()*0.8,
									entity.posY+entity.getEyeHeight(),
									entity.posZ-0.4f+ random.nextFloat()*0.8,
									0,0.2,0);
						}
					}
					else // 服务端创建药水效果
					{
						if (potioneffect.getPotion().isInstant())
						{
							potioneffect.getPotion().affectEntity(enlb, enlb, entity, amplifier, 1.0D);
						}
						else
						{
							entity.addPotionEffect(new PotionEffect(potioneffect));
						}
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
