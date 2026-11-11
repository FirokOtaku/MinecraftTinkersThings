package firok.tiths.intergration.conarm.traits;

import c4.conarm.lib.armor.ArmorModifications;
import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

import static firok.tiths.common.Keys.colorTraitHardMetal;
import static firok.tiths.common.Keys.nameTraitHardMetal;

/**
 * 硬金属 - 护甲
 * 提供的护甲韧性值降低 25%, 护甲值提高 25%
 */
public class TraitArmorHardMetal extends AbstractArmorTrait
{
    public TraitArmorHardMetal()
    {
        super(nameTraitHardMetal, colorTraitHardMetal);
    }

    @Override
    public ArmorModifications getModifications(EntityPlayer player, ArmorModifications mods, ItemStack armor, DamageSource source, double damage, int slot)
    {
        mods.addArmorMod(0.25f);
        mods.addToughnessMod(-0.25f);
        return mods;
    }
}
