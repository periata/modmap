package uk.co.periata.modmap;

import java.util.function.Consumer;

public interface Query
{

	Consumer<ModelNode> NULL_FOCUS_RECEIVER = (focus) -> { };

	void executeQuery (ModelNode focus, ObjectMap objectMap, Consumer<ModelNode> newFocusReceiver);

}
