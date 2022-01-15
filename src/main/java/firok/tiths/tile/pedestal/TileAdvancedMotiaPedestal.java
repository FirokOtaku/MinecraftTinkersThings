package firok.tiths.tile.pedestal;

import firok.tiths.item.ItemAdvancedMotiaCrystal;
import firok.tiths.item.TithsItems;
import firok.tiths.tile.TithsTiles;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Optional;

public class TileAdvancedMotiaPedestal extends TilePedestalBase
{
	public TileAdvancedMotiaPedestal()
	{
		super(TithsTiles.teAdvancedMotiaPedestal.get());
		System.out.println("ate 构造");
	}

	@OnlyIn(Dist.CLIENT)
	private Entity entity;
	@OnlyIn(Dist.CLIENT)
	public Entity getEntity()
	{
		if(this.entity != null) return this.entity;

		ItemStack stack = getStackPedestal();
		if(stack.getItem() != TithsItems.advancedMotiaCrystal.get() || !stack.hasTag()) return null;
		try
		{
			CompoundNBT tag = stack.getTag();
			CompoundNBT tagEntity = tag.getCompound(ItemAdvancedMotiaCrystal.KEY_NBT_ENTITY);

			Optional<Entity> entity = EntityType.loadEntityUnchecked(tagEntity, world);
			if(entity.isPresent())
			{
				this.entity = entity.get();
//				this.entity.read(tagEntity);
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return entity;
	}
	@OnlyIn(Dist.CLIENT)
	private void clearEntity()
	{
		this.entity = null;
	}

	@Override
	public void setStackPedestal(ItemStack stack)
	{
		super.setStackPedestal(stack);
		this.clearEntity();
	}
}
