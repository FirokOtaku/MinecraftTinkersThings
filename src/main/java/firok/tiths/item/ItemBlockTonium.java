package firok.tiths.item;

import firok.tiths.common.Potions;
import net.minecraft.block.Block;
import net.minecraft.potion.PotionEffect;

public class ItemBlockTonium extends ItemBlockInventoryEffector
{
    public ItemBlockTonium(Block block)
    {
        super(block, new PotionEffect(Potions.heavy, 80, 0));
    }
}
