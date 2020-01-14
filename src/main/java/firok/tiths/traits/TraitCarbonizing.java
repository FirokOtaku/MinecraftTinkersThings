package firok.tiths.traits;

import firok.tiths.util.Actions;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import slimeknights.tconstruct.library.traits.AbstractTrait;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import static firok.tiths.common.Keys.*;
import static firok.tiths.util.Predicates.canTrigger;
import static firok.tiths.util.Predicates.isStone;

// 碳化
public class TraitCarbonizing extends AbstractTrait
{
	public TraitCarbonizing()
	{
		super(nameTraitCarbonizing, colorTraitCarbonizing);
	}

	@Override
	public void blockHarvestDrops(ItemStack tool, BlockEvent.HarvestDropsEvent event)
	{
//		super.blockHarvestDrops(tool, event);
		World world=event.getWorld();
		if(world.isRemote) return; // 只在服务端进行
		Random rand=world.rand;
		EntityPlayer player=event.getHarvester();
		int countCoals=0;
		List<ItemStack> drops=event.getDrops();
		Iterator<ItemStack> iter=drops.iterator();
		while(iter.hasNext())
		{
			ItemStack drop=iter.next();
			if(isStone(drop) && canTrigger(rand,0.05f * drop.getCount()))
			{
				iter.remove();
				countCoals++;
			}
		}
		if(countCoals>0)
		{
			Actions.CauseSpawnItem(player,new ItemStack(net.minecraft.init.Items.COAL,countCoals));
		}
	}
}