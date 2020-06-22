//package firok.tiths.traits;
//
//import com.google.common.collect.ImmutableList;
//import firok.tiths.TinkersThings;
//import firok.tiths.util.Predicates;
//import net.minecraft.item.ItemStack;
//import net.minecraft.nbt.NBTTagCompound;
//import net.minecraft.util.text.translation.I18n;
//import net.minecraftforge.event.world.BlockEvent;
//import slimeknights.tconstruct.library.traits.AbstractTrait;
//
//import java.util.Iterator;
//import java.util.List;
//
//import static firok.tiths.common.Keys.colorTraitStonePhasing;
//import static firok.tiths.common.Keys.nameTraitStonePhasing;
//import static firok.tiths.util.Predicates.isAnyStone;
//
//// 石之相变
//public class TraitStonePhasing extends AbstractTrait
//{
//	public static final String NBTKey= TinkersThings.MOD_ID+'_'+nameTraitStonePhasing;
//	public TraitStonePhasing()
//	{
//		super(nameTraitStonePhasing, colorTraitStonePhasing);
//	}
//
//	@Override
//	public void blockHarvestDrops(ItemStack tool, BlockEvent.HarvestDropsEvent event)
//	{
//		Iterator<ItemStack> iter=event.getDrops().iterator();
//
//		while(iter.hasNext())
//		{
//			ItemStack stack=iter.next();
//			if(Predicates.isAnyStone(stack))
//			{
//				iter.remove();
//				NBTTagCompound nbt=tool.hasTagCompound()?tool.getTagCompound():new NBTTagCompound();
//				int origin=nbt.hasKey(NBTKey)?nbt.getInteger(NBTKey):0;
//				if(origin<150)
//				{
//					nbt.setInteger(NBTKey,origin+1);
//					tool.setTagCompound(nbt);
//				}
//			}
//		}
//	}
//
//	@Override
//	public List<String> getExtraInfo(ItemStack tool, NBTTagCompound modifierTag)
//	{
//		NBTTagCompound nbt;
//		int amount=tool.hasTagCompound()?((nbt=tool.getTagCompound()).hasKey(NBTKey)?nbt.getInteger(NBTKey):0):0;
//		return ImmutableList.of(I18n.translateToLocal("modifier.stone_phasing.tooltip")+amount);
//	}
//
//
//	public static boolean costStone(ItemStack stack)
//	{
//		NBTTagCompound nbt=stack.hasTagCompound()?stack.getTagCompound():new NBTTagCompound();
//		int amount=nbt.hasKey(NBTKey)?nbt.getInteger(NBTKey):0;
//		amount--;
//		boolean success=amount>=0;
//		if(success)
//		{
//			nbt.setInteger(NBTKey,success?amount:0);
//			stack.setTagCompound(nbt);
//		}
//		return amount>=0;
//	}
//}
