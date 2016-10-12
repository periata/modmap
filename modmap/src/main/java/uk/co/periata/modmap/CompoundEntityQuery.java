package uk.co.periata.modmap;

import java.util.Arrays;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode ()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode (subQueries);
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
		CompoundEntityQuery other = (CompoundEntityQuery) obj;
		if (!Arrays.equals (subQueries, other.subQueries)) return false;
		return true;
	}

}
