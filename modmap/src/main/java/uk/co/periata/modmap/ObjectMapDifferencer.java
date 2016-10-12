package uk.co.periata.modmap;

import static uk.co.periata.modmap.ObjectMapDifference.insertion;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Supplier;

public class ObjectMapDifferencer
{
	private Supplier<Set<ObjectMapDifference>> setSupplier = TreeSet::new;
	
	public Set<ObjectMapDifference> differenceBetween (ObjectMap last, ObjectMap next)
	{
		Set<ObjectMapDifference> result = setSupplier.get ();
		for (Map.Entry<String, JSONRepresentable> attribute : next.attributeSet ())
		{
			// TODO is this a valid way of handling non-existing attributes?
			JSONRepresentable lastValue = last.getAttribute(attribute.getKey ()).orElse(JSONRepresentable.NULL);
			if (lastValue.equals (attribute.getValue ())) continue;
			
			result.add (insertion (attribute.getKey (), attribute.getValue ()));
		}
		return result;
	}

}
