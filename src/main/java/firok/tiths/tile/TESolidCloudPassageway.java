package firok.tiths.tile;

import firok.tiths.TinkersThings;
import firok.tiths.block.BlockSolidCloudPassageway;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeMap;
import java.util.WeakHashMap;

@Mod.EventBusSubscriber(modid = TinkersThings.MOD_ID)
public class TESolidCloudPassageway extends TileEntity
{
    private static final int HOP_DELAY = 4;
    private static final EnumFacing[] FACES = EnumFacing.values();
    private static final Map<World, Work> WORK = new WeakHashMap<>();

    private boolean direct;
    // Only currently active remote sources are stored (and each has its inbound face).
    private final Map<BlockPos, EnumFacing> received = new HashMap<>();
    private final PriorityQueue<Arrival> pending = new PriorityQueue<>(Comparator.comparingLong(a -> a.due));

    private static final class Work
    {
        final Map<BlockPos, Integer> topology = new HashMap<>();
        final TreeMap<Long, Set<BlockPos>> wakeups = new TreeMap<>();
    }

    private static final class Route
    {
        final BlockPos pos;
        final int hops;
        final EnumFacing inbound;
        final Route previous;

        Route(BlockPos pos, int hops, EnumFacing inbound, Route previous)
        {
            this.pos = pos;
            this.hops = hops;
            this.inbound = inbound;
            this.previous = previous;
        }
    }

    private static final class Arrival
    {
        final BlockPos source;
        final EnumFacing inbound; // null withdraws this particular source
        final long due;
        final Route route; // null for topology reconciliation, not a direct redstone edge

        Arrival(BlockPos source, EnumFacing inbound, long due, Route route)
        {
            this.source = source;
            this.inbound = inbound;
            this.due = due;
            this.route = route;
        }
    }

    private static Work work(World world)
    {
        return WORK.computeIfAbsent(world, ignored -> new Work());
    }

    private static TESolidCloudPassageway at(World world, BlockPos pos)
    {
        if (!world.isBlockLoaded(pos) || !(world.getBlockState(pos).getBlock() instanceof BlockSolidCloudPassageway))
            return null;
        TileEntity tile = world.getTileEntity(pos);
        return tile instanceof TESolidCloudPassageway && !tile.isInvalid() ? (TESolidCloudPassageway) tile : null;
    }

    public static void placed(World world, BlockPos pos)
    {
        if (world.isRemote) return;
        work(world).topology.put(pos.toImmutable(), 0);
        refreshDirectInput(world, pos);
    }

    public static void removed(World world, BlockPos pos)
    {
        if (world.isRemote) return;
        Work work = work(world);
        work.topology.remove(pos);
        for (EnumFacing face : FACES) work.topology.putIfAbsent(pos.offset(face), 1);
    }

    public static void refreshDirectInput(World world, BlockPos pos)
    {
        if (world.isRemote) return;
        TESolidCloudPassageway tile = at(world, pos);
        if (tile == null) return;
        boolean powered = hasLoadedRedstoneInput(world, pos);
        if (tile.direct == powered) return;
        tile.setDirect(powered);

        // Plan the first arrival by shortest path. Nothing is sent back to the origin,
        // and an alternate path/loop cannot enqueue a second arrival for this edge.
        long now = world.getTotalWorldTime();
        Map<BlockPos, Route> routes = routes(world, pos, 0);
        Work work = work(world);
        for (Route route : routes.values())
        {
            if (route.hops == 0) continue;
            TESolidCloudPassageway target = at(world, route.pos);
            if (target != null) target.enqueue(work, pos, powered ? route.inbound : null, now, route.hops, route);
        }
    }

    // Equivalent to vanilla's indirect power check, without reading unprovided chunks.
    private static boolean hasLoadedRedstoneInput(World world, BlockPos pos)
    {
        for (EnumFacing face : FACES)
        {
            BlockPos neighbor = pos.offset(face);
            if (!world.isBlockLoaded(neighbor)) continue;
            IBlockState state = world.getBlockState(neighbor);
            if (state.getBlock() instanceof BlockSolidCloudPassageway) continue;
            if (state.getBlock().shouldCheckWeakPower(state, world, neighbor, face))
            {
                for (EnumFacing input : FACES)
                {
                    BlockPos from = neighbor.offset(input);
                    if (world.isBlockLoaded(from) && world.getBlockState(from).getStrongPower(world, from, input) > 0)
                        return true;
                }
            }
            else if (state.getWeakPower(world, neighbor, face) > 0) return true;
        }
        return false;
    }

