package uk.co.periata.modmap;

import java.io.IOException;
import java.util.List;

public class JSONArray implements JSONRepresentable
{
	JSONRepresentable [] items;
	
	public JSONArray (List<?> items, JSONRepresentableFactory factory)
	{
		this.items = items.stream ().map (factory::representableFor)
								    .toArray (JSONRepresentable[]::new);
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
