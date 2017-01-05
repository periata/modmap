package uk.co.periata.modmap;

import static org.junit.Assert.*;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

import org.junit.Test;

public class DifferencingModelObserverTest
{
	private static class TestClass1
	{
		int value = 1;
		@Attribute
		public int getValue ()
		{
			return value;
		}
		public void setValue (int value)
		{
			this.value = value;
		}
	}
	@Test
	public void modelChangeTriggersNotification ()
	{
		AtomicBoolean called = new AtomicBoolean (false);
		TestClass1 model = new TestClass1 ();
		DifferencingModelObserver<TestClass1, TestClass1> dmo = new DifferencingModelObserver<> (model, 
				Function.identity(), Query.NULL_QUERY, s -> { called.set(true); });
		called.set (false);	// will have been called during initialisation to pass initial value
		model.setValue (2);
		dmo.notifyUpdated ();
		assertEquals (true, called.get ());
	}
	private static class TestClass2
	{
		double value;
		public TestClass2 (TestClass1 src)
		{
			value = src.getValue () * 0.5;
		}
		@Attribute
		public double getValue ()
		{
			return value;
		}
	}
	@Test
	public void transformedModelInformationSentDuringInit ()
	{
		AtomicReference<String> received = new AtomicReference<String> ("NOT CALLED");
		TestClass1 model = new TestClass1 ();
		DifferencingModelObserver<TestClass1, TestClass2> dmo = new DifferencingModelObserver<> (
				model,
				TestClass2::new,
				Query.NULL_QUERY,
				received::set);
		assertEquals ("[ { \"type\": \"INSERTION\", \"key\": \"value\", \"value\": 0.5 } ]", received.get ());
	}
	@Test
	public void transformedModelInformationSentDuringUpdate ()
	{
		AtomicReference<String> received = new AtomicReference<String> ("NOT CALLED");
		TestClass1 model = new TestClass1 ();
		DifferencingModelObserver<TestClass1, TestClass2> dmo = new DifferencingModelObserver<> (
				model,
				TestClass2::new,
				Query.NULL_QUERY,
				received::set);
		model.setValue (3);
		dmo.notifyUpdated ();
		assertEquals ("[ { \"type\": \"INSERTION\", \"key\": \"value\", \"value\": 1.5 } ]", received.get ());
	}
	private static class TestClass3
	{
		boolean condition;
		public TestClass3 (TestClass1 src) { condition = src.getValue () < 10; }
		@Attribute
		public boolean isCondition ()
		{
			return condition;
		}
	}
	@Test
	public void unchangedTransformedModelDoesNotCauseUpdate ()
	{
		AtomicBoolean called = new AtomicBoolean (false);
		TestClass1 model = new TestClass1 ();
		DifferencingModelObserver<TestClass1, TestClass3> dmo = new DifferencingModelObserver<> (
				model, TestClass3::new, Query.NULL_QUERY, s -> { called.set(true); });
		called.set (false);	// will have been called during initialisation to pass initial value
		model.setValue (2);
		dmo.notifyUpdated ();
		assertEquals (false, called.get ());
	}
	private static class TestClass4
	{
		int value1 = 1, value2 = 2;
		@Attribute
		public int getValue1 ()
		{
			return value1;
		}
		@Attribute
		public int getValue2 ()
		{
			return value2;
		}
		public void setValue1 (int value1)
		{
			this.value1 = value1;
		}
		public void setValue2 (int value2)
		{
			this.value2 = value2;
		}
	}
	@Test
	public void multipleChangesDoNotCauseAccumulationOfValues ()
	{
		AtomicReference<String> received = new AtomicReference<String> ("NOT CALLED");
		TestClass4 model = new TestClass4 ();
		DifferencingModelObserver<TestClass4, TestClass4> dmo = new DifferencingModelObserver<> (
				model,
				Function.identity (),
				Query.NULL_QUERY,
				received::set);
		model.setValue1 (3);
		dmo.notifyUpdated ();
		model.setValue2 (4);
		dmo.notifyUpdated ();
		assertEquals ("[ { \"type\": \"INSERTION\", \"key\": \"value2\", \"value\": 4 } ]", received.get ());
	}
}
