package firok.tiths.gen;

import firok.tiths.TinkersThings;
import firok.tiths.common.Configs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Random;
import java.util.Set;

import static firok.tiths.util.Predicates.canTrigger;

/**
 * 对配置白名单内的怪物, 在自然刷新并完成原版装备后, 为空槽位按概率填充随机匠魂成品.
 * <p>
 * 防刷约定:
 * <ul>
 *     <li>仅当本次入世来自 {@link LivingSpawnEvent.SpecialSpawn} (自然刷新 / 刷怪笼等)
 *     时才尝试填充; 生物球捕获再放出通常只触发 {@link EntityJoinWorldEvent}, 不会再次填充.</li>
 *     <li>无论是否真正装上物品, 只要处理过一次就会在实体 {@code ForgeData} 写入永久标记
 *     {@code tiths_mob_gear_rolled}; 完整序列化 NBT 的捕获道具会带上该标记, 放出后仍跳过.</li>
 * </ul>
 */
@Mod.EventBusSubscriber(modid = TinkersThings.MOD_ID)
public final class MobRandomEquipment
{
    /** 永久标记: 已对该实体执行过一次随机装备判定 (随实体 NBT / ForgeData 持久化). */
    private static final String NBT_ROLLED = "tiths_mob_gear_rolled";
    /** 临时标记: 本次是 SpecialSpawn 路径, 允许在随后的 JoinWorld 里填充. */
    private static final String NBT_PENDING = "tiths_mob_gear_pending";

    private static final ToolTier[] TIER_ORDER = {
            ToolTier.TOP, ToolTier.HIGH, ToolTier.MID, ToolTier.LOW
    };

    private static String[] cachedEntitySource;
    private static Set<String> cachedEntityIds = Collections.emptySet();

    private MobRandomEquipment() {}

    /**
     * 自然刷新 / 刷怪笼等会走 SpecialSpawn; 在原版 onInitialSpawn 之前打上 pending.
     * 捕获再放出一般不会触发此事件.
     */
    @SubscribeEvent
    public static void onSpecialSpawn(LivingSpawnEvent.SpecialSpawn event)
    {
        World world = event.getWorld();
        if (world == null || world.isRemote) return;

        EntityLivingBase base = event.getEntityLiving();
        if (!(base instanceof EntityLiving)) return;

        EntityLiving living = (EntityLiving) base;
        NBTTagCompound data = living.getEntityData();
        if (data.getBoolean(NBT_ROLLED)) return;

        data.setBoolean(NBT_PENDING, true);
    }

    @SubscribeEvent
    public static void onEntityJoinWorld(EntityJoinWorldEvent event)
    {
        World world = event.getWorld();
        if (world == null || world.isRemote) return;

        Entity entity = event.getEntity();
        if (!(entity instanceof EntityLiving)) return;

        EntityLiving living = (EntityLiving) entity;
        NBTTagCompound data = living.getEntityData();

        // 已判定过: 清掉可能残留的 pending, 绝不重复填充
        if (data.getBoolean(NBT_ROLLED))
        {
            if (data.hasKey(NBT_PENDING))
            {
                data.removeTag(NBT_PENDING);
            }
            return;
        }

        // 非 SpecialSpawn 入世 (读档 / 生物球放出 / 召唤蛋等): 不填充
        if (!data.getBoolean(NBT_PENDING)) return;

        data.removeTag(NBT_PENDING);
        // 无论白名单或是否装上物品, 都立刻落永久标记, 避免后续捕获放出再刷
        data.setBoolean(NBT_ROLLED, true);

        if (!isWhitelisted(living)) return;

        Random random = world.rand;
        tryEquipMainhand(living, random);
        tryEquipArmor(living, EntityEquipmentSlot.HEAD, Configs.Gameplay.mob_random_equip_armor_chance_helmet, random);
        tryEquipArmor(living, EntityEquipmentSlot.CHEST, Configs.Gameplay.mob_random_equip_armor_chance_chest, random);
        tryEquipArmor(living, EntityEquipmentSlot.LEGS, Configs.Gameplay.mob_random_equip_armor_chance_legs, random);
        tryEquipArmor(living, EntityEquipmentSlot.FEET, Configs.Gameplay.mob_random_equip_armor_chance_boots, random);
    }

