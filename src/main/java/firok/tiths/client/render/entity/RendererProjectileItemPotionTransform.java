package firok.tiths.client.render.entity;

import firok.tiths.entity.projectile.ProjectileItemPotionTransform;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

// 渲染器-空中的药水瓶
public class RendererProjectileItemPotionTransform extends RenderSnowball<ProjectileItemPotionTransform>
{
	ItemStack stack2render=new ItemStack(Items.SNOWBALL);
	public RendererProjectileItemPotionTransform(RenderManager renderManagerIn)
	{
		super(renderManagerIn,Items.POTIONITEM, Minecraft.getMinecraft().getRenderItem());
	}

	public ItemStack getStackToRender(ProjectileItemPotionTransform entityIn)
	{
		return stack2render;
	}
}
