//package firok.tiths.intergration.conarm.traits;
//
//import c4.conarm.lib.traits.AbstractArmorTrait;
//import firok.tiths.entity.ai.AISelfRemovableAvoidEntity;
//import firok.tiths.util.Actions;
//import net.minecraft.entity.Entity;
//import net.minecraft.entity.EntityCreature;
//import net.minecraft.entity.ai.EntityAIBase;
//import net.minecraft.entity.ai.EntityAITasks;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.item.ItemStack;
//import net.minecraft.util.DamageSource;
//import net.minecraft.util.EntityDamageSource;
//import net.minecraftforge.event.entity.living.LivingHurtEvent;
//
//import java.util.Set;
//
//import static firok.tiths.common.Keys.colorTraitPanicking;
//import static firok.tiths.common.Keys.nameTraitPanicking;
//
///**
// * 威慑 - 护甲
// */
//public class TraitArmorPanicking extends AbstractArmorTrait
//{
//	public TraitArmorPanicking()
//	{
//		super(nameTraitPanicking,colorTraitPanicking);
//	}
//
//	@Override
//	public float onHurt(ItemStack armor, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingHurtEvent evt)
//	{
//		if(source instanceof EntityDamageSource)
//		{
//			try
//			{
//				EntityDamageSource ds=(EntityDamageSource)source;
//				Entity entityTrueSource=ds.getTrueSource();
//				if(entityTrueSource instanceof EntityCreature)
//				{
//					EntityCreature enlbSource=(EntityCreature)entityTrueSource;
//					System.out.println("try triggering");
//
//					Set<EntityAITasks.EntityAITaskEntry> executingTaskEntries= (Set)Actions.get(EntityAITasks.class,"executingTaskEntries",enlbSource.tasks);
//					executingTaskEntries.clear();
//
//					boolean hasAvoid=false;
//
//					for(EntityAITasks.EntityAITaskEntry entry:enlbSource.tasks.taskEntries)
//					{
//						EntityAIBase ai=entry.action;
//						if(ai instanceof AISelfRemovableAvoidEntity)
//						{
//							AISelfRemovableAvoidEntity aiAvoid=(AISelfRemovableAvoidEntity)ai;
//							aiAvoid.trigger();
//							hasAvoid=true;
//							break;
//						}
//					}
//
//					if(!hasAvoid)
//					{
//						AISelfRemovableAvoidEntity aiAvoid=new AISelfRemovableAvoidEntity(enlbSource,EntityPlayer.class,8,0.8f,1.3f);
//						aiAvoid.trigger();
//						enlbSource.tasks.addTask(0,aiAvoid);
//					}
//				}
//			}
//			catch (Exception e)
//			{
//				e.printStackTrace();
//			}
//		}
//		return 0;
//	}
//}
