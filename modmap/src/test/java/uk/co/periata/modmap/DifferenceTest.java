package uk.co.periata.modmap;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static uk.co.periata.modmap.ObjectMapDifference.*;

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
	
	@Test
	public void noChangesInIdenticalArrays ()
	{
		JSONRepresentableFactory f = new DefaultJSONRepresentableFactory ();
		
		assertThat (sut.differenceBetween (
		                     new JSONArray (new String[] { "first", "second" }, f),
		                     new JSONArray (new String[] { "first", "second" }, f)),
		            empty ());
	}
	@Test
	public void changeInArrayGivesInsertion ()
	{
		JSONRepresentableFactory f = new DefaultJSONRepresentableFactory ();
		
		assertThat (sut.differenceBetween (
		                     new JSONArray (new String[] { "first", "second" }, f),
		                     new JSONArray (new String[] { "first", "modified" }, f)),
		            contains (insertion ("1", new JSONString("modified"))));
	}
	@Test
	public void extensionOfArrayGivesInsertion ()
	{
		JSONRepresentableFactory f = new DefaultJSONRepresentableFactory ();
		
		assertThat (sut.differenceBetween (
		                     new JSONArray (new String[] { "first" }, f),
		                     new JSONArray (new String[] { "first", "modified" }, f)),
		            contains (insertion ("1", new JSONString("modified"))));
	}
	@Test
	public void contractionOfArrayGivesDeletion ()
	{
		JSONRepresentableFactory f = new DefaultJSONRepresentableFactory ();
		
		assertThat (sut.differenceBetween (
		                     new JSONArray (new String[] { "first", "second", "third" }, f),
		                     new JSONArray (new String[] { "first", "second" }, f)),
		            contains (deletion("2")));		
	}
	@Test
	public void contractionOfArrayByMultipleItemsGivesRangeDeletion ()
	{
		JSONRepresentableFactory f = new DefaultJSONRepresentableFactory ();
		
		assertThat (sut.differenceBetween (
		                     new JSONArray (new String[] { "first", "second", "third" }, f),
		                     new JSONArray (new String[] { "first" }, f)),
		            contains (deletion("1:2")));		
	}
	@Test
	public void arrayDifferencesInAttributeDetected ()
	{
		JSONRepresentableFactory f = new DefaultJSONRepresentableFactory ();
		
		assertThat (sut.differenceBetween (
		                     new ObjectMap().addAttribute ("array",
  		                         new JSONArray (new String[] { "first" }, f)),
		                     new ObjectMap().addAttribute ("array",
		                         new JSONArray (new String[] { "first", "modified" }, f))),
		            contains (modification ("array", Collections.singleton (
		                          insertion ("1", new JSONString("modified"))
		                     ))));
	}
	@Test
	public void identicalNumbersNotDetectedAsDifferent ()
	{
		JSONRepresentableFactory f = new DefaultJSONRepresentableFactory ();
		
		assertThat (sut.differenceBetween (
		                     new ObjectMap().addAttribute ("test1", new JSONNumber (3)),
		                     new ObjectMap().addAttribute ("test1", new JSONNumber (3))),
		            empty());
	}

}
