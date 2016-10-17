package uk.co.periata.modmap;

import java.io.IOException;

public class JSONBool implements JSONRepresentable
{

	private boolean value;

	public JSONBool (boolean value)
	{
		this.value = value;
	}

	@Override
	public void appendTo (Appendable builder) throws IOException
	{
		builder.append (toString());
	}

	@Override
	public String toString ()
	{
		return value ? "true" : "false";
	}
	
}
