package firok.tiths.item;

import firok.tiths.util.InnerActions;
import firok.tiths.util.PalantirData;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

/**
 * 真知晶球物品:
 * <ul>
 *   <li>潜行右键方块: 标记坐标与维度</li>
 *   <li>潜行右键空气: 清空标记</li>
 *   <li>非潜行: 放置方块并写入 NBT</li>
 * </ul>
 */
public class ItemPalantir extends ItemBlock
{
    public ItemPalantir(Block block)
    {
        super(block);
        this.setMaxStackSize(1);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand,
                                      EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        ItemStack stack = player.getHeldItem(hand);
        if (player.isSneaking())
        {
            if (!world.isRemote)
            {
                PalantirData.setTarget(stack, world, pos);
                player.sendStatusMessage(new TextComponentTranslation(
                        "item.tiths.block_palantir.marked",
                        pos.getX(), pos.getY(), pos.getZ(), world.provider.getDimension()
                ), true);
            }
            return EnumActionResult.SUCCESS;
        }
        // 手持晶球右键已放置的晶球时, 交给方块 onBlockActivated 触发窥视
        if (world.getBlockState(pos).getBlock() == this.block)
        {
            return EnumActionResult.PASS;
        }
        return super.onItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        ItemStack stack = player.getHeldItem(hand);
        if (!player.isSneaking())
        {
            return new ActionResult<>(EnumActionResult.PASS, stack);
        }

        RayTraceResult hit = this.rayTrace(world, player, false);
        if (hit != null && hit.typeOfHit == RayTraceResult.Type.BLOCK)
        {
            // 潜行对准方块时由 onItemUse 处理
            return new ActionResult<>(EnumActionResult.PASS, stack);
        }

        if (!world.isRemote)
        {
            PalantirData.clearTarget(stack);
            player.sendStatusMessage(new TextComponentTranslation("item.tiths.block_palantir.cleared"), true);
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag)
    {
        InnerActions.addInformation(this, tooltip, flag);
        appendTargetTooltip(stack, tooltip);
    }

    @SideOnly(Side.CLIENT)
    public static void appendTargetTooltip(ItemStack stack, List<String> tooltip)
    {
        if (PalantirData.hasTarget(stack))
        {
            BlockPos pos = PalantirData.getPos(stack);
            int dim = PalantirData.getDim(stack);
            tooltip.add(net.minecraft.client.resources.I18n.format(
                    "item.tiths.block_palantir.target",
                    pos.getX(), pos.getY(), pos.getZ(), dim
            ));
        }
        else
        {
            tooltip.add(net.minecraft.client.resources.I18n.format("item.tiths.block_palantir.no_target"));
        }
    }
}
