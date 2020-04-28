package firok.tiths.util.conf;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * 用来从json里取值的
 */
public class Values
{
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
		if(!__(arr) || arr.length<=0) return new int[0];
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

	public static boolean __(Object test)
	{
		return test!=null;
	}
}
