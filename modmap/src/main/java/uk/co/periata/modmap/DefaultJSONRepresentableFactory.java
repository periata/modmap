package uk.co.periata.modmap;

import java.util.Collection;

public class DefaultJSONRepresentableFactory implements JSONRepresentableFactory
{

	@Override
	public JSONRepresentable representableFor (Object o)
	{
		if (o instanceof String)
			return new JSONString ((String)o);
		if (o instanceof Collection)
			return new JSONArray ((Collection<?>)o, this);
		if (o.getClass ().isArray () && ! o.getClass ().getComponentType ().isPrimitive ())
			return new JSONArray ((Object[]) o, this);
		// fixme: what about primitive arrays?
		return new ModelNode (o, this).getAttributes ();
	}

}
