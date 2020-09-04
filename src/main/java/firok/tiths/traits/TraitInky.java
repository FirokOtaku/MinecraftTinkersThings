package firok.tiths.traits;

import com.google.common.collect.ImmutableList;
import firok.tiths.common.Configs;
import firok.tiths.util.InnerActions;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import slimeknights.mantle.util.RecipeMatch;
import slimeknights.tconstruct.library.modifiers.IModifierDisplay;
import slimeknights.tconstruct.library.tools.ToolNBT;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.TagUtil;

import java.util.List;

import static firok.tiths.common.Keys.colorTraitInky;
import static firok.tiths.common.Keys.nameTraitInky;

/**
 * 墨染
 */
public class TraitInky extends AbstractTrait implements IModifierDisplay
{
	public TraitInky()
	{
		super(nameTraitInky, colorTraitInky);
	}

	@Override
	public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
		if(wasHit && target.isEntityAlive()) {
			target.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, Configs.Traits.factor_inky));
		}
	}

	@Override
	public void applyEffect(NBTTagCompound rootCompound, NBTTagCompound modifierTag)
	{
		if(InnerActions.addTrait(this,rootCompound,modifierTag)) return;

		ToolNBT data = TagUtil.getToolStats(rootCompound);

		data.speed*=0.9f;
		data.attackSpeedMultiplier*=0.9f;

		TagUtil.setToolTag(rootCompound, data.get());
	}

	@Override
	public int getColor()
	{
		return this.color;
	}

	@Override
	public List<List<ItemStack>> getItems()
	{
		ImmutableList.Builder<List<ItemStack>> builder = ImmutableList.builder();

		for(RecipeMatch rm : items) {
			List<ItemStack> in = rm.getInputs();
			if(!in.isEmpty()) {
				builder.add(in);
			}
		}

		return builder.build();
	}
}
