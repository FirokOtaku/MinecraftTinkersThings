package firok.tiths.client.book;

import c4.conarm.lib.book.ArmoryBook;
import firok.tiths.TinkersThings;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.mantle.client.book.BookLoader;
import slimeknights.mantle.client.book.repository.ModuleFileRepository;
import slimeknights.tconstruct.library.book.TinkerBook;

@SideOnly(Side.CLIENT)
public class Books
{
	public static void initBooks()
	{
		BookLoader.registerPageType(ContentItem.ID, ContentItem.class);
		BookLoader.registerPageType(ContentPotion.ID, ContentPotion.class);

		initTool();
		if(TinkersThings.enableConarm()) initArmor();
	}

	private static void initTool()
	{
		TinkerBook.INSTANCE.addRepository(new ModuleFileRepository("tiths:tinker_book"));
	}
	private static void initArmor()
	{
		ArmoryBook.INSTANCE.addRepository(new ModuleFileRepository("tiths:armory_book"));
	}
}
