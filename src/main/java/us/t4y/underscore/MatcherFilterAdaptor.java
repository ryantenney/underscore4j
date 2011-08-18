package us.t4y.underscore;

import org.hamcrest.Matcher;

import us.t4y.underscore.fn.FilterFn;

class MatcherFilterAdaptor<K, V, O> implements FilterFn<K, V, O> {

	private final Matcher<?> matcher;

	protected MatcherFilterAdaptor(final Matcher<?> matcher) {
		this.matcher = matcher;
	}

	public boolean call(V value, K key, O obj) {
		return this.matcher.matches(value);
	}

}