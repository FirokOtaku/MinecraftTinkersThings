package firok.tiths.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import java.util.function.Consumer;

import static firok.tiths.util.Predicates.canTick;

public class ItemInventoryEffector extends ItemCustom
{
    private final Consumer<EntityLivingBase> effect;

    public ItemInventoryEffector(PotionEffect effect)
    {
        this(potion(effect));
    }

    public ItemInventoryEffector(Consumer<EntityLivingBase> effect)
    {
        this.effect = effect;
    }

    static Consumer<EntityLivingBase> potion(PotionEffect effect)
    {
        return carrier -> carrier.addPotionEffect(new PotionEffect(effect));
    }

    static void applyToCarrier(World world, Entity entity, Consumer<EntityLivingBase> effect)
    {
        if (!world.isRemote && canTick(world, 5, 1) && entity instanceof EntityLivingBase && entity.isEntityAlive())
        {
            effect.accept((EntityLivingBase) entity);
        }
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected)
    {
        super.onUpdate(stack, world, entity, itemSlot, isSelected);
        applyToCarrier(world, entity, effect);
    }
}
