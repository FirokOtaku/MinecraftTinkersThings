package firok.tiths.traits;

import firok.tiths.common.Configs;
import firok.tiths.common.SoundEvents;
import firok.tiths.util.Actions;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.traits.AbstractTrait;

import static firok.tiths.common.Keys.colorTraitBlowing;
import static firok.tiths.common.Keys.nameTraitBlowing;
import static firok.tiths.util.Predicates.canTrigger;

// 吹袭
public class TraitBlowing extends AbstractTrait
{
	public TraitBlowing()
	{
		super(nameTraitBlowing, colorTraitBlowing);
	}

	@Override
	public void onHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, boolean isCritical)
	{
		World world=target.world;
		if(!world.isRemote && canTrigger(world, Configs.Traits.rate_blowing))
		{
			String nameTarget=target.getName();
			for(String blacklist_entity_id:Configs.Traits.blacklist_blowing_entity)
			{
				if(nameTarget.equals(blacklist_entity_id)) // 实体类型在黑名单里
					return;
			}
			ItemStack stack2drop;
			stack2drop=target.getHeldItemMainhand();
			if(stack2drop.isEmpty()) // 主手没找到
			{
				stack2drop=target.getHeldItemOffhand();
				target.setHeldItem(EnumHand.OFF_HAND,ItemStack.EMPTY);
			}
			else // 主手找到了
			{
				target.setHeldItem(EnumHand.MAIN_HAND,ItemStack.EMPTY);
			}

			if(!stack2drop.isEmpty())
			{
				world.playSound(null,player.posX,player.posY,player.posZ, SoundEvents.effectSwing, SoundCategory.PLAYERS,1,1);
				Actions.CauseSpawnItem(target,stack2drop);
			}
		}
	}
}
