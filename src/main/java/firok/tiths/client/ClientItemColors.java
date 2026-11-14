package firok.tiths.client;

import firok.tiths.TinkersThings;
import firok.tiths.common.Items;
import firok.tiths.item.ItemLootBag;
import firok.tiths.item.ItemTraitToken;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * 客户端物品染色: 袋子按品级稀有度; 词条道具按绑定词条色.
 */
@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(value = Side.CLIENT, modid = TinkersThings.MOD_ID)
public final class ClientItemColors
{
    private ClientItemColors() {}

    @SubscribeEvent
    public static void onItemColors(ColorHandlerEvent.Item event)
    {
        event.getItemColors().registerItemColorHandler((stack, tintIndex) -> {
            if (tintIndex != 0) return 0xFFFFFF;
            Item item = stack.getItem();
            if (item instanceof ItemLootBag)
            {
                return ((ItemLootBag) item).getTier().getRarityTintColor();
            }
            return 0xFFFFFF;
        }, Items.allLootBags());

        event.getItemColors().registerItemColorHandler((stack, tintIndex) -> {
            if (tintIndex != 0) return 0xFFFFFF;
            Item item = stack.getItem();
            if (item instanceof ItemTraitToken)
            {
                return ((ItemTraitToken) item).getTintColor();
            }
            return 0xFFFFFF;
        }, Items.traitTokenMasterwork);
    }
}
