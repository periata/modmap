package uk.co.periata.modmap;

import java.io.IOException;

public class JSONNumber implements JSONRepresentable
{
	private Number number;
	
	
	public JSONNumber (Number number)
	{
		this.number = number;
	}


	@Override
	public void appendTo (Appendable builder) throws IOException
	{
		builder.append (number.toString ());
	}

}
