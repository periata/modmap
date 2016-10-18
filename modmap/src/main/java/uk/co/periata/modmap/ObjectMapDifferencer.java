package uk.co.periata.modmap;

import static uk.co.periata.modmap.ObjectMapDifference.*;

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
			if (lastValue instanceof ObjectMap && attribute.getValue () instanceof ObjectMap)
			{
				// items are sub-objects
				result.add (modification (attribute.getKey (), differenceBetween ((ObjectMap) lastValue,
				                                                                  (ObjectMap) attribute.getValue ())));
				// TODO it may be best to replace anyway; work out when we should do this
			}
			else if (lastValue instanceof JSONArray && attribute.getValue () instanceof JSONArray)
			{
				Set<ObjectMapDifference> arrayChanges = differenceBetween (
				          (JSONArray)lastValue, 
				          (JSONArray)attribute.getValue ());
				if (!arrayChanges.isEmpty ())
					result.add (modification (attribute.getKey (), arrayChanges));
			}
			else
			{
				// it's a plain value and has changed
				result.add (insertion (attribute.getKey (), attribute.getValue ()));
			}
		}
		for (Map.Entry<String, JSONRepresentable> attribute : last.attributeSet ())
			if (!next.getAttribute (attribute.getKey ()).isPresent ())
				result.add (deletion (attribute.getKey ()));
		return result;
	}

	public Set<ObjectMapDifference> differenceBetween (JSONArray last, JSONArray next)
	{
		Set<ObjectMapDifference> result = setSupplier.get ();
		JSONRepresentable[] lastItems = last.getItems();
		JSONRepresentable[] nextItems = next.getItems();
		for (int i = 0; i < nextItems.length; i ++)
		{
			if (i < lastItems.length && lastItems[i].equals (nextItems[i])) continue;
			result.add (insertion (""+i, nextItems[i]));
		}
		if (nextItems.length == lastItems.length - 1)
			result.add (deletion (""+nextItems.length));
		else if (nextItems.length < lastItems.length - 1)
			result.add (deletion ("" + nextItems.length + ":" + (lastItems.length - 1)));
			                      
		return result;
	}

}
