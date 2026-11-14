package firok.tiths.gen;

import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import firok.tiths.common.Items;
import firok.tiths.item.ItemLootBag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootEntry;
import net.minecraft.world.storage.loot.conditions.LootCondition;

import java.util.Collection;
import java.util.Random;

/**
 * 宝箱注入条目: 小概率袋子或直接成品, 品级偏向 LOW/MID.
 */
public class LootEntryRandomGear extends LootEntry
{
    public LootEntryRandomGear(int weight, int quality, LootCondition[] conditions, String entryName)
    {
        super(weight, quality, conditions, entryName);
    }

    @Override
    public void addLoot(Collection<ItemStack> stacks, Random rand, LootContext context)
    {
        ToolTier tier = rollTier(rand);
        // 65% 袋子, 35% 直接成品
        if (rand.nextFloat() < 0.65f)
        {
            Item bag = pickBagItem(tier, rand);
            if (bag != null)
            {
                stacks.add(new ItemStack(bag));
            }
            return;
        }

        EquipKind kind = rollEquipKind(rand);
        GenResult result = TinkerEquipmentGenerator.generate(GenContext.LOOT, tier, kind, rand);
        stacks.addAll(result.getStacks());
    }

    private static ToolTier rollTier(Random rand)
    {
        int roll = rand.nextInt(100);
        if (roll < 45) return ToolTier.LOW;
        if (roll < 75) return ToolTier.MID;
        if (roll < 92) return ToolTier.HIGH;
        return ToolTier.TOP;
    }

    private static EquipKind rollEquipKind(Random rand)
    {
        int roll = rand.nextInt(100);
        if (roll < 45) return EquipKind.TOOL;
        if (roll < 80) return EquipKind.WEAPON;
        return EquipKind.ARMOR;
    }

    private static Item pickBagItem(ToolTier tier, Random rand)
    {
        BagType type;
        int roll = rand.nextInt(100);
        if (roll < 35) type = BagType.TOOL;
        else if (roll < 60) type = BagType.WEAPON;
        else if (roll < 80) type = BagType.TINKER;
        else type = BagType.ARMOR;

        Item bag = Items.getLootBag(type, tier);
        if (bag instanceof ItemLootBag)
        {
            return bag;
        }
        return Items.getLootBag(BagType.TOOL, tier);
    }

    @Override
    protected void serialize(JsonObject json, JsonSerializationContext context)
    {
        // 仅运行时注入, 不写回 JSON
    }
}
