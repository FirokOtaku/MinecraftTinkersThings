package firok.tiths.traits;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.events.ProjectileEvent;

import java.util.Random;

/**
 * 处理射击方块事件
 */
public interface IHitBlockProjectile
{
	//final BlockPos posHit=event.pos;
	//final World world=event.projectile.world;
	//final Random rand=world.rand;
	//final ItemStack stackAmmo = event.projectile.tinkerProjectile.getItemStack();
	//final NBTTagCompound nbtAmmo = TagUtil.getTagSafe(stackAmmo);
	//final NBTTagList tagAmmoTraits = TagUtil.getTraitsTagList(nbtAmmo);
	//final int sizeAmmoTraits = tagAmmoTraits.tagCount();
	//final Entity shootingEntity = event.projectile.shootingEntity;
	default void hitBlock(
			ProjectileEvent.OnHitBlock event,
			BlockPos pos, World world, IBlockState state, Random rand,
			ItemStack stackAmmo, Entity shootingEntity
	) throws Exception
	{
		;
	}
}
