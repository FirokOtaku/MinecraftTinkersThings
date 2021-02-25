package firok.tiths.intergration.conarm.traits;

import c4.conarm.common.armor.utils.ArmorHelper;
import c4.conarm.lib.traits.AbstractArmorTrait;
import firok.tiths.common.Blocks;
import firok.tiths.tile.logic.TEEnderInterferedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDynamicLiquid;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockStaticLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidBase;
import slimeknights.tconstruct.library.utils.TinkerUtil;
import slimeknights.tconstruct.library.utils.ToolHelper;

import static firok.tiths.common.Keys.colorTraitEnderInterfering;
import static firok.tiths.common.Keys.nameTraitEnderInterfering;
import static firok.tiths.util.Predicates.canTick;

/**
 * 末影干涉
 */
public class TraitArmorEnderInterfering
	extends AbstractArmorTrait
{
	public TraitArmorEnderInterfering()
	{
		super(nameTraitEnderInterfering,colorTraitEnderInterfering);
	}

	@Override
	public void onArmorTick(ItemStack tool, World world, EntityPlayer player)
	{
		if(!player.isSneaking()) return;

		if(world.isRemote)
		{
			;
		}
		else // 服务端
		{
			if(!canTick(world,5,0)) return; // 降低频率 降低性能需求

			final BlockPos posCenter = player.getPosition();
			final IBlockState stateInterfered = Blocks.blockEnderInterferedBlock.getDefaultState();
			int cost = 0;
			FOR_X: for (int ox = -1; ox <= 1; ox++)
			{
				FOR_Y: for (int oy = 0; oy <= 1; oy++)
				{
					FOR_Z: for (int oz = -1; oz <= 1; oz++)
					{
						BlockPos posOffset = posCenter.add(ox,oy,oz);
						IBlockState state = world.getBlockState(posOffset);
						Block block = state.getBlock();

						if(block == net.minecraft.init.Blocks.AIR ||
							block.hasTileEntity(state) ||
							block instanceof BlockFluidBase ||
							block instanceof BlockLiquid)
							continue FOR_Z; // 不能干涉这类的

						TEEnderInterferedBlock te;
						if(block == Blocks.blockEnderInterferedBlock) // 变了
						{
							TileEntity teTemp = world.getTileEntity(posOffset);
							if(teTemp == null) // 没设定TileEntity
							{
								// 这种情况不应该
								// 应该破坏掉这个方块 吧

//								te = new TEEnderInterferedBlock(state,20);
//								te.setWorld(world);
//								te.setPos(posOffset);
//								world.setTileEntity(posOffset,te);
//
//								cost += 2;
							}
							if(teTemp instanceof TEEnderInterferedBlock) // 内部已经有了
							{
								te = (TEEnderInterferedBlock) teTemp;
								if(te.ticksCharge <= 10)
								{
									te.ticksCharge += 10;
									cost += 1;
								}
							}
							else
							{
								continue FOR_Z;
							}

						}
						else // 还没变
						{
							te = new TEEnderInterferedBlock(state,20);
							te.setWorld(world);
							te.setPos(posOffset);

							world.setBlockState(posOffset,stateInterfered);
							world.setTileEntity(posOffset,te);

							cost += 2;
						}
					}
				}
			}

//			ArmorHelper.damageArmor(tool, DamageSource.MAGIC, cost, player);
		}
	}
}
