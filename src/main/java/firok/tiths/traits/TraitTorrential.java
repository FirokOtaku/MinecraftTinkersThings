package firok.tiths.traits;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.traits.AbstractTrait;

import static firok.tiths.common.Keys.colorTraitTorrential;
import static firok.tiths.common.Keys.nameTraitTorrential;

/**
 * 激流
 */
public class TraitTorrential extends AbstractTrait
{
	public TraitTorrential()
	{
		super(nameTraitTorrential,colorTraitTorrential);
	}

	@Override
	public float knockBack(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float knockback, float newKnockback, boolean isCritical)
	{
		World world=player.world;

		if(player.isInWater() && target.isInWater())
		{
			return newKnockback * 1.2f + 0.5f;
		}
		if(world.isRainingAt(player.getPosition()))
		{
			return newKnockback * 1.05f + 0.15f;
		}
		return newKnockback;
	}
}
