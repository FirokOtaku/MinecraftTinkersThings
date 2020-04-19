package firok.tiths.gui.client;

import firok.tiths.gui.ContainerPage;
import firok.tiths.item.ItemPage;
import firok.tiths.util.Colors;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiContainerPage extends GuiContainer
{
	World world;
	EntityPlayer player;
	ItemStack stack;
	ItemPage page;
	String key;
	ResourceLocation background;

	public GuiContainerPage(ContainerPage inventorySlotsIn)
	{
		super(inventorySlotsIn);
		this.width=256;
		this.height=160;

		ItemStack stackHeldMain=inventorySlotsIn.player.getHeldItemMainhand();
		Item itemHeldMain=stackHeldMain.getItem();
		if(itemHeldMain instanceof ItemPage)
		{
			page=(ItemPage)itemHeldMain;
			key=page.pageKey(world,player,stack);
			background=page.background();
			return;
		}

		ItemStack stackHeldOff=inventorySlotsIn.player.getHeldItemOffhand();
		Item itemHeldOff=stackHeldOff.getItem();
		if(itemHeldOff instanceof ItemPage)
		{
			page=(ItemPage)itemHeldOff;
			key=page.pageKey(world,player,stack);
			background=page.background();
			return;
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GlStateManager.color(1.0F, 1.0F, 1.0F);
		drawDefaultBackground();

		this.mc.getTextureManager().bindTexture(background);
		int offsetX = (this.width - this.xSize) / 2, offsetY = (this.height - this.ySize) / 2;

		this.drawTexturedModalRect(offsetX, offsetY, 0, 0, this.xSize, this.ySize);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		// TODO
		String text= I18n.format(key);
		text=text.replace("<br>","\n");

		fontRenderer.drawSplitString(text,30,30,100, Colors.Black);
	}
}
