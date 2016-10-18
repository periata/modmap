package uk.co.periata.modmap;

import java.util.List;

public class DefaultJSONRepresentableFactory implements JSONRepresentableFactory
{

	@Override
	public JSONRepresentable representableFor (Object o)
	{
		if (o instanceof String)
			return new JSONString ((String)o);
		if (o instanceof List)
			return new JSONArray ((List<?>)o, this);
		return new ModelNode (o, this).getAttributes ();
	}

}
