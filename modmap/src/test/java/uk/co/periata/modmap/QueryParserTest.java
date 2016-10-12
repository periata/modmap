package uk.co.periata.modmap;

import static org.junit.Assert.*;

import org.junit.Test;

public class QueryParserTest
{
	@Test
	public void queryParserReturnsNullQueryOnEmptyString ()
	{
		assertSame (Query.NULL_QUERY, new QueryParser().parse(""));
	}
	
	@Test
	public void parseIdentifierAsIdentifiedEntityQuery ()
	{
		assertEquals (new IdentifiedEntityQuery ("anIdentifier"),
		              new QueryParser().parse ("anIdentifier"));
	}
	
	@Test
	public void parseIdentifierPath ()
	{
		assertEquals (new ChildEntityQuery (new IdentifiedEntityQuery ("parent"), new IdentifiedEntityQuery("child")),
		              new QueryParser ().parse ("parent.child"));
	}
	
	@Test
	public void parseAlternatives ()
	{
		assertEquals (new CompoundEntityQuery (new IdentifiedEntityQuery ("a"), new IdentifiedEntityQuery ("b")),
		              new QueryParser ().parse ("{a,b}"));
	}
	
	@Test
	public void parseAlternativesWithChild ()
	{
		assertEquals (new ChildEntityQuery (
		                  new CompoundEntityQuery (new IdentifiedEntityQuery ("a"), new IdentifiedEntityQuery ("b")),
		                  new IdentifiedEntityQuery ("c")),
		              new QueryParser ().parse ("{a,b}.c"));
	}
}
