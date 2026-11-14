package firok.tiths.common;

import firok.tiths.TinkersThings;
import firok.tiths.gen.BagType;
import firok.tiths.gen.EquipKind;
import firok.tiths.gen.GenContext;
import firok.tiths.gen.TinkerEquipmentGenerator;
import firok.tiths.gen.ToolTier;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.village.MerchantRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import slimeknights.tconstruct.shared.TinkerCommons;
import slimeknights.tconstruct.shared.TinkerFluids;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;
import slimeknights.tconstruct.tools.TinkerTools;

import javax.annotation.Nullable;

/**
 * 工具匠人 / 盔甲匠人村民职业与五级交易.
 * <p>
 * 1.12 无独立工作站点方块绑定; 主题上对齐工具站 / 焦黑砖相关方块.
 * <p>
 * 交易注意: {@code populateBuyingList} 会把某一级里的全部 {@link EntityVillager.ITradeList}
 * 都加入列表, 因此每级只应注册 2 条 (与常见原版职业升级节奏一致); 若同级有多种候选,
 * 用 {@link #pickOne} 包成单个槽位.
 */
@Mod.EventBusSubscriber(modid = TinkersThings.MOD_ID)
public final class Villagers
{
    public static VillagerRegistry.VillagerProfession professionToolTinker;
    public static VillagerRegistry.VillagerProfession professionArmorTinker;

    private Villagers() {}

    @SubscribeEvent
    public static void register(RegistryEvent.Register<VillagerRegistry.VillagerProfession> event)
    {
        IForgeRegistry<VillagerRegistry.VillagerProfession> registry = event.getRegistry();

        if (Configs.Gameplay.enable_tool_tinker_villager)
        {
            // 构造函数首参即 registry name, 内部已 setRegistryName, 勿再调用
            professionToolTinker = new VillagerRegistry.VillagerProfession(
                    TinkersThings.MOD_ID + ":tool_tinker",
                    TinkersThings.MOD_ID + ":textures/entity/villager/tool_tinker.png",
                    TinkersThings.MOD_ID + ":textures/entity/villager/tool_tinker_zombie.png"
            );
            registry.register(professionToolTinker);
            buildToolCareer(professionToolTinker);
        }

        // RegistryEvent 时 enableConarm() 可能尚未初始化 hasConarm, 直接查 Loader + 配置
        boolean conarmOk = Loader.isModLoaded(TinkersThings.CONARM_ID) && Configs.General.enable_conarm;
        if (Configs.Gameplay.enable_armor_tinker_villager && conarmOk)
        {
            professionArmorTinker = new VillagerRegistry.VillagerProfession(
                    TinkersThings.MOD_ID + ":armor_tinker",
                    TinkersThings.MOD_ID + ":textures/entity/villager/armor_tinker.png",
                    TinkersThings.MOD_ID + ":textures/entity/villager/armor_tinker_zombie.png"
            );
            registry.register(professionArmorTinker);
            buildArmorCareer(professionArmorTinker);
        }
    }

    /**
     * 强制写入职业并清空已有交易 / 生涯进度, 使下次打开交易时按新职业重新生成.
     * {@code setProfession} 本身不会清 Offers / Career.
     */
    public static void applyProfessionFresh(EntityVillager villager, VillagerRegistry.VillagerProfession profession)
    {
        if (villager == null || profession == null) return;
        villager.setProfession(profession);

        NBTTagCompound tag = new NBTTagCompound();
        villager.writeEntityToNBT(tag);
        tag.setString("ProfessionName", profession.getRegistryName().toString());
        tag.setInteger("Career", 0);
        tag.setInteger("CareerLevel", 0);
        // 空 Offers -> read 后 buyingList 为空表; Career 0/0 使下次 populate 走「初始级」分支
        NBTTagCompound offers = new NBTTagCompound();
        offers.setTag("Recipes", new NBTTagList());
        tag.setTag("Offers", offers);
        villager.readEntityFromNBT(tag);
        villager.setProfession(profession);
    }

