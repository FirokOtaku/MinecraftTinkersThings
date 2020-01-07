package firok.tiths.block;

import firok.tiths.TinkersThings;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
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
		setHardness(3.0F).setResistance(5.0F);
	}
	public BlockOre()
	{
		super(Material.ROCK, Material.ROCK.getMaterialMapColor());
		setHardness(3.0F).setResistance(5.0F);
	}
	public BlockOre(MapColor color)
	{
		super(Material.ROCK,color);
		setHardness(3.0F).setResistance(5.0F);
	}

	@Override
	public Item getItemDropped(IBlockState blockState, Random rand, int meta) {
		Item ret= itemDropped==null?Item.getItemFromBlock(this):itemDropped;
		return ret;
	}

	@Override
	public void getDrops(NonNullList<ItemStack> list, IBlockAccess world, BlockPos pos, IBlockState state, int luck) {
		Random rand = world instanceof World ? ((World)world).rand : RANDOM;
		int count = this.quantityDropped(state, luck, rand);

		for(int i = 0; i < count; ++i) {
			Item item = this.getItemDropped(state, rand, luck);
			if (item != Items.AIR) {
				list.add(new ItemStack(item, 1, this.damageDropped(state)));
			}
		}

	}

	@Override
	public int quantityDropped(IBlockState p_quantityDropped_1_, int p_quantityDropped_2_, Random p_quantityDropped_3_) {
		return this.quantityDroppedWithBonus(p_quantityDropped_2_, p_quantityDropped_3_);
	}

	@Override
	public int quantityDropped(Random rand) {
		int ret= itemDropped==null?1:minDropped+rand.nextInt(maxDropped-minDropped);
		return ret;
	}

	@Override
	public int quantityDroppedWithBonus(int luck, Random rand) {

		Object rand2=rand;
		return itemDropped!=null&&luck>0&&luckBonus>0?
				this.quantityDropped(rand)+rand.nextInt(luck*luckBonus):
				this.quantityDropped(rand);
	}

	//	public void dropBlockAsItemWithChance(World world, BlockPos pos, IBlockState blockState, float p_dropBlockAsItemWithChance_4_, int p_dropBlockAsItemWithChance_5_) {
//		super.dropBlockAsItemWithChance(world, pos, blockState, p_dropBlockAsItemWithChance_4_, p_dropBlockAsItemWithChance_5_);
//	}

	@Override
	public int getExpDrop(IBlockState p_getExpDrop_1_, IBlockAccess world, BlockPos p_getExpDrop_3_, int p_getExpDrop_4_) {
		Random rand= world instanceof World? ((World)world).rand: new Random();

		return this.itemDropped!=null&&this.maxExp>0?
				this.minExp+rand.nextInt(this.intervalExp):
				0;
	}

	@Override
	public ItemStack getItem(World world, BlockPos pos, IBlockState state) {
		return new ItemStack(this);
	}

//	public int damageDropped(IBlockState p_damageDropped_1_) {
//		return this == Blocks.LAPIS_ORE ? EnumDyeColor.BLUE.getDyeDamage() : 0;
//	}
}