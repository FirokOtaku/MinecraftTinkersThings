package firok.tiths.traits;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.ToolHelper;

import static firok.tiths.common.Keys.colorTraitSunPower;
import static firok.tiths.common.Keys.nameTraitSunPower;
import static firok.tiths.util.Predicates.canTick;

// 日之力量
public class TraitSunPower extends AbstractTrait
{
	public TraitSunPower()
	{
		super(nameTraitSunPower, colorTraitSunPower);
	}

	public static boolean checkHealSun(World world)
	{
		return canTick(world,100,2) &&  world.isDaytime();
	}

	@Override
	public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected)
	{
		if(!world.isRemote && entity instanceof EntityLivingBase && checkHealSun(world))
		{
			ToolHelper.healTool(tool, 1, (EntityLivingBase) entity);
		}
	}
}