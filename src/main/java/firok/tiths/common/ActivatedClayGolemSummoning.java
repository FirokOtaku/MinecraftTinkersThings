package firok.tiths.common;

import firok.tiths.TinkersThings;
import firok.tiths.entity.EntityActivatedClayGolem;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockWorldState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockMaterialMatcher;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.block.state.pattern.BlockStateMatcher;
import net.minecraft.block.state.pattern.FactoryBlockPattern;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = TinkersThings.MOD_ID)
public final class ActivatedClayGolemSummoning
{
    private ActivatedClayGolemSummoning() {}

    private static class Patterns
    {
        private static BlockPattern build(boolean withHead)
        {
            return FactoryBlockPattern.start()
                    .aisle("~^~", "###", "~#~")
                    .where('^', BlockWorldState.hasState(withHead ? ActivatedClayGolemSummoning::isActivatedPumpkin
                            : BlockMaterialMatcher.forMaterial(Material.AIR)))
                    .where('#', BlockWorldState.hasState(
                            BlockStateMatcher.forBlock(firok.tiths.common.Blocks.blockActivatedClay)))
                    .where('~', BlockWorldState.hasState(BlockMaterialMatcher.forMaterial(Material.AIR)))
                    .build();
        }

        private static final BlockPattern COMPLETE = build(true);
        private static final BlockPattern BASE = build(false);
    }

    private static boolean isActivatedPumpkin(IBlockState state)
    {
        return state != null && state.getBlock() == firok.tiths.common.Blocks.blockActivatedPumpkin;
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onPumpkinPlaced(BlockEvent.NeighborNotifyEvent event)
    {
        World world = event.getWorld();
        BlockPos head = event.getPos();
        // Placement notifications arrive after Forge has accepted the block placement.
        if (world.isRemote || !isActivatedPumpkin(event.getState()) || !isActivatedPumpkin(world.getBlockState(head))) return;
        BlockPattern.PatternHelper pattern = Patterns.COMPLETE.match(world, head);
        if (pattern != null && pattern.translateOffset(1, 0, 0).getPos().equals(head))
            summon(world, pattern, true);
    }

    public static void registerDispenserBehavior()
    {
        Item pumpkin = Item.getItemFromBlock(firok.tiths.common.Blocks.blockActivatedPumpkin);
        IBehaviorDispenseItem previous = BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.getObject(pumpkin);
        BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(pumpkin, (source, stack) -> {
            World world = source.getWorld();
            BlockPos head = source.getBlockPos().offset(source.getBlockState().getValue(BlockDispenser.FACING));
            if (!world.isRemote && world.isAirBlock(head))
            {
                BlockPattern.PatternHelper pattern = Patterns.BASE.match(world, head);
                if (pattern != null && pattern.translateOffset(1, 0, 0).getPos().equals(head)
                        && summon(world, pattern, false))
                {
                    stack.shrink(1);
                    world.playEvent(1000, source.getBlockPos(), 0);
                    return stack;
                }
            }
            return previous.dispense(source, stack);
        });
    }

    private static boolean summon(World world, BlockPattern.PatternHelper pattern, boolean withHead)
    {
        BlockPos foot = pattern.translateOffset(1, 2, 0).getPos();
        EntityActivatedClayGolem golem = new EntityActivatedClayGolem(world);
        golem.setPlayerCreated(true);
        golem.setLocationAndAngles(foot.getX() + 0.5, foot.getY() + 0.05, foot.getZ() + 0.5, 0, 0);
        if (!world.spawnEntity(golem)) return false;

        for (int y = 0; y < pattern.getHeight(); y++)
        {
            for (int x = 0; x < pattern.getWidth(); x++)
            {
                BlockPos pos = pattern.translateOffset(x, y, 0).getPos();
                IBlockState state = world.getBlockState(pos);
                if (state.getBlock() == Blocks.AIR)
                {
                    if (!withHead && x == 1 && y == 0)
                    {
                        world.playEvent(2001, pos, Block.getStateId(firok.tiths.common.Blocks.blockActivatedPumpkin.getDefaultState()));
                    }
                    continue;
                }
                world.setBlockState(pos, Blocks.AIR.getDefaultState(), 2);
                world.playEvent(2001, pos, Block.getStateId(state));
            }
        }

        for (EntityPlayerMP player : world.getEntitiesWithinAABB(EntityPlayerMP.class, golem.getEntityBoundingBox().grow(5)))
        {
            CriteriaTriggers.SUMMONED_ENTITY.trigger(player, golem);
        }
        for (int y = 0; y < pattern.getHeight(); y++)
        {
            for (int x = 0; x < pattern.getWidth(); x++)
            {
                world.notifyNeighborsRespectDebug(pattern.translateOffset(x, y, 0).getPos(), Blocks.AIR, false);
            }
        }
        return true;
    }
}
