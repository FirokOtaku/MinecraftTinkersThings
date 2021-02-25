package firok.tiths.tile.logic;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TEEnderInterferedBlock
	extends TileEntity
	implements ITickable
{
	public IBlockState stateInner;
	public int ticksCharge;

	public TEEnderInterferedBlock(int ticksCharge)
	{
		this(null,20);
	}
	public TEEnderInterferedBlock(IBlockState stateInner,int ticksCharge)
	{
		this.stateInner = stateInner;
		this.ticksCharge = ticksCharge;
	}

	@Override
	public void update()
	{
		this.ticksCharge --;
		if(this.ticksCharge <= 0)
		{
			world.setTileEntity(pos,null);

			if(world.getBlockState(pos).getBlock() == firok.tiths.common.Blocks.blockEnderInterferedBlock)
			{
				IBlockState stateToSet = this.stateInner == null ? Blocks.AIR.getDefaultState() : stateInner;
				world.setBlockState(pos,stateToSet);
			}
		}
	}

	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);

		this.stateInner = null;

		String rn = null;
		if(nbt.hasKey("rn_inner"))
		{
			try { rn = nbt.getString("rn_inner"); } catch (Exception ignored) { }
		}

		int meta = -1;
		if(nbt.hasKey("meta_inner"))
		{
			try { meta = nbt.getByte("meta_inner"); } catch (Exception ignored) { }
		}

		if(rn != null && meta != -1)
		{
			try
			{
				Block blockInner = Block.getBlockFromName(rn);
				this.stateInner = blockInner.getStateFromMeta(meta);
			}
			catch (Exception ignored) { }
		}

		this.ticksCharge = 20;

		if(nbt.hasKey("ticks_charge"))
		{
			this.ticksCharge = nbt.getInteger("ticks_charge");
		}
	}

	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		if(this.stateInner != null)
		{
			try
			{
				Block blockInner = stateInner.getBlock();
				int meta = blockInner.getMetaFromState(stateInner);

				String registryName = blockInner.getRegistryName().toString();

				nbt.setString("rn_inner",registryName);
				nbt.setInteger("meta_inner",meta);
			}
			catch (Exception ignored) { }

			nbt.setInteger("ticks_charge",this.ticksCharge);
		}
		return super.writeToNBT(nbt);
	}


}
