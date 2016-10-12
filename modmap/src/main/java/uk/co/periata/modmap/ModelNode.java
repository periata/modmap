package uk.co.periata.modmap;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ModelNode
{
	private final Class<?> clazz;
	private final Object node;
	private final JSONRepresentableFactory reprFactory;

	public ModelNode (Object node, JSONRepresentableFactory reprFactory)
	{
		this.node = node;
		this.reprFactory = reprFactory;
		clazz = node.getClass ();
	}

	public ObjectMap getAttributes ()
	{
		return addAttributes (new ObjectMap ());
	}

	public Object getNode ()
	{
		return node;
	}
	
	private ObjectMap addAttributes (ObjectMap objectMap)
	{
		// FIXME: cache the methods.
		// FIXME: abstract away from using reflection.
		for (Method m : clazz.getMethods ())
		{
			if (!m.isAnnotationPresent (Attribute.class)) continue;
			if (m.getParameterTypes ().length > 0) continue;
			try
			{
				JSONRepresentable repr = reprFactory.representableFor (m.invoke (node));
				objectMap.addAttribute (methodNameToKey (m.getName ()), repr);
			}
			catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
			{
				// FIXME log error
			}
		}
		return objectMap;
	}

	private String methodNameToKey (String name)
	{
		if (name.startsWith ("get"))
			return Character.toLowerCase (name.charAt (3)) + name.substring (4);
		return name;
	}

	public QueryMap getQueries ()
	{
		return addQueries (new QueryMap (reprFactory));
	}

	private QueryMap addQueries (QueryMap queryMap)
	{
		// FIXME: cache the methods.
		// FIXME: abstract away from using reflection.
		for (Method m : clazz.getMethods ())
		{
			if (!m.isAnnotationPresent (EntityQuery.class)) continue;
			if (m.getParameterTypes ().length > 0) continue;
			queryMap.addQuery (methodNameToKey (m.getName ()), new QueryMethod(m, node));
		}
		return queryMap;
	}

	
}
