package firok.tiths.item.bauble;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import baubles.api.cap.IBaublesItemHandler;
import firok.tiths.util.InnerActions;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.util.List;

import static firok.tiths.util.Predicates.canTick;

/**
 * 回溯纹章
 */
public class ItemCharmLapsing extends ItemBauble
{
	public ItemCharmLapsing()
	{
		super(BaubleType.CHARM);
	}

	private static final byte length=20;
	private static final String[] KX=new String[length];
	private static final String[] KY=new String[length];
	private static final String[] KZ=new String[length];
	static
	{
		for(byte i=0;i<length;i++)
		{
			KX[i]="x"+i;
			KY[i]="y"+i;
			KZ[i]="z"+i;
		}
	}
	private static final String KCD="cd";
	private static final String KT="time";

	@Override
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn)
	{
		NBTTagCompound nbt= InnerActions.getNBT(stack);

		for(String kx:KX) nbt.setFloat(kx,0);
		for(String kz:KZ) nbt.setFloat(kz,0);
		for(String ky:KY) nbt.setFloat(ky,Float.MIN_VALUE);
		nbt.setByte(KCD,(byte)0);
		nbt.setByte(KT,(byte)0);
	}

	@Override
	public void onWornTick(ItemStack itemstack, EntityLivingBase player)
	{
		if(!canTick(player.world,5,0)) return;

		NBTTagCompound nbt=InnerActions.getNBT(itemstack);

		byte time=nbt.hasKey(KT)?nbt.getByte(KT):0;
		byte cd=nbt.hasKey(KCD)?nbt.getByte(KCD):0;

		if(cd>0)
		{
			cd--;
			nbt.setByte(KCD,cd);
		}
		time++;
		time%=length;
		nbt.setByte(KT,time);

		nbt.setFloat(KX[time],(float)player.posX);
		nbt.setFloat(KY[time],(float)player.posY);
		nbt.setFloat(KZ[time],(float)player.posZ);
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
	{
//		if(target.isServerWorld())
//		{
//			try
//			{
//				System.out.println("输出信息:");
//				NBTTagCompound nbt=InnerActions.getNBT(stack);
//				Set<String> keys=nbt.getKeySet();
//				for(String key:keys)
//				{
//					System.out.println(key+" : "+nbt.getTag(key));
//				}
//			}
//			catch(Exception e)
//			{
//				e.printStackTrace();
//			}
//		}
//		return true;
		return false;
	}

	public void lapse(ItemStack stackRing, EntityPlayer player)
	{
		NBTTagCompound nbt=InnerActions.getNBT(stackRing);
		byte time=nbt.hasKey(KT)?nbt.getByte(KT):0;
		time++;
		time%=length;

		double px,py,pz;
		px=nbt.hasKey(KX[time])?nbt.getFloat(KX[time]):Float.MIN_VALUE;
		py=nbt.hasKey(KY[time])?nbt.getFloat(KY[time]):Float.MIN_VALUE;
		pz=nbt.hasKey(KZ[time])?nbt.getFloat(KZ[time]):Float.MIN_VALUE;

		if(px==Float.MIN_VALUE || py==Float.MIN_VALUE || pz==Float.MIN_VALUE) return; // 还没有记录这个时间点的位置信息 不能传送

		player.setPositionAndUpdate(px,py,pz);

		nbt.setByte(KCD,length); // 更新cd
	}

	public ItemStack getCharmNoneCD(EntityPlayer player)
	{
		if(player==null) return null;

		IBaublesItemHandler handler=BaublesApi.getBaublesHandler(player);
		int size=handler.getSlots();
		for(int i=0;i<size;i++)
		{
			ItemStack stack=handler.getStackInSlot(i);
			if(stack==null || stack.isEmpty() || !(stack.getItem() instanceof ItemCharmLapsing)) continue;

			NBTTagCompound nbt=InnerActions.getNBT(stack);
			byte cd=nbt.hasKey(KCD)?nbt.getByte(KCD):0;

			if(cd>0) continue;

			return stack;
		}
		return null;
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> list, ITooltipFlag flagIn)
	{
		super.addInformation(stack, worldIn, list, flagIn);
		NBTTagCompound nbt=InnerActions.getNBT(stack);
		byte cd=nbt.hasKey(KCD)?nbt.getByte(KCD):0;
		byte time=nbt.hasKey(KT)?nbt.getByte(KT):0;
		time++;
		time%=length;

		double px,py,pz;
		px=nbt.hasKey(KX[time])?nbt.getFloat(KX[time]):Float.MIN_VALUE;
		py=nbt.hasKey(KY[time])?nbt.getFloat(KY[time]):Float.MIN_VALUE;
		pz=nbt.hasKey(KZ[time])?nbt.getFloat(KZ[time]):Float.MIN_VALUE;
		if(px==Float.MIN_VALUE || py==Float.MIN_VALUE || pz==Float.MIN_VALUE) return; // 还没有记录这个时间点的位置信息 不能传送

		list.add(String.format("> %f , %f , %f <",px,py,pz));
	}
}
