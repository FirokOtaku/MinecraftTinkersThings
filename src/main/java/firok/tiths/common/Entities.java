package firok.tiths.common;

import firok.tiths.entity.projectile.ProjectileDashingStar;
import firok.tiths.entity.projectile.ProjectileJavelin;
import firok.tiths.util.RegEntity;

public class Entities
{
	@RegEntity(network=250)
	public static ProjectileJavelin projectile_javelin;
	@RegEntity(network=251)
	public static ProjectileDashingStar projectile_dashing_star;
}
