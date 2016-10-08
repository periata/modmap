package uk.co.periata.modmap;

import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

public class QueryMap
{
	private JSONRepresentableFactory reprFactory;
	private Map<String, QueryMethod> queries;
	public QueryMap (JSONRepresentableFactory reprFactory)
	{
		this.reprFactory = reprFactory;
		queries = new TreeMap<> ();
	}

	public Optional<JSONRepresentable> execute (String query)
	{
		return Optional.ofNullable (queries.get(query))
					   .flatMap (QueryMethod::invoke)
					   .map (reprFactory::representableFor);
	}

	public void addQuery (String name, QueryMethod queryMethod)
	{
		queries.put (name, queryMethod);
	}

}
