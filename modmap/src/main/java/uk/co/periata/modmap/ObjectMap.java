package uk.co.periata.modmap;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang3.StringEscapeUtils;

public class ObjectMap implements JSONRepresentable
{
	// FIXME: what would be the most efficient map implementation here?
	Map<String, JSONRepresentable> attributes = new TreeMap <> ();
	
	public ObjectMap addAttribute  (String key, JSONRepresentable repr)
	{
		attributes.put (key, repr);
		return this;
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
	
	@Override
	public void appendTo (Appendable builder) throws IOException
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

	public Set<Map.Entry<String, JSONRepresentable>> attributeSet ()
	{
		return attributes.entrySet ();
	}
	
	public Optional<JSONRepresentable> getAttribute (String key)
	{
		return Optional.ofNullable (attributes.get (key));
	}

	@Override
	public int hashCode ()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((attributes == null) ? 0 : attributes.hashCode ());
		return result;
	}

	@Override
	public boolean equals (Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass () != obj.getClass ()) return false;
		ObjectMap other = (ObjectMap) obj;
		if (attributes == null)
		{
			if (other.attributes != null) return false;
		}
		else if (!attributes.equals (other.attributes)) return false;
		return true;
	}
	
	
}