    private void setDirect(boolean powered)
    {
        direct = powered;
        markDirty();
        setOpen();
    }

    private void setOpen()
    {
        IBlockState state = world.getBlockState(pos);
        boolean open = direct || !received.isEmpty();
        if (state.getBlock() instanceof BlockSolidCloudPassageway && state.getValue(BlockSolidCloudPassageway.OPEN) != open)
            world.setBlockState(pos, state.withProperty(BlockSolidCloudPassageway.OPEN, open), 2);
    }

    private void enqueue(Work work, BlockPos source, EnumFacing inbound, long now, int hops, Route route)
    {
        long due = now + (long) HOP_DELAY * hops;
        // Preserve normally ordered pulses. If a newer edge takes a shorter route than an
        // outstanding older edge, discard the older arrival rather than reverting later.
        pending.removeIf(a -> a.source.equals(source) && a.due >= due);
        pending.add(new Arrival(source.toImmutable(), inbound, due, route));
        work.wakeups.computeIfAbsent(due, ignored -> new HashSet<>()).add(pos);
    }

    private void applyArrivals(long now)
    {
        boolean changed = false;
        while (!pending.isEmpty() && pending.peek().due <= now)
        {
            Arrival arrival = pending.remove();
            EnumFacing before = arrival.inbound == null ? received.remove(arrival.source) : received.put(arrival.source, arrival.inbound);
            if (before == arrival.inbound) continue;
            changed = true;
        }
        if (changed)
        {
            markDirty();
            setOpen();
        }
    }

    private static Map<BlockPos, Route> routes(World world, BlockPos origin, int initialHops)
    {
        Map<BlockPos, Route> result = new LinkedHashMap<>();
        if (at(world, origin) == null) return result;
        ArrayDeque<Route> queue = new ArrayDeque<>();
        Route first = new Route(origin, initialHops, null, null);
        queue.add(first);
        result.put(origin, first);
        while (!queue.isEmpty())
        {
            Route route = queue.remove();
            for (EnumFacing face : FACES)
            {
                if (face == route.inbound) continue;
                BlockPos next = route.pos.offset(face);
                if (result.containsKey(next) || at(world, next) == null) continue;
                Route child = new Route(next, route.hops + 1, face.getOpposite(), route);
                queue.add(child);
                result.put(next, child);
            }
        }
        return result;
    }

    private static boolean isRouteIntact(Route route, Set<BlockPos> component, Map<Route, Boolean> cache)
    {
        List<Route> unchecked = new ArrayList<>();
        for (; route != null && !cache.containsKey(route); route = route.previous)
        {
            unchecked.add(route);
            if (!component.contains(route.pos)) break;
        }
        boolean intact = route == null || Boolean.TRUE.equals(cache.get(route));
        for (Route step : unchecked) cache.put(step, intact);
        return intact;
    }

