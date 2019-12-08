package firok.tiths.traits;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.ToolHelper;

import static firok.tiths.util.Keys.colorTraitIcy;
import static firok.tiths.util.Keys.nameTraitIcy;
import static firok.tiths.util.Predicates.canTick;

public class TraitIcy extends AbstractTrait
{
	public TraitIcy()
	{
		super(nameTraitIcy,colorTraitIcy);
	}

	@Override
	public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected)
	{
		if(!world.isRemote && canTick(world,80,2) && entity instanceof EntityLivingBase)
		{
			BlockPos pos=entity.getPosition();
			float temp=world.getBiome(pos).getFloatTemperature(pos);
			if(temp==0.15f) return;
			// 每个生物群系均有一个温度数值来决定该位置是下雪还是下雨，或者是什么都不下。
			// 该数值低于0.15即下雪，0.15-0.95即下雨，高于1.0的话将会使区域保持干旱。
			// 这些数值也用于决定不同群系中积雪的高度。
			// 每从默认海平面（Y=64）向上升高1米，温度将会降低0.00166667（1⁄600）。
			// 海平面以下不会有温度变化。
			// ———————— minecraft wiki
			// https://minecraft-zh.gamepedia.com/%E7%94%9F%E7%89%A9%E7%BE%A4%E7%B3%BB
			if(!ToolHelper.isBroken(tool))
			{
				EntityLivingBase enlb=(EntityLivingBase) entity;

				float amount = temp>0.15f?
						( 6.5f * (temp-0.15f) ): // damage 0~12
						( 6.15f * (0.15f-temp) ); // heal 0~4

				if(temp>0) // fixme low 这里的方法说不定可以合并 因为底层实现看起来差不多
				{
					ToolHelper.damageTool(tool,(int)amount,enlb);
				}
				else // temp <0
				{
					ToolHelper.healTool(tool,(int)amount,enlb);
				}
			}
		}
	}

	@Override
	public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit)
	{
		if(wasHit && !target.world.isRemote && target.isEntityAlive())
		{
			target.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS,60));
		}
	}
}
