package firok.tiths.intergration.conarm.traits;

import c4.conarm.common.armor.utils.ArmorHelper;
import c4.conarm.lib.traits.AbstractArmorTrait;
import firok.tiths.common.Configs;
import firok.tiths.common.Potions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import static firok.tiths.common.Keys.colorTraitHotMeltMaterial;
import static firok.tiths.common.Keys.nameTraitHotMeltMaterial;

/**
 * 热熔材料 - 护甲
 * <p>
 * 受到着火攻击者伤害时额外消耗 2 点耐久并给予攻击者热熔胶状态效果.
 * </p>
 *
 * @see Configs.Traits#duration_hot_melt_material
 */
public class TraitArmorHotMeltMaterial extends AbstractArmorTrait
{
    public TraitArmorHotMeltMaterial()
    {
        super(nameTraitHotMeltMaterial, colorTraitHotMeltMaterial);
    }

    @Override
    public float onHurt(ItemStack armor, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingHurtEvent evt)
    {
        if (player.world.isRemote || !(source instanceof EntityDamageSource)) return newDamage;

        Entity entity = ((EntityDamageSource) source).getTrueSource();
        if (!(entity instanceof EntityLivingBase)) return newDamage;

        EntityLivingBase attacker = (EntityLivingBase) entity;
        if (!attacker.isEntityAlive() || !attacker.isBurning()) return newDamage;

        ArmorHelper.damageArmor(armor, source, 2, player);
        int duration = Math.max(1, Configs.Traits.duration_hot_melt_material) * 20;
        attacker.addPotionEffect(new PotionEffect(Potions.hot_glued, duration, 0));

        return newDamage;
    }
}
