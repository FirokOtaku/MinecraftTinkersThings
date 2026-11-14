package firok.tiths.gen;

import javax.annotation.Nullable;

/**
 * 品级禁用回退: 请求某级时若禁用则向下找可用级 (TOP→HIGH→MID→LOW);
 * 全不可用则返回 null, 由调用方走 {@link VanillaLootFallback}.
 */
public final class TierGeneratorSelector
{
    private TierGeneratorSelector() {}

    @Nullable
    public static ToolTier resolve(ToolTier requested)
    {
        ToolTier cur = requested;
        while (cur != null)
        {
            if (cur.isEnabled())
            {
                return cur;
            }
            cur = cur.lower();
        }
        return null;
    }
}
