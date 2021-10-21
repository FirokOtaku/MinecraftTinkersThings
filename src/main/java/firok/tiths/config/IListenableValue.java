package firok.tiths.config;

/**
 * @author DustW
 * @since 0.4.1
 */
public interface IListenableValue {
    /** 加载时 */
    void onLoad();
    /** 重载时 */
    void onReload();
}
