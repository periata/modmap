package uk.co.periata.modmap;

import java.util.function.BiConsumer;

/**
 * Contains an object that defines the root of the hierarchy to be queried
 * and reported. The object specified should have attributes annotated
 * with the <code>{@link Attribute}</code> annotation and entity query
 * methods with the <code>{@link EntityQuery}</code> annotation.  By default,
 * attributes are included in any query that selects a node of the 
 * hierarchy, while model nodes are excluded unless explicitly matched
 * by an active query.
 */
public class CompositionRoot
{
	private ModelNode modelNode;
	private QueryParser queryParser = new QueryParser ();
	
	public CompositionRoot (Object root)
	{
		this (root, new DefaultJSONRepresentableFactory ());
	}
	
	public CompositionRoot (Object root, JSONRepresentableFactory factory)
	{
		modelNode = new ModelNode (root, factory);
	}
	
	public String executeQuery (String query)
	{
		return executeQuery (queryParser.parse (query));
	}
	
	public String executeQuery (Query query)
	{
		return executeQuery (query, Query.NULL_FOCUS_RECEIVER);
	}

	String executeQuery (Query query, BiConsumer<ModelNode, ObjectMap> focusReceiver)
	{		
		ObjectMap attributes = modelNode.getAttributes ();
		query.executeQuery (modelNode, attributes, focusReceiver);
		return attributes.toString ();
	}
}
