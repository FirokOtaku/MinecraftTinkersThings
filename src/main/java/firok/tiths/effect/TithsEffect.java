package firok.tiths.effect;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

abstract class TithsEffect extends Effect
{
	private final boolean isInstant;
	private final boolean isDisplayHUD;
	private final boolean isDisplayInventory;

	protected TithsEffect(EffectType type, int liquidColor, boolean isInstant, boolean isDisplayHUD, boolean isDisplayInventory)
	{
		super(type, liquidColor);
		this.isInstant = isInstant;
		this.isDisplayHUD = isDisplayHUD;
		this.isDisplayInventory = isDisplayInventory;
		this.listCurativeItem = new ArrayList<>(3);
		this.listCurativeItem.add(new ItemStack(Items.MILK_BUCKET)); // could be cured by milk at default case
	}
	protected TithsEffect(EffectType type, int liquidColor, boolean isInstant, boolean isHidden)
	{
		this(type, liquidColor, isInstant, !isHidden, !isHidden);
	}
	/**
	 * non-hidden effect constructor
	 */
	protected TithsEffect(EffectType type, int liquidColor, boolean isInstant)
	{
		this(type, liquidColor, isInstant, false);
	}
	/**
	 * non-instant & non-hidden effect constructor
	 */
	protected TithsEffect(EffectType type, int liquidColor)
	{
		this(type, liquidColor, false);
	}

	protected List<ItemStack> listCurativeItem;
	public void setNoCure()
	{
		this.listCurativeItem.clear();
	}
	public void addCurativeItem(ItemStack stack)
	{
		this.listCurativeItem.add(stack);
	}
	@Override
	public final List<ItemStack> getCurativeItems()
	{
		return new ArrayList<>(this.listCurativeItem);
	}

	@Override
	public void performEffect(LivingEntity living, int level) { }

	@Override
	public void affectEntity(@Nullable Entity source, @Nullable Entity sourceIndirect, LivingEntity living, int level, double health) { }

	@Override
	public boolean isReady(int duration, int level) { return false; }

	@Override
	public final boolean isInstant() { return isInstant; }

	@Override
	public boolean shouldRenderHUD(EffectInstance effect)
	{
		return isDisplayHUD;
	}

	@Override
	public boolean shouldRender(EffectInstance effect)
	{
		return isDisplayInventory;
	}

	@Override
	public boolean shouldRenderInvText(EffectInstance effect)
	{
		return isDisplayInventory;
	}

	/* === we use CurativeItems to store data === */

	public Item getDataItem() { return null; }

	/**
	 * find data stored in CurativeItems
	 */
	public final CompoundNBT getData(EffectInstance ei)
	{
		Item itemData = getDataItem();
		if(itemData == null) return null;

		List<ItemStack> cis = ei.getCurativeItems();
		ItemStack stackData = null;
		for(ItemStack ci : cis)
		{
			Item item = ci.getItem();
			if(item == itemData)
			{
				stackData = ci;
				break;
			}
		}
		if(stackData == null)
		{
			stackData = new ItemStack(itemData, 1);
			cis.add(stackData);
		}

		CompoundNBT nbt = stackData.getTag();
		if(nbt == null)
		{
			nbt = new CompoundNBT();
			stackData.setTag(nbt);
		}
		return nbt;
	}

	/* ==== util method ==== */
	public void adjustTime(LivingEntity living, int time, int level)
	{
		living.removePotionEffect(this);
		living.addPotionEffect(new EffectInstance(this, time, level));
	}
}