    private static void buildToolCareer(VillagerRegistry.VillagerProfession profession)
    {
        new VillagerRegistry.VillagerCareer(profession, "tool_tinker")
                .addTrade(1,
                        pickOne(
                                sell(stack(TinkerCommons.searedBrick, 4), 1, 2),
                                buy(stack(TinkerCommons.searedBrick, 8), 1, 1)
                        ),
                        pickOne(
                                sell(new ItemStack(Items.SLIME_BALL, 4), 1, 2),
                                buy(new ItemStack(Items.SLIME_BALL, 6), 1, 1)
                        )
                )
                .addTrade(2,
                        pickOne(
                                sell(new ItemStack(Item.getItemFromBlock(TinkerSmeltery.searedTank), 1), 4, 7),
                                sell(new ItemStack(Item.getItemFromBlock(TinkerSmeltery.searedBlock), 4), 2, 4)
                        ),
                        pickOne(
                                fluidBucketTrade(TinkerFluids.iron, 5, 8),
                                fluidBucketTrade(TinkerFluids.blueslime, 6, 10)
                        )
                )
                .addTrade(3,
                        pickOne(
                                sell(new ItemStack(Items.REDSTONE, 8), 2, 4),
                                sell(new ItemStack(Items.QUARTZ, 4), 2, 4)
                        ),
                        pickOne(
                                randomToolPartTrade(false),
                                sell(new ItemStack(Item.getItemFromBlock(TinkerTools.toolTables), 1), 6, 10)
                        )
                )
                .addTrade(4,
                        generatedGearTrade(ToolTier.LOW, EquipKind.TOOL, 10, 16),
                        bagTrade(BagType.TOOL, ToolTier.LOW, 12, 18)
                )
                .addTrade(5,
                        pickOne(
                                generatedGearTrade(ToolTier.MID, EquipKind.TOOL, 16, 24),
                                generatedGearTrade(ToolTier.HIGH, EquipKind.TOOL, 22, 32)
                        ),
                        pickOne(
                                bagTrade(BagType.TOOL, ToolTier.MID, 18, 26),
                                bagTrade(BagType.TINKER, ToolTier.HIGH, 20, 28)
                        )
                );
    }

    private static void buildArmorCareer(VillagerRegistry.VillagerProfession profession)
    {
        new VillagerRegistry.VillagerCareer(profession, "armor_tinker")
                .addTrade(1,
                        pickOne(
                                sell(stack(TinkerCommons.searedBrick, 4), 1, 2),
                                buy(stack(TinkerCommons.searedBrick, 8), 1, 1)
                        ),
                        pickOne(
                                sell(new ItemStack(Items.SLIME_BALL, 4), 1, 2),
                                buy(new ItemStack(Items.LEATHER, 6), 1, 2)
                        )
                )
                .addTrade(2,
                        pickOne(
                                sell(new ItemStack(Item.getItemFromBlock(TinkerSmeltery.searedTank), 1), 4, 7),
                                sell(new ItemStack(Item.getItemFromBlock(TinkerSmeltery.searedBlock), 4), 2, 4)
                        ),
                        pickOne(
                                fluidBucketTrade(TinkerFluids.iron, 5, 8),
                                fluidBucketTrade(TinkerFluids.blueslime, 6, 10)
                        )
                )
                .addTrade(3,
                        pickOne(
                                sell(new ItemStack(Items.REDSTONE, 8), 2, 4),
                                sell(new ItemStack(Items.DIAMOND, 1), 6, 10)
                        ),
                        pickOne(
                                randomToolPartTrade(true),
                                sell(new ItemStack(Items.IRON_INGOT, 4), 2, 4)
                        )
                )
                .addTrade(4,
                        generatedGearTrade(ToolTier.LOW, EquipKind.ARMOR, 12, 18),
                        bagTrade(BagType.ARMOR, ToolTier.LOW, 14, 20)
                )
                .addTrade(5,
                        pickOne(
                                generatedGearTrade(ToolTier.MID, EquipKind.ARMOR, 18, 26),
                                generatedGearTrade(ToolTier.HIGH, EquipKind.ARMOR, 24, 34)
                        ),
                        pickOne(
                                bagTrade(BagType.ARMOR, ToolTier.MID, 20, 28),
                                bagTrade(BagType.TINKER, ToolTier.HIGH, 20, 28)
                        )
                );
    }

