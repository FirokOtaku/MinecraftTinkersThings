package firok.tiths.block.pedestal;

import firok.tiths.tile.pedestal.TilePedestalBase;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

/**
 * this pedestal is creative only and used to build some service site. <br>
 * any player in creative mode could change the stack by right-clicking pedestal when sneaking; <br>
 * other player could right-click pedestal to have one copy of the stack in pedestal. <br>
 */
public class CreativePedestalBlock extends PedestalBlockBase
{
	public CreativePedestalBlock()
	{
		super(Properties.create(Material.GOURD)
				.notSolid()
				.hardnessAndResistance(-1.0F, 3600000.0F)
				.noDrops());
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
	{
		TilePedestalBase te = getTilePedestalAt(world, pos);
		ItemStack stackHeld = player.getHeldItemMainhand();
		ItemStack stackPedestal = te.getStackPedestal();

		if(player.isCreative()) // edit stack
		{
			te.setStackPedestal(stackHeld);
			return ActionResultType.SUCCESS;
		}
		else if(!stackPedestal.isEmpty()) // give stack
		{
			player.addItemStackToInventory(stackPedestal.copy());
			return ActionResultType.SUCCESS;
		}
		else return ActionResultType.PASS;
	}
}
