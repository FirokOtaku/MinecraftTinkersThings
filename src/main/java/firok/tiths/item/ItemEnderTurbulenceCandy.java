package firok.tiths.item;

import firok.tiths.util.Actions;
import firok.tiths.util.InnerActions;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

/**
 * 末影乱流糖果
 */
public class ItemEnderTurbulenceCandy extends ItemFood
{
    public ItemEnderTurbulenceCandy(int food, float saturation, boolean isWolfFood)
    {
        super(food, saturation, isWolfFood);
    }

    @Override
    protected void onFoodEaten(ItemStack stack, World world, EntityPlayer player)
    {
        super.onFoodEaten(stack, world, player);
        if (!world.isRemote)
        {
            Actions.CauseEnderTeleport(player);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        InnerActions.addInformation(this, tooltip, flagIn);
    }
}
