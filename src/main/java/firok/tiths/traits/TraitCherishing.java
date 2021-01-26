package firok.tiths.traits;

import firok.tiths.util.Predicates;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.traits.AbstractTrait;

import java.util.Random;

import static firok.tiths.common.Keys.colorTraitCherishing;
import static firok.tiths.common.Keys.nameTraitCherishing;
import static firok.tiths.util.Predicates.canTrigger;

/**
 * 缅怀
 */
public class TraitCherishing extends AbstractTrait
{
	public TraitCherishing()
	{
		super(nameTraitCherishing,colorTraitCherishing);
	}

	@Override
	public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit)
	{
		World world=target.world;
		if(world.isRemote || target.isEntityAlive()) return;
		Random rand=world.rand;

		final IBlockState stateRedFlower=Blocks.RED_FLOWER.getDefaultState();
		final IBlockState stateYellowFlower=Blocks.YELLOW_FLOWER.getDefaultState();

		BlockPos pos=target.getPosition();
		FOR_X:for(int offsetX=-1;offsetX<=1;offsetX++)
		{
			FOR_Z:for(int offsetZ=-1;offsetZ<=1;offsetZ++)
			{
				if(canTrigger(rand,0.35)) continue FOR_Z;

				boolean wasDirt=false;
				FOR_Y:for(int offsetY=-3;offsetY<=1;offsetY++)
				{
					BlockPos posTemp=pos.add(offsetX,offsetY,offsetZ);
					IBlockState stateTemp=world.getBlockState(posTemp);

					if(Predicates.isAir(stateTemp) && wasDirt)
					{
						boolean color=rand.nextBoolean();
						world.setBlockState(posTemp,color?stateRedFlower:stateYellowFlower);
					}

					wasDirt = Predicates.isDirt(stateTemp);
				}
			}
		}
	}
}
