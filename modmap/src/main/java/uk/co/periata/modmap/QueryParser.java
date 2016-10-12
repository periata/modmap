package uk.co.periata.modmap;

public class QueryParser
{

	public Query parse (String queryString)
	{
		if (queryString.length () == 0)
			return Query.NULL_QUERY;
		else
			return new IdentifiedEntityQuery (queryString);
	}

}
