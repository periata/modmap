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

	public Optional<ModelNode> execute (String query)
	{
		return Optional.ofNullable (queries.get(query))
					   .flatMap (QueryMethod::invoke)
					   .map (node -> new ModelNode (node, reprFactory));
	}

	public void addQuery (String name, QueryMethod queryMethod)
	{
		queries.put (name, queryMethod);
	}

}
