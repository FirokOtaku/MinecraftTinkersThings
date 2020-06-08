package firok.tiths.common;

import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.IRarity;

public enum TithsRarity implements IRarity
{
	CreativeOnly("CreativeOnly",TextFormatting.DARK_RED),
	Indev("Indev",TextFormatting.DARK_GREEN),
	Special("Special",TextFormatting.BLUE),
	;

	TithsRarity(String name,TextFormatting color)
	{
		this.name=name;
		this.color=color;
	}

	TextFormatting color;
	String name;

	@Override
	public TextFormatting getColor()
	{
		return color;
	}

	@Override
	public String getName()
	{
		return name;
	}
}
