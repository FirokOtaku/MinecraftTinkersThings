package firok.tiths.util;

import c4.conarm.lib.traits.AbstractArmorTrait;
import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import firok.tiths.TinkersThings;
import firok.tiths.intergration.conarm.IAbstractArmorTrait;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.border.WorldBorder;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.Util;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;
import slimeknights.tconstruct.library.tinkering.Category;
import slimeknights.tconstruct.library.tinkering.ITinkerable;
import slimeknights.tconstruct.library.tools.ToolNBT;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.traits.ITrait;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.ToolHelper;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * 内部操作
 */
public final class InnerActions
{
	private InnerActions(){}

	/**
	 * 应用强化
	 */
	public static void apply(NBTTagCompound rootCompound, NBTTagCompound modifierTag, Applier fun)
	{
		ModifierNBT.IntegerNBT modData = ModifierNBT.readInteger(modifierTag);

		Set<Category> categories = ImmutableSet.copyOf(TagUtil.getCategories(rootCompound));
		boolean harvest = categories.contains(Category.HARVEST);
		boolean weapon = categories.contains(Category.WEAPON);
		boolean launcher = categories.contains(Category.LAUNCHER);

		ToolNBT data = TagUtil.getToolStats(rootCompound);
		int level = modData.current;

		fun.apply(rootCompound, data,level,harvest,weapon,launcher);
	}



	/* ---- 添加物品描述 ----*/
	@SideOnly(Side.CLIENT)
	public static void addInformation(Item item, List<String> list, ITooltipFlag flag, Object... params)
	{
		addInformation(item.getUnlocalizedName(), list, flag, params);
	}
	@SideOnly(Side.CLIENT)
	public static void addInformation(Block block, List<String> list, ITooltipFlag flag, Object... params)
	{
		addInformation(block.getUnlocalizedName(), list, flag, params);
	}
	@SideOnly(Side.CLIENT)
	public static void addInformation(String key, List<String> list, ITooltipFlag flag, Object... params)
	{
		if(key==null || list==null) return;

		String keyDesc=key+".desc";
		String keyMore=key+".more";

		String value;
		TRANSLATE:if(Util.isShiftKeyDown())
		{
			value=I18n.format(keyMore,params);
			if(!value.equals(keyMore)) break TRANSLATE;

			value=I18n.format(keyDesc,params);
			if(value.equals(keyDesc)) value=null;
		}
		else
		{
			value=I18n.format(keyDesc,params);
			if(value.equals(keyDesc)) value=null;
		}

		if(value!=null)
		{
			Collections.addAll(list, value.split("<br>"));
		}
	}

	/* ---- 给物品增加特性 ---- */
	public static boolean addTrait(ITrait trait, NBTTagCompound rootCompound, NBTTagCompound modifierTag)
	{
		return addTrait(trait.getIdentifier(),rootCompound,modifierTag);
	}
	/**
	 * 给物品添加特性
	 * @see AbstractTrait#applyEffect(net.minecraft.nbt.NBTTagCompound, net.minecraft.nbt.NBTTagCompound)
	 * @param idTrait 要添加特性的id
	 * @return 之前是否已经添加过这个特性
	 */
	public static boolean addTrait(String idTrait, NBTTagCompound rootCompound, NBTTagCompound modifierTag)
	{
		NBTTagList traits = TagUtil.getTraitsTagList(rootCompound);
		for(int i = 0; i < traits.tagCount(); i++)
		{
			if(idTrait.equals(traits.getStringTagAt(i)))
			{
				return true;
			}
		}

		traits.appendTag(new NBTTagString(idTrait));
		TagUtil.setTraitsTagList(rootCompound, traits);

		return false;
	}


	/* ---- 给材料增加属性 ---- */
	public static int addMaterialTraits(Material material, String[] traitNames)
	{
		return addMaterialTraits(material,traitNames,null,false);
	}
	public static int addMaterialTraits(Material material,String[] traitNames,String compo)
	{
		return addMaterialTraits(material, traitNames, compo, false);
	}
	public static boolean addMaterialTrait(Material material,String traitName)
	{
		return addMaterialTrait(material, traitName, null, false);
	}
	public static boolean addMaterialTrait(Material material,String traitName,String compo)
	{
		return addMaterialTrait(material, traitName, compo, false);
	}

