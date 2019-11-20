package firok.mtim.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

@SuppressWarnings("all")
public class BlockOre extends Block
{
	private Item itemDropped;
	private int minDropped,maxDropped,interval,luckBonus,minExp,maxExp,intervalExp;

	public BlockOre(MapColor color,Item itemDropped,int minDropped,int maxDropped,int luckBonus,int minExp,int maxExp) {
		super(Material.ROCK, color);
		this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
		this.itemDropped=itemDropped;
		this.minDropped=minDropped;
		this.maxDropped=maxDropped;
		this.interval=maxDropped-minDropped;
		this.luckBonus=luckBonus;
		this.minExp=minExp;
		this.maxExp=maxExp;
		this.intervalExp=this.maxExp-minExp;
	}
	public BlockOre(Item itemDropped,int minDropped,int maxDropped,int luckBonus,int minExp,int maxExp) {
		super(Material.ROCK, Material.ROCK.getMaterialMapColor());
		this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
		this.itemDropped=itemDropped;
		this.minDropped=minDropped;
		this.maxDropped=maxDropped;
		this.interval=maxDropped-minDropped;
		this.luckBonus=luckBonus;
		this.minExp=minExp;
		this.maxExp=maxExp;
		this.intervalExp=this.maxExp-minExp;
	}
	public BlockOre()
	{
		super(Material.ROCK, Material.ROCK.getMaterialMapColor());
	}
	public BlockOre(MapColor color)
	{
		super(Material.ROCK,color);
	}


	public Item getItemDropped(IBlockState blockState, Random rand, int meta) {
		return itemDropped==null?Item.getItemFromBlock(this):itemDropped;
	}

	public int quantityDropped(Random rand) {
		return itemDropped==null?1:MathHelper.getInt(rand,minDropped,maxDropped);
	}

	public int quantityDroppedWithBonus(int luck, Random rand) {

		return itemDropped!=null&&luck>0&&luckBonus>0?
				this.quantityDropped(rand):
				this.quantityDropped(rand)+rand.nextInt(luck*luckBonus);
	}

//	public void dropBlockAsItemWithChance(World world, BlockPos pos, IBlockState blockState, float p_dropBlockAsItemWithChance_4_, int p_dropBlockAsItemWithChance_5_) {
//		super.dropBlockAsItemWithChance(world, pos, blockState, p_dropBlockAsItemWithChance_4_, p_dropBlockAsItemWithChance_5_);
//	}

	public int getExpDrop(IBlockState p_getExpDrop_1_, IBlockAccess world, BlockPos p_getExpDrop_3_, int p_getExpDrop_4_) {
		Random rand= world instanceof World? ((World)world).rand: new Random();

		return this.itemDropped!=null&&this.maxExp>0?
				this.minExp+rand.nextInt(this.intervalExp):
				0;
	}

	public ItemStack getItem(World world, BlockPos pos, IBlockState state) {
		return new ItemStack(this);
	}

//	public int damageDropped(IBlockState p_damageDropped_1_) {
//		return this == Blocks.LAPIS_ORE ? EnumDyeColor.BLUE.getDyeDamage() : 0;
//	}
}
