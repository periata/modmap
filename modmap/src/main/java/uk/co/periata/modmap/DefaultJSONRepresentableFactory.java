package uk.co.periata.modmap;

public class DefaultJSONRepresentableFactory implements JSONRepresentableFactory
{

	@Override
	public JSONRepresentable representableFor (Object o)
	{
		if (o instanceof String)
			return new JSONString ((String)o);
		return new ModelNode (o, this).getAttributes ();
	}

}
