package uk.co.periata.modmap;

import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

import uk.co.periata.modmap.*;

/**
 * An object that tracks the state of a model, transforming it for a single user's view as required,
 * and performing queries on it and sending details of changes in the results of the query to 
 * a receiving function.
 * 
 * @param <OBJECTMODEL>   The type of the Object Model, i.e. the global model that is used by the entire application.
 * @param <SUBJECTMODEL>  The type of the Subject Model, i.e. the model as viewed by a single user.
 */
public class DifferencingModelObserver <OBJECTMODEL, SUBJECTMODEL>
{
	
	private OBJECTMODEL objectModel;
	private Function<OBJECTMODEL, SUBJECTMODEL> subjectMapper;
	private Query userQuery;
	private final Consumer<String> jsonConsumer;
	private JSONRepresentableFactory jsonFactory = new DefaultJSONRepresentableFactory ();
	private ObjectMapDifferencer differencer = new ObjectMapDifferencer ();
	private ObjectMap lastObjectMap;
	
	/**
	 * Creates a new observer for a given model and user-specific parameters.
	 * @param objectModel    The Object Model, i.e. the global model that is used by the entire application
	 * @param subjectMapper  A function which converts the Object Model into a Subject Model, i.e. a view of the model
	 *                        that is suitable for use by a single user.  This function may (for example) remove portions
	 *                        of the model that are not accessible to the current user, or perform localisation of 
	 *                        model strings for the user's settings, or retrieve data specific to the user, etc.
	 * @param userQuery      A user-specified query identifying the portions of the Subject Model that the user
	 *                        is interested in viewing, which is expected to change regularly as the user opens and
	 *                        closes portions of the application that interact with different model data.
	 * @param jsonConsumer   A function that transfers data (in the form of a JSON string describing changes in the model)
	 *                        to the user's application.
	 */
	public DifferencingModelObserver (OBJECTMODEL objectModel, Function<OBJECTMODEL, SUBJECTMODEL> subjectMapper, Query userQuery, Consumer<String> jsonConsumer)
	{
		this.objectModel = objectModel;
		this.subjectMapper = subjectMapper;
		this.userQuery = userQuery;
		this.jsonConsumer = jsonConsumer;
		
		sendInitial ();
	}

	private synchronized void sendInitial ()
	{
		lastObjectMap = generateObjectMap ();
		jsonConsumer.accept (jsonFactory.jsonStringFor (differencer.differenceBetween (new ObjectMap (), lastObjectMap)));
	}

	private ObjectMap generateObjectMap ()
	{
		return new CompositionRoot (subjectMapper.apply (objectModel)).executeQueryToMap (userQuery);
	}

	/**
	 * Called when the model has changed in a way that may affect the user's current view of the data.  This may
	 * (but does not necessarily always) cause a push of new data to the user via their registered consumer function.
	 */
	public synchronized void notifyUpdated ()
	{
		// FIXME performance - we don't need to be synchronized during difference set generation or sending.
		ObjectMap newObjectMap = generateObjectMap ();
		Set<ObjectMapDifference> differences = differencer.differenceBetween (lastObjectMap, newObjectMap);
		if (!differences.isEmpty ()) {
			jsonConsumer.accept (jsonFactory.jsonStringFor (differences));
			lastObjectMap = newObjectMap;
			System.out.println ("New map saved as last");
		}
	}

	public void setObjectModel (OBJECTMODEL objectModel)
	{
		this.objectModel = objectModel;
		notifyUpdated ();
	}
	
	public void setSubjectMapper (Function<OBJECTMODEL, SUBJECTMODEL> subjectMapper)
	{
		this.subjectMapper = subjectMapper;
		notifyUpdated ();
	}
	
	public void setUserQuery (Query userQuery)
	{
		this.userQuery = userQuery;
		notifyUpdated ();
	}
}
