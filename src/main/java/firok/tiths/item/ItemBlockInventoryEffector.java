package firok.tiths.item;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import java.util.function.Consumer;

public abstract class ItemBlockInventoryEffector extends ItemBlock
{
    private final Consumer<EntityLivingBase> effect;

    protected ItemBlockInventoryEffector(Block block, PotionEffect effect)
    {
        this(block, ItemInventoryEffector.potion(effect));
    }

    protected ItemBlockInventoryEffector(Block block, Consumer<EntityLivingBase> effect)
    {
        super(block);
        this.effect = effect;
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected)
    {
        super.onUpdate(stack, world, entity, itemSlot, isSelected);
        ItemInventoryEffector.applyToCarrier(world, entity, effect);
    }
}
