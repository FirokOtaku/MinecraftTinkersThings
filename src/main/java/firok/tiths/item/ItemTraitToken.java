package firok.tiths.item;

import firok.tiths.common.TithsRarity;
import firok.tiths.util.InnerActions;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.traits.ITrait;

import javax.annotation.Nullable;
import java.util.List;

/**
 * 渠道词条强化道具: 共用灰度贴图, 按绑定词条颜色染色; CreativeOnly.
 */
public class ItemTraitToken extends ItemCustom
{
    private final String traitId;
    private final int tintColor;

    public ItemTraitToken(String traitId, int tintColor)
    {
        super();
        this.traitId = traitId;
        this.tintColor = tintColor & 0xFFFFFF;
        this.setMaxStackSize(16);
        this.setRarity(TithsRarity.CreativeOnly);
    }

    public String getTraitId()
    {
        return traitId;
    }

    public int getTintColor()
    {
        return tintColor;
    }

    @Nullable
    public ITrait getTrait()
    {
        return TinkerRegistry.getTrait(traitId);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World worldIn, List<String> list, ITooltipFlag flagIn)
    {
        InnerActions.addInformation(this, list, flagIn);
    }
}
