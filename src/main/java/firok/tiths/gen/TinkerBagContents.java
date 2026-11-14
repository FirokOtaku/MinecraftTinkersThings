package firok.tiths.gen;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 匠人袋内容表: 匠魂相关物资与强化用原版物.
 */
public final class TinkerBagContents
{
    private TinkerBagContents() {}

    public static List<ItemStack> roll(ToolTier tier, Random random)
    {
        int rolls = baseRolls(tier) + random.nextInt(2);
        List<ItemStack> out = new ArrayList<>(rolls);
        for (int i = 0; i < rolls; i++)
        {
            out.add(rollOne(tier, random));
        }
        return out;
    }

    private static int baseRolls(ToolTier tier)
    {
        switch (tier)
        {
            case LOW:
                return 2;
            case MID:
                return 3;
            case HIGH:
                return 4;
            case TOP:
                return 5;
            default:
                return 2;
        }
    }

    private static ItemStack rollOne(ToolTier tier, Random random)
    {
        int pick = random.nextInt(100);
        if (pick < 18)
        {
            return sized(Items.REDSTONE, 2 + random.nextInt(tierOrdinal(tier) + 3));
        }
        if (pick < 30)
        {
            return sized(Items.QUARTZ, 1 + random.nextInt(tierOrdinal(tier) + 2));
        }
        if (pick < 40)
        {
            return sized(Items.DYE, 2 + random.nextInt(4), 4); // lapis
        }
        if (pick < 50)
        {
            return sized(Items.SLIME_BALL, 1 + random.nextInt(3));
        }
        if (pick < 58)
        {
            return oreOrFallback("slimeball", Items.SLIME_BALL, 1 + random.nextInt(2));
        }
        if (pick < 68)
        {
            return sized(Items.CLAY_BALL, 2 + random.nextInt(4));
        }
        if (pick < 76)
        {
            return sized(Item.getItemFromBlock(Blocks.COBBLESTONE), 4 + random.nextInt(8));
        }
        if (pick < 84)
        {
            return sized(Items.IRON_NUGGET, 2 + random.nextInt(6));
        }
        if (pick < 90)
        {
            return sized(Items.GOLD_NUGGET, 1 + random.nextInt(4));
        }
        if (pick < 95)
        {
            return sized(Items.BLAZE_POWDER, 1 + random.nextInt(2));
        }
        // 高品级略偏强化材料
        if (tier.ordinal() >= ToolTier.HIGH.ordinal() && random.nextBoolean())
        {
            return sized(Items.DIAMOND, 1);
        }
        return sized(Items.EMERALD, 1);
    }

    private static int tierOrdinal(ToolTier tier)
    {
        return tier == null ? 0 : tier.ordinal();
    }

    private static ItemStack sized(Item item, int count)
    {
        return new ItemStack(item, Math.max(1, count));
    }

    private static ItemStack sized(Item item, int count, int meta)
    {
        return new ItemStack(item, Math.max(1, count), meta);
    }

    private static ItemStack oreOrFallback(String ore, Item fallback, int count)
    {
        List<ItemStack> ores = OreDictionary.getOres(ore);
        if (ores != null && !ores.isEmpty())
        {
            ItemStack sample = ores.get(0).copy();
            sample.setCount(Math.max(1, count));
            return sample;
        }
        return sized(fallback, count);
    }
}
