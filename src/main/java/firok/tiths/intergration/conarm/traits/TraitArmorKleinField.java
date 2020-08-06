package firok.tiths.intergration.conarm.traits;

import c4.conarm.lib.traits.AbstractArmorTrait;
import firok.tiths.common.SoundEvents;
import firok.tiths.util.ITraitData;
import firok.tiths.util.TraitExtraData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import static firok.tiths.common.Keys.colorTraitKleinField;
import static firok.tiths.common.Keys.nameTraitKleinField;
import static firok.tiths.util.Predicates.canDealWith;
import static firok.tiths.util.Predicates.canTick;

/**
 * 克莱因力场
 */
public class TraitArmorKleinField extends AbstractArmorTrait implements ITraitData<TraitArmorKleinField.KleinFieldData>
{
	public TraitArmorKleinField()
	{
		super(nameTraitKleinField,colorTraitKleinField);
	}



	@Override
	public float onHurt(ItemStack armor, EntityPlayer player, DamageSource source,final float damage, float newDamage, LivingHurtEvent evt)
	{
		if(!player.world.isRemote)
		{
			player.world.playSound(null,player.posX,player.posY,player.posZ, SoundEvents.effectForceField, SoundCategory.PLAYERS,1,1);
		}

		if(newDamage>0 && canDealWith(source,true,null,null,null,null))
		{
			KleinFieldData data=readExtraDataFromStack(armor);
			if(data.point>=0)
			{
				data.point+=damage/4;
				newDamage-=damage/4;
				if(newDamage<0) newDamage=0;
				player.world.playSound(null,player.posX,player.posY,player.posZ,
						SoundEvents.effectForceField,SoundCategory.MASTER,1,1);

				if(data.point>=15) // 吸收15点伤害
				{
					data.point=-120; // 120点恢复期 每秒4点 = 30秒
					player.world.playSound(null,player.posX,player.posY,player.posZ,
							SoundEvents.effectTransforming,SoundCategory.MASTER,1,1);

				}

				writeExtraDataToStack(armor,data);
			}
		}

		return newDamage;
	}

	@Override
	public void onArmorTick(ItemStack tool, World world, EntityPlayer player)
	{
		if(canTick(world,5,0))
		{
			KleinFieldData data=readExtraDataFromStack(tool);

			if(data.point<0) // 已经超过吸收上限 正在恢复CD
			{
				data.point-=1;
			}
			else // data.point>=0 仍然在吸收伤害
			{
				data.point-=0.25; // 每秒
			}

			writeExtraDataToStack(tool,data);
		}
	}

	public static class KleinFieldData implements TraitExtraData
	{
		public float point;
	}

	@Override
	public String getDataKey()
	{
		return "KleinFieldData";
	}

	@Override
	public KleinFieldData readExtraData(NBTTagCompound nbtExtraData)
	{
		KleinFieldData data=new KleinFieldData();
		data.point = nbtExtraData!=null && nbtExtraData.hasKey("point") ? nbtExtraData.getFloat("point") : 0;
		return data;
	}

	@Override
	public NBTTagCompound writeExtraData(KleinFieldData data)
	{
		NBTTagCompound nbt=new NBTTagCompound();
		nbt.setFloat("point",data.point);
		return nbt;
	}
}
