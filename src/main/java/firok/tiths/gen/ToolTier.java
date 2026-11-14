package firok.tiths.gen;

import firok.tiths.common.Configs;
import net.minecraft.item.EnumRarity;

import javax.annotation.Nullable;

/**
 * 随机装备生成品级. 袋子稀有度映射: LOW→Common / MID→Uncommon / HIGH→Rare / TOP→Epic.
 */
public enum ToolTier
{
    LOW(EnumRarity.COMMON),
    MID(EnumRarity.UNCOMMON),
    HIGH(EnumRarity.RARE),
    TOP(EnumRarity.EPIC),
    ;

    private final EnumRarity bagRarity;

    ToolTier(EnumRarity bagRarity)
    {
        this.bagRarity = bagRarity;
    }

    public EnumRarity getBagRarity()
    {
        return bagRarity;
    }

    /** 稀有度染色用 RGB (不透明), 对齐 Common/Uncommon/Rare/Epic 观感. */
    public int getRarityTintColor()
    {
        switch (this)
        {
            case LOW:
                return 0xC6C6C6; // common gray
            case MID:
                return 0xFFFF55; // uncommon yellow
            case HIGH:
                return 0x55FFFF; // rare aqua
            case TOP:
                return 0xFF55FF; // epic magenta
            default:
                return 0xFFFFFF;
        }
    }

    public boolean isEnabled()
    {
        switch (this)
        {
            case LOW:
                return Configs.Gameplay.enable_tier_low;
            case MID:
                return Configs.Gameplay.enable_tier_mid;
            case HIGH:
                return Configs.Gameplay.enable_tier_high;
            case TOP:
                return Configs.Gameplay.enable_tier_top;
            default:
                return false;
        }
    }

    public GenMode getGenMode()
    {
        String raw;
        switch (this)
        {
            case LOW:
                raw = Configs.Gameplay.gen_mode_low;
                break;
            case MID:
                raw = Configs.Gameplay.gen_mode_mid;
                break;
            case HIGH:
                raw = Configs.Gameplay.gen_mode_high;
                break;
            case TOP:
                raw = Configs.Gameplay.gen_mode_top;
                break;
            default:
                raw = "default";
        }
        return GenMode.fromConfig(raw);
    }

    public String[] getCustomMaterialIds()
    {
        switch (this)
        {
            case LOW:
                return Configs.Gameplay.custom_materials_low;
            case MID:
                return Configs.Gameplay.custom_materials_mid;
            case HIGH:
                return Configs.Gameplay.custom_materials_high;
            case TOP:
                return Configs.Gameplay.custom_materials_top;
            default:
                return new String[0];
        }
    }

    /**
     * DEFAULT 模式下材料头部挖掘等级是否属于本品级.
     * 无 HEAD 属性的材料不参与品级判定, 由调用方另行纳入辅助槽位池.
     */
    public boolean matchesHeadHarvestLevel(int harvestLevel)
    {
        switch (this)
        {
            case LOW:
                return harvestLevel <= Configs.Gameplay.tier_hl_low_max;
            case MID:
                return harvestLevel <= Configs.Gameplay.tier_hl_mid_max;
            case HIGH:
                return harvestLevel <= Configs.Gameplay.tier_hl_high_max;
            case TOP:
                return harvestLevel >= Configs.Gameplay.tier_hl_top_min;
            default:
                return false;
        }
    }

    /** 禁用回退时向下一级 (TOP→HIGH→MID→LOW). */
    @Nullable
    public ToolTier lower()
    {
        switch (this)
        {
            case TOP:
                return HIGH;
            case HIGH:
                return MID;
            case MID:
                return LOW;
            case LOW:
            default:
                return null;
        }
    }

    @Nullable
    public static ToolTier fromBagRarity(EnumRarity rarity)
    {
        if (rarity == null) return null;
        for (ToolTier tier : values())
        {
            if (tier.bagRarity == rarity) return tier;
        }
        return null;
    }
}
