package firok.tiths.util;

public enum VersionPhase
{
	Release(true),
	Beta(true),
	Alpha(false),
	Indev(false),
	;

	/**
	 * 是否允许禁用登录警告
	 */
	public final boolean canDisableLoginWarning;

	VersionPhase(boolean canDisableLoginWarning)
	{
		this.canDisableLoginWarning=canDisableLoginWarning;
	}
}