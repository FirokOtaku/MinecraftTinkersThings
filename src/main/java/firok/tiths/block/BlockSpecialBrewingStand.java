package firok.tiths.block;

import firok.tiths.tile.TEGemBrewingStand;
import net.minecraft.block.BlockBrewingStand;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

/**
 * 特殊酿造台
 */
public class BlockSpecialBrewingStand extends BlockBrewingStand
{
	public BlockSpecialBrewingStand()
	{
		super();
	}

	@Override
	public TEGemBrewingStand createNewTileEntity(World worldIn, int meta)
	{
		return new TEGemBrewingStand()
		{
			@Override
			public String getGuiID()
			{
				return "tiths:gem_brewing_stand";
			}
		};
	}

	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if (worldIn.isRemote)
		{
			return true;
		}
		else
		{
			TileEntity tileentity = worldIn.getTileEntity(pos);

			if (tileentity instanceof TEGemBrewingStand)
			{
				playerIn.displayGUIChest((TEGemBrewingStand)tileentity);
				playerIn.addStat(StatList.BREWINGSTAND_INTERACTION);
			}

			return true;
		}
	}

	/**
	 * Called by ItemBlocks after a block is set in the world, to allow post-place logic
	 */
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		if (stack.hasDisplayName())
		{
			TileEntity tileentity = worldIn.getTileEntity(pos);

			if (tileentity instanceof TEGemBrewingStand)
			{
				((TEGemBrewingStand)tileentity).setName(stack.getDisplayName());
			}
		}
	}

	/**
	 * Called serverside after this block is replaced with another in Chunk, but before the Tile Entity is updated
	 */
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	{
		TileEntity tileentity = worldIn.getTileEntity(pos);

		if (tileentity instanceof TEGemBrewingStand)
		{
			InventoryHelper.dropInventoryItems(worldIn, pos, (TEGemBrewingStand)tileentity);
		}

		super.breakBlock(worldIn, pos, state);
	}

	/**
	 * Get the Item that this Block should drop when harvested.
	 */
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return Items.BREWING_STAND;
	}

	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
	{
		return new ItemStack(Items.BREWING_STAND);
	}
}
