package uk.co.periata.modmap;

import java.util.function.BiConsumer;

public class CompoundEntityQuery implements Query
{
	private final Query[] subQueries;
	
	public CompoundEntityQuery (Query... subQueries)
	{
		this.subQueries = subQueries;
	}

	@Override
	public void executeQuery (ModelNode focus, ObjectMap objectMap, BiConsumer<ModelNode, ObjectMap> newFocusReceiver)
	{
		for (Query q : subQueries)
			q.executeQuery (focus, objectMap, newFocusReceiver); 
	}

}
