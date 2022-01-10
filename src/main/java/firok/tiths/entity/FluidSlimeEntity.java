package firok.tiths.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.Effects;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ITag;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;
import slimeknights.mantle.registration.adapter.FluidRegistryAdapter;

import java.util.EnumSet;

import static firok.tiths.util.Predicates.canTickServer;

public class FluidSlimeEntity extends SlimeEntity
{
	int processGrow = 0;
	Fluid fluid = Fluids.WATER;
	public FluidSlimeEntity(EntityType<FluidSlimeEntity> type, World worldIn)
	{
		super(type, worldIn);
		this.enablePersistence(); // no despawn
		this.moveController = new MoveHelperController(this);
	}

	public Fluid getFluid()
	{
		return this.fluid;
	}
	public void setFluid(Fluid fluid)
	{
		this.fluid = fluid;
	}

	public void setSlimeSize(int size, boolean resetHealth) {
		super.setSlimeSize(size, resetHealth);
	}

	protected boolean isDespawnPeaceful() {
		return false;
	}

	/* 显示效果 */

	protected IParticleData getSquishParticle() {
		return ParticleTypes.ITEM_SLIME;
	}


	/* AI部分 */

	@Override
	public void tick()
	{
		super.tick();

		if(canTickServer(world, 200, 0)) // 10秒检查一次
		{
			int sizeCurrent = getSlimeSize();

			if(sizeCurrent >= 3) return; // 长到3不长了

			processGrow++;

			if(processGrow < 12) return; // 2分钟长大一次

			processGrow = 0;
			this.setSlimeSize(sizeCurrent + 1, true);
		}
	}

	public void applyEntityCollision(Entity entityIn) {
		// 无碰撞检测
	}

	public void onCollideWithPlayer(PlayerEntity entityIn) {
		// 无伤害检测
	}

	public boolean handleFluidAcceleration(ITag<Fluid> fluidTag, double motionScale) {
		// 无视水流
		return false;
	}

	// isInFluid
	// called by Entity every tick and refresh isInFluid status
	// returned boolean value indicate whether this entity is in any fluid
	protected boolean func_233566_aG_() {
		this.eyesFluidLevel.clear();
		this.eyesFluidLevel.put(FluidTags.WATER, 0);
		this.eyesFluidLevel.put(FluidTags.LAVA, 0);

		if (this.getRidingEntity() instanceof BoatEntity)
			this.inWater = false;
		else
		{
//			BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();
//
//			for(int l1 = i; l1 < j; ++l1) {
//				for(int i2 = k; i2 < l; ++i2) {
//					for(int j2 = i1; j2 < j1; ++j2) {
//						blockpos$mutable.setPos(l1, i2, j2);
//						FluidState fluidstate = this.world.getFluidState(blockpos$mutable);
//						if (fluidstate.isTagged(fluidTag)) {
//							double d1 = (double)((float)i2 + fluidstate.getActualHeight(this.world, blockpos$mutable));
//							if (d1 >= axisalignedbb.minY) {
//								flag1 = true;
//								d0 = Math.max(d1 - axisalignedbb.minY, d0);
//								if (flag) {
//									Vector3d vector3d1 = fluidstate.getFlow(this.world, blockpos$mutable);
//									if (d0 < 0.4D) {
//										vector3d1 = vector3d1.scale(d0);
//									}
//
//									vector3d = vector3d.add(vector3d1);
//									++k1;
//								}
//							}
//						}
//					}
//				}
//			}
		}
		return false;
	}

	protected void registerGoals()
	{
		// 我不是坏史莱姆哟
		this.goalSelector.addGoal(1, new FluidSlimeEntity.FloatGoal(this));
		this.goalSelector.addGoal(2, new FluidSlimeEntity.FaceRandomGoal(this));
		this.goalSelector.addGoal(5, new FluidSlimeEntity.HopGoal(this));
	}

	static class FaceRandomGoal extends Goal
	{
		private final FluidSlimeEntity slime;
		private float chosenDegrees;
		private int nextRandomizeTime;

		public FaceRandomGoal(FluidSlimeEntity slimeIn) {
			this.slime = slimeIn;
			this.setMutexFlags(EnumSet.of(Goal.Flag.LOOK));
		}

		public boolean shouldExecute() {
			return this.slime.getAttackTarget() == null && (this.slime.onGround || this.slime.isInWater() || this.slime.isInLava() || this.slime.isPotionActive(Effects.LEVITATION)) && this.slime.getMoveHelper() instanceof FluidSlimeEntity.MoveHelperController;
		}

