package firok.tiths.common;

// import com.google.common.collect.ImmutableSet; // 仅供暂缓的多方块结构注册使用
import firok.tiths.TinkersThings;
import firok.tiths.item.ItemCustom;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import slimeknights.mantle.item.ItemBlockMeta;
import slimeknights.mantle.item.ItemBlockSlab;
import slimeknights.mantle.util.RecipeMatch;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.smeltery.CastingRecipe;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;
import slimeknights.tconstruct.smeltery.block.BlockSeared;
import slimeknights.tconstruct.smeltery.block.BlockSeared.SearedType;
import slimeknights.tconstruct.smeltery.block.BlockSearedGlass;
import slimeknights.tconstruct.smeltery.block.BlockSearedSlab;
import slimeknights.tconstruct.smeltery.block.BlockSearedSlab2;
import slimeknights.tconstruct.smeltery.block.BlockSearedStairs;
import slimeknights.tconstruct.smeltery.block.BlockTank;
import slimeknights.tconstruct.smeltery.item.ItemTank;

import java.util.EnumMap;
import java.util.Map;

public enum SearedBuildingMaterials
{
    ASHEN("ashen", Fluids.moltenAshenStone, 0),
    CINDER("cinder", Fluids.moltenCinderStone, 0),
    LUMINOUS("luminous", Fluids.moltenLuminousStone, 12);

    private static final int BRICK_VOLUME = Material.VALUE_Shard;
    private static final int BLOCK_VOLUME = BRICK_VOLUME * 4;

