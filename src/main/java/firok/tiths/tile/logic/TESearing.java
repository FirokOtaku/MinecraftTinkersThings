package firok.tiths.tile.logic;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import slimeknights.tconstruct.shared.TinkerCommons;

// 焦黑窖
public class TESearing extends TileEntity implements ITickable
{
	public static final int maxProgress =400; // 20秒

	public Block blockOrigin;
	public int progress;

	public void update()
	{
		this.progress++;
		if(progress> maxProgress)
		{
			// fixme low 改成焦黑砖的state
			progress(true);
		}
	}

	public void progress(boolean isSuccess)
	{
		world.setBlockState(pos, isSuccess?TinkerCommons.blockDecoGround.getDefaultState(): Blocks.STONE.getDefaultState());
	}


	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setInteger("te_progress",progress);
		nbt.setString("te_block_origin", blockOrigin.getRegistryName().toString());
		return nbt;
	}
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		this.progress=nbt.hasKey("te_progress")?nbt.getInteger("te_progress"):0;
		String tempBlockOrigin=nbt.hasKey("te_block_origin")?nbt.getString("te_block_origin"):null;
		this.blockOrigin= tempBlockOrigin!=null?ForgeRegistries.BLOCKS.getValue(new ResourceLocation(tempBlockOrigin)):Blocks.STONE;
	}
}
