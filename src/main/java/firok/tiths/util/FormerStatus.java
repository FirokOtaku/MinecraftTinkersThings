package firok.tiths.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.PotionEffect;

import java.util.ArrayList;
import java.util.List;

/**
 * 往昔水晶数据
 */
public class FormerStatus
{
	public String name=null;
	public float hp=1;
	public int food=1;
	public float saturation=1;
	public List<PotionEffect> effects=new ArrayList<>();

	static String[] keys=new String[]{ "name","hp","food","saturation","effects"};
	static boolean check(NBTTagCompound nbt)
	{
		for(String key:keys) if(!nbt.hasKey(key)) return false;
		return true;
	}

	public static FormerStatus fromNBT(NBTTagCompound nbt)
	{
		if(check(nbt))
		{
			FormerStatus status=new FormerStatus();

			status.name=nbt.getString("name");
			status.hp=nbt.getFloat("hp");
			status.food=nbt.getInteger("food");
			status.saturation=nbt.getFloat("saturation");
			for(NBTBase nbtBase:nbt.getTagList("effects",10))
			{
				NBTTagCompound nbtPE=(NBTTagCompound)nbtBase;
				PotionEffect pe=PotionEffect.readCustomPotionEffectFromNBT(nbtPE);
				status.effects.add(pe);
			}

			return status;
		}
		else return null;
	}
	public NBTTagCompound toNBT()
	{
		NBTTagCompound nbt=new NBTTagCompound();

		nbt.setString("name",name);
		nbt.setFloat("hp",hp);
		nbt.setInteger("food",food);
		nbt.setFloat("saturation",saturation);

		NBTTagList listPE=new NBTTagList();
		for(PotionEffect pe:effects)
		{
			NBTTagCompound nbtPE=new NBTTagCompound();
			pe.writeCustomPotionEffectToNBT(nbtPE);
			listPE.appendTag(nbtPE);
		}
		nbt.setTag("effects",listPE);

		return nbt;
	}

	public static FormerStatus fromPlayer(EntityPlayer player)
	{
		FormerStatus status=new FormerStatus();
		status.name=player.getName();
		status.hp=player.getHealth();
		status.food=player.getFoodStats().getFoodLevel();
		status.saturation=player.getFoodStats().getSaturationLevel();
		for(PotionEffect pe:player.getActivePotionEffects())
		{
			status.effects.add(new PotionEffect(pe));
		}

		return status;
	}
	public void toPlayer(EntityPlayer player)
	{
		player.setHealth(hp);
		player.clearActivePotions();
		for(PotionEffect pe:effects)
		{
			player.addPotionEffect(pe);
		}
		player.getFoodStats().setFoodSaturationLevel(saturation);
		player.getFoodStats().setFoodLevel(food);
	}

	public static boolean hasStatus(NBTTagCompound nbtRoot)
	{
		return nbtRoot.hasKey("status");
	}
}
