package firok.tiths.config;

import net.minecraft.tags.ITag;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DustW
 * @since 0.4.1
 */
public abstract class TagListValue<T> implements IListenableValue {
    private final ForgeConfigSpec.ConfigValue<List<? extends String>> configValue;
    private final ArrayList<ITag<T>> tags = new ArrayList<>();
    private final ArrayList<T> instance = new ArrayList<>();

    public TagListValue(ForgeConfigSpec.ConfigValue<List<? extends String>> configValue) {
        this.configValue = configValue;
        handle(configValue);
    }

    public boolean contains(T value) {
        return tags.stream().anyMatch(tag -> tag.contains(value)) || instance.contains(value);
    }

    protected void handle(ForgeConfigSpec.ConfigValue<List<? extends String>> configValue) {
        configValue.get().forEach(string -> {
            if (isTag(string)) {
                tags.add(toTag(string));
            }
            else {
                instance.add(toInstance(string));
            }
        });
    }

    protected boolean isTag(String context) {
        return context.startsWith("#");
    }

    @Override
    public void onLoad() {
        handle(configValue);
    }

    @Override
    public void onReload() {
        handle(configValue);
    }

    /**
     * 将字符串解析为 tag
     * @param context 字符串
     * @return tag
     * */
    abstract ITag<T> toTag(String context);

    /**
     * 将字符串解析为实例
     * @param context 字符串
     * @return 实例
     * */
    abstract T toInstance(String context);
}
