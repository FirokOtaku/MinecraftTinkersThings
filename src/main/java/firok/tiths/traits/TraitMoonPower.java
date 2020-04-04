package firok.tiths.traits;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.ToolHelper;

import static firok.tiths.common.Keys.colorTraitMoonPower;
import static firok.tiths.common.Keys.nameTraitMoonPower;
import static firok.tiths.util.Predicates.canTick;

// 月之力量
public class TraitMoonPower extends AbstractTrait
{
	public TraitMoonPower()
	{
		super(nameTraitMoonPower, colorTraitMoonPower);
	}

	public static boolean checkHealMoon(World world)
	{
		return canTick(world,100,2) && !world.isDaytime();
	}
	@Override
	public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected)
	{
		if(!world.isRemote && entity instanceof EntityLivingBase && checkHealMoon(world))
		{
			ToolHelper.healTool(tool, 1, (EntityLivingBase) entity);
		}
	}
}