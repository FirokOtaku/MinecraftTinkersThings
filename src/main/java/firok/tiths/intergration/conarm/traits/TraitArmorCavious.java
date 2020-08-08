package firok.tiths.intergration.conarm.traits;

import c4.conarm.lib.traits.AbstractArmorTrait;
import firok.tiths.common.Configs;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import static firok.tiths.common.Keys.colorTraitCavious;
import static firok.tiths.common.Keys.nameTraitCavious;

/**
 * 洞藏
 */
public class TraitArmorCavious extends AbstractArmorTrait
{
	public TraitArmorCavious()
	{
		super(nameTraitCavious,colorTraitCavious);
	}

	@Override
	public float onHurt(ItemStack armor, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingHurtEvent evt)
	{
		if(player==null || player.getActivePotionEffect(MobEffects.WEAKNESS)!=null) return newDamage;

		World world=player.world;
		if(!world.isRemote)
		{
			BlockPos posCenter=player.getPosition().add(0,-3,0);
			boolean canHide=true;
			SEARCH:for(int ox=-1;ox<=1;ox++)
			{
				for(int oz=-1;oz<=1;oz++)
				{
					for(int oy=0;oy<=1;oy++)
					{
						BlockPos posTemp=posCenter.add(ox,oy,oz);

						IBlockState stateTemp=world.getBlockState(posTemp);
						Block blockTemp=stateTemp.getBlock();
						if (blockTemp != Blocks.STONE &&
							blockTemp != Blocks.COBBLESTONE &&
						    blockTemp != Blocks.DIRT &&
						    blockTemp != Blocks.SAND &&
						    blockTemp != Blocks.AIR
						)
						{

							canHide=false;
							break SEARCH;
						}
					}
				}
			}

			if(canHide)
			{
				for(int ox=-1;ox<=1;ox++)
				{
					for(int oz=-1;oz<=1;oz++)
					{
						for(int oy=0;oy<=1;oy++)
						{
							BlockPos posTemp=posCenter.add(ox,oy,oz);

							world.setBlockToAir(posTemp);
						}
					}
				}

				player.setPositionAndUpdate(player.posX,player.posY-3,player.posZ);
				player.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, Configs.ArmorTraits.cavious_weakness_duration, 0));
				world.playSound(null,player.posX,player.posY,player.posZ, SoundEvents.BLOCK_ANVIL_BREAK, SoundCategory.MASTER,1,1);
			}
		}
		return super.onHurt(armor, player, source, damage, newDamage, evt);
	}
}
