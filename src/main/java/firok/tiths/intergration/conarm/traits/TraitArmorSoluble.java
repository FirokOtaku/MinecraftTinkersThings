package firok.tiths.intergration.conarm.traits;

import c4.conarm.lib.traits.AbstractArmorTrait;
import com.google.common.collect.Multimap;
import firok.tiths.common.Potions;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.utils.ToolHelper;

import javax.annotation.Nonnull;

import java.util.UUID;

import static firok.tiths.common.Keys.*;
import static firok.tiths.util.Predicates.canTick;

/**
 * 可溶 - 护甲
 */
public class TraitArmorSoluble extends AbstractArmorTrait
{
	public TraitArmorSoluble()
	{
		super(nameTraitSoluble,colorTraitSoluble);
	}

	@Override
	public void onArmorTick(ItemStack tool, World world, EntityPlayer player)
	{
		if(!world.isRemote && player.isInWater())
		{
			if( canTick(world,15,2) )
			{
				ToolHelper.damageTool(tool, 3, player);
			}

			if( canTick(world,4,0) )
			{
				player.addPotionEffect( new PotionEffect(Potions.hidden_soluble, 5, 0 ));
			}
		}
	}

	@Override
	public void getAttributeModifiers(
			EntityEquipmentSlot slot, ItemStack stack, Multimap<String, AttributeModifier> attributeMap) {
		if (slot == EntityLiving.getSlotForItemStack(stack)) {
			attributeMap.put(
					EntityPlayer.SWIM_SPEED.getName(),
					new AttributeModifier(UUID.fromString(uuidTraitSolubleSwimSpeed), "Soluble trait modifier", 0.4, 2));
		}
	}
}
