package firok.tiths.traits;

import firok.tiths.common.Configs;
import firok.tiths.common.Potions;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import slimeknights.tconstruct.library.traits.AbstractTrait;

import static firok.tiths.common.Keys.colorTraitGravitomagnetism;
import static firok.tiths.common.Keys.nameTraitGravitomagnetism;

/**
 * 引力磁场
 * <p>
 * 击中敌人时叠加踉跄: 无则施加配置时长的 1 级;
 * 已有则等级 +1 且持续时间重置; 已达配置层数上限时仅刷新持续时间.
 * </p>
 *
 * @see Configs.Traits#max_level_gravitomagnetism
 * @see Configs.Traits#duration_gravitomagnetism
 * @see firok.tiths.intergration.conarm.traits.TraitArmorGravitomagnetism
 */
public class TraitGravitomagnetism extends AbstractTrait
{
    public TraitGravitomagnetism()
    {
        super(nameTraitGravitomagnetism, colorTraitGravitomagnetism);
    }

    /**
     * 对目标叠加 / 刷新踉跄效果.
     */
    public static void applyStaggering(EntityLivingBase target)
    {
        if (target == null || !target.isServerWorld() || !target.isEntityAlive()) return;

        int maxLevel = Math.max(1, Configs.Traits.max_level_gravitomagnetism);
        int duration = Math.max(1, Configs.Traits.duration_gravitomagnetism) * 20;

        PotionEffect pe = target.getActivePotionEffect(Potions.staggering);
        int amplifier;
        if (pe == null)
        {
            amplifier = 0;
        }
        else if (pe.getAmplifier() + 1 < maxLevel)
        {
            amplifier = pe.getAmplifier() + 1;
        }
        else
        {
            // 已达层数上限: 保持当前等级 (并钳制到上限), 仅刷新持续时间
            amplifier = Math.min(pe.getAmplifier(), maxLevel - 1);
        }

        // 先移除再施加, 避免同 amplifier 时原版 combine 不刷新持续时间
        target.removePotionEffect(Potions.staggering);
        target.addPotionEffect(new PotionEffect(Potions.staggering, duration, amplifier));
    }

    @Override
    public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit)
    {
        if (!wasHit) return;
        applyStaggering(target);
    }
}
