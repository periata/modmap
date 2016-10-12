package uk.co.periata.modmap;

import org.apache.commons.lang3.StringEscapeUtils;

public class JSONString implements JSONRepresentable
{
	private String value;
	
	public JSONString (String value)
	{
		this.value = value;
	}

	@Override
	public void appendTo (StringBuilder builder)
	{
		builder.append ('"')
			   .append (StringEscapeUtils.escapeJson (value))
			   .append ('"');
	}

	public String toString ()
	{
		StringBuilder builder = new StringBuilder ();
		appendTo (builder);
		return builder.toString ();
	}
	@Override
	public int hashCode ()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode ());
		return result;
	}

	@Override
	public boolean equals (Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass () != obj.getClass ()) return false;
		JSONString other = (JSONString) obj;
		if (value == null)
		{
			if (other.value != null) return false;
		}
		else if (!value.equals (other.value)) return false;
		return true;
	}

}
