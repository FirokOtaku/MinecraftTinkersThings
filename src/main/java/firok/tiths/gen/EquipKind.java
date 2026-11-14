package firok.tiths.gen;

import firok.tiths.common.Configs;

/**
 * 生成成品类型: 工具 / 武器 / 护甲.
 */
public enum EquipKind
{
    TOOL,
    WEAPON,
    ARMOR,
    ;

    public String[] getBonusModifierIds()
    {
        switch (this)
        {
            case TOOL:
                return Configs.Gameplay.bonus_modifier_ids_tool;
            case WEAPON:
                return Configs.Gameplay.bonus_modifier_ids_weapon;
            case ARMOR:
                return Configs.Gameplay.bonus_modifier_ids_armor;
            default:
                return new String[0];
        }
    }
}
