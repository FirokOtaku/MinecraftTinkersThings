package firok.tiths.block;

import net.minecraft.block.BlockPumpkin;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockActivatedPumpkin extends BlockPumpkin
{
    public BlockActivatedPumpkin()
    {
        setHardness(1.0F);
        setSoundType(SoundType.WOOD);
        setLightLevel(8.0F / 15.0F);
    }

    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state)
    {
        // BlockPumpkin would check vanilla golem patterns here. Clay golem summoning is handled separately.
    }

    @Override
    public boolean canDispenserPlace(World world, BlockPos pos)
    {
        // Do not expose the vanilla snow and iron golem patterns for this pumpkin.
        return false;
    }
}
