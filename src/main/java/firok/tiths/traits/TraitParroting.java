package firok.tiths.traits;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import slimeknights.tconstruct.library.traits.AbstractTrait;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static firok.tiths.common.Keys.colorTraitParroting;
import static firok.tiths.common.Keys.nameTraitParroting;
import static firok.tiths.util.Predicates.canTrigger;

// 学舌
public class TraitParroting extends AbstractTrait
{
	public TraitParroting()
	{
		super(nameTraitParroting, colorTraitParroting);
	}

	@Override
	public void onHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, boolean isCritical)
	{
		if(!target.world.isRemote && canTrigger(target.world,0.08f))
		{
			try // 开始尝试获取声音
			{
//				net.minecraft.util.SoundEvent soundEvent=null;
//				Class<? extends EntityLivingBase> classEntity=target.getClass();
//
//				if(target instanceof EntityLiving)
//				{
//					Method method=classEntity.getDeclaredMethod("getAmbientSound");
//					soundEvent=(net.minecraft.util.SoundEvent)method.invoke(target);
//				}
//				else
//				{
//					Method method=classEntity.getDeclaredMethod("getHurtSound", DamageSource.class);
//					soundEvent=(net.minecraft.util.SoundEvent)method.invoke(target,null);
//				}
//
//				ResourceLocation rlSound=soundEvent.getRegistryName();



			}
			catch (Exception ignored)
			{
				;
			}
		}
	}

	public static boolean playSound(ItemStack stack)
	{
		if(stack.hasTagCompound())
		{
			NBTTagCompound nbt=stack.getTagCompound();
			if(nbt.hasKey("SOUND_KEY"))
			{
				;
			}
		}
		return false;
	}

	// 从音效注册表获取声音
	public static net.minecraft.util.SoundEvent getSound(ResourceLocation rlSound)
	{
		return ForgeRegistries.SOUND_EVENTS.getValue(rlSound);
	}

	// 获取日常声音
	public static net.minecraft.util.SoundEvent getAmbientSound(EntityLiving entity) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException
	{
		Class<? extends EntityLivingBase> classEntity=entity.getClass();
		Method method=classEntity.getDeclaredMethod("getAmbientSound");
		return (net.minecraft.util.SoundEvent)method.invoke(entity);
	}

	// 获取受伤声音
	public static net.minecraft.util.SoundEvent getHurtSound(EntityLivingBase entity,DamageSource damage) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException
	{
		Class<? extends EntityLivingBase> classEntity=entity.getClass();
		Method method=classEntity.getDeclaredMethod("getHurtSound", DamageSource.class);
		return (net.minecraft.util.SoundEvent)method.invoke(entity,damage);
	}

	@Override
	public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected)
	{
		super.onUpdate(tool, world, entity, itemSlot, isSelected);
	}
}