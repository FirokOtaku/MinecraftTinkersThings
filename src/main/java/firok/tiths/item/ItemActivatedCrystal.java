package firok.tiths.item;

import firok.tiths.common.Blocks;
import net.minecraft.block.BlockFarmland;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemActivatedCrystal extends ItemCustom
{
    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand,
                                      EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        IBlockState original = world.getBlockState(pos);
        if (original.getBlock() != net.minecraft.init.Blocks.FARMLAND) return EnumActionResult.PASS;

        ItemStack stack = player.getHeldItem(hand);
        if (!world.isBlockModifiable(player, pos) || !player.canPlayerEdit(pos, facing, stack))
        {
            return EnumActionResult.FAIL;
        }
        if (world.isRemote) return EnumActionResult.SUCCESS;

        int moisture = original.getValue(BlockFarmland.MOISTURE);
        IBlockState activated = Blocks.blockActivatedFarmland.getDefaultState().withProperty(BlockFarmland.MOISTURE, moisture);
        boolean changed = world.setBlockState(pos, activated, 3);
        if (changed && !player.capabilities.isCreativeMode) stack.shrink(1);
        return changed ? EnumActionResult.SUCCESS : EnumActionResult.FAIL;
    }
}
