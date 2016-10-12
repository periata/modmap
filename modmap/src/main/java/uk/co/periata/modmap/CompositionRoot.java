package uk.co.periata.modmap;

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
		// FIXME: should support more types of query!
		if (query.length () > 0)
			return executeQuery (new IdentifiedEntityQuery (query)).toString ();
		else
			return modelNode.getAttributes ().toString ();
	}
	
	public String executeQuery (Query query)
	{
		ObjectMap attributes = modelNode.getAttributes ();
		query.executeQuery (modelNode, attributes);
		return attributes.toString ();
	}
}
