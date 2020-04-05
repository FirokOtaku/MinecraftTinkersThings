package firok.tiths.entity.item;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

// 免疫火焰 爆炸伤害的物品实体
public class EntityItemStable extends EntityItem
{
	public EntityItemStable(World world, double posX, double posY, double posZ)
	{
		super(world, posX, posY, posZ);
	}

	public EntityItemStable(World world, double posX, double posY, double posZ, ItemStack stack)
	{
		super(world, posX, posY, posZ, stack);
	}

	public EntityItemStable(World world)
	{
		super(world);
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float damage)
	{
		if(source==DamageSource.ON_FIRE||
			source==DamageSource.IN_FIRE||
			source==DamageSource.CACTUS||
			source==DamageSource.DRAGON_BREATH||
			source==DamageSource.LAVA||
			source.isExplosion())
			return false;
		else
			return super.attackEntityFrom(source, damage);
//		if (!this.world.isRemote && !this.isDead) {
//			if (this.isEntityInvulnerable(source)) {
//				return false;
//			} else if (!this.getItem().isEmpty() && this.getItem().getItem() == Items.NETHER_STAR && source.isExplosion()) {
//				return false;
//			} else {
//				this.setBeenAttacked();
//				this.health = (int)((float)this.health - damage);
//				if (this.health <= 0) {
//					this.setDead();
//				}
//
//				return false;
//			}
//		} else {
//			return false;
//		}
	}
}
