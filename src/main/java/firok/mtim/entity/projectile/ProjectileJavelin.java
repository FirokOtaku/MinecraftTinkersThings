package firok.mtim.entity.projectile;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.entity.EntityProjectileBase;

// todo
// 投射物实体 - 标枪
public class ProjectileJavelin extends EntityProjectileBase
{
	// animation
	public int spin = 0;
	public int rollAngle = 0;

	public ProjectileJavelin(World world) {
		super(world);
	}

	public ProjectileJavelin(World world, double d, double d1, double d2) {
		super(world, d, d1, d2);
	}

	public ProjectileJavelin(World world, EntityPlayer player, float speed, float inaccuracy, ItemStack stack, ItemStack launchingStack) {
		super(world, player, speed, inaccuracy, 1f, stack, launchingStack);
	}

	@Override
	protected void init() {
		setSize(0.3f, 0.1f);
		this.bounceOnNoDamage = false;
	}

	@Override
	public double getGravity() {
		return 0.08d; // integer division. so the first ticks it will have no gravity at all.
	}

	@Override
	public double getSlowdown() {
		return 0.05f;
	}

	@Override
	protected void playHitEntitySound() {

	}

	@Override
	public void readSpawnData(ByteBuf data) {
		super.readSpawnData(data);

		// this is only relevant clientside only, so we don't actually have it on the server
		spin = rand.nextInt(360);
		rollAngle = 7 - rand.nextInt(14);
	}
}