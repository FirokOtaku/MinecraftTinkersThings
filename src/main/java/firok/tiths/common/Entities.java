package firok.tiths.common;

import firok.tiths.entity.projectile.ProjectileDashingStar;
import firok.tiths.entity.special.EnderBeacon;
import firok.tiths.entity.trans.Alchemy;
import firok.tiths.util.reg.RegEntity;

@SuppressWarnings("all")
public class Entities
{
//	@RegEntity(network=250)
//	public static ProjectileJavelin projectile_javelin;
	@RegEntity(network=251)
	public static ProjectileDashingStar projectile_dashing_star;
//	@RegEntity(network=252)
//	public static ProjectileItemPotionTransform item_potion_transform;
//
//	@RegEntity(network=301)
//	public static TransformingCloud transforming_cloud;
	@RegEntity(network = 302)
	public static Alchemy alchemy;

	@RegEntity(network = 350)
	public static EnderBeacon ender_beacon;
}
