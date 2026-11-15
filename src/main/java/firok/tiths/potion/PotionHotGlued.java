package firok.tiths.potion;

import net.minecraft.entity.SharedMonsterAttributes;

import static firok.tiths.common.Keys.colorPotionHotGlued;
import static firok.tiths.common.Keys.uuidPotionHotGluedSpeed;

/**
 * 热熔胶
 * <p>
 * 降低 30% 移动速度.
 * </p>
 */
public class PotionHotGlued extends BasePotion
{
    public PotionHotGlued()
    {
        super(icon("hot_glued"), true, colorPotionHotGlued);
    }

    @Override
    public void postInit()
    {
        registerPotionAttributeModifier(
                SharedMonsterAttributes.MOVEMENT_SPEED,
                uuidPotionHotGluedSpeed,
                -0.3,
                2
        );
    }
}
