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
}
