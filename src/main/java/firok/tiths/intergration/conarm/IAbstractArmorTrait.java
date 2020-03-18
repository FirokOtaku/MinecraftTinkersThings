package firok.tiths.intergration.conarm;

import c4.conarm.lib.armor.ArmorModifications;
import c4.conarm.lib.traits.IArmorAbility;
import c4.conarm.lib.traits.IArmorTrait;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;

public interface IAbstractArmorTrait extends IArmorTrait,IArmorAbility
{
	@Override
	default ArmorModifications getModifications(EntityPlayer player, ArmorModifications mods, ItemStack armor, DamageSource source, double damage, int slot)
	{
		return mods;
	}

	@Override
	default void onItemPickup(ItemStack armor, EntityItem item, EntityItemPickupEvent evt)
	{
		;
	}

	@Override
	default float onHeal(ItemStack armor, EntityPlayer player, float amount, float newAmount, LivingHealEvent evt)
	{
		return newAmount;
	}

	@Override
	default float onHurt(ItemStack armor, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingHurtEvent evt)
	{
		return newDamage;
	}

	@Override
	default float onDamaged(ItemStack armor, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingDamageEvent evt)
	{
		return newDamage;
	}

	@Override
	default void onKnockback(ItemStack armor, EntityPlayer player, LivingKnockBackEvent evt)
	{
		;
	}

	@Override
	default void onFalling(ItemStack armor, EntityPlayer player, LivingFallEvent evt)
	{
		;
	}

	@Override
	default void onJumping(ItemStack armor, EntityPlayer player, LivingEvent.LivingJumpEvent evt)
	{
		;
	}

	@Override
	default void onAbilityTick(int level, World world, EntityPlayer player)
	{
		;
	}

	@Override
	default void onArmorEquipped(ItemStack armor, EntityPlayer player, int slot)
	{
		;
	}

	@Override
	default void onArmorRemoved(ItemStack armor, EntityPlayer player, int slot)
	{
		;
	}

	@Override
	default int onArmorDamage(ItemStack armor, DamageSource source, int damage, int newDamage, EntityPlayer player, int slot)
	{
		return newDamage;
	}

	@Override
	default int onArmorHeal(ItemStack armor, DamageSource source, int amount, int newAmount, EntityPlayer player, int slot)
	{
		return newAmount;
	}

	@Override
	default int getAbilityLevel(ModifierNBT data)
	{
		return data.level;
	}

	@Override
	default boolean disableRendering(ItemStack armor, EntityLivingBase entityLivingBase)
	{
		return false;
	}
}
