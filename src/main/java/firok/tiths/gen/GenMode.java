package firok.tiths.gen;

import firok.tiths.TinkersThings;

/**
 * 品级材料池模式.
 */
public enum GenMode
{
    DEFAULT,
    CUSTOM,
    ;

    public static GenMode fromConfig(String raw)
    {
        if (raw == null || raw.trim().isEmpty())
        {
            return DEFAULT;
        }
        String key = raw.trim().toLowerCase();
        if ("custom".equals(key))
        {
            return CUSTOM;
        }
        if (!"default".equals(key))
        {
            TinkersThings.log("Unknown gen_mode \"" + raw + "\", falling back to default");
        }
        return DEFAULT;
    }
}
