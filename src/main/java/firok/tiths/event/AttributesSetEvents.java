package firok.tiths.event;

import firok.tiths.entity.TithsEntities;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class AttributesSetEvents
{
    @SubscribeEvent
    public static void setupAttributes(FMLCommonSetupEvent event) {
//        event.enqueueWork(() -> {
//            GlobalEntityTypeAttributes.put(EntityTypeRegistry.obsidianAnimal.get(), MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 10.0D).create());
//        });
    }

    @SubscribeEvent
    public static void onEntityAttributeSetup(EntityAttributeCreationEvent event)
    {
        AttributeModifierMap.MutableAttribute map = AttributeModifierMap.createMutableAttribute();
        map
                .createMutableAttribute(Attributes.MAX_HEALTH, 100)
                .createMutableAttribute(Attributes.KNOCKBACK_RESISTANCE, 1)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.5)
                .createMutableAttribute(Attributes.ARMOR, 1)
                .createMutableAttribute(Attributes.ARMOR_TOUGHNESS, 1)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 6)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 0)
                .createMutableAttribute(net.minecraftforge.common.ForgeMod.SWIM_SPEED.get())
                .createMutableAttribute(net.minecraftforge.common.ForgeMod.NAMETAG_DISTANCE.get())
                .createMutableAttribute(net.minecraftforge.common.ForgeMod.ENTITY_GRAVITY.get());


//        event.put(TithsEntities.etFluidSlime.get(), map.create());
    }
}