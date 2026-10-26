package firok.tiths.potion;

import net.minecraft.entity.SharedMonsterAttributes;

import static firok.tiths.common.Keys.colorPotionEmpathic;
import static firok.tiths.common.Keys.uuidPotionEmpathicDamage;

public class PotionEmpathic extends BasePotion
{
    public PotionEmpathic()
    {
        super(icon("empathic"), true, colorPotionEmpathic);
    }

    /**
     * @see firok.tiths.common.Potions
     */
    @Override
    public void postInit()
    {
        registerPotionAttributeModifier(
                SharedMonsterAttributes.ATTACK_DAMAGE,
                uuidPotionEmpathicDamage,
                0.4D,
                2
        )
                .setBeneficial();
    }
}
