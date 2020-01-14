package firok.tiths.traits;

import firok.tiths.util.Actions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.ToolHelper;
import slimeknights.tconstruct.tools.TinkerTraits;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import static firok.tiths.common.Keys.*;
import static firok.tiths.util.Predicates.*;

// 日之力量
public class TraitSunPower extends AbstractTrait
{
	public TraitSunPower()
	{
		super(nameTraitSunPower, colorTraitSunPower);
	}

	@Override
	public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected)
	{
		if(!world.isRemote && canTick(world,100,2) && entity instanceof EntityLivingBase && world.isDaytime())
		{
			ToolHelper.healTool(tool, 1, (EntityLivingBase) entity);
		}
	}
}