    // Edits and chunk boundaries invalidate preplanned routes. Reconcile only affected
    // loaded components; never trust saved remote inputs as redstone sources.
    private static void reconcileTopology(World world, Work work, long now)
    {
        Map<BlockPos, Integer> dirty = new HashMap<>(work.topology);
        work.topology.clear();
        while (!dirty.isEmpty())
        {
            Map.Entry<BlockPos, Integer> seed = dirty.entrySet().iterator().next();
            Map<BlockPos, Route> component = routes(world, seed.getKey(), seed.getValue());
            dirty.remove(seed.getKey());
            if (component.isEmpty()) continue;

            // Compute the distance to the nearest boundary/edit, even when a whole chunk changed.
            Map<BlockPos, Integer> distance = new HashMap<>();
            PriorityQueue<Route> frontier = new PriorityQueue<>(Comparator.comparingInt(r -> r.hops));
            frontier.add(new Route(seed.getKey(), seed.getValue(), null, null));
            for (BlockPos pos : component.keySet())
            {
                Integer hops = dirty.remove(pos);
                if (hops != null) frontier.add(new Route(pos, hops, null, null));
            }
            while (!frontier.isEmpty())
            {
                Route next = frontier.remove();
                if (distance.putIfAbsent(next.pos, next.hops) != null) continue;
                for (EnumFacing face : FACES)
                {
                    BlockPos neighbor = next.pos.offset(face);
                    if (component.containsKey(neighbor) && !distance.containsKey(neighbor))
                        frontier.add(new Route(neighbor, next.hops + 1, null, null));
                }
            }

            List<TESolidCloudPassageway> tiles = new ArrayList<>();
            Map<Route, Boolean> intact = new IdentityHashMap<>();
            Map<BlockPos, Map<BlockPos, Route>> desired = new HashMap<>();
            for (BlockPos pos : component.keySet())
            {
                TESolidCloudPassageway tile = at(world, pos);
                if (tile == null) continue;
                tiles.add(tile);
                tile.pending.removeIf(a -> a.route == null || !isRouteIntact(a.route, component.keySet(), intact));
                boolean powered = hasLoadedRedstoneInput(world, pos);
                if (tile.direct != powered) tile.setDirect(powered);
                if (!powered) continue;
                for (Route route : routes(world, pos, 0).values())
                {
                    if (route.hops == 0) continue;
                    desired.computeIfAbsent(route.pos, ignored -> new HashMap<>()).put(pos, route);
                }
            }
            for (TESolidCloudPassageway tile : tiles)
            {
                Map<BlockPos, Route> inputs = desired.getOrDefault(tile.pos, Collections.emptyMap());
                Map<BlockPos, Arrival> latest = new HashMap<>();
                for (Arrival a : tile.pending) latest.merge(a.source, a, (x, y) -> x.due > y.due ? x : y);
                Map<BlockPos, EnumFacing> projected = new HashMap<>(tile.received);
                for (Arrival a : latest.values())
                {
                    if (a.inbound == null) projected.remove(a.source);
                    else projected.put(a.source, a.inbound);
                }
                int hops = distance.get(tile.pos);
                for (BlockPos source : projected.keySet())
                    if (!inputs.containsKey(source)) tile.enqueue(work, source, null, now, hops, null);
                for (Map.Entry<BlockPos, Route> input : inputs.entrySet())
                {
                    Route route = input.getValue();
                    EnumFacing before = projected.get(input.getKey());
                    if (before != route.inbound)
                        tile.enqueue(work, input.getKey(), route.inbound, now, Math.max(hops, route.hops), route);
                }
                tile.setOpen();
            }
        }
    }

    @SubscribeEvent
    public static void onWorldTick(TickEvent.WorldTickEvent event)
    {
        if (event.phase != TickEvent.Phase.END || event.world.isRemote) return;
        Work work = WORK.get(event.world);
        if (work == null) return;
        long now = event.world.getTotalWorldTime();
        if (!work.topology.isEmpty()) reconcileTopology(event.world, work, now);
        while (!work.wakeups.isEmpty() && work.wakeups.firstKey() <= now)
        {
            for (BlockPos pos : work.wakeups.pollFirstEntry().getValue())
            {
                TESolidCloudPassageway tile = at(event.world, pos);
                if (tile != null) tile.applyArrivals(now);
            }
        }
        if (work.wakeups.isEmpty() && work.topology.isEmpty()) WORK.remove(event.world);
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState)
    {
        // OPEN is synchronized block metadata, not a replacement of the passageway.
        return oldState.getBlock() != newState.getBlock();
    }

    @Override
    public void onLoad()
    {
        super.onLoad();
        if (world != null && !world.isRemote) work(world).topology.put(pos.toImmutable(), 0);
    }

    @Override
    public void onChunkUnload()
    {
        if (world != null && !world.isRemote) removed(world, pos);
        pending.clear();
        super.onChunkUnload();
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
        super.readFromNBT(nbt);
        direct = nbt.getBoolean("Direct");
        received.clear();
        NBTTagList inputs = nbt.getTagList("Received", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < inputs.tagCount(); i++)
        {
            NBTTagCompound input = inputs.getCompoundTagAt(i);
            int direction = input.getByte("From");
            if (direction >= 0 && direction < FACES.length)
                received.put(BlockPos.fromLong(input.getLong("Source")), FACES[direction]);
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt)
    {
        super.writeToNBT(nbt);
        nbt.setBoolean("Direct", direct);
        NBTTagList inputs = new NBTTagList();
        for (Map.Entry<BlockPos, EnumFacing> entry : received.entrySet())
        {
            NBTTagCompound input = new NBTTagCompound();
            input.setLong("Source", entry.getKey().toLong());
            input.setByte("From", (byte) entry.getValue().getIndex());
            inputs.appendTag(input);
        }
        nbt.setTag("Received", inputs);
        return nbt;
    }
}
