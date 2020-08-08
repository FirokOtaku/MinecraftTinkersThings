package firok.tiths.intergration.conarm.traits;

import c4.conarm.lib.traits.AbstractArmorTrait;
import firok.tiths.common.Configs;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

import static firok.tiths.common.Keys.colorTraitQuickFreezing;
import static firok.tiths.common.Keys.nameTraitQuickFreezing;
import static firok.tiths.util.Predicates.canTrigger;

/**
 * 速冻 - 护甲
 */
public class TraitArmorQuickFreezing extends AbstractArmorTrait
{
	public TraitArmorQuickFreezing()
	{
		super(nameTraitQuickFreezing,colorTraitQuickFreezing);
	}

	static boolean[][][] map={
		{ // 1
			{ false,false,false,false,false, },
			{ false,false,false,false,false, },
			{ false,false,true,false,false, },
			{ false,false,false,false,false, },
			{ false,false,false,false,false, },
		},
		{ // 2
			{ false,false,false,false,false, },
			{ false,true,true,true,false, },
			{ false,true,false,true,false, },
			{ false,true,true,true,false, },
			{ false,false,false,false,false, },
		},
		{ // 3
			{ true,true,true,true,true, },
			{ true,false,false,false,true, },
			{ true,false,false,false,true, },
			{ true,false,false,false,true, },
			{ true,true,true,true,true, },
		},
		{ // 4
			{ true,true,true,true,true, },
			{ true,false,false,false,true, },
			{ true,false,false,false,true, },
			{ true,false,false,false,true, },
			{ true,true,true,true,true, },
		},
		{ // 5
			{ true,true,true,true,true, },
			{ true,false,false,false,true, },
			{ true,false,false,false,true, },
			{ true,false,false,false,true, },
			{ true,true,true,true,true, },
		},
		{ // 6
			{ true,true,true,true,true, },
			{ true,false,false,false,true, },
			{ true,false,false,false,true, },
			{ true,false,false,false,true, },
			{ true,true,true,true,true, },
		},
		{ // 7
			{ false,false,false,false,false, },
			{ false,true,true,true,false, },
			{ false,true,false,true,false, },
			{ false,true,true,true,false, },
			{ false,false,false,false,false, },
		},
		{ // 8
			{ false,false,false,false,false, },
			{ false,false,false,false,false, },
			{ false,false,true,false,false, },
			{ false,false,false,false,false, },
			{ false,false,false,false,false, },
		},
	};

	@Override
	public float onDamaged(ItemStack armor, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingDamageEvent evt)
	{
		if(player.isServerWorld())
		{
			World world=player.world;
			if(canTrigger(world, Configs.ArmorTraits.rate_quick_freezing))
			{
				BlockPos center=player.getPosition();
				IBlockState stateIce=Blocks.FROSTED_ICE.getDefaultState();
				FOR_LAYER_Y: for (int num_layer_y = 0; num_layer_y < map.length; num_layer_y++)
				{
					final boolean[][] layer_y = map[num_layer_y];
					FOR_LINE_Z: for (int num_line_z = 0; num_line_z < layer_y.length; num_line_z++)
					{
						boolean[] line_z = layer_y[num_line_z];
						FOR_POS_X: for (int num_pos_x = 0; num_pos_x < line_z.length; num_pos_x++)
						{
							boolean pos_x = line_z[num_pos_x];
							if(pos_x)
							{
//								final int ry=center.getY()+num_layer_y-5,rx=center.getX()+num_pos_x-2,rz=center.getZ()+num_line_z-2;
								BlockPos posTemp=center.add(num_pos_x-2,num_layer_y-4,+num_line_z-2);
								IBlockState stateOld=world.getBlockState(posTemp);
								Block blockOld= stateOld.getBlock();
								if( blockOld == Blocks.WATER || blockOld == Blocks.FLOWING_WATER)
								{
									world.setBlockState(posTemp,stateIce);
								}
							}
						}
					}
				}
			}
		}
		return newDamage;
	}
}
