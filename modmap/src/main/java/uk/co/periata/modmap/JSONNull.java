package uk.co.periata.modmap;

import java.io.IOException;

public class JSONNull implements JSONRepresentable
{	@Override
	public void appendTo (Appendable builder) throws IOException
	{
		builder.append ("null");
	}

	@Override
	public String toString ()
	{
		return "null";
	}
}
