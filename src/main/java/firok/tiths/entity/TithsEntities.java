package firok.tiths.entity;

import firok.tiths.TithsModule;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;

public class TithsEntities extends TithsModule
{
	public static final RegistryObject<EntityType<FluidSlimeEntity>> etFluidSlime = ENTITIES.register("fluid_slime", ()->
			EntityType.Builder.create(FluidSlimeEntity::new, EntityClassification.MONSTER)
					.setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(6)
					.size(2.04F, 2.04F)
					.setCustomClientFactory((spawnEntity, world) -> TithsEntities.etFluidSlime.get().create(world))
	);
}
