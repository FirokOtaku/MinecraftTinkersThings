package firok.tiths.block.table;

import c4.conarm.common.armor.utils.ArmorHelper;
import c4.conarm.lib.armor.ArmorCore;
import firok.tiths.TinkersThings;
import firok.tiths.block.BlockCompressed;
import firok.tiths.client.particle.ParticleBuilder;
import firok.tiths.client.particle.ParticleType;
import firok.tiths.common.Blocks;
import firok.tiths.common.Keys;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.library.tools.ToolCore;
import slimeknights.tconstruct.library.utils.ToolHelper;

import java.util.Random;

import static firok.tiths.util.Predicates.canTrigger;

public abstract class BlockConvergingTableBase
	extends BlockTableBase
{
	abstract boolean hasMainBlock(World world, BlockPos posCenter, int offsetX, int offsetZ);
	abstract boolean hasSubBlock(World world, BlockPos posCenter, int offsetX, int offsetZ);

	abstract public boolean checkTime(World world);

	public boolean checkSurroundings(World world, BlockPos posCenter)
	{
		return hasMainBlock(world,posCenter,-2,-2) &&
				hasMainBlock(world,posCenter,-2,2) &&
				hasMainBlock(world,posCenter,2,-2) &&
				hasMainBlock(world,posCenter,2,2) &&

				hasSubBlock(world,posCenter,-2,-2) &&
				hasSubBlock(world,posCenter,-2,2) &&
				hasSubBlock(world,posCenter,2,-2) &&
				hasSubBlock(world,posCenter,2,2);
	}

	@Override
	public void onPlayerCollideWithBlock(World world, BlockPos pos, IBlockState state, EntityPlayer player)
	{
		if(!world.isRemote && checkTime(world) && checkSurroundings(world, pos))
		{
			Iterable<ItemStack> equipments = player.getEquipmentAndArmor();
			for(ItemStack equipment : equipments)
			{
				if(equipment == ItemStack.EMPTY) continue;
				Item item = equipment.getItem();

				if(item instanceof ToolCore)
				{
					ToolHelper.healTool(equipment,1,player);
				}
				else if(TinkersThings.enableConarm() && item instanceof ArmorCore)
				{
					ArmorHelper.healArmor(equipment,1,player,0);
				}
			}
		}
	}

	@SideOnly(Side.CLIENT)
	abstract public void spawnParticles(World world, BlockPos pos, Random rand);

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
	{
		if(canTrigger(rand,0.135))
		{
			spawnParticles(worldIn, pos, rand);
		}
	}
}
