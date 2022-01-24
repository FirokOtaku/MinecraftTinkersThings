package firok.tiths.entity;

import firok.tiths.TithsModule;
import firok.tiths.entity.projectile.ProjectileDashingStar;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;

public class TithsEntities extends TithsModule
{
	public static RegistryObject<EntityType<FluidSlimeEntity>> etFluidSlime ; // = ENTITIES.register("fluid_slime", ()->
//			EntityType.Builder.create(FluidSlimeEntity::new, EntityClassification.MONSTER)
//					.setShouldReceiveVelocityUpdates(true)
//					.setTrackingRange(6)
//					.size(2.04F, 2.04F)
//					.setCustomClientFactory((spawnEntity, world) -> TithsEntities.etFluidSlime.get().create(world))
//	);

	public static RegistryObject<EntityType<ProjectileDashingStar>> projectileDashingStar = ENTITIES.register("projectile_dashing_star",()->{
		return EntityType.Builder.create(ProjectileDashingStar::new, EntityClassification.MISC)
				.setShouldReceiveVelocityUpdates(true)
				.setTrackingRange(24)
				.size(0.5,0.5)
				.setCustomClientFactory((spawnEntity, world) -> TithsEntities.projectileDashingStar.get().create(world));
	});
}
