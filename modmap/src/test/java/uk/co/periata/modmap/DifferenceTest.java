package uk.co.periata.modmap;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.Test;

public class DifferenceTest
{
	ObjectMapDifferencer sut = new  ObjectMapDifferencer ();
	
	@Test
	public void equalEmptyNodesGiveEmptyDifferenceSet ()
	{
		assertThat (sut.differenceBetween(new ObjectMap (), new ObjectMap ()), 
		            empty ());
	}
	
	@Test
	public void nodeWithAnExtraAttributeGivesInsertion ()
	{
		assertThat (sut.differenceBetween (new ObjectMap(),
		                                   new ObjectMap ().addAttribute ("key", new JSONString ("value"))),
		            contains (ObjectMapDifference.insertion ("key", new JSONString ("value"))));
	}
	@Test
	public void nodeWithMissingAttributeGivesDeletion ()
	{
		assertThat (sut.differenceBetween (new ObjectMap ().addAttribute ("key", new JSONString ("value")),
		                                   new ObjectMap()),
		            contains (ObjectMapDifference.deletion ("key")));
		
	}
}
