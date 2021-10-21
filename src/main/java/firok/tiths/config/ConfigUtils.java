package firok.tiths.config;

import firok.tiths.TinkersThings;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;
import java.util.function.Predicate;

/**
 * @author DustW
 * @since 0.4.1
 */
public class ConfigUtils {
    static String key(String key) {
        return "config." + TinkersThings.MOD_ID + "." + key;
    }

    public static ForgeConfigSpec.ConfigValue<Boolean> defineBoolean(ForgeConfigSpec.Builder builder,
                                                                     String comment, String key, boolean defaultValue) {
        return builder.comment(comment).translation(key(key)).define(key, defaultValue);
    }

    public static ForgeConfigSpec.ConfigValue<Integer> defineInteger(ForgeConfigSpec.Builder builder,
                                                                     String comment, String key, int defaultValue, int min, int max) {
        return builder.comment(comment).translation(key(key)).defineInRange(key, defaultValue, min, max);
    }

    public static ForgeConfigSpec.ConfigValue<Double> defineDouble(ForgeConfigSpec.Builder builder,
                                                                   String comment, String key, double defaultValue, double min, double max) {
        return builder.comment(comment).translation(key(key)).defineInRange(key, defaultValue, min, max);
    }

    public static <T> ForgeConfigSpec.ConfigValue<List<? extends T>> defineList(ForgeConfigSpec.Builder builder,
                                                                                String comment, String key,
                                                                                List<? extends T> defaultValue, Predicate<Object> validator) {
        return builder.comment(comment).translation(key(key)).defineList(key, defaultValue, validator);
    }

    public static <T> ForgeConfigSpec.ConfigValue<List<? extends T>> defineList(ForgeConfigSpec.Builder builder,
                                                                                String comment, String key,
                                                                                List<? extends T> defaultValue) {
        return defineList(builder, comment, key, defaultValue, o -> true);
    }
}
