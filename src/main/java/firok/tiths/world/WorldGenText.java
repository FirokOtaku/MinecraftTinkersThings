//package firok.tiths.world;
//
//import net.minecraft.block.state.IBlockState;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.world.World;
//
//import java.awt.*;
//import java.awt.image.BufferedImage;
//import java.util.List;
//import java.util.Random;
//
//public class WorldGenText extends BaseChunkGen
//{
//	@Override
//	public List<BlockPos> genAtRealPos(World world, int posX, int posY, int posZ, int chunkVX, int chunkVZ, Random rand)
//	{
////		final int width=100;
////		final int height=20;
////
////		BufferedImage image=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
////		Graphics gra=image.getGraphics();
////		gra.fillRect(0,0,width,height);
////		gra.setFont(new Font("微软雅黑",Font.BOLD,16));
////		gra.setColor(Color.RED);
////		StringBuilder text=new StringBuilder();
////		for(int i=1;i<args.length;i++) text.append(args[i]).append(" ");
////
////		System.out.println("生成");
////		System.out.println(text);
//////							gra.fillOval(0,0,20,20);
////		gra.drawString(text.toString(),0,16);
////
////		BlockPos posStart=new BlockPos( player.posX,player.posY+5,player.posZ);
////		World world=player.world;
////		Random rand=world.rand;
////
////		IBlockState stateGold=net.minecraft.init.Blocks.GOLD_BLOCK.getDefaultState();
////		IBlockState stateIron=net.minecraft.init.Blocks.IRON_BLOCK.getDefaultState();
////		IBlockState stateObsidian=net.minecraft.init.Blocks.OBSIDIAN.getDefaultState();
////		IBlockState stateStone=net.minecraft.init.Blocks.STONE.getDefaultState();
////
////		int count=0;
////
////		for(int x=0;x<width;x++)
////		{
////			for(int y=0;y<height;y++)
////			{
////				int rgb=image.getRGB(x,y);
////
////				BlockPos posTemp=posStart.add(x,height-y,0);
////
////				if(rgb!=-65536)
////				{
////					world.setBlockToAir(posTemp);
////					continue;
////				}
////
////				count++;
////
//////									IBlockState state=stateStone;
//////									switch(rand.nextInt(4))
//////									{
//////										case 0: state=stateGold; break;
//////										case 1: state=stateIron; break;
//////										case 2: state=stateObsidian; break;
//////										case 3: state=stateStone; break;
//////									}
////				IBlockState state=stateIron;
////
////				world.setBlockState(posTemp,state);
////			}
////		}
////
////		System.out.println("数量"+count);
////
//		return null;
//	}
//}
