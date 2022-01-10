package firok.tiths.event;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.LiteralCommandNode;
import firok.tiths.TinkersThings;
import firok.tiths.command.TestSpawnFluidSlimeCommand;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = TinkersThings.MOD_ID)
public class CommandEvents
{
	@SubscribeEvent
	public static void onServerStaring(RegisterCommandsEvent event) {
		CommandDispatcher<CommandSource> dispatcher = event.getDispatcher();
		LiteralCommandNode<CommandSource> cmd = dispatcher.register(
				Commands.literal(TinkersThings.MOD_ID).then(
						Commands.literal("test")
//								.requires((commandSource) -> commandSource.hasPermissionLevel(0))
								.executes(TestSpawnFluidSlimeCommand.instance)
				)
		);
		dispatcher.register(Commands.literal("bs").redirect(cmd));
	}
}
