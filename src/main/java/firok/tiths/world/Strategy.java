package firok.tiths.world;

public enum Strategy
{
	/**
	 * 总是
	 */
	ALWAYS,
	/**
	 * 仅当于白名单
	 */
	ONLY_WHITELIST,
	/**
	 * 当所有非黑名单
	 */
	NONE_BLACKLIST,
	/**
	 * 总不
	 */
	NONE,
	;

	public static Strategy getStrategy(String str,Strategy defaultValue)
	{
		if(str==null) return defaultValue;
		else switch (str.toLowerCase())
		{
			case "none": return Strategy.NONE;
			case "always": return Strategy.ALWAYS;
			case "only_whitelist": return Strategy.ONLY_WHITELIST;
			default: case "none_blacklist": return Strategy.NONE_BLACKLIST;
		}
	}
}
