package firok.tiths.traits;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.traits.AbstractTrait;

import static firok.tiths.common.Keys.colorTraitLuxurious;
import static firok.tiths.common.Keys.nameTraitLuxurious;

/**
 * 奢华
 */
public class TraitLuxurious extends AbstractTrait
{
	public TraitLuxurious() {
		super(nameTraitLuxurious,colorTraitLuxurious);
	}

	@Override
	public int onToolHeal(ItemStack tool, int amount, int newAmount, EntityLivingBase entity) {
		return newAmount - amount / 5; // 降低20%的耐久恢复
	}
}