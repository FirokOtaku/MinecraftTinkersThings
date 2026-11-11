package firok.tiths.intergration.conarm.traits;

import c4.conarm.common.armor.utils.ArmorHelper;
import c4.conarm.lib.traits.AbstractArmorTrait;
import firok.tiths.common.DamageSources;
import firok.tiths.util.EntityFinders;
import firok.tiths.util.Selectors;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import slimeknights.tconstruct.library.utils.ToolHelper;

import java.util.List;

import static firok.tiths.common.DamageSources.TypeReactiveExplosion;
import static firok.tiths.common.Keys.colorTraitReactiveArmor;
import static firok.tiths.common.Keys.nameTraitReactiveArmor;

/**
 * 反应装甲 - 护甲
 * 受到爆炸伤害时损失 2 点耐久并免疫本次伤害;
 * 每件带有此特性的护甲对周围生物造成 25% 所受爆炸伤害的反应爆炸伤害
 */
public class TraitArmorReactiveArmor extends AbstractArmorTrait
{
    private static final int DURABILITY_COST = 2;
    private static final float DAMAGE_FACTOR_PER_PIECE = 0.25f;
    private static final double RANGE = 5;

    public TraitArmorReactiveArmor()
    {
        super(nameTraitReactiveArmor, colorTraitReactiveArmor);
    }

    @Override
    public float onHurt(ItemStack armor, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingHurtEvent evt)
    {
        if (player.world.isRemote) return newDamage;
        if (evt.isCanceled()) return 0;
        if (!source.isExplosion() || TypeReactiveExplosion.equals(source.getDamageType())) return newDamage;
        if (TypeReactiveExplosion.equals(source.getDamageType())) return newDamage;
        if (damage <= 0) return newDamage;

        int pieceCount = 0;
        for (ItemStack stack : player.getArmorInventoryList())
        {
            if (!stack.isEmpty() && !ToolHelper.isBroken(stack) && ToolHelper.getTraits(stack).contains(this))
            {
                pieceCount++;
            }
        }
        if (pieceCount <= 0) return newDamage;

        ArmorHelper.damageArmor(armor, source, DURABILITY_COST, player);

        float reflectDamage = damage * DAMAGE_FACTOR_PER_PIECE * pieceCount;
        if (reflectDamage > 0)
        {
            List<EntityLivingBase> entities = (List) EntityFinders.Nearby(player, RANGE, Selectors.livingBaseAlive);
            for (EntityLivingBase entity : entities)
            {
                entity.hurtResistantTime = 0;
                entity.attackEntityFrom(DamageSources.ReactiveExplosion(player), reflectDamage);
            }
        }

        evt.setCanceled(true);
        return 0;
    }
}
