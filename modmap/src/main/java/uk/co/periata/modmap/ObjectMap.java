package uk.co.periata.modmap;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.StringEscapeUtils;

public class ObjectMap implements JSONRepresentable
{
	// FIXME: what would be the most efficient map implementation here?
	Map<String, JSONRepresentable> attributes = new TreeMap <> ();
	
	public String toString ()
	{
		StringBuilder builder = new StringBuilder ();
		appendTo (builder);
		return builder.toString ();
	}

	public void addAttribute  (String key, JSONRepresentable repr)
	{
		attributes.put (key, repr);
	}
	
	
	@Override
	public void appendTo (StringBuilder builder)
	{
		builder.append ("{ ");
		boolean commaRequired = false;
		for (Map.Entry<String, JSONRepresentable> e : attributes.entrySet ()) 
		{
			if (commaRequired)
				builder.append (", ");
			builder
				.append ('"')
				.append (StringEscapeUtils.escapeJava (e.getKey ()))
				.append ("\": ");
			e.getValue ().appendTo(builder);
			commaRequired = true;
		}
		builder.append (" }");
	}
}
