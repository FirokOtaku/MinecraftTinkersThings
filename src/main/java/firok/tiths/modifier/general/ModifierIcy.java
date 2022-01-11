package firok.tiths.modifier.general;

import firok.tiths.util.Colors;
import firok.tiths.util.DevUse;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.helper.ToolDamageUtil;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

import static firok.tiths.util.Predicates.canTickServer;

/**
 * 冰凉
 *
 * https://github.com/351768593/MinecraftTinkersThings/blob/indev1122/src/main/java/firok/tiths/traits/TraitIcy.java
 */
@DevUse
public class ModifierIcy extends Modifier
{
	public ModifierIcy()
	{
		super(Colors.DeepSkyBlue);
	}

	public static boolean canEffect(World world)
	{
		return canTickServer(world,80,2);
	}

	public static float calcAmount(Entity entity)
	{
		BlockPos pos=entity.getPosition();
		World world=entity.world;
		float temp=world.getBiome(pos).getTemperature(pos);

		if(temp==0.15f) return 0;
		// 每个生物群系均有一个温度数值来决定该位置是下雪还是下雨，或者是什么都不下。
		// 该数值低于0.15即下雪，0.15-0.95即下雨，高于1.0的话将会使区域保持干旱。
		// 这些数值也用于决定不同群系中积雪的高度。
		// 每从默认海平面（Y=64）向上升高1米，温度将会降低0.00166667（1⁄600）。
		// 海平面以下不会有温度变化。
		// ———————— minecraft wiki
		// https://minecraft-zh.gamepedia.com/%E7%94%9F%E7%89%A9%E7%BE%A4%E7%B3%BB

		// damage 0~12 // heal 0~4
		return (temp > 0.15f?6.5f:6.15f) * (0.15f - temp);
		// 热的时候temp>0.15 amount就会小于0
		// 冷的时候amount大于0
	}

	@Override
	public void onInventoryTick(IModifierToolStack tool, int level, World world, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack)
	{
		if(canEffect(world) && !tool.isBroken())
		{
			int amount = (int) calcAmount(holder);
			if(amount < 0) ToolDamageUtil.damage(tool, -amount, holder, stack);
			else ToolDamageUtil.repair(tool, amount);
		}
	}

	@Override
	public int afterEntityHit(IModifierToolStack tool, int level, ToolAttackContext context, float damageDealt)
	{
		LivingEntity target = context.getLivingTarget();
		if(target != null)
		{
			target.extinguish(); // let's add some new flavor
			target.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 60, 0));
		}
		return 0;
	}
}
