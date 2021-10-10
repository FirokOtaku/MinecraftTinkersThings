package firok.tiths.modifier.general;

import firok.tiths.util.Actions;
import firok.tiths.util.DevUse;
import firok.tiths.util.Predicates;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolHarvestContext;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

// 碳化
@DevUse(isPlaceholder = true)
public class ModifierCarbonizing extends Modifier
{
	public ModifierCarbonizing()
	{
		super(0x2b2b2b);
	}

	// fixme 匠魂3的这个方法不直接满足原本特性需要的参数
	@Override
	public void afterBlockBreak(IModifierToolStack tool, int level, ToolHarvestContext context)
	{
		super.afterBlockBreak(tool, level, context);


//		super.blockHarvestDrops(tool, event);
//		World world = context.getWorld();
//		if (world.isRemote) return; // 只在服务端进行
//		Random rand = world.rand;
//		ServerPlayerEntity player = context.getPlayer();
//
//		int countCoals = 0;
//
//		List<ItemStack> drops = event.getDrops();
//		Iterator<ItemStack> iter = drops.iterator();
//		while (iter.hasNext())
//		{
//			ItemStack drop = iter.next();
//			if (Predicates.isAnyStone(drop) && canTrigger(rand, Configs.Traits.rate_carbonizing_transform * drop.getCount()))
//			{
//				iter.remove();
//				countCoals += drop.getCount();
//			}
//		}
//		if (countCoals > 0)
//		{
//			Actions.CauseSpawnItem(player, new ItemStack(net.minecraft.init.Items.COAL, countCoals));
//			player.world.playSound(null, player.posX, player.posY, player.posZ,
//					SoundEvents.effectFriction, SoundCategory.MASTER, 1, 1);
//		}
	}
}
