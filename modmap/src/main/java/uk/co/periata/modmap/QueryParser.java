package uk.co.periata.modmap;

import java.util.Arrays;

public class QueryParser
{

	public Query parse (String queryString)
	{
		// TODO This design doesn't allow for nested braces.  We may want that in future.
		if (queryString.length () == 0)
			return Query.NULL_QUERY;
		else if (queryString.charAt(0) == '{')
		{
			int closeBrace = queryString.indexOf ('}');
			if (closeBrace < 0) throw new IllegalArgumentException ("Unmatched '{' in query string");
			String [] subQueryStrings = queryString.substring (1, closeBrace).split (",");
			Query [] subQueries = Arrays.stream (subQueryStrings).map (this::parse).toArray (Query[]::new);
			return new CompoundEntityQuery (subQueries);
		}
		else
		{
			int dot = queryString.indexOf ('.');
			if (dot > 0)
				return new ChildEntityQuery (parse (queryString.substring (0, dot)), 
				                              parse (queryString.substring (dot + 1)));
			return new IdentifiedEntityQuery (queryString);
		}
	}

}
