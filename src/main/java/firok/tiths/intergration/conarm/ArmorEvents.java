package firok.tiths.intergration.conarm;

import c4.conarm.lib.capabilities.ArmorAbilityHandler;
import firok.tiths.common.Configs;
import firok.tiths.common.Potions;
import firok.tiths.common.Traits;
import firok.tiths.intergration.conarm.traits.TraitArmorWidening;
import firok.tiths.util.EntityFinders;
import firok.tiths.util.InnerActions;
import firok.tiths.util.Selectors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.*;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.event.entity.EntityMobGriefingEvent;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerWakeUpEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;
import slimeknights.tconstruct.library.tinkering.ITinkerable;
import slimeknights.tconstruct.library.traits.ITrait;
import slimeknights.tconstruct.library.utils.TinkerUtil;
import slimeknights.tconstruct.library.utils.ToolHelper;

import java.util.List;
import java.util.Set;

import static firok.tiths.util.Predicates.canDealWith;
import static firok.tiths.util.Predicates.canTrigger;

public class ArmorEvents
{
	public static void onPlayerHurt(LivingHurtEvent event,EntityPlayer player)
	{
		DamageSource source=event.getSource();
		Set<ITrait> traitsArmor= InnerActions.getArmorTraits(player);

		if(traitsArmor.contains(ArmorTraits.thresholdLimiting)) // 阈限
		{
			float damage=event.getAmount();
			if(damage > 4 && canDealWith(source,true,null,null,null,null))
			{
				float damageMax = player.getHealth()/4;
//				System.out.println("damage max "+damageMax+" damage "+damage);
				if(damage>damageMax)
				{
					event.setAmount(damageMax<1?1:damageMax);
				}
			}
		}
		else if(traitsArmor.contains(ArmorTraits.smooth) && !player.world.isRemote) // 光滑
		{
			if(canDealWith(source,true,null,null,null,null))
			{
				float rate=(float) (player.motionX * player.motionX + player.motionY * player.motionY + player.motionZ * player.motionZ > 0 ?
						Configs.ArmorTraits.rate_smooth_moving : Configs.ArmorTraits.rate_smooth_standing );
				if(canTrigger(player.world,rate))
				{
					event.setAmount(0);
					event.setCanceled(true);
				}
			}
		}
	}
	public static void onItemUsed(LivingEntityUseItemEvent.Finish event)
	{
		EntityLivingBase enlb=event.getEntityLiving();
		try
		{
			ItemStack stackUsed=event.getItem();
			Item itemUsed=stackUsed.getItem();
			if(itemUsed instanceof ItemPotion) // 广域化
			{
				Iterable<ItemStack> armorStacks=enlb.getArmorInventoryList();
 				FOR_WIDENING:for(ItemStack armorStack:armorStacks)
				{
					if(armorStack!=null && !armorStack.isEmpty())
					{
						List<ITrait> traits= ToolHelper.getTraits(armorStack);
						if(traits.contains(ArmorTraits.widening))
						{
							TraitArmorWidening.widen(enlb,stackUsed.copy());
							break FOR_WIDENING;
						}
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void onPlayerWaken(PlayerWakeUpEvent event)
	{
		World world=event.getEntityLiving().world;
		try
		{
			if(!event.wakeImmediately() &&  event.shouldSetSpawn())
			{
				EntityPlayer enlb=event.getEntityPlayer();
				int lvWS=0;

				Iterable<ItemStack> armorStacks=enlb.getArmorInventoryList();
				int i=0;
				FOR_WS:for(ItemStack armorStack:armorStacks)
				{
					i++;
					if(armorStack!=null && !armorStack.isEmpty())
					{
						System.out.println("i=="+i);
						System.out.println(armorStack.getTagCompound());
//						ModifierNBT data;
//						int lvTemp=0;
//						if((lvTemp=getLv(armorStack,ArmorTraits.warmSoft3)) > 0) lvWS=lvTemp;
//						else if((lvTemp=getLv(armorStack,ArmorTraits.warmSoft2)) > 0) lvWS=lvTemp;
//						else if((lvTemp=getLv(armorStack,ArmorTraits.warmSoft1)) > 0) lvWS=lvTemp;
						ArmorAbilityHandler.IArmorAbilities armorAbilities = ArmorAbilityHandler.getArmorAbilitiesData(enlb);
						int temp=armorAbilities.getAbilityLevel("warm_soft1_armor");
						if(temp>=0) lvWS=temp;
					}
				}

				System.out.println("isRemote?"+ world.isRemote +lvWS);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void onEntityStruckByLightning(EntityStruckByLightningEvent event)
	{
		Entity entityStruck=event.getEntity();
		if(entityStruck instanceof EntityPlayer)
		{
			EntityPlayer player=(EntityPlayer) entityStruck;
			for(ItemStack stackArmor:player.getArmorInventoryList())
			{
				if(stackArmor==null || stackArmor.isEmpty()) continue;

				Item itemArmor=stackArmor.getItem();

				if(!(itemArmor instanceof ITinkerable)) continue;

				if(ToolHelper.getTraits(stackArmor).contains(ArmorTraits.carrier))
				{
					ToolHelper.healTool(stackArmor,300,player);
				}
			}
		}
	}

	@Optional.Method(modid="conarm")
	private static int getLv(ItemStack stack,ITrait trait)
	{
		return new ModifierNBT(TinkerUtil.getModifierTag(stack, trait.getIdentifier())).level;
	}

	@SideOnly(Side.CLIENT)
	public static void onClientSoundPlay(PlaySoundEvent event)
	{
		try
		{
			EntityPlayerSP playerSP= Minecraft.getMinecraft().player;
			if(playerSP==null) return; // game has not started

			boolean hasDeadening=false; // 是否有消音

			ItemStack stackHead=playerSP.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
			if(stackHead.isEmpty()) return;

			List<ITrait> modifiers= ToolHelper.getTraits(stackHead);
			if(modifiers.contains(ArmorTraits.deadening))
			{
				hasDeadening=true;
			}

			ISound soundNew=null;
			ISound s=event.getSound();

			if(hasDeadening && s instanceof PositionedSound)
			{
				PositionedSound sound=(PositionedSound)s;

				final String field_name="field_147662_b"; // volume

				float volume= ObfuscationReflectionHelper.getPrivateValue(PositionedSound.class,sound,field_name);

				volume*=0.15;

				ObfuscationReflectionHelper.setPrivateValue(PositionedSound.class,sound,volume,field_name);

//				InnerActions.set(PositionedSound.class,"volume",s,volume*0.15f);

				soundNew=s;
			}

			if(soundNew!=null) event.setResultSound(soundNew);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void onTrample(BlockEvent.FarmlandTrampleEvent event)
	{
		World world=event.getWorld();
		if(world.isRemote) return;

		Entity entity=event.getEntity();
		if(entity instanceof EntityPlayer)
		{
			EntityPlayer player=(EntityPlayer) entity;
			Set<ITrait> traits=InnerActions.getArmorTraits(player);

			// 很好 我很喜欢
			if(traits.contains(ArmorTraits.farmlandProtective))
			{
				event.setCanceled(true);
			}
		}
	}

	public static void onMobGriefing(EntityMobGriefingEvent event)
	{
		Entity entity = event.getEntity();
		List<Entity> listPlayers = EntityFinders.Nearby(entity,16, Selectors.playerAlive);
		for(Entity en : listPlayers)
		{
			EntityPlayer player = (EntityPlayer) en;
			Set<ITrait> traits = InnerActions.getArmorTraits(player);

			if(traits.contains(ArmorTraits.antiGriefing)) // 破坏压制
			{
				event.setResult(Event.Result.DENY);
			}
		}
	}
}
