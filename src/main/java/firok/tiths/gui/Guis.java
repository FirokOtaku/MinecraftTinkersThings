package firok.tiths.gui;

import firok.tiths.gui.client.GuiContainerGemBrewingStand;
import firok.tiths.gui.client.GuiContainerPage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Guis implements IGuiHandler
{
	public static final int GUI_PAGE = 1;
	public static final int GUI_GEM_BREWING_STAND = 2;

	private static Guis instance;
	public static Guis getInstance()
	{
		if(instance==null)
		{
			instance=new Guis();
		}
		return instance;
	}

	public Guis() { }

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		switch (ID)
		{
			case GUI_PAGE: return new ContainerPage(player);
			case GUI_GEM_BREWING_STAND: return new ContainerGemBrewingStand(player.inventory,(IInventory) world.getTileEntity(new BlockPos(x,y,z)));
			default: return null;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		switch (ID)
		{
			case GUI_PAGE: return new GuiContainerPage(new ContainerPage(player));
			case GUI_GEM_BREWING_STAND: return new GuiContainerGemBrewingStand(player.inventory,(IInventory) world.getTileEntity(new BlockPos(x,y,z)));
			default: return null;
		}
	}
}