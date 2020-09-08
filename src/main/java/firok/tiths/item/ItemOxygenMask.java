package firok.tiths.item;

import c4.conarm.lib.client.DynamicTextureHelper;
import firok.tiths.TinkersThings;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

import static firok.tiths.util.Predicates.canTick;

/**
 * 氧气面罩
 */
public class ItemOxygenMask extends ItemArmor
{
	public ItemOxygenMask()
	{
		super(ArmorMaterial.IRON,2, EntityEquipmentSlot.HEAD);
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack helmet)
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
				if(!airSupply.canAutoSupply(stack,helmet,player)) continue;

				airSupply.onAirSupply(stack,player);
				break;
			}
		}
	}

	static ResourceLocation texture=new ResourceLocation(TinkersThings.MOD_ID,"textures/models/armor/oxygen_mask.png");

	@SideOnly(Side.CLIENT)
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type)
	{
		return texture.toString();
	}
}
