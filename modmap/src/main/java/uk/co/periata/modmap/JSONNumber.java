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


	@Override
	public int hashCode ()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((number == null) ? 0 : number.hashCode ());
		return result;
	}


	@Override
	public boolean equals (Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass () != obj.getClass ()) return false;
		JSONNumber other = (JSONNumber) obj;
		if (number == null)
		{
			if (other.number != null) return false;
		}
		else if (!number.equals (other.number)) return false;
		return true;
	}

	
}
