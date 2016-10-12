package uk.co.periata.modmap;

import static org.junit.Assert.*;

import java.util.concurrent.atomic.AtomicReference;

import org.junit.Test;

public class EntityQueryTest
{
	public static class SimpleEntity
	{
		private String name;
		@Attribute
		public String getName ()
		{
			return name;
		}
		public void setName (String name)
		{
			this.name = name;
		}
	}
	public static class EntityRoot
	{
		private SimpleEntity anEntity, anotherEntity;
		@EntityQuery
		public SimpleEntity getAnEntity () { return anEntity; }
		public void setAnEntity (SimpleEntity e) { anEntity = e; }
		@EntityQuery
		public SimpleEntity getAnotherEntity () { return anotherEntity; }
		public void setAnotherEntity (SimpleEntity anotherEntity) { this.anotherEntity = anotherEntity; }
		@EntityQuery
		public EntityRoot getSelf () { return this; }
	}
	
	@Test
	public void simpleEntityQuery ()
	{
		EntityRoot r = new EntityRoot ();
		SimpleEntity e = new SimpleEntity ();
		e.setName ("Bob");
		r.setAnEntity (e);
		
		/* A query that identifies the name of an entity causes that entity to be included */
		assertEquals ("{ \"anEntity\": { \"name\": \"Bob\" } }",
		              new CompositionRoot (r).executeQuery ("anEntity"));
	}
	
	@Test
	public void queryWithSimpleQueryObject ()
	{
		EntityRoot r = new EntityRoot ();
		SimpleEntity e = new SimpleEntity ();
		e.setName ("Bob");
		r.setAnEntity (e);
		
		/* A query that identifies the name of an entity causes that entity to be included */
		assertEquals ("{ \"anEntity\": { \"name\": \"Bob\" } }",
		              new CompositionRoot (r).executeQuery (
		                       new IdentifiedEntityQuery ("anEntity")));

	}
	
	@Test
	public void multipleQueries ()
	{
		EntityRoot r = new EntityRoot ();
		SimpleEntity e = new SimpleEntity ();
		e.setName ("Bob");
		r.setAnEntity (e);
		e = new SimpleEntity ();
		e.setName ("Alice");
		r.setAnotherEntity (e);
		
		assertEquals ("{ \"anEntity\": { \"name\": \"Bob\" }, \"anotherEntity\": { \"name\": \"Alice\" } }",
		              new CompositionRoot (r).executeQuery (
		                       new CompoundEntityQuery (
		                           new IdentifiedEntityQuery ("anEntity"),
		                           new IdentifiedEntityQuery ("anotherEntity"))));
	}
	
	@Test
	public void focusReceiverCalledInSimpleQuery ()
	{
		AtomicReference<ModelNode> focus = new AtomicReference<ModelNode> (null);
		
		EntityRoot r = new EntityRoot ();
		SimpleEntity e = new SimpleEntity ();
		e.setName ("Bob");
		r.setAnEntity (e);
		
		new CompositionRoot (r).executeQuery (new IdentifiedEntityQuery ("anEntity"), focus::set);
		
		assertSame (e, focus.get ().getNode ());		
	}
}
