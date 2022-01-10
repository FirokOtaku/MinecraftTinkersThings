package firok.tiths.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import firok.tiths.entity.FluidSlimeEntity;
import firok.tiths.entity.TithsEntities;
import firok.tiths.util.EntityFinders;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import slimeknights.tconstruct.fluids.TinkerFluids;

import java.util.List;

public class TestSpawnFluidSlimeCommand implements Command<CommandSource>
{
	public static final TestSpawnFluidSlimeCommand instance = new TestSpawnFluidSlimeCommand();

	@Override
	public int run(CommandContext<CommandSource> context) throws CommandSyntaxException
	{
		System.out.println("run command test spawn fluid");
		try
		{
			CommandSource source = context.getSource();
			World world = source.getWorld();
			Vector3d pos = source.getPos();
			FluidSlimeEntity slime = new FluidSlimeEntity(TithsEntities.etFluidSlime.get(), world);
			slime.setPosition(pos.x, pos.y, pos.z);
			slime.setSlimeSize(0, false);
			slime.setFluid(TinkerFluids.blood.get());

			world.addEntity(slime);

			List<? extends Entity> list = EntityFinders.Nearby(world, new BlockPos(pos), 10);
			for(Entity en : list)
			{
				System.out.println("en: "+en.getDisplayName());
			}
		}
		catch (Exception e)
		{
			System.out.println("执行指令发生错误:");
			e.printStackTrace();
		}
		return 1;
	}
}
