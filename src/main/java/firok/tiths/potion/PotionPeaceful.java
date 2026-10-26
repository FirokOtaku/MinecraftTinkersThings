package firok.tiths.potion;

import firok.tiths.common.Keys;
import firok.tiths.util.Predicates;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;

import static firok.tiths.common.Keys.uuidPotionPeacefulDamage;

// 平和
public class PotionPeaceful extends BasePotion
{
    public PotionPeaceful()
    {
        super(icon("peaceful"), true, Keys.colorPotionPeaceful);
    }

    @Override
    public boolean isReady(int tick, int level)
    {
        return true;
    }

    @Override
    public void performEffect(EntityLivingBase entity, int level)
    {
        if (entity.world.isRemote) return;
        if (!Predicates.isEnemies(entity)) return;

        // 药水效果在 AI 之前结算, 这里清一次; 同 tick 内 AI 重新索敌由事件再拦
        if (Predicates.hasAttackTarget(entity))
        {
            ((EntityLiving) entity).setAttackTarget(null);
        }
        if (Predicates.hasRevengeTarget(entity))
        {
            entity.setRevengeTarget(null);
        }
    }

    @Override
    public void postInit()
    {
        // operation 2: 最终值 *= (1 + amount) → -20% 攻击伤害
        registerPotionAttributeModifier(
                SharedMonsterAttributes.ATTACK_DAMAGE,
                uuidPotionPeacefulDamage,
                -0.2,
                2
        );
    }
}
