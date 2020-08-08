package firok.tiths.intergration.conarm.traits;

import c4.conarm.lib.traits.AbstractArmorTrait;
import firok.tiths.TinkersThings;
import firok.tiths.common.Configs;
import firok.tiths.util.Actions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootTableList;
import slimeknights.tconstruct.library.utils.ToolHelper;

import java.util.List;

import static firok.tiths.common.Keys.colorTraitMeshing;
import static firok.tiths.common.Keys.nameTraitMeshing;
import static firok.tiths.util.Predicates.canTick;
import static firok.tiths.util.Predicates.canTrigger;

/**
 * 筛网 - 护甲
 */
public class TraitArmorMeshing extends AbstractArmorTrait
{
	public TraitArmorMeshing()
	{
		super(nameTraitMeshing,colorTraitMeshing);
	}

	@Override
	public void onArmorTick(ItemStack tool, World world, EntityPlayer player)
	{
		if(!world.isRemote && player.isInWater() && canTick(world,80,0) && canTrigger(world, Configs.ArmorTraits.rate_meshing))
		{
			try
			{
				LootContext.Builder lootBuilding = new LootContext.Builder((WorldServer)world);
				lootBuilding.withLuck(player.getLuck()).withPlayer(player);
				List<ItemStack> result = world.getLootTableManager()
						.getLootTableFromLocation(LootTableList.GAMEPLAY_FISHING)
						.generateLootForPools(world.rand, lootBuilding.build());

				for (ItemStack itemstack : result)
				{
					Actions.CauseSpawnItem(player,itemstack);
				}

				ToolHelper.damageTool(tool,result.size() * Configs.ArmorTraits.factor_meshing,player);
			}
			catch (Exception e)
			{
				TinkersThings.log(e);
			}
		}
	}
}
