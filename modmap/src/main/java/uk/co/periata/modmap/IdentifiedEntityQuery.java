package uk.co.periata.modmap;

import java.util.function.BiConsumer;

/**
 * Identifies an entity mapped in the current focus entity by its identifier.
 */
public class IdentifiedEntityQuery implements Query
{
	private final String identifier;

	public IdentifiedEntityQuery (String identifier)
	{
		this.identifier = identifier;
	}
	
	@Override
	public void executeQuery (ModelNode focus, ObjectMap objectMap, BiConsumer<ModelNode, ObjectMap> newFocusReceiver)
	{
		focus.getQueries ().execute(identifier).ifPresent (repr -> {
			ObjectMap childMap = repr.getAttributes ();
			objectMap.addAttribute (identifier, childMap);
			newFocusReceiver.accept (repr, childMap);
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
		result = prime * result + ((identifier == null) ? 0 : identifier.hashCode ());
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
		IdentifiedEntityQuery other = (IdentifiedEntityQuery) obj;
		if (identifier == null)
		{
			if (other.identifier != null) return false;
		}
		else if (!identifier.equals (other.identifier)) return false;
		return true;
	}
	
	
}
