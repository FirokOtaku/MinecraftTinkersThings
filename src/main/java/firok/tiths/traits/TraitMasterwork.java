package firok.tiths.traits;

import firok.tiths.util.InnerActions;
import net.minecraft.nbt.NBTTagCompound;
import slimeknights.tconstruct.library.tools.ToolNBT;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.TagUtil;

import static firok.tiths.common.Keys.colorTraitMasterwork;
import static firok.tiths.common.Keys.nameTraitMasterwork;

/**
 * 占位渠道词条: 大师之作.
 * <p>
 * 验证词条道具注册 / 染色 / SingleAspect 应用管线; 正式效果待 special-traits 定稿后替换.
 * AbstractTrait 默认已带 SingleAspect (大多一次).
 */
public class TraitMasterwork extends AbstractTrait
{
    public TraitMasterwork()
    {
        super(nameTraitMasterwork, colorTraitMasterwork);
    }

    @Override
    public void applyEffect(NBTTagCompound rootCompound, NBTTagCompound modifierTag)
    {
        if (InnerActions.addTrait(this, rootCompound, modifierTag)) return;

        ToolNBT data = TagUtil.getToolStats(rootCompound);
        data.attack += 1.0f;
        data.durability = Math.max(1, (int) (data.durability * 1.05f));
        TagUtil.setToolTag(rootCompound, data.get());
    }
}
