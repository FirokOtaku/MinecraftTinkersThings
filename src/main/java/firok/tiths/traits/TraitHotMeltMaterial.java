package firok.tiths.traits;

import firok.tiths.common.Configs;
import firok.tiths.common.Potions;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.ToolHelper;

import static firok.tiths.common.Keys.colorTraitHotMeltMaterial;
import static firok.tiths.common.Keys.nameTraitHotMeltMaterial;

/**
 * 热熔材料
 * <p>
 * 击中着火的敌人时额外消耗 2 点耐久并给予热熔胶状态效果.
 * </p>
 *
 * @see Configs.Traits#duration_hot_melt_material
 */
public class TraitHotMeltMaterial extends AbstractTrait
{
    public TraitHotMeltMaterial()
    {
        super(nameTraitHotMeltMaterial, colorTraitHotMeltMaterial);
    }

    @Override
    public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit)
    {
        if (!wasHit || !player.isServerWorld() || !target.isEntityAlive()) return;
        if (!target.isBurning()) return;

        ToolHelper.damageTool(tool, 2, player);
        int duration = Math.max(1, Configs.Traits.duration_hot_melt_material) * 20;
        target.addPotionEffect(new PotionEffect(Potions.hot_glued, duration, 0));
    }
}
