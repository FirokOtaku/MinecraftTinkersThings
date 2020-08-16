package firok.tiths.traits;

import firok.tiths.common.Configs;
import firok.tiths.util.Actions;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.traits.AbstractTrait;

import static firok.tiths.common.Keys.colorTraitStarDashing;
import static firok.tiths.common.Keys.nameTraitStarDashing;

// 星绽
public class TraitStarDashing extends AbstractTrait
{
	public TraitStarDashing()
	{
		super(nameTraitStarDashing,colorTraitStarDashing);
	}

	@Override
	public void onHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, boolean isCritical)
	{
		super.onHit(tool, player, target, damage, isCritical);

		if(player.isServerWorld())
		{
//			Actions.CauseStarDashing(player,target,0.2f,3);
			Actions.CauseStarDashing(player.world,player.posX,player.posY,player.posZ,
					Configs.Traits.factor_star_dashing_amount,
					(float) Configs.Traits.factor_star_dashing_speed,
					(float) Configs.Traits.factor_star_dashing_damage,
					player);
		}
	}
}
