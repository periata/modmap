package uk.co.periata.modmap;

import java.io.IOException;

public interface JSONRepresentableFactory
{
	JSONRepresentable representableFor(Object o);
	
	default String jsonStringFor (Object o)
	{
		StringBuilder builder = new StringBuilder ();
		try
		{
			representableFor (o).appendTo (builder);
		}
		catch (IOException ignored)
		{
			// shouldn't happen as builder is not expected to be able to throw an IOException, which
			// should be the only way this will happen.
		}
		return builder.toString ();
	}
}
