package firok.mtim.traits;

import firok.mtim.util.Colors;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.traits.AbstractTrait;

/**
 * 奢华
 */
public class TraitLuxurious extends AbstractTrait
{
	public TraitLuxurious() {
		super("luxurious", Colors.Gold);
	}

	@Override
	public int onToolHeal(ItemStack tool, int amount, int newAmount, EntityLivingBase entity) {
		return newAmount - amount / 5; // 降低20%的耐久恢复
	}
}