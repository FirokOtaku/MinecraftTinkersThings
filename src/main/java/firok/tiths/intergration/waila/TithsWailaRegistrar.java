package firok.tiths.intergration.waila;

import firok.tiths.tile.TECrystallizer;
import mcp.mobius.waila.api.IWailaRegistrar;

public final class TithsWailaRegistrar
{
    private TithsWailaRegistrar() {}

    public static void wailaCallback(IWailaRegistrar registrar)
    {
        CrystallizerDataProvider provider = new CrystallizerDataProvider();
        registrar.registerBodyProvider(provider, TECrystallizer.class);
        registrar.registerNBTProvider(provider, TECrystallizer.class);
    }
}
