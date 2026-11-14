package firok.tiths.gen;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 品级生成器全部禁用时的回退: 随机出若干原版道具.
 */
public final class VanillaLootFallback
{
    private static final Item[] POOL = {
            Items.IRON_INGOT,
            Items.GOLD_INGOT,
            Items.COAL,
            Items.REDSTONE,
            Items.QUARTZ,
            Items.IRON_PICKAXE,
            Items.IRON_SWORD,
            Items.BOW,
            Items.ARROW,
            Items.BREAD,
            Items.APPLE,
            Items.STICK,
            Items.STRING,
            Items.LEATHER,
            Items.FLINT,
    };

    private VanillaLootFallback() {}

    public static List<ItemStack> roll(Random random)
    {
        return roll(random, 1 + random.nextInt(3));
    }

    public static List<ItemStack> roll(Random random, int count)
    {
        List<ItemStack> out = new ArrayList<>(count);
        for (int i = 0; i < count; i++)
        {
            Item item = POOL[random.nextInt(POOL.length)];
            int size = 1;
            if (item == Items.ARROW || item == Items.COAL || item == Items.REDSTONE || item == Items.STICK)
            {
                size = 2 + random.nextInt(6);
            }
            out.add(new ItemStack(item, size));
        }
        return out;
    }

    /** 单件回退, 供袋子等只需一件产出的场景. */
    public static ItemStack rollOne(Random random)
    {
        List<ItemStack> list = roll(random, 1);
        return list.isEmpty() ? ItemStack.EMPTY : list.get(0);
    }
}
