package firok.tiths.traits;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDynamicLiquid;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockStaticLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketBlockChange;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fluids.BlockFluidBase;
import slimeknights.tconstruct.common.TinkerNetwork;
import slimeknights.tconstruct.library.entity.EntityProjectileBase;
import slimeknights.tconstruct.library.events.ProjectileEvent;
import slimeknights.tconstruct.library.traits.AbstractProjectileTrait;

import javax.annotation.Nullable;

import java.util.Random;

import static firok.tiths.common.Keys.colorTraitPonderous;
import static firok.tiths.common.Keys.nameTraitPonderous;

/**
 * 重磅
 */
public class TraitPonderous extends AbstractProjectileTrait implements IHitBlockProjectile
{
	public TraitPonderous()
	{
		super(nameTraitPonderous,colorTraitPonderous);
	}

	@Override
	public void onLaunch(EntityProjectileBase projectileBase, World world, @Nullable EntityLivingBase shooter)
	{
		projectileBase.motionX /= 4d;
		projectileBase.motionY /= 4d;
		projectileBase.motionZ /= 4d;
	}

	@Override
	public float knockBack(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float knockback, float newKnockback, boolean isCritical)
	{
		return (newKnockback + 0.5f) * 1.5f;
	}

	@Override
	public float damage(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float newDamage, boolean isCritical)
	{
		return newDamage + damage * 0.5f;
	}

	@Override
	public void hitBlock(ProjectileEvent.OnHitBlock event, BlockPos pos, World world, IBlockState state, Random rand, ItemStack stackAmmo, Entity shootingEntity)
	{
		if(world.isRemote)
		{
			int i=36;
			final int cx=pos.getX(),cy=pos.getY(),cz=pos.getZ();
			final int idParticle=Block.getStateId(state);
			while(i-->0)
			{
				float px = cx + 2.5f * (rand.nextFloat() - 0.5f) + 1f;
				float py = cy + 2.5f * (rand.nextFloat() - 0.5f) + 0.5f;
				float pz = cz + 2.5f * (rand.nextFloat() - 0.5f) + 1f;
				float sx = 4 * (rand.nextFloat() - 0.5f);
				float sy = 0.5f;
				float sz = 4 * (rand.nextFloat() - 0.5f);
				world.spawnParticle(
						EnumParticleTypes.BLOCK_CRACK,
						px, py, pz,
						sx, sy, sz,
						idParticle
				);
			}
		}

		if(!(shootingEntity instanceof EntityPlayer)) return;
		EntityPlayer player=(EntityPlayer) shootingEntity;

		int countBroken=0;
		try
		{
			for(int ox=-1;ox<=1;ox++)
			{
				for(int oy=-1;oy<=1;oy++)
				{
					for(int oz=-1;oz<=1;oz++)
					{
						BlockPos posTemp=pos.add(ox,oy,oz);
						IBlockState stateTemp=world.getBlockState(posTemp);
						Block blockTemp=stateTemp.getBlock();
						boolean hasTile=blockTemp.hasTileEntity(stateTemp);
						float hardness=stateTemp.getBlockHardness(world,posTemp);
//					    ItemStack stack=null;

						if(!hasTile &&
							hardness <= 1.55 &&
							!(blockTemp instanceof BlockFluidBase) && // 避免破坏流体
							!(blockTemp instanceof BlockLiquid)
						)
						{
							if(!world.isRemote && player instanceof EntityPlayerMP)
							{
								EntityPlayerMP playerMP=(EntityPlayerMP)shootingEntity;

								int xp= ForgeHooks.onBlockBreakEvent(world,playerMP.interactionManager.getGameType(), playerMP, posTemp);

//							    TileEntity tileEntity = world.getTileEntity(posTemp);

								boolean hasRemoved=blockTemp.removedByPlayer(stateTemp, world, posTemp, playerMP, true);
								if(hasRemoved)
								{
									countBroken++;

									blockTemp.onBlockDestroyedByPlayer(world, posTemp, stateTemp);
//								    blockTemp.harvestBlock(world, player, posTemp, stateTemp, tileEntity, stack);
									blockTemp.dropXpOnBlockBreak(world, posTemp, xp);
									TinkerNetwork.sendPacket(playerMP, new SPacketBlockChange(world, posTemp));
								}
							}
							if(world.isRemote)
							{
								world.playBroadcastSound(2001, posTemp, Block.getStateId(stateTemp));
								if(blockTemp.removedByPlayer(stateTemp, world, posTemp, player, true)) {
									blockTemp.onBlockDestroyedByPlayer(world, posTemp, stateTemp);
								}
							}
						}
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(countBroken>0 && event.projectile != null)
			{
				event.projectile.setDead();
			}
		}
	}
}
