package firok.tiths.traits;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.traits.AbstractTrait;

import static firok.tiths.util.Predicates.canTick;
import static firok.tiths.util.Predicates.canTrigger;

// 抽象属性-抗效果 目前只能抗单种效果 // todo 以后也许做成抗多种效果的
public class AbstractTraitAntiEffect extends AbstractTrait
{
	private int interval;
	private int offset;
	private float rate;
	private Potion potion;
	public AbstractTraitAntiEffect(String name, int color, int interval, int offset, float rate,Potion potion)
	{
		super(name,color);
		this.interval=interval;
		this.offset=offset;
		this.rate=rate;
		this.potion=potion;
	}

	@Override
	public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected)
	{
		if(!world.isRemote && isSelected && canTick(world,interval,offset) && canTrigger(world,rate) && entity instanceof EntityLivingBase)
		{
			EntityLivingBase enlb=(EntityLivingBase)entity;
			enlb.removePotionEffect(potion);
		}
	}
}
