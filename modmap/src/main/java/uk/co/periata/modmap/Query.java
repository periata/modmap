package uk.co.periata.modmap;

import java.util.function.BiConsumer;

/**
 * Represents a query on the sub-entities of a model entity, i.e. an object that may select 
 * additional data from the entity and add it to the entity's object map.
 */
public interface Query
{
	/** 
	 * The null focus receiver is a value for the <code>newFocusReceiver</code> parameter of
	 * {@link #executeQuery(ModelNode, ObjectMap, BiConsumer)} that causes any new focus
	 * produced in the query to be ignored.  This is usually what you want.
	 */
	BiConsumer<ModelNode, ObjectMap> NULL_FOCUS_RECEIVER = (focus, objectMap) -> { };
	/**
	 * The null query is a query that selects no new data.
	 */
	Query NULL_QUERY = (f, o, r) -> { };
	/**
	 * Called to find any data that needs to be added from the queried node into its object map.
	 * @param focus             the node that is being queried
	 * @param objectMap         an object map representing the values mapped for output
	 * @param newFocusReceiver  a callback object that should be notified whenever any new entities are added to the
	 *                           object map.
	 */
	void executeQuery (ModelNode focus, ObjectMap objectMap, BiConsumer<ModelNode, ObjectMap> newFocusReceiver);

}
