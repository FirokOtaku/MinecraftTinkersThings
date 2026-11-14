package firok.tiths.gen;

import firok.tiths.common.Configs;

/**
 * 随机生成上下文: 影响隐藏材料进入候选池的概率等.
 */
public enum GenContext
{
    VILLAGER,
    LOOT,
    ;

    public double getHiddenMaterialChance()
    {
        switch (this)
        {
            case VILLAGER:
                return Configs.Gameplay.hidden_material_chance_villager;
            case LOOT:
                return Configs.Gameplay.hidden_material_chance_loot;
            default:
                return 0;
        }
    }
}