		public void tick() {
			if (--this.nextRandomizeTime <= 0) {
				this.nextRandomizeTime = 40 + this.slime.getRNG().nextInt(60);
				this.chosenDegrees = (float)this.slime.getRNG().nextInt(360);
			}

			((FluidSlimeEntity.MoveHelperController)this.slime.getMoveHelper()).setDirection(this.chosenDegrees, false);
		}
	}

	static class FloatGoal extends Goal {
		private final FluidSlimeEntity slime;

		public FloatGoal(FluidSlimeEntity slimeIn) {
			this.slime = slimeIn;
			this.setMutexFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
			slimeIn.getNavigator().setCanSwim(true);
		}

		public boolean shouldExecute() {
			return (this.slime.isInWater() || this.slime.isInLava()) && this.slime.getMoveHelper() instanceof FluidSlimeEntity.MoveHelperController;
		}

		public void tick() {
			if (this.slime.getRNG().nextFloat() < 0.8F) {
				this.slime.getJumpController().setJumping();
			}

			((FluidSlimeEntity.MoveHelperController)this.slime.getMoveHelper()).setSpeed(1.2D);
		}
	}

	static class HopGoal extends Goal {
		private final FluidSlimeEntity slime;

		public HopGoal(FluidSlimeEntity slimeIn) {
			this.slime = slimeIn;
			this.setMutexFlags(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
		}

		public boolean shouldExecute() {
			return !this.slime.isPassenger();
		}

		public void tick() {
			((FluidSlimeEntity.MoveHelperController)this.slime.getMoveHelper()).setSpeed(1.0D);
		}
	}

	protected float getSoundPitch() {
		float f = this.isSmallSlime() ? 1.4F : 0.8F;
		return ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) * f;
	}

	static class MoveHelperController extends MovementController
	{
		private float yRot;
		private int jumpDelay;
		private final FluidSlimeEntity slime;
		private boolean isAggressive;

		public MoveHelperController(FluidSlimeEntity slimeIn) {
			super(slimeIn);
			this.slime = slimeIn;
			this.yRot = 180.0F * slimeIn.rotationYaw / (float)Math.PI;
		}

		public void setDirection(float yRotIn, boolean aggressive) {
			this.yRot = yRotIn;
			this.isAggressive = aggressive;
		}

		public void setSpeed(double speedIn) {
			this.speed = speedIn;
			this.action = MovementController.Action.MOVE_TO;
		}

		public void tick() {
			this.mob.rotationYaw = this.limitAngle(this.mob.rotationYaw, this.yRot, 90.0F);
			this.mob.rotationYawHead = this.mob.rotationYaw;
			this.mob.renderYawOffset = this.mob.rotationYaw;
			if (this.action != MovementController.Action.MOVE_TO) {
				this.mob.setMoveForward(0.0F);
			} else {
				this.action = MovementController.Action.WAIT;
				if (this.mob.isOnGround()) {
					this.mob.setAIMoveSpeed((float)(this.speed * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
					if (this.jumpDelay-- <= 0) {
						this.jumpDelay = this.slime.getJumpDelay();
						if (this.isAggressive) {
							this.jumpDelay /= 3;
						}

						this.slime.getJumpController().setJumping();
						if (this.slime.makesSoundOnJump()) {
							this.slime.playSound(this.slime.getJumpSound(), this.slime.getSoundVolume(), this.slime.getSoundPitch());
						}
					} else {
						this.slime.moveStrafing = 0.0F;
						this.slime.moveForward = 0.0F;
						this.mob.setAIMoveSpeed(0.0F);
					}
				} else {
					this.mob.setAIMoveSpeed((float)(this.speed * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED)));
				}

			}
		}
	}

	/* 数据持久化 */

	public static final String KEY_NBT_PROCESS_GROW = "process_grow";
	public static final String KEY_NBT_FLUID_TYPE = "fluid_type";

	@Override
	public void readAdditional(CompoundNBT compound)
	{
		super.readAdditional(compound);
		this.processGrow = compound.contains(KEY_NBT_PROCESS_GROW, 1) ?
				compound.getInt(KEY_NBT_PROCESS_GROW) :
				0;
		try
		{
			ResourceLocation rlFluid = new ResourceLocation(compound.getString(KEY_NBT_FLUID_TYPE));
			Fluid fluid = ForgeRegistries.FLUIDS.getValue(rlFluid);
			this.fluid = fluid;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void writeAdditional(CompoundNBT compound)
	{
		super.writeAdditional(compound);
		compound.putInt(KEY_NBT_PROCESS_GROW, this.processGrow);

		try
		{
			ResourceLocation rlFluid = this.fluid.getRegistryName();
			compound.putString(KEY_NBT_FLUID_TYPE, rlFluid.toString());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
