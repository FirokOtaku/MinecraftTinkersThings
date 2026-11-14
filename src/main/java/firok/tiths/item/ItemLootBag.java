package firok.tiths.item;

import firok.tiths.TinkersThings;
import firok.tiths.gen.BagType;
import firok.tiths.gen.EquipKind;
import firok.tiths.gen.GenContext;
import firok.tiths.gen.GenResult;
import firok.tiths.gen.TinkerBagContents;
import firok.tiths.gen.TinkerEquipmentGenerator;
import firok.tiths.gen.ToolTier;
import firok.tiths.util.InnerActions;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

/**
 * 分级掉落袋: 右键打开生成成品或匠人物资.
 */
public class ItemLootBag extends ItemCustom
{
    private final BagType bagType;
    private final ToolTier tier;

    public ItemLootBag(BagType bagType, ToolTier tier)
    {
        super();
        this.bagType = bagType;
        this.tier = tier;
        this.setMaxStackSize(16);
        this.setRarity(tier.getBagRarity());
    }

    public BagType getBagType()
    {
        return bagType;
    }

    public ToolTier getTier()
    {
        return tier;
    }

    public EnumRarity getBagRarity()
    {
        return tier.getBagRarity();
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        ItemStack stack = player.getHeldItem(hand);
        if (!world.isRemote)
        {
            openBag(world, player, stack);
        }
        else
        {
            BlockPos pos = player.getPosition();
            world.playSound(player, pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.6f, 1.1f);
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }

    private void openBag(World world, EntityPlayer player, ItemStack stack)
    {
        if (bagType == BagType.TINKER)
        {
            for (ItemStack drop : TinkerBagContents.roll(tier, world.rand))
            {
                giveOrDrop(player, drop);
            }
        }
        else
        {
            EquipKind kind = toEquipKind();
            GenResult result = TinkerEquipmentGenerator.generate(GenContext.LOOT, tier, kind, world.rand);
            for (ItemStack drop : result.getStacks())
            {
                giveOrDrop(player, drop);
            }
        }

        if (!player.isCreative())
        {
            stack.shrink(1);
        }
    }

    private EquipKind toEquipKind()
    {
        switch (bagType)
        {
            case WEAPON:
                return EquipKind.WEAPON;
            case ARMOR:
                return TinkersThings.enableConarm() ? EquipKind.ARMOR : EquipKind.TOOL;
            case TOOL:
            default:
                return EquipKind.TOOL;
        }
    }

    private static void giveOrDrop(EntityPlayer player, ItemStack drop)
    {
        if (drop == null || drop.isEmpty()) return;
        if (!player.inventory.addItemStackToInventory(drop))
        {
            player.dropItem(drop, false);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World worldIn, List<String> list, ITooltipFlag flagIn)
    {
        InnerActions.addInformation(this, list, flagIn);
    }
}
