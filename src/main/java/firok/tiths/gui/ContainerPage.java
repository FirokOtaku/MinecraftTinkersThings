package firok.tiths.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class ContainerPage extends Container
{
	public EntityPlayer player;
	public ContainerPage(EntityPlayer player)
	{
		super();
		this.player=player;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return player.isEntityAlive();
	}
}