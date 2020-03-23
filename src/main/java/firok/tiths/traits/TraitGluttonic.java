package firok.tiths.traits;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.FoodStats;
import slimeknights.tconstruct.library.traits.AbstractTrait;

import static firok.tiths.common.Keys.*;

// 暴食
public class TraitGluttonic extends AbstractTrait
{
	public TraitGluttonic()
	{
		super(nameTraitGluttonic, colorTraitGluttonic);
	}

	@Override
	public void afterHit(ItemStack tool, EntityLivingBase holder, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit)
	{
		if(!holder.world.isRemote && wasHit && holder instanceof EntityPlayer)
		{
			holder.addPotionEffect(new PotionEffect(MobEffects.HUNGER,125,0));

			if(!target.isEntityAlive())
			{
				EntityPlayer player=(EntityPlayer)holder;
				FoodStats stats=player.getFoodStats();

				int foodLevel=stats.getFoodLevel();
				float saturationLevel=stats.getSaturationLevel();

				// 增加饱食度
				stats.setFoodLevel(foodLevel+4);
				stats.setFoodSaturationLevel(saturationLevel+4);
			}
		}
	}
}
/*
在player.dat中，有4个字段是关于饥饿系统的：

食物水平（foodLevel）：它表示玩家目前的饥饿值，取值范围是从0到20，显示在饥饿条上。1点等于1（Half Hunger.svg）（半个“鸡腿”）。初始值为20（即满饥饿值）。
食物饱和度（foodSaturationLevel）：它表示玩家目前的饱和度等级，决定了饥饿度下降的速度，吃不同种类的食物补充的饱和度不同。这是一项隐藏的食物变量，这个变量的值是无法超过食物水平的，其初始值为5。当饱和度降至0时，饥饿条会规律地颤抖。
食物计刻表（foodTickTimer）：当食物水平为18（Hunger.svgHunger.svgHunger.svgHunger.svgHunger.svgHunger.svgHunger.svgHunger.svgHunger.svg）及更高或者等于0（Empty Hunger.svg）时，它就会以刻（1/20秒）为单位来增加。当其到达80（4秒）时，就会重设至0，并且对玩家生命值治疗或者伤害1（Half Heart.svg）。如果玩家的饥饿值是满的即20（Hunger.svgHunger.svgHunger.svgHunger.svgHunger.svgHunger.svgHunger.svgHunger.svgHunger.svgHunger.svg），玩家生命值会恢复1（Half Heart.svg）的1⁄6，最多回复1（Half Heart.svg）生命值，当食物计刻表达到了10（1⁄2秒），就会重置为0。
饥饿等级（foodExhaustionLevel）：玩家目前的饥饿等级，它决定了食物饱和度下降的速度，取值范围是从0.0到4.0。玩家的每项行动都会增加它。初始的数值为0，当饥饿等级到达4.0时，它会自动归零，并且从食物饱和度或者食物水平（当食物饱和度等于0时）减去1点。注意的是如果饱和度不足1将减为0，不会从饥饿值中扣除点数。
食用食物同时补充饥饿值和饱和度，先补充的是饥饿值，饥饿值的提高也允许了饱和度的提高（因为饱和度不能超过饥饿值）。例如，玩家吃下一个金胡萝卜（补充6饥饿值，14.4饱和度），吃之前饥饿值为9，饱和度低于1，那么饥饿值会升至15，金胡萝卜提供的饱和度也会被充分利用。然而，如果吃之前饥饿值低于9，一部分的饱和度就浪费掉了。

效果
当饥饿值在20（Hunger.svgHunger.svgHunger.svgHunger.svgHunger.svgHunger.svgHunger.svgHunger.svgHunger.svgHunger.svg），且仍有富余饱和度时，生命值每半秒回复最多1（Half Heart.svg），每点生命值消耗1.5食物水平（6饥饿等级）。若玩家生命值已满，则多余的饥饿值会保留到玩家受伤时进行快速恢复。[1]
当饥饿值在18（Empty Hunger.svgHunger.svgHunger.svgHunger.svgHunger.svgHunger.svgHunger.svgHunger.svgHunger.svgHunger.svg）或更高，且没有多余饱和度时，生命值每4秒回复最多1（Half Heart.svg），每点生命值消耗1.5食物水平（6饥饿等级）。
当饥饿值在17（Empty Hunger.svgHalf Hunger.svgHunger.svgHunger.svgHunger.svgHunger.svgHunger.svgHunger.svgHunger.svgHunger.svg）或更低，玩家的生命值不会自行回复，除非处于和平模式。
当饥饿值在6（Empty Hunger.svgEmpty Hunger.svgEmpty Hunger.svgEmpty Hunger.svgEmpty Hunger.svgEmpty Hunger.svgEmpty Hunger.svgHunger.svgHunger.svgHunger.svg）或者以下时，玩家将不能进行奔跑。
当饥饿值降低到0（Empty Hunger.svgEmpty Hunger.svgEmpty Hunger.svgEmpty Hunger.svgEmpty Hunger.svgEmpty Hunger.svgEmpty Hunger.svgEmpty Hunger.svgEmpty Hunger.svgEmpty Hunger.svg）时，玩家的生命值将会以每4秒1（Half Heart.svg）的速度减少（此时不能睡觉）。在简单难度下，玩家的生命值将只会减少到10（Heart.svgHeart.svgHeart.svgHeart.svgHeart.svgEmpty Heart.svgEmpty Heart.svgEmpty Heart.svgEmpty Heart.svgEmpty Heart.svg）；在普通难度下，玩家的生命值只会减少到1（Half Heart.svgEmpty Heart.svgEmpty Heart.svgEmpty Heart.svgEmpty Heart.svgEmpty Heart.svgEmpty Heart.svgEmpty Heart.svgEmpty Heart.svgEmpty Heart.svg），而在困难难度下，若不立即进食，你的生命值将会不断减少至0（Empty Heart.svgEmpty Heart.svgEmpty Heart.svgEmpty Heart.svgEmpty Heart.svgEmpty Heart.svgEmpty Heart.svgEmpty Heart.svgEmpty Heart.svgEmpty Heart.svg）（死亡）。[2]
* */