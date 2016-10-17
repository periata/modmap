package uk.co.periata.modmap;

import java.io.IOException;

/** Objects that know how to translate themselves into JSON */
public interface JSONRepresentable
{
	/** An object intended to represent the JSON value "null" */
	JSONRepresentable NULL = new JSONNull();
	/** An object intended to represent the JSON value "true" */
	JSONRepresentable TRUE = new JSONBool(true);
	/** An object intended to represent the JSON value "false" */
	JSONRepresentable FALSE = new  JSONBool(false);

	/** Appends the JSON representation of the object to an Appendable (e.g. a StringBuilder or Writer) 
	 * @throws IOException */
	void appendTo (Appendable builder) throws IOException;


}
