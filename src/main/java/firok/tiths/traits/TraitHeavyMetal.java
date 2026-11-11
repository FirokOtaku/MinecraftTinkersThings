package firok.tiths.traits;

import firok.tiths.util.InnerActions;
import net.minecraft.nbt.NBTTagCompound;
import slimeknights.tconstruct.library.tools.ToolNBT;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.TagUtil;

import static firok.tiths.common.Keys.colorTraitHeavyMetal;
import static firok.tiths.common.Keys.nameTraitHeavyMetal;

/**
 * 重金属
 * 威力提高 25%, 攻速降低 25%
 */
public class TraitHeavyMetal extends AbstractTrait
{
    public TraitHeavyMetal()
    {
        super(nameTraitHeavyMetal, colorTraitHeavyMetal);
    }

    @Override
    public void applyEffect(NBTTagCompound rootCompound, NBTTagCompound modifierTag)
    {
        if (InnerActions.addTrait(this, rootCompound, modifierTag)) return;

        ToolNBT data = TagUtil.getToolStats(rootCompound);
        data.attack *= 1.25f;
        data.attackSpeedMultiplier *= 0.75f;
        TagUtil.setToolTag(rootCompound, data.get());
    }
}
