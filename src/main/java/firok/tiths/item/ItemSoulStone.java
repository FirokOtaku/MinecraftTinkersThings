package firok.tiths.item;

import baubles.api.BaubleType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;


// 灵魂石
public class ItemSoulStone extends AbstractSoulBauble
{
	public ItemSoulStone()
	{
		super(BaubleType.AMULET);
		this.setMaxStackSize(1);
		this.setNoRepair();
	}

	@Override
	public int chargeSoul(ItemStack stack, EntityLivingBase entity, int point)
	{
		return chargeSoulMax(stack,entity,point,20000);
	}

	@Override
	public int soulDropBase(ItemStack stack)
	{
		return 1;
	}
}
