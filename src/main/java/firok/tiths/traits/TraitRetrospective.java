package firok.tiths.traits;

import firok.tiths.TinkersThings;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.tools.ToolNBT;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.TagUtil;

import java.util.Random;

import static firok.tiths.common.Keys.colorTraitRetrospective;
import static firok.tiths.common.Keys.nameTraitRetrospective;
import static firok.tiths.util.Predicates.canTrigger;

// 后知
// fixme 升级次数问题
public class TraitRetrospective extends AbstractTrait
{
	public static final int maxTimesImproving=100;
	public static final float rateImproving=0.00025f;
	public static final String NBTKey= TinkersThings.MOD_ID+'_'+nameTraitRetrospective;
	public TraitRetrospective()
	{
		super(nameTraitRetrospective, colorTraitRetrospective);
	}

	@Override
	public void applyEffect(NBTTagCompound rootCompound, NBTTagCompound modifierTag)
	{
		super.applyEffect(rootCompound, modifierTag);

		rootCompound.setInteger(NBTKey,maxTimesImproving);
	}

	@Override
	public void afterBlockBreak(ItemStack tool, World world, IBlockState state, BlockPos pos, EntityLivingBase player, boolean wasEffective)
	{
//		super.afterBlockBreak(tool, world, state, pos, player, wasEffective);
		if(!world.isRemote && canTrigger(world,rateImproving))
		{
			improveAttributes(tool,player);
		}
	}

	@Override
	public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit)
	{
//		super.afterHit(tool, player, target, damageDealt, wasCritical, wasHit);
		if(wasHit && !player.world.isRemote && canTrigger(player.world,rateImproving))
		{
			improveAttributes(tool,player);
		}
	}

	public static void improveAttributes(ItemStack stack, EntityLivingBase player)
	{
		Random rand=player.world.rand;
		NBTTagCompound rootCompound=TagUtil.getTagSafe(stack);

		int times=rootCompound.hasKey(NBTKey)?rootCompound.getInteger(NBTKey):0;
		if(times<=0)
		{
			rootCompound.removeTag(NBTKey);
			return;
		}

		ToolNBT data = TagUtil.getToolStats(rootCompound);

		float temp=rand.nextFloat();
		if(temp<0.3333)
		{
			data.attack*=1.01f;
		}
		else if(temp<0.6666)
		{
			data.attackSpeedMultiplier*=1.008f;
			data.speed*=1.008f;
		}
		else
		{
			data.durability+=15;
		}

		TagUtil.setToolTag(rootCompound, data.get());
		stack.setTagCompound(rootCompound);

		TinkersThings.tell("your tool had improved!");
	}
}