    private final String id;
    private final Fluid fluid;
    private final int light;
    public final Item powder = new ItemCustom();
    public final Item brick = new ItemCustom();
    public final BlockSeared stone = new BlockSeared();
    public final BlockSearedGlass glass = new BlockSearedGlass();
    public final BlockTank tank = new BlockTank()
    {
        @Override
        public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos)
        {
            return Math.max(light, super.getLightValue(state, world, pos));
        }
    };
    public final BlockSearedSlab slab = new BlockSearedSlab()
    {
        @Override
        public IBlockState getFullBlock(IBlockState state)
        {
            return stoneState(state.getValue(TYPE).asSearedBlock());
        }
    };
    public final BlockSearedSlab2 slab2 = new BlockSearedSlab2()
    {
        @Override
        public IBlockState getFullBlock(IBlockState state)
        {
            return stoneState(state.getValue(TYPE).asSearedBlock());
        }
    };
    public final Map<SearedType, Block> stairs = new EnumMap<>(SearedType.class);

    SearedBuildingMaterials(String id, Fluid fluid, int light)
    {
        this.id = id;
        this.fluid = fluid;
        this.light = light;
    }

    private IBlockState stoneState(SearedType type)
    {
        return stone.getDefaultState().withProperty(BlockSeared.TYPE, type);
    }

    public static void register()
    {
        for (SearedBuildingMaterials family : values()) family.registerFamily();
    }

    private void registerFamily()
    {
        registerItem(powder, "dust_stone_" + id);
        registerItem(brick, id + "_brick");
        registerBlock(stone, id + "_stone", new ItemBlockMeta(stone));
        registerBlock(glass, id + "_glass", new ItemBlockMeta(glass));
        registerBlock(tank, id + "_tank", new ItemTank(tank));
        registerBlock(slab, id + "_slab", new ItemBlockSlab<>(slab));
        registerBlock(slab2, id + "_slab2", new ItemBlockSlab<>(slab2));
        ItemBlockMeta.setMappingProperty(stone, BlockSeared.TYPE);
        ItemBlockMeta.setMappingProperty(glass, BlockSearedGlass.TYPE);
        ItemBlockMeta.setMappingProperty(tank, BlockTank.TYPE);
        ItemBlockMeta.setMappingProperty(slab, BlockSearedSlab.TYPE);
        ItemBlockMeta.setMappingProperty(slab2, BlockSearedSlab2.TYPE);
        for (SearedType type : SearedType.values())
        {
            Block block = new BlockSearedStairs(stoneState(type));
            registerBlock(block, id + "_stairs_" + type.getName(), new ItemBlock(block));
            stairs.put(type, block);
        }
    }

    private static void registerItem(Item item, String name)
    {
        item.setRegistryName(TinkersThings.resource(name));
        item.setTranslationKey(TinkersThings.MOD_ID + '.' + name);
        item.setCreativeTab(TinkerRegistry.tabSmeltery);
        ForgeRegistries.ITEMS.register(item);
    }

    private void registerBlock(Block block, String name, ItemBlock item)
    {
        block.setRegistryName(TinkersThings.resource(name));
        block.setTranslationKey(TinkersThings.MOD_ID + '.' + name);
        block.setLightLevel(light / 15f);
        block.setCreativeTab(TinkerRegistry.tabSmeltery);
        ForgeRegistries.BLOCKS.register(block);
        item.setRegistryName(block.getRegistryName());
        ForgeRegistries.ITEMS.register(item);
    }

    public static void init()
    {
        if (TinkerSmeltery.searedBlock == null) return;

        for (SearedBuildingMaterials family : values()) family.registerCasting();

        // 暂停多方块结构注册。熔化、浇筑和烧炼配方仍在上面注册。
        /*
        ImmutableSet.Builder<Block> smeltery = ImmutableSet.<Block>builder().addAll(TinkerSmeltery.validSmelteryBlocks);
        ImmutableSet.Builder<Block> tank = ImmutableSet.<Block>builder().addAll(TinkerSmeltery.validTinkerTankBlocks);
        ImmutableSet.Builder<Block> floor = ImmutableSet.<Block>builder().addAll(TinkerSmeltery.validTinkerTankFloorBlocks);
        ImmutableSet.Builder<Block> roof = ImmutableSet.<Block>builder().addAll(TinkerSmeltery.searedStairsSlabs);
        for (SearedBuildingMaterials family : values())
        {
            smeltery.add(family.stone, family.glass);
            tank.add(family.stone, family.glass, family.tank);
            floor.add(family.stone, family.glass);
            roof.add(family.stone, family.slab, family.slab2).addAll(family.stairs.values());
        }
        TinkerSmeltery.validSmelteryBlocks = smeltery.build();
        TinkerSmeltery.validTinkerTankBlocks = tank.build();
        TinkerSmeltery.validTinkerTankFloorBlocks = floor.build();
        TinkerSmeltery.searedStairsSlabs = roof.build();
        */
    }

    private void registerCasting()
    {
        ItemStack stoneBlock = new ItemStack(stone, 1, SearedType.STONE.getMeta());
        ItemStack cobble = new ItemStack(stone, 1, SearedType.COBBLE.getMeta());
        GameRegistry.addSmelting(new ItemStack(powder), new ItemStack(brick), 0.1f);
        GameRegistry.addSmelting(cobble, stoneBlock, 0.1f);
        GameRegistry.addSmelting(new ItemStack(stone, 1, SearedType.BRICK.getMeta()),
                new ItemStack(stone, 1, SearedType.BRICK_CRACKED.getMeta()), 0.1f);
        TinkerRegistry.registerMelting(powder, fluid, BRICK_VOLUME);
        TinkerRegistry.registerMelting(brick, fluid, BRICK_VOLUME);
        TinkerRegistry.registerMelting(stone, fluid, BLOCK_VOLUME);
        TinkerRegistry.registerTableCasting(new ItemStack(brick), ItemStack.EMPTY, fluid, BRICK_VOLUME);
        TinkerRegistry.registerBasinCasting(stoneBlock, ItemStack.EMPTY, fluid, BLOCK_VOLUME);
        TinkerRegistry.registerBasinCasting(new CastingRecipe(cobble, RecipeMatch.of("cobblestone"),
                new FluidStack(fluid, BRICK_VOLUME * 3), true, false));
        TinkerRegistry.registerBasinCasting(new CastingRecipe(new ItemStack(glass), RecipeMatch.of("blockGlass"),
                new FluidStack(fluid, BLOCK_VOLUME), true, true));
    }

    // These slots compare block identity in TConstruct 1.12 instead of consulting its public sets.
    // 暂停 ASM 补丁的入口，与 core 包中的补丁一起保留。
    /*
    public static boolean matchesStructureBlock(Block actual, Block original)
    {
        if (actual == original) return true;
        for (SearedBuildingMaterials family : values())
        {
            if (original == TinkerSmeltery.searedBlock && actual == family.stone) return true;
            if (original == TinkerSmeltery.searedTank && actual == family.tank) return true;
        }
        return false;
    }
    */
}
