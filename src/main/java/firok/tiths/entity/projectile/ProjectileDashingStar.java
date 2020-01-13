package firok.tiths.entity.projectile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.entity.EntityProjectileBase;

import static firok.tiths.util.Predicates.canTick;

// 投射物实体 - 星绽
public class ProjectileDashingStar extends EntityProjectileBase
{
	public float speed,damage;
	public int ticks=40;
	public String shotter=null;

	public ProjectileDashingStar(World world) {
		super(world);
	}

	public ProjectileDashingStar(World world, double d, double d1, double d2) {
		super(world, d, d1, d2);
	}

	public ProjectileDashingStar(World world, EntityPlayer player, float speed, float inaccuracy, ItemStack stack, ItemStack launchingStack) {
		super(world, player, speed, inaccuracy, 1f, stack, launchingStack);
	}

	@Override
	protected void init() {
//		setSize(0.3f, 0.1f);
		setSize(0,0);
		this.bounceOnNoDamage = false;
	}

	@Override
	public double getGravity() {
		return 0d; // integer division. so the first ticks it will have no gravity at all.
	}

	@Override
	public double getSlowdown() {
		return -0.05f;
	}

//	@Override
//	protected void playHitEntitySound() {
//
//	}

	public static final DamageSource DamageSource=new DamageSource("DASHING_STAR").setProjectile().setMagicDamage();
	public void onHitEntity(RayTraceResult raytraceResult) {

		Entity entityHit = raytraceResult.entityHit;

		if(entityHit instanceof EntityPlayer && ((EntityPlayer) entityHit).getDisplayNameString().equals(shotter))
			return;

		entityHit.hurtResistantTime=0;
		entityHit.attackEntityFrom(DamageSource,damage);
		playHitEntitySound();
		setDead();
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
			world.spawnParticle(EnumParticleTypes.FLAME,
					posX+rand.nextFloat()*0.1,posY+rand.nextFloat()*0.1,posZ+rand.nextFloat()*0.1,
					0.0D, 0.0D, 0.0D);
		}
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound tags)
	{
		super.readEntityFromNBT(tags);
		this.speed=tags.getFloat("p_speed");
		this.damage=tags.getFloat("p_damage");
		this.ticks=tags.hasKey("p_ticks")?tags.getInteger("p_ticks"):40;
		this.shotter=tags.hasKey("p_shotter")?tags.getString("p_shotter"):null;
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound tags)
	{
		super.writeEntityToNBT(tags);
		tags.setFloat("p_speed",speed);
		tags.setFloat("p_damage",damage);
		tags.setInteger("p_ticks",ticks);
		tags.setString("p_shotter",shotter);
	}
}