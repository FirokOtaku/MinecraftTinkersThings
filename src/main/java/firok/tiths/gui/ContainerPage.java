package firok.tiths.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class ContainerPage extends Container
{
	public ContainerPage()
	{
		super();
	}

	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return player.isEntityAlive();
	}
}