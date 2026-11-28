package firok.tiths.client;

import firok.tiths.TinkersThings;
import firok.tiths.common.SearedBuildingMaterials;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.mantle.item.ItemBlockMeta;
import slimeknights.mantle.util.LocUtils;
import slimeknights.tconstruct.smeltery.block.BlockSearedGlass;
import slimeknights.tconstruct.smeltery.block.BlockTank;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(modid = TinkersThings.MOD_ID, value = Side.CLIENT)
public final class SearedBuildingModels
{
    private SearedBuildingModels() {}

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event)
    {
        for (SearedBuildingMaterials family : SearedBuildingMaterials.values())
        {
            registerModel(family.powder, 0, "inventory");
            registerModel(family.brick, 0, "inventory");
            for (Block block : family.stairs.values()) registerModel(Item.getItemFromBlock(block), 0, "inventory");
            for (Block block : new Block[]{family.stone, family.slab, family.slab2})
            {
                ((ItemBlockMeta) Item.getItemFromBlock(block)).registerItemModels();
            }
            ModelLoader.setCustomStateMapper(family.glass, new StateMap.Builder().ignore(BlockSearedGlass.TYPE).build());
            registerModel(Item.getItemFromBlock(family.glass), 0, "inventory");
            for (BlockTank.TankType type : BlockTank.TankType.values())
            {
                registerModel(Item.getItemFromBlock(family.tank), type.getMeta(), type.getName());
            }
        }
    }

    private static void registerModel(Item item, int meta, String variant)
    {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), variant));
    }

    @SubscribeEvent
    public static void addBuildingTooltip(ItemTooltipEvent event)
    {
        Item item = event.getItemStack().getItem();
        if (!(item instanceof ItemBlock)) return;
        Block block = ((ItemBlock) item).getBlock();
        for (SearedBuildingMaterials family : SearedBuildingMaterials.values())
        {
            if (block == family.stone || block == family.glass || block == family.slab || block == family.slab2
                    || block == family.tank || family.stairs.containsValue(block))
            {
                addTooltip(event, "tooltip.tiths.seared_building.no_structure");
                if (block == family.tank) addTooltip(event, "tile.tconstruct.seared_tank.tooltip");
                return;
            }
        }
    }

    private static void addTooltip(ItemTooltipEvent event, String key)
    {
        event.getToolTip().addAll(LocUtils.getTooltips(TextFormatting.GRAY + LocUtils.translateRecursive(key)));
    }

    @SubscribeEvent
    public static void registerTankColors(ColorHandlerEvent.Item event)
    {
        for (SearedBuildingMaterials family : SearedBuildingMaterials.values())
        {
            event.getItemColors().registerItemColorHandler((stack, tint) -> {
                if (tint != 0 || !stack.hasTagCompound()) return 0xffffff;
                FluidStack fluid = FluidStack.loadFluidStackFromNBT(stack.getTagCompound());
                return fluid == null ? 0xffffff : fluid.getFluid().getColor(fluid);
            }, family.tank);
        }
    }
}
