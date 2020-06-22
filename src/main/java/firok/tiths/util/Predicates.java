package firok.tiths.util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockStone;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.tinkering.ITinkerable;
import slimeknights.tconstruct.library.traits.ITrait;
import slimeknights.tconstruct.library.utils.ToolHelper;

import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

public final class Predicates
{
	private Predicates(){}

	public static boolean canTick(long time,int interval,int offset)
	{
		return time%interval==offset;
	}
	public static boolean canTick(World world,int interval,int offset)
	{
		return canTick(world.getTotalWorldTime(),interval,offset);
	}

	static final Item blockStone=Item.getItemFromBlock(Blocks.STONE);
	static final Item blockCobbleStone=Item.getItemFromBlock(Blocks.COBBLESTONE);
	static final Item blockNetherrack=Item.getItemFromBlock(Blocks.NETHERRACK);
	static final Item blockEndStone=Item.getItemFromBlock(Blocks.END_STONE);
	static final Item blockAir=Item.getItemFromBlock(Blocks.AIR);
	static final Item blockDirt=Item.getItemFromBlock(Blocks.DIRT);
	static final Item blockGrass=Item.getItemFromBlock(Blocks.GRASS);
	static final Item blockSand=Item.getItemFromBlock(Blocks.SAND);
	static final Item blockLAVA=Item.getItemFromBlock(Blocks.LAVA);
	static final Item blockMagma=Item.getItemFromBlock(Blocks.MAGMA);

	public static boolean any(Object... obj)
	{
		return true;
	}
	public static boolean none(Object... obj)
	{
		return false;
	}

	/* ---- 判断是否是圆石或者石头 ---- */
	public static boolean isAnyStone(ItemStack stack)
	{
		Item item;
		return stack != null &&
				stack != ItemStack.EMPTY &&
				((item = stack.getItem()) == blockStone ||
						item == blockCobbleStone);
	}
	public static boolean isAnyStone(IBlockState state)
	{
		return state!=null && isAnyStone(state.getBlock());
	}
	public static boolean isAnyStone(Item item)
	{
		return item!=null && (item == blockStone || item == blockCobbleStone);
	}
	public static boolean isAnyStone(Block block)
	{
		return block!=null && (block == Blocks.STONE || block == Blocks.COBBLESTONE);
	}

	/* ---- 判断是否是石头 ---- */
	public static boolean isStone(ItemStack stack)
	{
		return stack!=null && !stack.isEmpty() && stack.getItem() == blockStone;
	}
	public static boolean isStone(IBlockState state)
	{
		return state!=null && isStone(state.getBlock());
	}
	public static boolean isStone(Item item)
	{
		return item==blockStone;
	}
	public static boolean isStone(Block block)
	{
		return block==Blocks.STONE;
	}

	/* ---- 判断是否是圆石 ---- */
	public static boolean isCobbleStone(ItemStack stack)
	{
		return stack!=null && !stack.isEmpty() && stack.getItem() == blockCobbleStone;
	}
	public static boolean isCobbleStone(IBlockState state)
	{
		return state!=null && isCobbleStone(state.getBlock());
	}
	public static boolean isCobbleStone(Item item)
	{
		return item==blockCobbleStone;
	}
	public static boolean isCobbleStone(Block block)
	{
		return block!=null && block==Blocks.COBBLESTONE;
	}

	/* ---- 判断是否是地狱岩 ---- */
	public static boolean isNetherrack(ItemStack stack)
	{
		return stack!=null && !stack.isEmpty() && stack.getItem() == blockNetherrack;
	}
	public static boolean isNetherrack(IBlockState state)
	{
		return state!=null && isNetherrack(state.getBlock());
	}
	public static boolean isNetherrack(Item item)
	{
		return item==blockNetherrack;
	}
	public static boolean isNetherrack(Block block)
	{
		return block==Blocks.NETHERRACK;
	}

	/* ---- 判断是否是岩浆块 ---- */
	public static boolean isMagma(ItemStack stack)
	{
		return stack!=null && !stack.isEmpty() && stack.getItem() == blockMagma;
	}
	public static boolean isMagma(IBlockState state)
	{
		return state!=null && isMagma(state.getBlock());
	}
	public static boolean isMagma(Item item)
	{
		return item==blockMagma;
	}
	public static boolean isMagma(Block block)
	{
		return block==Blocks.MAGMA;
	}

	/* ---- 判断是否是岩浆源 ---- */
	public static boolean isLava(ItemStack stack)
	{
		return stack!=null && !stack.isEmpty() && stack.getItem() == blockLAVA;
	}
	public static boolean isLava(IBlockState state)
	{
		return state!=null && isLava(state.getBlock());
	}
	public static boolean isLava(Item item)
	{
		return item==blockLAVA;
	}
	public static boolean isLava(Block block)
	{
		return block==Blocks.LAVA;
	}

