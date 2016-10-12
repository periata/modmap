package uk.co.periata.modmap;

import java.util.function.Consumer;

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
	public void executeQuery (ModelNode focus, ObjectMap objectMap, Consumer<ModelNode> newFocusReceiver)
	{
		focus.getQueries ().execute(identifier).ifPresent (repr -> {
			objectMap.addAttribute (identifier, repr.getAttributes ());
			newFocusReceiver.accept (repr);
		});
	}
}
