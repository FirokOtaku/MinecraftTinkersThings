package firok.tiths.block.logic;

import firok.tiths.block.IRuneBarrierProvider;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RuneBarrierManager
{
	private RuneBarrierManager() { }

	private static int maxTop = 2;
	public static int getMaxTop()
	{
		return maxTop;
	}

//	private static final Map<ResourceLocation, Block> map = new HashMap<>();

	public static <TypeBlock extends Block & IRuneBarrierProvider> void registerBarrierProvider(TypeBlock block)
	{
		Objects.requireNonNull(block);
//		final ResourceLocation key = block.getRegistryName();
//
//		if(map.containsKey(key)) throw new RuntimeException("duplicated registry object: "+key);
//		map.put(key, block);

		final int blockMaxTop = block.provideRuneBarrierTopHeightMax();
		if(blockMaxTop > maxTop) maxTop = blockMaxTop;
	}
}
