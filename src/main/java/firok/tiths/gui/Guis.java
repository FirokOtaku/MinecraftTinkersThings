package firok.tiths.gui;

import firok.tiths.TinkersThings;
import firok.tiths.gui.client.GuiContainerPage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class Guis implements IGuiHandler
{
	public static final int GUI_PAGE = 1;

	public Guis()
	{
		NetworkRegistry.INSTANCE.registerGuiHandler(TinkersThings.INSTANCE, this);
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		switch (ID)
		{
			case GUI_PAGE: return new ContainerPage();
			default: return null;
		}
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		switch (ID)
		{
			case GUI_PAGE: return new GuiContainerPage(new ContainerPage());
			default: return null;
		}
	}
}