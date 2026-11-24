package firok.tiths.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class BlockActivatedClay extends Block
{
    public BlockActivatedClay()
    {
        super(Material.CLAY, MapColor.GREEN);
        setHardness(0.6F);
        setSoundType(SoundType.GROUND);
    }
}
