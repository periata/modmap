package uk.co.periata.modmap;

import java.io.IOException;
import java.util.Collection;

public class JSONArray implements JSONRepresentable
{
	JSONRepresentable [] items;
	
	public JSONArray (Collection<?> items, JSONRepresentableFactory factory)
	{
		this.items = items.stream ().map (factory::representableFor)
								    .toArray (JSONRepresentable[]::new);
	}
	
	public JSONArray (Object[] items, JSONRepresentableFactory factory)
	{
		this.items = new JSONRepresentable[items.length];
		for (int i = 0; i < items.length; i ++)
			this.items[i] = factory.representableFor (items[i]);
	}

	@Override
	public void appendTo (Appendable builder) throws IOException
	{
		builder.append ("[ ");
		boolean needComma = false;
		for (JSONRepresentable item : items)
		{
			if (needComma)
				builder.append (", ");
			item.appendTo (builder);
			needComma = true;
		}
		builder.append (" ]");
	}
	
	public String toString ()
	{
		StringBuilder builder = new StringBuilder ();
		try
		{
			appendTo (builder);
		}
		catch (IOException e)
		{
			throw new RuntimeException ("Unexpected IOException appending to StringBuilder", e);
		}
		return builder.toString ();
	}

}
