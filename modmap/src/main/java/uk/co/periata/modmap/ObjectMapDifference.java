package uk.co.periata.modmap;

import java.util.Collections;
import java.util.Set;

import org.hamcrest.Matcher;

/**
 * Represents a difference between two {@link ObjectMap} values.
 */
public class ObjectMapDifference implements Comparable<ObjectMapDifference>, JSONRepresentable
{
	private final DifferenceType type;
	private final String key;
	private final JSONRepresentable value;
	private final Set<ObjectMapDifference> modifications;

	/** Identifies one of the possible kinds of difference between two object maps
	 *  that can  be  represented by an {@link ObjectMapDifference}.
	 */
	public enum DifferenceType
	{
		/** Represents an insertion or complete replacement of a value */
		INSERTION, 
		/** Represents the removal of a value */
		DELETION, 
		/** Represents the modification of child elements of an existing value */
		MODIFICATION
	}
	
	private ObjectMapDifference (DifferenceType type, String key, JSONRepresentable value)
	{
		this.type = type;
		this.key = key;
		this.value = value;
		this.modifications = Collections.emptySet ();
	}

	
	private ObjectMapDifference (DifferenceType type, String key, Set<ObjectMapDifference> modifications)
	{
		this.type = type;
		this.key = key;
		this.value = JSONRepresentable.NULL;
		this.modifications = modifications;
	}


	/**
	 * @return the type of difference represented by this object
	 */
	public DifferenceType getType ()
	{
		return type;
	}


	/**
	 * @return the identifier of the field that is different between the objects
	 */
	public String getKey ()
	{
		return key;
	}


	/**
	 * @return the value of the field if the difference is an insertion, or null otherwise
	 */
	public JSONRepresentable getValue ()
	{
		return value;
	}


	/**
	 * @return the set of differences between the child attributes of the objects
	 * if the difference is a modification, or the empty set otherwise.
	 */
	public Set<ObjectMapDifference> getModifications ()
	{
		return modifications;
	}

	@Override
	public int hashCode ()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode ());
		result = prime * result + ((modifications == null) ? 0 : modifications.hashCode ());
		result = prime * result + ((type == null) ? 0 : type.hashCode ());
		result = prime * result + ((value == null) ? 0 : value.hashCode ());
		return result;
	}


	@Override
	public boolean equals (Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass () != obj.getClass ()) return false;
		ObjectMapDifference other = (ObjectMapDifference) obj;
		if (key == null)
		{
			if (other.key != null) return false;
		}
		else if (!key.equals (other.key)) return false;
		if (modifications == null)
		{
			if (other.modifications != null) return false;
		}
		else if (!modifications.equals (other.modifications)) return false;
		if (type != other.type) return false;
		if (value == null)
		{
			if (other.value != null) return false;
		}
		else if (!value.equals (other.value)) return false;
		return true;
	}


	public static ObjectMapDifference insertion (String key, JSONRepresentable value)
	{
		return new ObjectMapDifference (DifferenceType.INSERTION, key, value);
	}


	@Override
	public int compareTo (ObjectMapDifference o)
	{
		int r = key.compareTo (o.getKey());
		if (r == 0) r = type.compareTo (o.getType ());
		if (r == 0) r = value.toString ().compareTo (o.getValue ().toString ());
		if (r == 0) r = IterableComparison.compare (modifications.stream ().sorted ().iterator (),
		                                            o.getModifications ().stream ().sorted ().iterator ());
		return r;
	}


	public static ObjectMapDifference deletion (String key)
	{
		return new ObjectMapDifference (DifferenceType.DELETION, key, JSONRepresentable.NULL);
	}
	
	public static ObjectMapDifference modification (String key, Set<ObjectMapDifference> modifications)
	{
		return new ObjectMapDifference (DifferenceType.MODIFICATION, key, modifications);
	}


	@SuppressWarnings ("incomplete-switch")
	@Override
	public void appendTo (StringBuilder builder)
	{
		builder.append ("{ \"type\": \"").append (type.toString ())
			   .append ("\", \"key\": \"").append (key).append ('"');  // nb: assumes key contains no special chars
		
		switch (type)
		{
		case INSERTION:
			builder.append (", \"value\": ");
			value.appendTo (builder);
			break;
		case MODIFICATION:
			builder.append (", \"modifications\": [");
			boolean needComma = false;
			for (ObjectMapDifference mod : modifications)
			{
				if (needComma) builder.append (", ");
				mod.appendTo (builder);
				needComma = true;
			}
			builder.append (']');
			break;
		}
		builder.append (" }");
	}

	@Override
	public String toString ()
	{
		StringBuilder b = new StringBuilder ();
		appendTo(b);
		return b.toString ();
	}
}
