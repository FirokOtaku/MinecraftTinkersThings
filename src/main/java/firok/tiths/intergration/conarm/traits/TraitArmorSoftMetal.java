package firok.tiths.intergration.conarm.traits;

import c4.conarm.lib.armor.ArmorModifications;
import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

import static firok.tiths.common.Keys.colorTraitSoftMetal;
import static firok.tiths.common.Keys.nameTraitSoftMetal;

/**
 * 软金属 - 护甲
 * 提供的护甲韧性值提升 25%, 护甲值降低 25%
 */
public class TraitArmorSoftMetal extends AbstractArmorTrait
{
    public TraitArmorSoftMetal()
    {
        super(nameTraitSoftMetal, colorTraitSoftMetal);
    }

    @Override
    public ArmorModifications getModifications(EntityPlayer player, ArmorModifications mods, ItemStack armor, DamageSource source, double damage, int slot)
    {
        mods.addArmorMod(-0.25f);
        mods.addToughnessMod(0.25f);
        return mods;
    }
}
