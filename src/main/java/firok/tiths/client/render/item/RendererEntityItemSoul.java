package firok.tiths.client.render.item;

import firok.tiths.TinkersThings;
import firok.tiths.common.Datas;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.RenderEntityItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityItem;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RendererEntityItemSoul extends RenderEntityItem
{
	public RendererEntityItemSoul(RenderManager renderManagerIn)
	{
		super(renderManagerIn, Minecraft.getMinecraft().getRenderItem());
	}

	@Override
	public boolean shouldRender(EntityItem entity, ICamera camera, double camX, double camY, double camZ)
	{
		try
		{
//			if(entity.ticksExisted % 40==0)
//			{
//				TinkersThings.log("Data.client.instance() : "+Datas.Client.instance());
//			}
			return Datas.Client.instance().hasSoulScanners() &&
			       super.shouldRender(entity, camera, camX, camY, camZ);
		}
		catch (Exception ignored) { return false; }
	}

	@Override
	public boolean shouldSpreadItems()
	{
		return false;
	}
}
