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
}
