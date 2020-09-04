package firok.tiths.client.book;

import com.google.gson.*;
import com.google.gson.annotations.SerializedName;
import firok.tiths.potion.BasePotion;
import firok.tiths.util.IResourceLocationContainer;
import firok.tiths.util.InnerActions;
import net.minecraft.potion.Potion;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.mantle.client.book.data.BookData;
import slimeknights.mantle.client.book.data.element.ImageData;
import slimeknights.mantle.client.book.data.element.TextData;
import slimeknights.mantle.client.gui.book.GuiBook;
import slimeknights.mantle.client.gui.book.element.BookElement;
import slimeknights.mantle.client.gui.book.element.ElementImage;
import slimeknights.mantle.client.gui.book.element.ElementText;
import slimeknights.tconstruct.library.book.content.ContentModifier;

import java.util.ArrayList;

/**
 * 书页类型 - 状态效果
 */
@SideOnly(Side.CLIENT)
@SuppressWarnings("all")
public class ContentPotion extends ContentBase
{
	public static final String ID="tiths_potion";

	protected transient Potion[] potions;
	protected TextData[] text;
	@SerializedName("potions")
	private JsonArray arr_potions;

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
				potions= InnerActions.ARRAY2potions(arr_potions);

				if(potions!=null && potions.length>0)
				{
					int ox=172-potions.length*24,oy=130;
					for(Potion potion:potions)
					{
						if(!(potion instanceof IResourceLocationContainer)) continue;

						list.add(new ElementImage(ox,oy,-1,-1, ContentModifier.IMG_SLOT_1));
//						list.add(new ElementImage(ox,oy,-1,-1, ContentModifier.IMG_SLOT_1, 0xafb4b4));
						ImageData image=new ImageData(((IResourceLocationContainer) potion).getResourceLocation(),0,0,16,16,16,16);
						list.add(new ElementImage(ox+3,oy+3,-1,-1,image ));

						ox+=24;
					}
				}
			}
			catch (Exception ignored) { ignored.printStackTrace(); }
		}
	}
}
