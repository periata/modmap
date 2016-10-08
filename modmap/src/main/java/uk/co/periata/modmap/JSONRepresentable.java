package uk.co.periata.modmap;

import java.util.Map;

import org.apache.commons.lang3.StringEscapeUtils;

public interface JSONRepresentable
{

	void appendTo (StringBuilder builder);

}
