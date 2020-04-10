package firok.tiths.potion;

import firok.tiths.TinkersThings;
import firok.tiths.common.Keys;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;

import static firok.tiths.common.Keys.uuidPotionRootedSpeed;
import static firok.tiths.util.Predicates.canTrigger;

// 缠绕
public class PotionRooted extends BasePotion
{
	public PotionRooted()
	{
		super(new ResourceLocation(TinkersThings.MOD_ID,"textures/potions/rooted.png"),true, Keys.colorPotionRooted);
	}

	@Override
	public boolean isReady(int tick, int level)
	{
		return tick%3==0;
	}

	static int ID_LEAVES=Integer.MIN_VALUE;
	static int ID_WOOD=Integer.MIN_VALUE;

	@Override
	public void performEffect(EntityLivingBase entity, int level)
	{
		if(entity.world.isRemote)
		{
			int id2spawn=
					canTrigger(TinkersThings.randClient,0.3)?
					((ID_WOOD==Integer.MIN_VALUE)? (ID_WOOD=Block.getIdFromBlock(Blocks.LOG)):ID_WOOD):
					(ID_LEAVES==Integer.MIN_VALUE? (ID_LEAVES=Block.getIdFromBlock(Blocks.LEAVES)):ID_LEAVES);
			entity.world.spawnParticle(EnumParticleTypes.BLOCK_DUST,
					entity.posX-0.5+TinkersThings.randClient.nextFloat(),
					entity.posY+entity.getEyeHeight()-0.4,
					entity.posZ-0.5+TinkersThings.randClient.nextFloat(),
					0,0,0,
					id2spawn
					);
		}
	}

	@Override
	public void postInit()
	{
		registerPotionAttributeModifier(
				SharedMonsterAttributes.MOVEMENT_SPEED,
				uuidPotionRootedSpeed,
				-1,
				2
		);
	}
}
