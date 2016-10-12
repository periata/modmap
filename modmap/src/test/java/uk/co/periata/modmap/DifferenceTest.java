package uk.co.periata.modmap;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.assertThat;
import static uk.co.periata.modmap.ObjectMapDifference.insertion;
import static uk.co.periata.modmap.ObjectMapDifference.modification;

import java.util.Collections;

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
	
	@Test
	public void changedChildNodeGivesModification ()
	{
		assertThat (sut.differenceBetween (new ObjectMap ().addAttribute ("sub", 
		                                          new ObjectMap().addAttribute ("key", new JSONString ("value"))),
		                                   new ObjectMap ().addAttribute ("sub", 
		                                          new ObjectMap().addAttribute ("key", new JSONString ("newValue")))),
		            contains (modification ("sub", Collections.singleton (
		                                             insertion ("key", new JSONString ("newValue"))
		                                           ))));
		                                                            
	
	}
}