    private static void tryEquipMainhand(EntityLiving living, Random random)
    {
        if (!living.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND).isEmpty()) return;

        ItemStack stack = rollAndGenerateHand(random);
        if (stack.isEmpty()) return;

        living.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, stack);
        living.setDropChance(EntityEquipmentSlot.MAINHAND, (float) Configs.Gameplay.mob_random_equip_drop_chance_hand);
    }

    private static void tryEquipArmor(EntityLiving living, EntityEquipmentSlot slot, double slotChance, Random random)
    {
        if (!TinkersThings.enableConarm()) return;
        if (!living.getItemStackFromSlot(slot).isEmpty()) return;
        if (!canTrigger(random, slotChance)) return;

        ItemStack stack = rollAndGenerateArmor(slot, random);
        if (stack.isEmpty()) return;

        living.setItemStackToSlot(slot, stack);
        living.setDropChance(slot, (float) Configs.Gameplay.mob_random_equip_drop_chance_armor);
    }

    private static ItemStack rollAndGenerateHand(Random random)
    {
        for (ToolTier tier : TIER_ORDER)
        {
            if (!canTrigger(random, getTierChance(tier))) continue;

            // 主手以武器为主 (含弓弩等 LAUNCHER), 偶尔给工具
            EquipKind kind = random.nextFloat() < 0.75f ? EquipKind.WEAPON : EquipKind.TOOL;
            GenResult result = TinkerEquipmentGenerator.generate(GenContext.LOOT, tier, kind, random);
            if (result.isVanillaFallback()) continue;

            ItemStack stack = result.firstOrEmpty();
            if (!stack.isEmpty())
            {
                return stack;
            }
        }
        return ItemStack.EMPTY;
    }

    private static ItemStack rollAndGenerateArmor(EntityEquipmentSlot slot, Random random)
    {
        for (ToolTier tier : TIER_ORDER)
        {
            if (!canTrigger(random, getTierChance(tier))) continue;

            GenResult result = TinkerEquipmentGenerator.generateArmorForSlot(GenContext.LOOT, tier, slot, random);
            ItemStack stack = result.firstOrEmpty();
            if (!stack.isEmpty())
            {
                return stack;
            }
        }
        return ItemStack.EMPTY;
    }

    private static double getTierChance(ToolTier tier)
    {
        switch (tier)
        {
            case TOP:
                return Configs.Gameplay.mob_random_equip_tier_chance_top;
            case HIGH:
                return Configs.Gameplay.mob_random_equip_tier_chance_high;
            case MID:
                return Configs.Gameplay.mob_random_equip_tier_chance_mid;
            case LOW:
            default:
                return Configs.Gameplay.mob_random_equip_tier_chance_low;
        }
    }

    private static boolean isWhitelisted(EntityLiving living)
    {
        String id = resolveEntityId(living);
        return id != null && allowedEntityIds().contains(id);
    }

    @Nullable
    private static String resolveEntityId(Entity entity)
    {
        ResourceLocation key = EntityList.getKey(entity);
        return key == null ? null : key.toString().toLowerCase(Locale.ROOT);
    }

    private static Set<String> allowedEntityIds()
    {
        String[] source = Configs.Gameplay.mob_random_equip_entities;
        if (source != cachedEntitySource)
        {
            cachedEntitySource = source;
            cachedEntityIds = buildEntityIdSet(source);
        }
        return cachedEntityIds;
    }

    private static Set<String> buildEntityIdSet(String[] source)
    {
        if (source == null || source.length == 0)
        {
            return Collections.emptySet();
        }
        Set<String> out = new HashSet<>();
        for (String raw : source)
        {
            String normalized = normalizeEntityId(raw);
            if (normalized != null)
            {
                out.add(normalized);
            }
        }
        return out;
    }

    @Nullable
    private static String normalizeEntityId(String raw)
    {
        if (raw == null) return null;
        String id = raw.trim().toLowerCase(Locale.ROOT);
        if (id.isEmpty()) return null;
        if (id.indexOf(':') < 0)
        {
            id = "minecraft:" + id;
        }
        return id;
    }
}
