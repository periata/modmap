package uk.co.periata.modmap;

public class JSONBool implements JSONRepresentable
{

	private boolean value;

	public JSONBool (boolean value)
	{
		this.value = value;
	}

	@Override
	public void appendTo (StringBuilder builder)
	{
		builder.append (toString());
	}

	@Override
	public String toString ()
	{
		return value ? "true" : "false";
	}
	
}
