package uk.co.periata.modmap;

import java.util.Iterator;

public class IterableComparison
{

	public static <T extends Comparable<T>> int compare (Iterable<? extends T> first, Iterable<? extends T> second)
	{
		return compare (first.iterator (), second.iterator ());
	}
	public static <T extends Comparable<T>> int compare (Iterator<? extends T> first, Iterator<? extends T> second)
	{
		T a, b;
		while (first.hasNext () && second.hasNext ())
		{
			a = first.next ();
			b = second.next ();
			int r = a.compareTo (b);
			if (r != 0) return r;
		}
		if (first.hasNext ()) return 1;
		if (second.hasNext ()) return -1;
		return 0;
	}
}
