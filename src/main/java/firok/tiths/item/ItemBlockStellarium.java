package firok.tiths.item;

import firok.tiths.traits.TraitRadiant;
import net.minecraft.block.Block;

public class ItemBlockStellarium extends ItemBlockInventoryEffector
{
    public ItemBlockStellarium(Block block)
    {
        super(block, carrier -> TraitRadiant.ignite(carrier, 2));
    }
}
