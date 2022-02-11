package firok.tiths.modifier.general;

import firok.tiths.util.DevUse;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.helper.ToolDamageUtil;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import static firok.tiths.util.Predicates.canTickServer;

/**
 * 不安定
 *
 * 吸取背包内物品的耐久
 *
 * https://github.com/351768593/MinecraftTinkersThings/blob/indev1122/src/main/java/firok/tiths/traits/TraitUnsettled.java
 */
@DevUse
public class ModifierUnsettled extends Modifier
{
	public ModifierUnsettled()
	{
		super(0x1d1d1d);
	}

	@Override
	public void onInventoryTick(IModifierToolStack tool, int level, World world, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack)
	{
		if(!canTickServer(world, 80, 5) || !holder.isAlive() || !ToolDamageUtil.needsRepair(stack) || !(holder instanceof PlayerEntity)) return;
//		final int currentDurability = tool.getCurrentDurability();
//		final int maxDurability = currentDurability + currentDamage;
		final PlayerEntity player = (PlayerEntity) holder;
		final PlayerInventory inv = player.inventory;

		int currentDamage = tool.getDamage();
		int sizeInv = inv.getSizeInventory();
		int countAbsorb = 0;
		while(sizeInv --> 0)
		{
			ItemStack stackInv = inv.getStackInSlot(sizeInv);
			if(stackInv.isEmpty() || !stackInv.getItem().isIn(TinkerTags.Items.MODIFIABLE)) continue;

			ToolStack toolInv = ToolStack.from(stackInv);
			ToolDamageUtil.directDamage(toolInv, 2, holder, stackInv);
			ToolDamageUtil.repair(tool, 1);
			currentDamage --;
			countAbsorb ++;
			if(currentDamage <= 0 || countAbsorb >= 4) return;
		}
	}
}
