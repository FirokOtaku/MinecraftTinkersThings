package firok.tiths.entity.ai;

import com.google.common.base.Predicate;
import firok.tiths.common.Potions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIAvoidEntity;

public class EntityAIAvoidEntityFear<T extends Entity> extends EntityAIAvoidEntity<T>
{

	Class<T> classToAvoid;
	public Class<T> getClassToAvoid()
	{
		return classToAvoid;
	}

	public EntityAIAvoidEntityFear(EntityCreature entityIn, Class<T> classToAvoidIn, float avoidDistanceIn, double farSpeedIn, double nearSpeedIn)
	{
		super(entityIn, classToAvoidIn, avoidDistanceIn, farSpeedIn, nearSpeedIn);
		this.classToAvoid=classToAvoidIn;
	}

	public EntityAIAvoidEntityFear(EntityCreature entityIn, Class<T> classToAvoidIn, Predicate<? super T> avoidTargetSelectorIn, float avoidDistanceIn, double farSpeedIn, double nearSpeedIn)
	{
		super(entityIn, classToAvoidIn, avoidTargetSelectorIn, avoidDistanceIn, farSpeedIn, nearSpeedIn);
		this.classToAvoid=classToAvoidIn;
	}

	public boolean isInFear()
	{
		return this.entity.getActivePotionEffect(Potions.fear)!=null;
	}

	@Override
	public boolean shouldExecute()
	{
		return isInFear() && super.shouldExecute();
	}

	@Override
	public boolean shouldContinueExecuting()
	{
		return isInFear() && super.shouldContinueExecuting();
	}
}
