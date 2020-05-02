package firok.tiths.item;

import firok.tiths.TinkersThings;
import firok.tiths.common.SoundEvents;
import firok.tiths.util.FormerStatus;
import firok.tiths.util.InnerActions;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

/**
 * 往昔水晶
 */
public class ItemFormerCrystal extends Item
{
	public ItemFormerCrystal()
	{
		super();
		this.setMaxStackSize(1);
		this.setNoRepair();

		this.addPropertyOverride(new ResourceLocation(TinkersThings.MOD_ID, "former_crystal"), new IItemPropertyGetter() {
			@Override
			@SideOnly(Side.CLIENT)
			public float apply(ItemStack stack, World world, EntityLivingBase entity) {
				return stack.hasTagCompound() && FormerStatus.hasStatus(stack.getTagCompound()) ? 1 : 0;
			}
		});
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
	{
		playerIn.setActiveHand(handIn);
		return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase entity)
	{
		try
		{
			if(!world.isRemote && entity instanceof EntityPlayer)
			{
				EntityPlayer player=(EntityPlayer)entity;

				NBTTagCompound nbtRoot=stack.hasTagCompound()?stack.getTagCompound():new NBTTagCompound();

				assert nbtRoot != null;
				if(FormerStatus.hasStatus(nbtRoot)) // 读取
				{
					FormerStatus status=FormerStatus.fromNBT(nbtRoot.getCompoundTag("status"));
					if(status!=null) status.toPlayer(player);
				}
				else // 储存
				{
					FormerStatus status=FormerStatus.fromPlayer(player);

					player.getFoodStats().setFoodLevel(1);
					player.getFoodStats().setFoodSaturationLevel(1);
					player.setHealth(1);
					player.clearActivePotions();

					nbtRoot.setTag("status",status.toNBT());
					stack.setTagCompound(nbtRoot);
				}

				world.playSound(null,player.posX,player.posY,player.posZ, SoundEvents.effectForward, SoundCategory.MASTER,1,1);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return stack;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack)
	{
		return EnumAction.BOW;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack)
	{
		return 20;
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> list, ITooltipFlag flagIn)
	{
		InnerActions.addInformation(this,list,flagIn);
		try
		{
			NBTTagCompound nbtRoot=stack.getTagCompound();
			if(nbtRoot==null || !FormerStatus.hasStatus(nbtRoot)) return;

			FormerStatus status=FormerStatus.fromNBT(nbtRoot.getCompoundTag("status"));
			if(status==null) return;

			list.add(status.name);
			list.add("HP: "+status.hp);
			list.add("FOOD: "+status.food);
			list.add("SATURATION: "+status.saturation);
			list.add("EFFECTS: ");
			for(PotionEffect pe:status.effects)
			{
				list.add(pe.toString());
			}
		}
		catch (Exception ignored) {}
	}


}
