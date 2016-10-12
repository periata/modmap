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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode ()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((child == null) ? 0 : child.hashCode ());
		result = prime * result + ((parent == null) ? 0 : parent.hashCode ());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals (Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass () != obj.getClass ()) return false;
		ChildEntityQuery other = (ChildEntityQuery) obj;
		if (child == null)
		{
			if (other.child != null) return false;
		}
		else if (!child.equals (other.child)) return false;
		if (parent == null)
		{
			if (other.parent != null) return false;
		}
		else if (!parent.equals (other.parent)) return false;
		return true;
	}

	
}