    private static ItemStack stack(ItemStack template, int count)
    {
        ItemStack copy = template.copy();
        copy.setCount(count);
        return copy;
    }

    /** 从候选中随机挑 1 条实际写入, 占交易槽位的一格. */
    private static EntityVillager.ITradeList pickOne(EntityVillager.ITradeList... options)
    {
        return (merchant, recipeList, random) -> {
            if (options == null || options.length == 0) return;
            options[random.nextInt(options.length)].addMerchantRecipe(merchant, recipeList, random);
        };
    }

    /** 村民出售物品给玩家 (玩家付绿宝石). */
    private static EntityVillager.ITradeList sell(ItemStack selling, int minEmeralds, int maxEmeralds)
    {
        return (merchant, recipeList, random) -> {
            int price = minEmeralds + random.nextInt(Math.max(1, maxEmeralds - minEmeralds + 1));
            recipeList.add(new MerchantRecipe(new ItemStack(Items.EMERALD, price), selling.copy()));
        };
    }

    /** 村民收购玩家物品 (玩家付物品换绿宝石). */
    private static EntityVillager.ITradeList buy(ItemStack buying, int minEmeralds, int maxEmeralds)
    {
        return (merchant, recipeList, random) -> {
            int price = minEmeralds + random.nextInt(Math.max(1, maxEmeralds - minEmeralds + 1));
            recipeList.add(new MerchantRecipe(buying.copy(), new ItemStack(Items.EMERALD, price)));
        };
    }

    private static EntityVillager.ITradeList fluidBucketTrade(Fluid fluid, int minE, int maxE)
    {
        return (merchant, recipeList, random) -> {
            ItemStack bucket = filledBucket(fluid);
            if (bucket.isEmpty()) return;
            int price = minE + random.nextInt(Math.max(1, maxE - minE + 1));
            recipeList.add(new MerchantRecipe(new ItemStack(Items.EMERALD, price), bucket));
        };
    }

    private static ItemStack filledBucket(@Nullable Fluid fluid)
    {
        if (fluid == null) return ItemStack.EMPTY;
        ItemStack bucket = FluidUtil.getFilledBucket(new FluidStack(fluid, Fluid.BUCKET_VOLUME));
        return bucket == null ? ItemStack.EMPTY : bucket;
    }

    private static EntityVillager.ITradeList randomToolPartTrade(boolean armorBias)
    {
        return (merchant, recipeList, random) -> {
            ItemStack offer;
            if (armorBias)
            {
                offer = new ItemStack(Items.IRON_INGOT, 2 + random.nextInt(3));
            }
            else if (random.nextBoolean())
            {
                offer = TinkerCommons.searedBrick.copy();
                offer.setCount(2 + random.nextInt(4));
            }
            else
            {
                offer = new ItemStack(Items.QUARTZ, 2 + random.nextInt(3));
            }
            int price = 2 + random.nextInt(4);
            recipeList.add(new MerchantRecipe(new ItemStack(Items.EMERALD, price), offer));
        };
    }

    private static EntityVillager.ITradeList generatedGearTrade(ToolTier tier, EquipKind kind, int minE, int maxE)
    {
        return (merchant, recipeList, random) -> {
            ItemStack gear = TinkerEquipmentGenerator.generateOne(GenContext.VILLAGER, tier, kind, random);
            if (gear.isEmpty()) return;
            int price = minE + random.nextInt(Math.max(1, maxE - minE + 1));
            recipeList.add(new MerchantRecipe(new ItemStack(Items.EMERALD, price), gear));
        };
    }

    private static EntityVillager.ITradeList bagTrade(BagType type, ToolTier tier, int minE, int maxE)
    {
        return (merchant, recipeList, random) -> {
            Item bag = firok.tiths.common.Items.getLootBag(type, tier);
            if (bag == null) return;
            int price = minE + random.nextInt(Math.max(1, maxE - minE + 1));
            recipeList.add(new MerchantRecipe(new ItemStack(Items.EMERALD, price), new ItemStack(bag)));
        };
    }
}
