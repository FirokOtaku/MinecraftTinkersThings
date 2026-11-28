package firok.tiths.item;

import firok.tiths.common.Potions;
import net.minecraft.block.Block;
import net.minecraft.potion.PotionEffect;

public class ItemBlockHothium extends ItemBlockInventoryEffector
{
    public ItemBlockHothium(Block block)
    {
        super(block, new PotionEffect(Potions.extreme_freezing, 40, 0));
    }
}
