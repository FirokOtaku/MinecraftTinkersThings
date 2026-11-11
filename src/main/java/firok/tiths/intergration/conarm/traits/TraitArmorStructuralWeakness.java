package firok.tiths.intergration.conarm.traits;

import c4.conarm.lib.armor.ArmorModifications;
import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import slimeknights.tconstruct.library.utils.ToolHelper;

import static firok.tiths.common.Keys.colorTraitStructuralWeakness;
import static firok.tiths.common.Keys.nameTraitStructuralWeakness;

/**
 * 结构弱点 - 护甲
 * 当护甲耐久低于一半时, 提供的护甲值和护甲韧性值减半
 */
public class TraitArmorStructuralWeakness extends AbstractArmorTrait
{
    public TraitArmorStructuralWeakness()
    {
        super(nameTraitStructuralWeakness, colorTraitStructuralWeakness);
    }

    @Override
    public ArmorModifications getModifications(EntityPlayer player, ArmorModifications mods, ItemStack armor, DamageSource source, double damage, int slot)
    {
        int current = ToolHelper.getCurrentDurability(armor);
        int max = ToolHelper.getMaxDurability(armor);
        if (max > 0 && current * 2 < max)
        {
            mods.addArmorMod(-0.5f);
            mods.addToughnessMod(-0.5f);
        }
        return mods;
    }
}
