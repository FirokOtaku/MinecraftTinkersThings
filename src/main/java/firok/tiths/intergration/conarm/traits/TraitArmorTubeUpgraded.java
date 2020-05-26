package firok.tiths.intergration.conarm.traits;

import c4.conarm.lib.traits.AbstractArmorTrait;
import firok.tiths.item.IAirSupply;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import static firok.tiths.common.Keys.colorTraitTubeUpgraded;
import static firok.tiths.common.Keys.nameTraitTubeUpgraded;
import static firok.tiths.util.Predicates.canTick;

/**
 * 导管升级
 */
public class TraitArmorTubeUpgraded extends AbstractArmorTrait
{
	public TraitArmorTubeUpgraded()
	{
		super(nameTraitTubeUpgraded,colorTraitTubeUpgraded);
	}

	@Override
	public void onArmorTick(ItemStack tool, World world, EntityPlayer player)
	{
		if(!world.isRemote && canTick(world,20,0))
		{
			InventoryPlayer inv=player.inventory;
			final int size=inv.getSizeInventory();
			for(int i=0;i<size;i++)
			{
				ItemStack stack=inv.getStackInSlot(i);
				if(stack.isEmpty()) continue;

				Item item=stack.getItem();
				if(!(item instanceof IAirSupply)) continue;

				IAirSupply airSupply=(IAirSupply)item;
				if(!airSupply.canAutoSupply(stack,tool,player)) continue;

				airSupply.onAirSupply(stack,player);
				break;
			}
		}
	}

}
