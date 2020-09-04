package firok.tiths.client.book;

import com.google.gson.*;
import com.google.gson.annotations.SerializedName;
import firok.tiths.util.InnerActions;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.mantle.client.book.data.BookData;
import slimeknights.mantle.client.book.data.element.TextData;
import slimeknights.mantle.client.gui.book.GuiBook;
import slimeknights.mantle.client.gui.book.element.BookElement;
import slimeknights.mantle.client.gui.book.element.ElementImage;
import slimeknights.mantle.client.gui.book.element.ElementText;
import slimeknights.tconstruct.library.book.content.ContentModifier;
import slimeknights.tconstruct.library.book.elements.ElementTinkerItem;

import java.util.ArrayList;

/**
 * 书页类型 - 物品
 */
@SideOnly(Side.CLIENT)
@SuppressWarnings("all")
public class ContentItem extends ContentBase
{
	public static final String ID="tiths_item";

	protected transient ItemStack[] stacks;
	protected TextData[] text;
	@SerializedName("stacks")
	protected JsonArray arr_stacks;

	@Override
	public void build(BookData book, ArrayList<BookElement> list, boolean rightSide) {
		int yOff = 0;

		TITLE:if(title != null)
		{
			addTitle(list, title);
			yOff = 20;
		}

		TEXT:if(text != null && text.length > 0)
		{
			list.add(new ElementText(0, yOff, GuiBook.PAGE_WIDTH, GuiBook.PAGE_HEIGHT - yOff, text));
		}

		ITEMSTACK:{
			try
			{
				stacks= InnerActions.ARRAY2stacks(arr_stacks);

				if(stacks!=null && stacks.length>0)
				{
					int ox=172-stacks.length*24,oy=130;
					for(ItemStack stack:stacks)
					{
						list.add(new ElementImage(ox,oy,-1,-1, ContentModifier.IMG_SLOT_1));
						list.add(new ElementImage(ox,oy,-1,-1, ContentModifier.IMG_SLOT_1, book.appearance.slotColor));
						list.add(new ElementTinkerItem(ox+3,oy+3,1f,stack));
						ox+=24;
					}
				}
			}
			catch (Exception ignored) { ignored.printStackTrace(); }
		}
	}
}
