package uk.co.periata.modmap;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;

public class QueryMethod
{
	private Method method;
	private Object owner;

	public QueryMethod (Method method, Object owner)
	{
		this.method = method;
		this.owner = owner;
		method.setAccessible (true);
	}
	
	public Optional<Object> invoke ()
	{
		try
		{
			return Optional.ofNullable (method.invoke (owner));
		}
		catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
		{
			return Optional.empty ();
		}
	}
}
