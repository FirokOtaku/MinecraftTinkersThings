package net.minecraftforge.fml.relauncher;

/**
 * DEV-ONLY source used by Gradle task {@code patchForgeSide}.
 * Do NOT put this under src/main — it must not ship in the distributable mod jar.
 *
 * FG5/RetroGradle userdev jars may include Side.BUKKIT; NetworkRegistry only seeds
 * CLIENT/SERVER maps, so Side.values() hitting BUKKIT NPEs in newChannel.
 */
public enum Side
{
	CLIENT,
	SERVER;

	public boolean isServer()
	{
		return !this.isClient();
	}

	public boolean isClient()
	{
		return this == CLIENT;
	}
}
