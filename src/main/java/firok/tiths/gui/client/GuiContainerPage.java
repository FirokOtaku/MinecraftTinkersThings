package firok.tiths.gui.client;

import firok.tiths.gui.ContainerPage;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiContainerPage extends GuiContainer
{
	public GuiContainerPage(ContainerPage inventorySlotsIn)
	{
		super(inventorySlotsIn);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		// TODO
	}
}
