package firok.tiths.item.bauble;


import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemSoulStoneCreative extends ItemSoulStone
{
	public ItemSoulStoneCreative()
	{
		super();
	}

	@Override
	public int chargeSoulPriority(ItemStack stack, EntityLivingBase entity)
	{
		return Low;
	}

	@Override
	public int chargeSoul(ItemStack stack, EntityLivingBase entity, int point)
	{
		return 0;
	}

	@Override
	public int costSoulPriority(ItemStack stack, EntityLivingBase entity)
	{
		return High;
	}

	@Override
	public int costSoul(ItemStack stack, EntityLivingBase entity, int point, boolean fromDeath)
	{
		return point;
	}

	@Override
	public int countSoul(ItemStack stack)
	{
		return 10000000;
	}

	@Override
	public boolean canDeathDrain(ItemStack stack, World world)
	{
		return false;
	}
}
