package firok.tiths.block.table;

import firok.tiths.client.particle.ParticleBuilder;
import firok.tiths.client.particle.ParticleType;
import firok.tiths.common.Blocks;
import firok.tiths.common.Keys;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

import static firok.tiths.util.Predicates.canTick;

public class BlockSunlightConvergingTable
	extends BlockConvergingTableBase
{
	public boolean hasMainBlock(World world, BlockPos posCenter, int offsetX, int offsetZ)
	{
		return world.getBlockState(posCenter.add(offsetX,2,offsetZ)).getBlock() == Blocks.blockSunStone;
	}
	public boolean hasSubBlock(World world, BlockPos posCenter, int offsetX, int offsetZ)
	{
		return world.getBlockState(posCenter.add(offsetX,1,offsetZ)).getBlock() == net.minecraft.init.Blocks.OBSIDIAN &&
				world.getBlockState(posCenter.add(offsetX,0,offsetZ)).getBlock() == net.minecraft.init.Blocks.OBSIDIAN;
	}

	@Override
	public boolean checkTime(World world)
	{
		return canTick(world,80,9) && world.isDaytime();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void spawnParticles(World world, BlockPos pos, Random rand)
	{
		ParticleBuilder.create(ParticleType.STAR)
				.pos(
						pos.getX() + rand.nextFloat(),
						pos.getY() + 0.5 + rand.nextFloat() * 0.5,
						pos.getZ() + rand.nextFloat()
				)
				.clr(Keys.colorSunStone)
				.spawn(world);
	}
}
