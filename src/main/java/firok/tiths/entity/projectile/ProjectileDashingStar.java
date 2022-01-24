package firok.tiths.entity.projectile;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.network.IPacket;
import net.minecraft.world.World;

// todo 还没有实现具体逻辑
public class ProjectileDashingStar extends ThrowableEntity
{

	float damage;
	public void setDamage(float value)
	{
		this.damage = value;
		this.markLoadedDirty();
	}
	public float getDamage()
	{
		return this.damage;
	}

	public ProjectileDashingStar(EntityType<ProjectileDashingStar> type, World world) {
		super(type, world);
	}

	@Override
	protected void registerData()
	{

	}

//	@Override
//	public IPacket<?> createSpawnPacket()
//	{
//		return null;
//	}
}
