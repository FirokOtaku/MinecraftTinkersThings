package firok.tiths.modifier.general;

import firok.tiths.util.DevUse;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

import static firok.tiths.util.Predicates.canTickServer;

/**
 * 月色
 *
 * todo 重做
 * https://github.com/351768593/MinecraftTinkersThings/blob/indev1122/src/main/java/firok/tiths/traits/TraitMoonlight.java
 * */
@DevUse
public class ModifierMoonlight extends Modifier
{
	public ModifierMoonlight()
	{
		super(0x85e8de);
	}

	@Override
	public void onInventoryTick(IModifierToolStack tool, int level, World world, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack)
	{
		if(canTickServer(world, 100, 0))
		{
			holder.addPotionEffect(new EffectInstance(Effects.NIGHT_VISION, 105, 0));
		}
	}
}
