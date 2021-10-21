package firok.tiths.config;

import net.minecraft.entity.EntityType;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ITag;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

/**
 * @author DustW
 * @since 0.4.1
 */
public class EntityTypeValue extends TagListValue<EntityType<?>> {
    public EntityTypeValue(ForgeConfigSpec.ConfigValue<List<? extends String>> configValue) {
        super(configValue);
    }

    @Override
    ITag<EntityType<?>> toTag(String context) {
        return EntityTypeTags.getCollection().get(new ResourceLocation(context));
    }

    @Override
    EntityType<?> toInstance(String context) {
        return ForgeRegistries.ENTITIES.getValue(new ResourceLocation(context));
    }
}
