package firok.tiths.modifier.general;

import firok.tiths.util.DevUse;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

import static firok.tiths.util.Predicates.canTickClient;
import static firok.tiths.util.Predicates.canTickServer;

/**
 * 精力汇聚
 *
 * https://github.com/351768593/MinecraftTinkersThings/blob/indev1122/src/main/java/firok/tiths/traits/TraitStaminaFocusing.java
 */
@DevUse
public class ModifierStaminaFocusing extends Modifier
{
	public ModifierStaminaFocusing()
	{
		super(0x4e8d41);
	}

	@Override
	public void onInventoryTick(IModifierToolStack tool, int level, World world, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack)
	{

		if(isSelected && holder.isSneaking())
		{
			if(canTickServer(world, 4, 0))
			{
				holder.addPotionEffect(new EffectInstance(Effects.RESISTANCE, 80, 1));
			}
			if(canTickClient(world, 4, 0))
			{
				Vector3d pos = holder.getPositionVec();
				world.addOptionalParticle(ParticleTypes.WITCH, pos.x, pos.y, pos.z, 0,0,0);
			}
		}
	}
}
