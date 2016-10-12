package uk.co.periata.modmap;

import java.util.function.BiConsumer;

public interface Query
{

	BiConsumer<ModelNode, ObjectMap> NULL_FOCUS_RECEIVER = (focus, objectMap) -> { };

	void executeQuery (ModelNode focus, ObjectMap objectMap, BiConsumer<ModelNode, ObjectMap> newFocusReceiver);

}