	public static int addMaterialTraits(Material material, String[] traitNames,boolean checkArmor)
	{
		return addMaterialTraits(material,traitNames,null,checkArmor);
	}
	public static int addMaterialTraits(Material material,String[] traitNames,String compo,boolean checkArmor)
	{
		if(traitNames==null || traitNames.length<=0)
		{
			return 0;
		}

		int ret=0;
		for(String traitName:traitNames)
		{
			ret+= addMaterialTrait(material,traitName,compo,checkArmor)? 1: 0;
		}
		return ret;
	}
	public static boolean addMaterialTrait(Material material,String traitName,boolean checkArmor)
	{
		return addMaterialTrait(material, traitName, null,checkArmor);
	}
	public static boolean addMaterialTrait(Material material,String traitName,String compo,boolean checkArmor)
	{
//		log(String.format("adding trait: %s -> %s [ %s ]",traitName,material.identifier,compo));
		if(material==null||traitName==null) return false;

		String traitNameTarget=checkArmor?(traitName+"_armor"):traitName;
		ITrait trait= TinkerRegistry.getTrait(traitNameTarget);

		if(trait!=null)
		{
			if(checkArmor && !(trait instanceof IAbstractArmorTrait || trait instanceof AbstractArmorTrait))
			{
				throw new RuntimeException("CHECKING_ARMOR_TRAIT : not an armor trait! "+traitNameTarget);
			}
			material.addTrait(trait,compo);
			return true;
		}
//		else
//		{
//			log("cannot find trait!");
//		}
		return false;
	}

	/**
	 * 用反射读数据
	 * @see ObfuscationReflectionHelper
	 */
	@Deprecated
	public static Object get(Class<?> clasz,String fieldName,Object obj) throws NoSuchFieldException, IllegalAccessException
	{
		Field field=clasz.getDeclaredField(fieldName);
		field.setAccessible(true);
		return field.get(obj);
	}

	/**
	 * 用反射写数据
	 * @see ObfuscationReflectionHelper
	 */
	@Deprecated
	public static void set(Class<?> clasz,String fieldName,Object obj,Object value) throws NoSuchFieldException, IllegalAccessException
	{
		Field field=clasz.getDeclaredField(fieldName);
		field.setAccessible(true);
		field.set(obj,value);
	}

	/**
	 * 用来判断是不是本地客户端世界
	 * @param player 玩家
	 */
	public static boolean isLocalClient(EntityPlayer player)
	{
		try { return player.world.isRemote && Minecraft.getMinecraft().player.getDisplayNameString().equals(player.getDisplayNameString()); }
		catch (Exception ignore) { return false; }
	}

	/* ---- 一些数据操作工具方法 ---- */

	public static String getStr(JsonObject json, String key)
	{
		JsonElement temp;
		return json.has(key) && (temp=json.get(key)).isJsonPrimitive()?
				temp.getAsString():null;
	}
	public static void getStr(JsonObject json, String key, Consumer<String> callback)
	{
		Optional.ofNullable(getStr(json,key)).ifPresent(callback);
	}
	public static Float getFloat(JsonObject json, String key)
	{
		JsonElement temp;
		return json.has(key) && (temp=json.get(key)).isJsonPrimitive()?
				temp.getAsFloat():null;
	}
	public static void getFloat(JsonObject json, String key, Consumer<Float> callback)
	{
		Optional.ofNullable(getFloat(json,key)).ifPresent(callback);
	}
	public static Integer getInteger(JsonObject json, String key)
	{
		JsonElement temp;
		return json.has(key) && (temp=json.get(key)).isJsonPrimitive()?
				temp.getAsInt(): null;
	}
	public static void getInteger(JsonObject json, String key, Consumer<Integer> callback)
	{
		Optional.ofNullable(getInteger(json,key)).ifPresent(callback);
	}
	public static Integer[] getIntegers(JsonObject json, String key)
	{
		if(!json.has(key) || !json.get(key).isJsonArray()) return null;

		JsonArray array=json.get(key).getAsJsonArray();
		final int size=array.size();

		Integer[] ret=new Integer[size];

		for(int i=0;i<size;i++)
		{
			ret[i]= array.get(i).getAsInt();
		}

		return ret;
	}
	public static void getIntegers(JsonObject json, String key, Consumer<Integer[]> callback)
	{
		Optional.ofNullable(getIntegers(json,key)).ifPresent(callback);
	}
	public static Byte getByte(JsonObject json, String key)
	{
		JsonElement temp;
		return json.has(key) && (temp=json.get(key)).isJsonPrimitive()?
				temp.getAsByte(): null;
	}
	public static void getByte(JsonObject json, String key, Consumer<Byte> callback)
	{
		Optional.ofNullable(getByte(json,key)).ifPresent(callback);
	}
	public static Boolean getBool(JsonObject json, String key)
	{
		JsonElement temp;
		return json.has(key) && (temp=json.get(key)).isJsonPrimitive()?
				temp.getAsBoolean(): null;
	}
	public static void getBool(JsonObject json, String key, Consumer<Boolean> callback)
	{
		Optional.ofNullable(getBool(json,key)).ifPresent(callback);
	}

