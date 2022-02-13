package firok.tiths.event;

import firok.tiths.data.TithsBlockTagProvider;
import firok.tiths.data.TithsMaterialProvider;
import firok.tiths.data.TithsRecipeProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenEvents
{
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event)
	{
		DataGenerator gen = event.getGenerator();
		ExistingFileHelper file = event.getExistingFileHelper();

		if (event.includeServer())
		{
			gen.addProvider(new TithsRecipeProvider(gen, file));
			gen.addProvider(new TithsMaterialProvider(gen));
			gen.addProvider(new TithsBlockTagProvider(gen, file));
		}
//        else
//        {
//
//        }
	}
}
