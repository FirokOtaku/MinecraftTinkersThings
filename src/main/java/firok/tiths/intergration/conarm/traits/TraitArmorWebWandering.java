package firok.tiths.intergration.conarm.traits;

import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import static firok.tiths.common.Keys.nameTraitWebWandering;
import static firok.tiths.common.Keys.colorTraitWebWandering;

/**
 * 蛛网漫行
 */
public class TraitArmorWebWandering extends AbstractArmorTrait
{
	public TraitArmorWebWandering()
	{
		super(nameTraitWebWandering,colorTraitWebWandering);
	}

	@Override
	public void onArmorTick(ItemStack tool, World world, EntityPlayer player)
	{
		ObfuscationReflectionHelper.setPrivateValue(Entity.class,player,false,"field_70134_J"); // Entity.isInWeb
	}
}
