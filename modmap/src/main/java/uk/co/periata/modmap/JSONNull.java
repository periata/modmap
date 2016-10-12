package uk.co.periata.modmap;

public class JSONNull implements JSONRepresentable
{

	@Override
	public void appendTo (StringBuilder builder)
	{
		builder.append ("null");
	}

	@Override
	public String toString ()
	{
		return "null";
	}
}
