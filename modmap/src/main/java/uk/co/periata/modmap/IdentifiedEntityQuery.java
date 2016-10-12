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
}
