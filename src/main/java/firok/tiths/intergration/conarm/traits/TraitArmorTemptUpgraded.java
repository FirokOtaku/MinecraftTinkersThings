package firok.tiths.intergration.conarm.traits;

import c4.conarm.common.armor.utils.ArmorTagUtil;
import c4.conarm.lib.armor.ArmorNBT;
import c4.conarm.lib.traits.AbstractArmorTrait;
import firok.tiths.common.Datas;
import firok.tiths.entity.ai.EntityAITemptPlayer;
import firok.tiths.util.EntityFinders;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.utils.TagUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static firok.tiths.common.Keys.colorTraitCarrotUpgraded;
import static firok.tiths.common.Keys.nameTraitCarrotUpgraded;
import static firok.tiths.util.Predicates.canTick;

/**
 * @author Firok
 */
public class TraitArmorTemptUpgraded extends AbstractArmorTrait
{
	Predicate<EntityPlayer> temptor;
	List<Predicate<? super Entity>> listPredicators=new ArrayList<>();
	public TraitArmorTemptUpgraded(String id,int color,Predicate<EntityPlayer> temptor)
	{
		super(id,color);
		this.temptor=temptor;
	}

	@Override
	public void applyEffect(NBTTagCompound rootCompound, NBTTagCompound modifierTag)
	{
		// add the trait to the traitlist so it gets processed
		NBTTagList traits = TagUtil.getTraitsTagList(rootCompound);
		// if it's not already present
		for(int i = 0; i < traits.tagCount(); i++) {
			if(identifier.equals(traits.getStringTagAt(i))) {
				return;
			}
		}

		traits.appendTag(new NBTTagString(identifier));
		TagUtil.setTraitsTagList(rootCompound, traits);
		
		ArmorNBT data = ArmorTagUtil.getArmorStats(rootCompound);
		data.modifiers--;
		TagUtil.setToolTag(rootCompound, data.get());
	}

	Consumer<EntityPlayer> consumer;
	@Override
	public void onUpdate(ItemStack tool, World world, Entity wearer, int itemSlot, boolean isSelected)
	{
		if(world.isRemote || !(wearer instanceof EntityPlayer) || !canTick(world,80,0)) return;

		EntityPlayer player=(EntityPlayer)wearer;
		List<Entity> surroundings= (List<Entity>) EntityFinders.Nearby(player,6);
		for(Entity entity:surroundings)
		{
			handleEntity(entity);
		}

		consumer.accept(player);
	}

	public TraitArmorTemptUpgraded always(Consumer<EntityPlayer> consumer)
	{
		if(consumer==null) return this;
		this.consumer=consumer;
		return this;
	}
	public TraitArmorTemptUpgraded toEntity(Predicate<? super Entity>... predicates)
	{
		if(predicates==null || predicates.length<=0) return this;

		for(Predicate<? super Entity> predicate:predicates)
			if(predicate!=null)
				this.listPredicators.add(predicate);

		return this;
	}
	public boolean handleEntity(Entity entity)
	{
		if(!(entity instanceof EntityLiving)) return false;

		boolean canApply=false;
		for(Predicate<? super Entity> predicate:listPredicators)
		{
			canApply = predicate.test(entity);
			if(canApply) break;
		}
		if(!canApply) return false;

		EntityLiving living=(EntityLiving) entity;

		EntityAITasks tasks=living.tasks;
		boolean hasOurAI=false;
		for(EntityAITasks.EntityAITaskEntry task:tasks.taskEntries)
		{
			EntityAIBase ai=task.action;
			if(ai instanceof EntityAITemptPlayer)
			{
				hasOurAI=true;
				break;
			}
		}

		if(!hasOurAI)
		{
			living.tasks.addTask(3,createAI(living,1.2,false));
			return true;
		}

		return false;
	}

	public EntityAITemptPlayer createAI(EntityLiving entity,double speed,boolean scared)
	{
		return new EntityAITemptPlayer(entity,speed,scared,temptor);
	}

}
