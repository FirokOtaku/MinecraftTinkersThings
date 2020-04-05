package firok.tiths.entity.projectile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.entity.EntityProjectileBase;

import static firok.tiths.util.Predicates.canTick;

// 投射物实体 - 凋零粒子
public class ProjectileWitheringParticle extends EntityProjectileBase
{
	public int ticks=40;
	public ProjectileWitheringParticle(World world) {
		super(world);
	}

	public ProjectileWitheringParticle(World world, double d, double d1, double d2) {
		super(world, d, d1, d2);
	}

	public ProjectileWitheringParticle(World world, EntityPlayer player, float speed, float inaccuracy, ItemStack stack, ItemStack launchingStack) {
		super(world, player, speed, inaccuracy, 1f, stack, launchingStack);
	}

	@Override
	protected void init() {
//		setSize(0.3f, 0.1f);
		setSize(0,0);
	}

	@Override
	public double getGravity() {
		return -0.1; // integer division. so the first ticks it will have no gravity at all.
	}

	@Override
	public double getSlowdown() {
		return -0.05f;
	}

	public void onHitEntity(RayTraceResult raytraceResult) {

		Entity entityHit = raytraceResult.entityHit;

		if(!world.isRemote && entityHit instanceof EntityPlayer)
		{
			((EntityPlayer) entityHit).addPotionEffect(new PotionEffect(MobEffects.WITHER,60,0));
			setDead();
		}
	}

	@Override
	public void onHitBlock(RayTraceResult raytraceResult)
	{
		setDead();
	}

	@Override
	public void onEntityUpdate()
	{
		super.onEntityUpdate();

		ticks--;
		if(ticks<=0)
		{
			setDead();
			return;
		}

		if(world.isRemote && canTick(world,2,0))
		{
			world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL,
					posX,posY,posZ,
					0.0D, 0.0D, 0.0D);
		}
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound tags)
	{
		super.readEntityFromNBT(tags);
		this.ticks=tags.hasKey("p_ticks")?tags.getInteger("p_ticks"):80;
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound tags)
	{
		super.writeEntityToNBT(tags);
		tags.setInteger("p_ticks",ticks);
	}
}
