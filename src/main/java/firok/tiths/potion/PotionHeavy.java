package firok.tiths.potion;

import firok.tiths.common.Keys;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;

// 沉重
public class PotionHeavy extends Potion
{
	public PotionHeavy()
	{
		super(true, Keys.colorPotionHeavy);
	}

	@Override
	public boolean isReady(int p_isReady_1_, int p_isReady_2_) // todo low 改一下形参名字
	{
		return true;
	}

	@Override
	public void performEffect(EntityLivingBase entity, int level)
	{
		if(entity instanceof EntityPlayer && ((EntityPlayer) entity).isCreative()) return;
		level++;
		if(entity.motionY>-3*level) entity.motionY-=0.08f*level;
	}
}
