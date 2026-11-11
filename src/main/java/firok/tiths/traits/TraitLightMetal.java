package firok.tiths.traits;

import firok.tiths.util.InnerActions;
import net.minecraft.nbt.NBTTagCompound;
import slimeknights.tconstruct.library.tools.ToolNBT;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.TagUtil;

import static firok.tiths.common.Keys.colorTraitLightMetal;
import static firok.tiths.common.Keys.nameTraitLightMetal;

/**
 * 轻金属
 * 威力降低 25%, 攻速提高 25%
 */
public class TraitLightMetal extends AbstractTrait
{
    public TraitLightMetal()
    {
        super(nameTraitLightMetal, colorTraitLightMetal);
    }

    @Override
    public void applyEffect(NBTTagCompound rootCompound, NBTTagCompound modifierTag)
    {
        if (InnerActions.addTrait(this, rootCompound, modifierTag)) return;

        ToolNBT data = TagUtil.getToolStats(rootCompound);
        data.attack *= 0.75f;
        data.attackSpeedMultiplier *= 1.25f;
        TagUtil.setToolTag(rootCompound, data.get());
    }
}
