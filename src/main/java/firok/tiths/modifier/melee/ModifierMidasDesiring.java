package firok.tiths.modifier.melee;

import firok.tiths.util.DevUse;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.util.DamageSource;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

import javax.annotation.Nullable;
import java.util.List;
import java.util.ListIterator;

/**
 * 迈达斯之欲
 *
 * todo rework
 * 计划把负面效果分离成新的modifier
 * 迈达斯魔力 增加金产量
 * 迈达斯之欲 随时间消耗物品栏中的金子
 * 迈达斯之触 点金 有几率把石头或者其它什么东西转化成金子
 *
 * https://github.com/351768593/MinecraftTinkersThings/blob/indev1122/src/main/java/firok/tiths/traits/TraitMidasDesiring.java
 */
@DevUse
public class ModifierMidasDesiring extends Modifier
{
	public ModifierMidasDesiring()
	{
		super(0xdc9113);
	}

//	@Override
//	public List<ItemStack> processLoot(IModifierToolStack tool, int level, List<ItemStack> list, LootContext context)
//	{
//		ListIterator<ItemStack> iter = list.listIterator();
//
//
//		while(iter.hasNext())
//		{
//			ItemStack stackLoot = iter.next();
//		}
//
//		return list;
//	}

	@Override
	public int getLootingValue(IModifierToolStack tool, int level, LivingEntity holder, Entity target, @Nullable DamageSource damageSource, int looting)
	{
		// add some new flavor
		return looting + 2;
	}
}
