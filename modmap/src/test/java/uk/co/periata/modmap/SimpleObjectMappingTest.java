package uk.co.periata.modmap;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import uk.co.periata.modmap.EntityQueryTest.EntityRoot;
import uk.co.periata.modmap.EntityQueryTest.SimpleEntity;

public class SimpleObjectMappingTest
{
	public static class TestObject
	{
		private String name;
		@Attribute
		public String getName () { return name; }
		public void setName (String name) { this.name = name; }
	}
	
	@Test
	public void canMapSimpleObject ()
	{
		TestObject to = new TestObject ();
		to.setName ("Bob");
		/* An empty query should match the composition root itself (and any attributes
		 * defined within it) but no child entities.
		 */
		assertEquals ("{ \"name\": \"Bob\" }", 
		              new CompositionRoot (to).executeQuery(""));
	}
	
	public static class TestObject2
	{
		private String name, name2;
		@Attribute
		public String getName () { return name; }
		public void setName (String name) { this.name = name; }
		@Attribute
		public String getName2 () { return name2; }
		public void setName2 (String name) { this.name2 = name; }
	}

	@Test
	public void canHandleMultipleAttributes ()
	{
		TestObject2 to = new TestObject2 ();
		to.setName ("Bob");
		to.setName2 ("Chris");
		assertEquals ("{ \"name\": \"Bob\", \"name2\": \"Chris\" }", 
		              new CompositionRoot (to).executeQuery(""));
	}

	public static class TestObject3
	{
		private String name;
		private TestObject child;
		@Attribute
		public String getName () { return name; }
		public void setName (String name) { this.name = name; }
		@Attribute
		public TestObject getChild () { return child; }
		public void setChild (TestObject child) { this.child = child; }
	}
	
	@Test
	public void nestedObjectAttribute ()
	{
		TestObject to = new TestObject ();
		to.setName ("Bob");
		TestObject3 to3 = new  TestObject3 ();
		to3.setName ("Alice");
		to3.setChild (to);
		assertEquals ("{ \"child\": { \"name\": \"Bob\" }, \"name\": \"Alice\" }", 
		              new CompositionRoot (to3).executeQuery(""));
	}
	
	public static class TestObject4
	{
		private String name;
		private TestObject child;
		@Attribute
		public String getName () { return name; }
		public void setName (String name) { this.name = name; }
		@EntityQuery
		public TestObject getChild () { return child; }
		public void setChild (TestObject child) { this.child = child; }
		
	}
	@Test
	public void entityQueriesNotIncludedWhenNotSelected ()
	{
		TestObject to = new TestObject ();
		to.setName ("Bob");
		TestObject4 to4 = new  TestObject4 ();
		to4.setName ("Alice");
		to4.setChild (to);
		assertEquals ("{ \"name\": \"Alice\" }", 
		              new CompositionRoot (to4).executeQuery(""));
	}
	
}