	/* ---- 判断是否是末地石 ---- */
	public static boolean isEndStone(ItemStack stack)
	{
		return stack!=null && !stack.isEmpty() && stack.getItem() == blockEndStone;
	}
	public static boolean isEndStone(IBlockState state)
	{
		return state!=null && isEndStone(state.getBlock());
	}
	public static boolean isEndStone(Item item)
	{
		return item==blockEndStone;
	}
	public static boolean isEndStone(Block block)
	{
		return block==Blocks.END_STONE;
	}

	/* ---- 判断是否是空气 ---- */
	public static boolean isAir(ItemStack stack)
	{
		return stack != null && !stack.isEmpty() && stack.getItem() == blockAir;
	}
	public static boolean isAir(IBlockState state)
	{
		return state!=null && isAir(state.getBlock());
	}
	public static boolean isAir(Item item)
	{
		return item==blockAir;
	}
	public static boolean isAir(Block block)
	{
		return block==Blocks.AIR;
	}

	/* ---- 判断是否是沙子 ---- */
	public static boolean isSand(ItemStack stack)
	{
		return stack != null && !stack.isEmpty() && stack.getItem() == blockSand;
	}
	public static boolean isSand(IBlockState state)
	{
		return state!=null && isSand(state.getBlock());
	}
	public static boolean isSand(Item item)
	{
		return item==blockSand;
	}
	public static boolean isSand(Block block)
	{
		return block==Blocks.SAND;
	}
	/* ---- 判断是否是土方块 ---- */
	public static boolean isDirt(ItemStack stack)
	{
		return stack != null && !stack.isEmpty() && stack.getItem() == blockDirt && stack.getItemDamage()==0;
	}
	public static boolean isDirt(IBlockState state)
	{
		return state!=null && isDirt(state.getBlock());
	}
	public static boolean isDirt(Item item)
	{
		return item==blockDirt;
	}
	public static boolean isDirt(Block block)
	{
		return block==Blocks.DIRT;
	}

	/* ---- 判断是否是草方块 ---- */
	public static boolean isGrass(ItemStack stack)
	{
		return stack != null && !stack.isEmpty() && stack.getItem() == blockGrass;
	}
	public static boolean isGrass(IBlockState state)
	{
		return state!=null && isGrass(state.getBlock());
	}
	public static boolean isGrass(Item item)
	{
		return item==blockGrass;
	}
	public static boolean isGrass(Block block)
	{
		return block==Blocks.GRASS;
	}

	public static boolean isWater(IBlockState state)
	{
		return state!=null && isWater(state.getBlock());
	}
	public static boolean isWater(Block block)
	{
		return block==Blocks.WATER || block==Blocks.FLOWING_WATER;
	}


	public static Predicate<ItemStack> getPredicateItemStack(String str,Predicate<ItemStack> defaultValue)
	{
		if(str==null) return defaultValue;
		else switch (str)
		{
			case "any": return Predicates::any;
			case "none": return Predicates::none;
			case "air": return Predicates::isAir;
			case "any_stone": return Predicates::isAnyStone;
			case "stone": return Predicates::isStone;
			case "cobble_stone": return Predicates::isCobbleStone;
			case "end_stone": return Predicates::isEndStone;
			case "netherrack": return Predicates::isNetherrack;
			case "grass": return Predicates::isGrass;
			case "dirt": return Predicates::isDirt;
			case "sand": return Predicates::isSand;
			case "lava": return Predicates::isLava;
			case "magma": return Predicates::isMagma;
			default: return defaultValue;
		}
	}
	public static Predicate<IBlockState> getPredicateIBlockState(String str,Predicate<IBlockState> defaultValue)
	{
		if(str==null) return defaultValue;
		else switch (str)
		{
			case "any": return Predicates::any;
			case "none": return Predicates::none;
			case "air": return Predicates::isAir;
			case "any_stone": return Predicates::isAnyStone;
			case "stone": return Predicates::isStone;
			case "cobble_stone": return Predicates::isCobbleStone;
			case "end_stone": return Predicates::isEndStone;
			case "netherrack": return Predicates::isNetherrack;
			case "grass": return Predicates::isGrass;
			case "dirt": return Predicates::isDirt;
			case "sand": return Predicates::isSand;
			case "lava": return Predicates::isLava;
			case "magma": return Predicates::isMagma;
			default: return defaultValue;
		}
	}
	public static Predicate<Item> getPredicateItem(String str,Predicate<Item> defaultValue)
	{
		if(str==null) return defaultValue;
		else switch (str)
		{
			case "any": return Predicates::any;
			case "none": return Predicates::none;
			case "air": return Predicates::isAir;
			case "any_stone": return Predicates::isAnyStone;
			case "stone": return Predicates::isStone;
			case "cobble_stone": return Predicates::isCobbleStone;
			case "end_stone": return Predicates::isEndStone;
			case "netherrack": return Predicates::isNetherrack;
			case "grass": return Predicates::isGrass;
			case "dirt": return Predicates::isDirt;
			case "sand": return Predicates::isSand;
			case "lava": return Predicates::isLava;
			case "magma": return Predicates::isMagma;
			default: return defaultValue;
		}
	}
	public static Predicate<Block> getPredicateBlock(String str,Predicate<Block> defaultValue)
	{
		if(str==null) return defaultValue;
		else switch (str)
		{
			case "any": return Predicates::any;
			case "none": return Predicates::none;
			case "air": return Predicates::isAir;
			case "any_stone": return Predicates::isAnyStone;
			case "stone": return Predicates::isStone;
			case "cobble_stone": return Predicates::isCobbleStone;
			case "end_stone": return Predicates::isEndStone;
			case "netherrack": return Predicates::isNetherrack;
			case "grass": return Predicates::isGrass;
			case "dirt": return Predicates::isDirt;
			case "sand": return Predicates::isSand;
			case "lava": return Predicates::isLava;
			case "magma": return Predicates::isMagma;
			default: return defaultValue;
		}
	}

