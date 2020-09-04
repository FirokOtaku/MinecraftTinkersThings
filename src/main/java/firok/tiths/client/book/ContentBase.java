package firok.tiths.client.book;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.mantle.client.book.data.element.TextData;
import slimeknights.mantle.client.gui.book.GuiBook;
import slimeknights.mantle.client.gui.book.element.BookElement;
import slimeknights.mantle.client.gui.book.element.ElementText;
import slimeknights.tconstruct.library.book.TinkerPage;

import java.util.ArrayList;

@SideOnly(Side.CLIENT)
public abstract class ContentBase extends TinkerPage
{
	protected String title;

	public void addTitle(ArrayList<BookElement> list, String title) {
		TextData tdTitle = new TextData(title);
		tdTitle.underlined = true;
		addTitle(list, new TextData[]{tdTitle});
	}

	public void addTitle(ArrayList<BookElement> list, TextData[] title) {
		list.add(new ElementText(0, 0, GuiBook.PAGE_WIDTH, 9, title));
	}
}
