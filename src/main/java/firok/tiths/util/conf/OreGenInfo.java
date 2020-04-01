package firok.tiths.util.conf;

import java.util.Arrays;

/**
 * 矿物生成
 */
public class OreGenInfo
{
	public String name;

	public Integer minY;
	public Integer maxY;
	public Integer times;
	public Float timeRate;
	public Integer size;
	public Integer[] dims;

	public Float meteoRateChunk;
	public Float meteoRateOre;
	public Integer[] meteoDims;

	public boolean checkOreInfoComplete()
	{
		return minY!=null &&
				maxY!=null &&
				times!=null &&
				timeRate!=null &&
				size!=null &&
				dims!=null;
	}
	public boolean checkMeteoInfoComplete()
	{
		return meteoRateChunk!=null &&
				meteoRateOre!=null &&
				meteoDims!=null;
	}

	@Override
	public String toString()
	{
		return String.format("OreGenInfo{name='%s', minY=%d, maxY=%d, times=%d, timeRate=%s, size=%d, dims=%s, meteoRateChunk=%s, meteoRateOre=%s, meteoDims=%s}", name, minY, maxY, times, timeRate, size, Arrays.toString(dims), meteoRateChunk, meteoRateOre, Arrays.toString(meteoDims));
	}
}
