package uk.co.periata.modmap;

import java.util.Arrays;

/**
 * Process a string into a query.  The string may have the following forms:
 * <ul>
 * <li> a literal identifier - becomes an {@link IdentifiedEntityQuery}</li>
 * <li> <code>q1.q2</code> - becomes a {@link ChildEntityQuery} with 
 *      <code>q1</code> as parent and <code>q2</code> as child.</li>
 * <li> <code>{q1,q2,...}</code> - becomes a {@link CompoundEntityQuery} with 
 *      subqueries <code>q1</code>, <code>q2</code>, and so on.</li>
 * </ul>
 */
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
			Query query = new CompoundEntityQuery (subQueries);
			if (closeBrace != queryString.length () - 1)
			{
				if (queryString.charAt (closeBrace + 1) != '.') throw new IllegalArgumentException ("Character after '}' in query string may only be '.'");
				return new ChildEntityQuery (query, parse (queryString.substring (closeBrace + 2)));
			}
			return query;
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
