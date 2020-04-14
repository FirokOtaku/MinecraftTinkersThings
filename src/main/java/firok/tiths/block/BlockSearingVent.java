package firok.tiths.block;

import firok.tiths.entity.special.LogicSearing;
import firok.tiths.util.EntityFinders;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
/**
 * 焦黑通风孔
 */
public class BlockSearingVent extends Block
{
	public BlockSearingVent()
	{
		super(Material.ROCK);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		boolean ret;

		ItemStack stackHeldMain=player.getHeldItemMainhand();
		if(stackHeldMain.getItem() != Items.FLINT_AND_STEEL) return false;

		if(EntityFinders.Nearby(world,pos,2,en->en instanceof LogicSearing).size()>0) return false;

		LogicSearing entity=new LogicSearing(world);
		ret=entity.initFromPos(pos);
		System.out.println(ret);
		entity.setPosition(pos.getX(),pos.getY(),pos.getZ());
		if(ret)
		{
			world.spawnEntity(entity);
		}
		return ret;
	}
}
