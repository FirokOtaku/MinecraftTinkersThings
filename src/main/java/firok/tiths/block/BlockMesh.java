package firok.tiths.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Random;

import static firok.tiths.util.Predicates.canTrigger;

public class BlockMesh extends BlockCompressed
{
	public BlockMesh()
	{
		super(Material.WOOD);
		this.setHardness(1);
		this.setResistance(3);
		this.setTickRandomly(true);
		this.enableTransparent();
	}

	@Override
	public boolean isTopSolid(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return true;
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
	{
		if(!worldIn.isRemote)
		{
			IBlockState stateTop = worldIn.getBlockState(pos.up());
			Block blockTop = stateTop.getBlock();

			if( (blockTop == Blocks.WATER || blockTop == Blocks.FLOWING_WATER) && worldIn.isAirBlock(pos.down()) )
			{
				try
				{
					LootContext.Builder lootBuilding = new LootContext.Builder((WorldServer) worldIn);
					lootBuilding.withLuck(0);
					List<ItemStack> result = worldIn.getLootTableManager()
							.getLootTableFromLocation(LootTableList.GAMEPLAY_FISHING)
							.generateLootForPools(rand, lootBuilding.build());

					for (ItemStack itemstack : result)
					{
						EntityItem ei = new EntityItem(worldIn,
								pos.getX() + rand.nextFloat(),
								pos.getY() - 0.8,
								pos.getZ() + rand.nextFloat(),
								itemstack);
						worldIn.spawnEntity(ei);
					}
				} catch (Exception ignored) { }
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockState blockstateThis, IBlockAccess world, BlockPos pos, EnumFacing side)
	{
		return super.shouldSideBeRendered(blockstateThis, world, pos, side);
	}

	@Override
	public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face)
	{
		return face == EnumFacing.UP || face == EnumFacing.DOWN;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
	{
		if(canTrigger(rand,0.15f))
		{
			IBlockState stateTop = worldIn.getBlockState(pos.up());
			Block blockTop = stateTop.getBlock();
			if((blockTop == Blocks.WATER || blockTop == Blocks.FLOWING_WATER) && worldIn.isAirBlock(pos.down()))
			{
				worldIn.spawnParticle(EnumParticleTypes.DRIP_WATER,
						pos.getX() + rand.nextFloat(),
						pos.getY(),
						pos.getZ() + rand.nextFloat(),
						0,0,0
				);
			}
		}
	}
}
