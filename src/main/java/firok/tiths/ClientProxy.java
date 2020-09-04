package firok.tiths;

import firok.tiths.client.book.Books;
public class ClientProxy extends CommonProxy
{
	public ClientProxy()
	{
		Books.initBooks();
	}

}
