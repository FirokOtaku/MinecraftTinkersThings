package firok.tiths.intergration.conarm.traits;

import c4.conarm.lib.traits.AbstractArmorTrait;
import com.google.common.collect.ImmutableList;
import firok.tiths.TinkersThings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

import java.util.List;

import static firok.tiths.common.Keys.colorTraitHydrating;
import static firok.tiths.common.Keys.nameTraitHydrating;
import static firok.tiths.util.Predicates.canTick;

/**
 * 水合 - 护甲
 */
public class TraitArmorHydrating extends AbstractArmorTrait
{
	public static final String NBTKey= TinkersThings.MOD_ID+'_'+nameTraitHydrating;
	public TraitArmorHydrating()
	{
		super(nameTraitHydrating,colorTraitHydrating);
	}

	public static void regen(ItemStack stack,int point,int max)
	{
		if(stack==null) return;

		NBTTagCompound nbt=stack.hasTagCompound()?stack.getTagCompound():new NBTTagCompound();
		assert nbt!=null;
		stack.setTagCompound(nbt);

		int origin=nbt.hasKey(NBTKey)?nbt.getInteger(NBTKey):0;
		int after=origin+point;
		if(after>max) after=max;
		nbt.setInteger(NBTKey,after);
	}
	public static int cost(ItemStack stack,int cost)
	{
		if(stack==null) return 0;

		NBTTagCompound nbt=stack.hasTagCompound()?stack.getTagCompound():new NBTTagCompound();
		assert nbt!=null;
		stack.setTagCompound(nbt);

		int origin=nbt.hasKey(NBTKey)?nbt.getInteger(NBTKey):0;
		if(origin<0) origin=0;

		if(origin<=cost)
		{
			nbt.setInteger(NBTKey,0);
			return origin;
		}
		else
		{
			nbt.setInteger(NBTKey,origin-cost);
			return cost;
		}
	}
	public static int get(ItemStack stack)
	{
		if(stack==null || !stack.hasTagCompound()) return 0;

		NBTTagCompound nbt=stack.getTagCompound();
		assert nbt!=null;
		return nbt.hasKey(NBTKey)?nbt.getInteger(NBTKey):0;
	}

	/*
	每1点可以降低伤害0.01 = 抵消1点伤害需要消耗100点

	水中每秒恢复10点 恢复上限为500 = 每秒恢复0.1血量
	淋雨每秒恢复7点 500 = 每秒恢复0.07血量
	*/
	@Override
	public void onArmorTick(ItemStack tool, World world, EntityPlayer player)
	{
		if(!world.isRemote && canTick(world,20,0))
		{
			if(player.isInWater())
			{
				regen(tool,10,500);
			}
			else if(world.isRainingAt(player.getPosition()))
			{
				regen(tool,7,500);
			}
		}
	}

	@Override
	public float onDamaged(ItemStack armor, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingDamageEvent evt)
	{
		int costMax=(int)(newDamage*100);
		int costReal=cost(armor,costMax);

		if(costReal>0)
		{
			newDamage-= costReal/100f;
			// todo 播放音效
		}

		return newDamage;
	}

	@Override
	public List<String> getExtraInfo(ItemStack tool, NBTTagCompound modifierTag)
	{
		int point=get(tool);

		if(point<=100) return ImmutableList.of();

		StringBuilder info=new StringBuilder().append("§3");
		while(point>0)
		{
			if(point>=100) info.append("●");
			point-=100;
		}
		info.append("§r");

		return ImmutableList.of(info.toString());
	}
}
