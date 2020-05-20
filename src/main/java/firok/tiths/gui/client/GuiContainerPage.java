package firok.tiths.gui.client;

import firok.tiths.gui.ContainerPage;
import firok.tiths.item.IPage;
import firok.tiths.item.ItemLangPage;
import firok.tiths.util.Colors;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiContainerPage extends GuiContainer
{
	ItemStack stack;
	IPage ipage;

	public GuiContainerPage(ContainerPage inventorySlotsIn)
	{
		super(inventorySlotsIn);
		this.width=256;
		this.height=160;

		ItemStack stackHeldMain=inventorySlotsIn.player.getHeldItemMainhand();
		Item itemHeldMain=stackHeldMain.getItem();
		if(itemHeldMain instanceof ItemLangPage)
		{
			ipage=(ItemLangPage)itemHeldMain;
			return;
		}

		ItemStack stackHeldOff=inventorySlotsIn.player.getHeldItemOffhand();
		Item itemHeldOff=stackHeldOff.getItem();
		if(itemHeldOff instanceof ItemLangPage)
		{
			ipage=(ItemLangPage)itemHeldOff;
			return;
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GlStateManager.color(1.0F, 1.0F, 1.0F);
		drawDefaultBackground();

		ResourceLocation background=ipage.getBackground(stack);

		if(background!=null)
		{
			this.mc.getTextureManager().bindTexture(background);
			int offsetX = (this.width - this.xSize) / 2, offsetY = (this.height - this.ySize) / 2;

			this.drawTexturedModalRect(offsetX, offsetY, 0, 0, this.xSize, this.ySize);
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		String text = ipage.getText(stack);

		if(text!=null)
		{
			fontRenderer.drawSplitString(text,30,30,100, Colors.Black);
		}
	}
}
