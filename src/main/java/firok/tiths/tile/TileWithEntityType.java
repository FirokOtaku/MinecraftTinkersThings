package firok.tiths.tile;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * this kind of TileEntity could store one EntityType
 */
public class TileWithEntityType extends TileEntity
{
	EntityType<?> et = null;
	public TileWithEntityType(TileEntityType<?> tileEntityTypeIn)
	{
		super(tileEntityTypeIn);
	}

	public void setEntityType(EntityType<?> et)
	{
		if(et != this.et)
		{
			this.et = et;
			this.markDirty();
		}
	}
	public EntityType<?> getEntityType()
	{
		return this.et;
	}
	public boolean isInstance(Entity en)
	{
		if(en == null || et == null) return false;
		return en.getType() == this.et;
	}

	public static final String KEY_NBT_ENTITY_TYPE = "et";
	@Override
	public void read(BlockState state, CompoundNBT nbt)
	{
		super.read(state, nbt);

		if(nbt.contains(KEY_NBT_ENTITY_TYPE))
		{
			try
			{
				String rlEt = nbt.getString(KEY_NBT_ENTITY_TYPE);
				ResourceLocation rl = new ResourceLocation(rlEt);
				this.et = ForgeRegistries.ENTITIES.getValue(rl);
			}
			catch (Exception ignored) { }
		}
	}

	@Override
	public CompoundNBT write(CompoundNBT nbt)
	{
		super.write(nbt);

		if(et != null)
		{
			nbt.putString(KEY_NBT_ENTITY_TYPE, et.getRegistryName().toString());
		}

		return nbt;
	}

	public CompoundNBT getUpdateTag()
	{
		return this.write(new CompoundNBT());
	}
}
