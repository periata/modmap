package uk.co.periata.modmap;

import static org.junit.Assert.assertEquals;

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
		private SimpleEntity anEntity;
		@EntityQuery
		public SimpleEntity getAnEntity () { return anEntity; }
		public void setAnEntity (SimpleEntity e) { anEntity = e; }
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
}
