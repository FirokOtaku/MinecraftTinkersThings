package firok.tiths.block;

import com.zeitheron.hammercore.api.lighting.ColoredLight;
import com.zeitheron.hammercore.api.lighting.impl.IGlowingBlock;
import firok.tiths.util.EntityFinders;
import firok.tiths.util.InnerActions;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.shared.block.BlockGlow;

import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

@SuppressWarnings("all")
@Optional.Interface(
		iface = "com.zeitheron.hammercore.api.lighting.impl.IGlowingBlock",
		modid = "hammercore"
)
public class BlockBait extends BlockGlow implements IGlowingBlock
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

	@Optional.Method(
			modid = "hammercore"
	)
	public ColoredLight produceColoredLight(World world, BlockPos blockPos, IBlockState state, float v) {
		EnumFacing facing = state.getValue(FACING);
		int index=facing.getIndex();
		float r,g,b;
		switch (index)
		{
			case 0: r=0;g=0.5f;b=0.5f; break;
			case 1: r=1;g=1;b=1; break;
			case 2: r=0.25f;g=0.7f;b=0.8f;break;
			case 3: r=0.8f;g=0;b=0; break;
			default: r=1;g=1;b=0; break;
		}

		return ColoredLight.builder().pos(blockPos).radius(15.0F).color(r,g,b).build();
	}
}
