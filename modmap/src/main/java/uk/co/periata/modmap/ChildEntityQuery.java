package uk.co.periata.modmap;

import java.util.function.BiConsumer;

public class ChildEntityQuery implements Query
{

	private Query parent;
	private Query child;

	public ChildEntityQuery (Query parent, Query child)
	{
		this.parent = parent;
		this.child = child;
	}

	@Override
	public void executeQuery (ModelNode focus, ObjectMap objectMap,
			BiConsumer<ModelNode, ObjectMap> newFocusReceiver)
	{
		parent.executeQuery (focus, objectMap, (childFocus, childMap) -> {
			child.executeQuery (childFocus, childMap, newFocusReceiver);
		});
	}

}
