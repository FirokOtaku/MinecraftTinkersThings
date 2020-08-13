package firok.tiths.block;

import firok.tiths.util.EntityFinders;
import firok.tiths.util.InnerActions;
import firok.tiths.util.Predicates;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.gadgets.TinkerGadgets;
import slimeknights.tconstruct.gadgets.item.ItemThrowball;
import slimeknights.tconstruct.shared.block.BlockGlow;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

@SuppressWarnings("all")
public class BlockBait extends BlockGlow
{
	Predicate[] canAttracts;
	@SafeVarargs
	public BlockBait(Predicate<Entity>... canAttracts)
	{
		this.setTickRandomly(true);
		this.setHardness(0);
		this.setLightLevel(0);
		this.setSoundType(SoundType.CLOTH);
		this.canAttracts=canAttracts==null?new Predicate[0]:canAttracts.clone();
	}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
	{
		attract(worldIn, pos, state);
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
	{
		attract(worldIn, pos, state);
	}

	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
	{
		if(state.getBlock()==this && canAttract(entityIn))
		{
			worldIn.setBlockToAir(pos);
			if(entityIn instanceof EntityLivingBase) ((EntityLivingBase) entityIn).heal(2);
		}
	}

	public boolean canAttract(Entity en)
	{
		if(en==null) return false;
		for(Predicate pre:this.canAttracts)
		{
			if(pre.test(en)) return true;
		}
		return false;
	}

	public void attract(World world, BlockPos pos, IBlockState state)
	{
		try
		{
			List<Entity> entities=(List) EntityFinders.Nearby(world,pos,8, this::canAttract);
			int size=entities.size();
			if(size<=0) return;
			EntityCreature enlb = (EntityCreature) entities.get( world.rand.nextInt(size) );
			enlb.getNavigator().tryMoveToXYZ(pos.getX(),pos.getY(),pos.getZ(),0.8f);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
	{
		return ItemStack.EMPTY;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World worldIn, List<String> list, ITooltipFlag flagIn)
	{
		list.add( I18n.format("tooltip.tiths.block_bait") );
		InnerActions.addInformation(this,list,flagIn);
	}
}
