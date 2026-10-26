package firok.tiths.intergration.conarm.traits;

import c4.conarm.common.armor.utils.ArmorHelper;
import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import static firok.tiths.common.Keys.colorTraitHemolytic;
import static firok.tiths.common.Keys.nameTraitHemolytic;

/**
 * 溶血 - 护甲
 */
public class TraitArmorHemolytic extends AbstractArmorTrait
{
    public TraitArmorHemolytic()
    {
        super(nameTraitHemolytic, colorTraitHemolytic);
    }

    @Override
    public float onHurt(ItemStack armor, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingHurtEvent evt)
    {
        if (!player.world.isRemote && newDamage > 0)
        {
            ArmorHelper.healArmor(armor, (int) (newDamage / 5), player, 0);
        }
        return newDamage;
    }
}
