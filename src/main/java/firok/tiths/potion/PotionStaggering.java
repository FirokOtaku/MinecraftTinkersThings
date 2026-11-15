package firok.tiths.potion;

import static firok.tiths.common.Keys.colorPotionStaggering;

/**
 * 踉跄
 * <p>
 * 状态效果自身无 tick 效果;
 * 带有此 debuff 的生物受到摔落伤害 ({@code DamageSource} 类型为 {@code fall}) 时,
 * 摔落伤害 ×2, 并降低一层本效果等级.
 * </p>
 *
 * @see firok.tiths.common.Events#onStaggeringFall
 */
public class PotionStaggering extends BasePotion
{
    public PotionStaggering()
    {
        super(icon("staggering"), true, colorPotionStaggering);
    }
}
