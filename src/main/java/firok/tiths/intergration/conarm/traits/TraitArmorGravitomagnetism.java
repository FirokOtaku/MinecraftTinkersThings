package firok.tiths.intergration.conarm.traits;

import c4.conarm.lib.traits.AbstractArmorTrait;
import firok.tiths.traits.TraitGravitomagnetism;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import static firok.tiths.common.Keys.colorTraitGravitomagnetism;
import static firok.tiths.common.Keys.nameTraitGravitomagnetism;
import static firok.tiths.util.Predicates.canDealWith;

/**
 * 引力磁场 - 护甲
 * <p>
 * 被实体攻击时, 对攻击者叠加踉跄 (逻辑与工具词条相同).
 * </p>
 *
 * @see TraitGravitomagnetism
 */
public class TraitArmorGravitomagnetism extends AbstractArmorTrait
{
    public TraitArmorGravitomagnetism()
    {
        super(nameTraitGravitomagnetism, colorTraitGravitomagnetism);
    }

    @Override
    public float onHurt(ItemStack armor, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingHurtEvent evt)
    {
        if (!(source instanceof EntityDamageSource)) return newDamage;
        if (!canDealWith(source, true, null, null, null, false)) return newDamage;
        if (player.world.isRemote) return newDamage;

        Entity entity = ((EntityDamageSource) source).getTrueSource();
        if (entity instanceof EntityLivingBase)
        {
            TraitGravitomagnetism.applyStaggering((EntityLivingBase) entity);
        }
        return newDamage;
    }
}
