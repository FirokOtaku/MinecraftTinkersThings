package firok.tiths.traits;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.FoodStats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.ToolHelper;

import static firok.tiths.common.Keys.*;
import static firok.tiths.util.Predicates.canTick;

// 自然祝福
public class TraitNatureBlessing extends AbstractTrait
{
	public TraitNatureBlessing()
	{
		super(nameTraitNatureBlessing, colorTraitNatureBlessing);
	}

	@Override
	public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected)
	{
		if(!world.isRemote && canTick(world,200,3) && entity instanceof EntityLivingBase)
		{
			final int cx=(int)entity.posX,cy=(int)entity.posY,cz=(int)entity.posZ;
			int countNature=0;
			FOR_X:for(int ox=-4;ox<=4;ox++)
			{
				FOR_Y:for(int oz=-4;oz<=4;oz++)
				{
					FOR_Z:for(int oy=2;oy>=-2;oy++)
					{
						BlockPos posTemp=new BlockPos(cx+ox,cy+oy,cz+oz);
						IBlockState state=world.getBlockState(posTemp);
						Block block=state.getBlock();
						boolean isNature=block.isLeaves(state,world,posTemp) || block.isWood(world,posTemp);
						if(isNature) countNature++;

						if(countNature>=8) break FOR_X;
					}
				}
			}

			if(countNature>=8)
			{
				ToolHelper.healTool(tool,12,(EntityLivingBase)entity);
			}
		}
	}
}
