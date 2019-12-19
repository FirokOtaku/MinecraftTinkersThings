package firok.tiths.traits;

import firok.tiths.util.Actions;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.traits.AbstractTrait;

import java.util.Random;

import static firok.tiths.common.Keys.colorTraitTreasureDetecting;
import static firok.tiths.common.Keys.nameTraitTreasureDetecting;
import static firok.tiths.util.Predicates.canTrigger;
import static firok.tiths.util.Predicates.isStone;

// 宝藏感知
public class TraitTreasureDetecting extends AbstractTrait
{
	public TraitTreasureDetecting()
	{
		super(nameTraitTreasureDetecting, colorTraitTreasureDetecting);
	}

	@Override
	public void afterBlockBreak(ItemStack tool, World world, IBlockState state, BlockPos pos, EntityLivingBase player, boolean wasEffective)
	{
		// super.afterBlockBreak(tool, world, state, pos, player, wasEffective);
		if(!world.isRemote && pos.getY()<world.getHeight()/2 && isStone(state) && canTrigger(world,0.001f))
		{
			Random rand=world.rand;
			final int ox=20-rand.nextInt(40),oy=20-rand.nextInt(40),oz=20-rand.nextInt(40);
			final int cx=pos.getX()+ox-4,cy=pos.getY()+oy-4,cz=pos.getZ()+oz-4;
			boolean check=true;
			FOR_FIND_NO_STONE:for(int tx=cx;tx<cx+7;tx++)
			{
				for(int ty=cy;ty<cy+7;ty++)
				{
					for(int tz=cz;tz<cz+7;tz++)
					{
						if(!isStone(world.getBlockState(new BlockPos(tx,ty,tz))))
						{
							check=false;
							break FOR_FIND_NO_STONE;
						}
					}
				}
			}
			if(check)
			{
				Actions.generateTreasureRoom(world,new BlockPos(cx+1,cy+1,cz+1));
			}
		}
	}
}
