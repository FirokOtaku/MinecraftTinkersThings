package firok.tiths;

import firok.tiths.client.book.Books;
import firok.tiths.client.render.tile.TESRPalantir;
import firok.tiths.tile.TEPalantir;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy
{
    public ClientProxy()
    {
        Books.initBooks();
    }

    @Override
    public void init()
    {
        ClientRegistry.bindTileEntitySpecialRenderer(TEPalantir.class, new TESRPalantir());
    }
}