	public static String[] getStrs(JsonObject json, String key)
	{
		if(!json.has(key) || !json.get(key).isJsonArray()) return null;

		JsonArray array=json.get(key).getAsJsonArray();
		final int size=array.size();

		String[] ret=new String[size];

		for(int i=0;i<size;i++)
		{
			ret[i]=array.get(i).getAsString();
		}

		return ret;
	}
	public static void getStrs(JsonObject json, String key, Consumer<String[]> callback)
	{
		Optional.ofNullable(getStrs(json,key)).ifPresent(callback);
	}

	public static JsonObject getObj(JsonObject json, String key)
	{
		JsonElement temp;
		return json.has(key) && (temp=json.get(key)).isJsonObject()?
				temp.getAsJsonObject():null;
	}
	public static void getObj(JsonObject json, String key, Consumer<JsonObject> callback)
	{
		Optional.ofNullable(getObj(json,key)).ifPresent(callback);
	}
	public static JsonArray getArr(JsonObject json, String key)
	{
		JsonElement temp;
		return json.has(key) && (temp=json.get(key)).isJsonArray()?
				temp.getAsJsonArray():null;
	}

	public static boolean greater(Number num,Number...numbers)
	{
		double temp=num.doubleValue();
		for(Number number:numbers) if(number.doubleValue()<temp) return false;
		return true;
	}
	public static boolean lesser(Number num,Number...numbers)
	{
		double temp=num.doubleValue();
		for(Number number:numbers) if(number.doubleValue()>temp) return false;
		return true;
	}

	public static int[] arr(Integer[] arr)
	{
		if(!Objects.nonNull(arr) || arr.length <= 0) return new int[0];
		int[] ret=new int[arr.length];
		for(int i=0;i<arr.length;i++) ret[i]=arr[i];
		return ret;
	}

	/**
	 * 用位运算把int数组转换为long数组
	 */
	public static long[] toLongArray(int[] values)
	{
		if(values==null||values.length<=0||values.length%2!=0) return new long[0];
		long[] ret=new long[values.length/2];
		for(int i=0;i<values.length;i+=2)
		{
			int high=values[i],low=values[i+1];
			ret[i/2]=(((long)high)<<32)|(low & 0xFFFF_FFFFL);
		}
		return ret;
	}

	/**
	 * 用位运算把long数组转换为int数组
	 */
	public static int[] toIntArray(long[] values)
	{
		if(values==null||values.length<=0) return new int[0];
		int[] ret=new int[values.length*2];
		for(int i=0;i<values.length;i++)
		{
			long raw=values[i];
			int high=(int)(raw>>>32),low=(int)raw;
			ret[i*2]=high;
			ret[i*2+1]=low;
		}
		return ret;
	}

	/**
	 * 判断数组里有没有指定元素
	 */
	public static <T> boolean has(T[] array,T bean)
	{
		if(array==null || array.length<=0) return false;
		for(T obj : array)
		{
			if((obj==null && bean==null) || (obj.equals(bean))) return true;
		}
		return false;
	}

	/**
	 * 角度弧度互相换算用的
	 */
	public static final float FAC=(float) Math.PI/180;

	public static boolean isTrue(Object object)
	{
		return object instanceof Boolean && (Boolean) object;
	}
	public static boolean isFalse(Object obj)
	{
		return obj==null || (obj instanceof Boolean && !(Boolean)obj);
	}

	public static NBTTagCompound getNBT(ItemStack stack)
	{
		NBTTagCompound nbt=stack.getTagCompound();
		if(nbt==null)
		{
			nbt=new NBTTagCompound();
			stack.setTagCompound(nbt);
		}
		return nbt;
	}

	/**
	 * 获取某个玩家穿戴的护甲的所有特性
	 */
	public static Set<ITrait> getArmorTraits(EntityPlayer player)
	{
		Set<ITrait> ret=new HashSet<>();
		if(player!=null)
		{
			for(ItemStack stackArmor:player.getArmorInventoryList())
			{
				if(stackArmor==null || stackArmor.isEmpty() || !(stackArmor.getItem() instanceof ITinkerable)) continue;

				List<ITrait> traits= ToolHelper.getTraits(stackArmor);

				ret.addAll(traits);
			}
		}
		return ret;
	}
}
