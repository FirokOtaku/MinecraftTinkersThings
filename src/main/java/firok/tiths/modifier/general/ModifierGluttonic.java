package firok.tiths.modifier.general;

import firok.tiths.config.ConfigModifier;
import firok.tiths.util.DevUse;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.FoodStats;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

/**
 * 暴食
 *
 * https://github.com/351768593/MinecraftTinkersThings/blob/indev1122/src/main/java/firok/tiths/traits/TraitGluttonic.java
 * */
@DevUse
public class ModifierGluttonic extends Modifier
{
	public ModifierGluttonic()
	{
		super(0x752b40);
	}

	public float getEntityDamage(IModifierToolStack tool, int level, ToolAttackContext context, float baseDamage, float damage)
	{
		// 1.12.2 we didn't do this.
		return damage + 1.5f;
	}

	@Override
	public int afterEntityHit(IModifierToolStack tool, int level, ToolAttackContext context, float damageDealt)
	{
		PlayerEntity player = context.getPlayerAttacker();
		LivingEntity target = context.getLivingTarget();
		if(player != null && player.isServerWorld() && target != null)
		{
			player.addPotionEffect(new EffectInstance(Effects.HUNGER, 125, 0));

			if(target.getHealth() < 0) // dead
			{
				FoodStats fs = player.getFoodStats();
				fs.setFoodLevel(fs.getFoodLevel() + ConfigModifier.factor_gluttonic_food.get());
			}
		}

		return 0;
	}
}
/*
在player.dat中，有4个字段是关于饥饿系统的：
食物水平（foodLevel）：它表示玩家目前的饥饿值，取值范围是从0到20，显示在饥饿条上。1点等于1（半个“鸡腿”）。初始值为20（即满饥饿值）。
食物饱和度（foodSaturationLevel）：它表示玩家目前的饱和度等级，决定了饥饿度下降的速度，吃不同种类的食物补充的饱和度不同。这是一项隐藏的食物变量，这个变量的值是无法超过食物水平的，其初始值为5。当饱和度降至0时，饥饿条会规律地颤抖。
食物计刻表（foodTickTimer）：当食物水平为18时，它就会以刻（1/20秒）为单位来增加。当其到达80（4秒）时，就会重设至0，并且对玩家生命值治疗或者伤害1（Half Heart.svg）。如果玩家的饥饿值是满的即20，玩家生命值会恢复1（Half Heart.svg）的1⁄6，最多回复1（Half Heart.svg）生命值，当食物计刻表达到了10（1⁄2秒），就会重置为0。
饥饿等级（foodExhaustionLevel）：玩家目前的饥饿等级，它决定了食物饱和度下降的速度，取值范围是从0.0到4.0。玩家的每项行动都会增加它。初始的数值为0，当饥饿等级到达4.0时，它会自动归零，并且从食物饱和度或者食物水平（当食物饱和度等于0时）减去1点。注意的是如果饱和度不足1将减为0，不会从饥饿值中扣除点数。
食用食物同时补充饥饿值和饱和度，先补充的是饥饿值，饥饿值的提高也允许了饱和度的提高（因为饱和度不能超过饥饿值）。例如，玩家吃下一个金胡萝卜（补充6饥饿值，14.4饱和度），吃之前饥饿值为9，饱和度低于1，那么饥饿值会升至15，金胡萝卜提供的饱和度也会被充分利用。然而，如果吃之前饥饿值低于9，一部分的饱和度就浪费掉了。

效果
当饥饿值在20，且仍有富余饱和度时，生命值每半秒回复最多1（Half Heart.svg），每点生命值消耗1.5食物水平（6饥饿等级）。
若玩家生命值已满，则多余的饥饿值会保留到玩家受伤时进行快速恢复。[1]
当饥饿值在18或更高，且没有多余饱和度时，生命值每4秒回复最多1（Half Heart.svg），每点生命值消耗1.5食物水平（6饥饿等级）。
当饥饿值在17或更低，玩家的生命值不会自行回复，除非处于和平模式。
当饥饿值在6或者以下时，玩家将不能进行奔跑。
当饥饿值降低到0时，玩家的生命值将会以每4秒1（Half Heart.svg）的速度减少（此时不能睡觉）。
在简单难度下，玩家的生命值将只会减少到10；
在普通难度下，玩家的生命值只会减少到1），
而在困难难度下，若不立即进食，你的生命值将会不断减少至0（死亡）。[2]
*/