	/**
	 * 判断是否是植物<br>
	 * @apiNote 这个断言目前只用于工具特性, 不用于世界生成
	 */
	public static boolean isPlants(IBlockState state)
	{
		if(state==null) return false;
		Material material=state.getMaterial();
		return material==Material.LEAVES ||
		       material==Material.WOOD ||
		       material==Material.GRASS ||
		       material==Material.PLANTS ||
		       material==Material.CACTUS;
	}

//	public static boolean canReplaceWithOre(IBlockState state, IBlockAccess world, BlockPos pos)
//	{
//		return state!=null && canReplaceWithOre(state.getBlock(), world, pos);
//	}
//	public static boolean canReplaceWithOre(Block block, IBlockAccess world, BlockPos pos)
//	{
//		return block!=null && (
//			block==Blocks.AIR ||
//			block==Blocks.STONE ||
//			block==Blocks.COBBLESTONE ||
//			block==Blocks.DIRT ||
//			block==Blocks.SAND ||
//			block==Blocks.GRASS ||
//			(world!=null && pos !=null && block.isReplaceable(world,pos))
//		);
//	}

	public static boolean canTrigger(Entity entity,double rate)
	{
		return entity!=null && canTrigger(entity.world,rate);
	}
	public static boolean canTrigger(World world,double rate)
	{
		return world!=null && canTrigger(world.rand,rate);
	}
	public static boolean canTrigger(Random rand,double rate)
	{
		return rand!=null && rate > 0 && rand.nextDouble() < rate;
	}

	/**
	 * @deprecated 将来会移除这个断言
	 */
	@Deprecated
	public static boolean canOreGenWorld(IBlockState state)
	{
		if(state==null) return false;
		Block block=state.getBlock();

		return block==Blocks.STONE && state.getValue(BlockStone.VARIANT).isNatural();
	}
	/**
	 * @deprecated 将来会移除这个断言
	 */
	@Deprecated
	public static boolean canMeteoGen(IBlockState state)
	{
		if(state==null) return false;
		Block block=state.getBlock();

		return block == Blocks.AIR ||
				block == Blocks.STONE ||
				block == Blocks.COBBLESTONE ||
				block == Blocks.DIRT ||
				block == Blocks.SAND ||
				block == Blocks.GRASS;
	}
	/**
	 * @deprecated 将来会移除这个断言
	 */
	@Deprecated
	public static boolean canOreGenNether(IBlockState state)
	{
		return state!=null && state.getBlock()==Blocks.NETHERRACK;
	}
	/**
	 * @deprecated 将来会移除这个断言
	 */
	@Deprecated
	public static boolean canOreGenEnd(IBlockState state)
	{
		return state!=null && state.getBlock()==Blocks.END_STONE;
	}
	/**
	 * @deprecated 将来会移除这个断言
	 */
	@Deprecated
	public static boolean canOreGenOther(IBlockState state)
	{
		return canOreGenWorld(state);
	}

	/**
	 * 护甲是否可以处理指定伤害类型
	 */
	public static boolean canDealWith(DamageSource source,Boolean isBlockable,Boolean isMagic,Boolean isFire,Boolean isExplosion,Boolean isProjectile)
	{

		return source!=null &&
		       (isBlockable==null || isBlockable==!source.isUnblockable()) &&
		       (isMagic==null || isMagic==source.isMagicDamage()) &&
		       (isFire==null || isFire==source.isFireDamage()) &&
		       (isExplosion==null || isExplosion==source.isExplosion()) &&
		       (isProjectile==null || isProjectile==source.isProjectile())
				;
	}

	public static boolean canIgnoredByCreeper(EntityPlayer player)
	{
		if(player==null || player.inventory==null || player.inventory.armorInventory==null) return false;
		try
		{
			for(ItemStack stackArmor:player.inventory.armorInventory)
			{
				if(stackArmor==null || stackArmor.isEmpty()) continue;
				Item itemArmor=stackArmor.getItem();
				if(!(itemArmor instanceof ITinkerable)) continue;

				List<ITrait> traits= ToolHelper.getTraits(stackArmor);
				if(traits.contains(1)) // fixme 改成正确的特性
				{
					return true;
				}
			}
			return false;
		}
		catch (Exception e)
		{
			return false;
		}
	}
}
