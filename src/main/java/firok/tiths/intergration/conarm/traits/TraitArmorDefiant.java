package firok.tiths.intergration.conarm.traits;

import c4.conarm.lib.armor.ArmorModifications;
import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;

import static firok.tiths.common.Keys.colorTraitDefiant;
import static firok.tiths.common.Keys.nameTraitDefiant;

/**
 * 反抗 - 护甲
 * 穿戴者身上每具有一种 debuff, 护甲提供的护甲值和护甲韧性值提高 10%
 */
public class TraitArmorDefiant extends AbstractArmorTrait
{
    public TraitArmorDefiant()
    {
        super(nameTraitDefiant, colorTraitDefiant);
    }

    @Override
    public ArmorModifications getModifications(EntityPlayer player, ArmorModifications mods, ItemStack armor, DamageSource source, double damage, int slot)
    {
        int debuffCount = 0;
        for (PotionEffect effect : player.getActivePotionEffects())
        {
            if (effect.getPotion().isBadEffect())
            {
                debuffCount++;
            }
        }
        if (debuffCount > 0)
        {
            float bonus = debuffCount * 0.1f;
            mods.addArmorMod(bonus);
            mods.addToughnessMod(bonus);
        }
        return mods;
    }
}
