package firok.tiths.block;

import firok.tiths.util.Actions;
import firok.tiths.util.InnerActions;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.tinkering.ITinkerable;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.TinkerUtil;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class BlockTinkerDisintegrator extends Block
{
	public BlockTinkerDisintegrator()
	{
		super(net.minecraft.block.material.Material.IRON);
		this.setHardness(20);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if(!world.isRemote && facing==EnumFacing.UP)
		{
			ItemStack stackHeld=player.getHeldItem(hand);
			if(stackHeld.isEmpty()) return false;

			Item itemHeld=stackHeld.getItem();
			if(itemHeld instanceof ITinkerable)
			{
				NBTTagList list=TagUtil.getBaseMaterialsTagList(stackHeld);
				List<Material> materials=TinkerUtil.getMaterialsFromTagList(list);

				if(!player.isCreative()) player.setHeldItem(hand,ItemStack.EMPTY);
				world.playSound(null,pos, SoundEvents.ENTITY_ITEM_BREAK, SoundCategory.BLOCKS, 1,1);

				List<ItemStack> stacks=new ArrayList<>(materials.size()+1);

				for(Material mat:materials)
				{
					float factor=world.rand.nextFloat();
					int amount;
					/* 数学期望值 25%×0 + 50%×1 + 25%×2 = 1 */
					if(factor<0.25f) amount=0;
					else if(factor<0.75f) amount=1;
					else amount=2;

					if(amount>0)
					{
						ItemStack stackShard=mat.getShard();
						stackShard.setCount(amount);
						stacks.add(stackShard);
					}
				}

				if(stacks.size()>0)
				{
					for(ItemStack stack:stacks)
					{
						Actions.CauseSpawnItem(player,stack);
					}
				}
			}
		}

		return false;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced)
	{
		InnerActions.addInformation(this, tooltip, advanced);
	}
}
