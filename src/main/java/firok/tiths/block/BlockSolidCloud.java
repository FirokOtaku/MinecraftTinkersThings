package firok.tiths.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockSolidCloud extends Block
{
    public BlockSolidCloud()
    {
        super(Material.CLOTH);
        setHardness(0.6F);
        setLightOpacity(255);
        setSoundType(SoundType.CLOTH);
    }
}
