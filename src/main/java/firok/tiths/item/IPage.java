package firok.tiths.item;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

/**
 * 纸页interface
 */
public interface IPage
{
	default String getText(ItemStack stack)
	{
		return stack!=null && stack.hasTagCompound() && stack.getTagCompound().hasKey("text")?
				stack.getTagCompound().getString("text"):
				null;
	}

	default void setText(ItemStack stack,String text)
	{
		if(stack==null) return;
		NBTTagCompound nbt=stack.hasTagCompound()? stack.getTagCompound():new NBTTagCompound();
		stack.setTagCompound(nbt);

		assert nbt!=null;
		nbt.setString("text",text);
	}

	default ResourceLocation getBackground(ItemStack stack)
	{
		return stack!=null && stack.hasTagCompound() && stack.getTagCompound().hasKey("domain") && stack.getTagCompound().hasKey("path")?
				new ResourceLocation(stack.getTagCompound().getString("domain"),stack.getTagCompound().getString("path")):
				null;
	}

	default void setBackground(ItemStack stack,ResourceLocation background)
	{
		if(stack==null) return;
		NBTTagCompound nbt=stack.hasTagCompound()? stack.getTagCompound():new NBTTagCompound();
		stack.setTagCompound(nbt);

		assert nbt!=null;
		nbt.setString("domain",background.getResourceDomain());
		nbt.setString("path",background.getResourcePath());
	}
}
