package uk.co.periata.modmap;

/** Objects that know how to translate themselves into JSON */
public interface JSONRepresentable
{
	/** An object intended to represent the JSON value "null" */
	JSONRepresentable NULL = b -> b.append ("null");
	/** An object intended to represent the JSON value "true" */
	JSONRepresentable TRUE = b -> b.append ("true");
	/** An object intended to represent the JSON value "false" */
	JSONRepresentable FALSE = b -> b.append ("false");

	/** Appends the JSON representation of the object to a StringBuilder */
	void appendTo (StringBuilder builder);

}
