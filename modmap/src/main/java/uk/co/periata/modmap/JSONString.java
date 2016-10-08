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

}
