package us.t4y.underscore;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONObject;
import org.junit.Test;

import static org.hamcrest.Matchers.*;

import us.t4y.underscore.fn.EachFn;
import us.t4y.underscore.fn.FilterFn;
import us.t4y.underscore.fn.MapFn;

public class Tests {

	private FilterFn<Object, Integer, Object> evenFilter = new FilterFn<Object, Integer, Object>() {
		public boolean call(Integer value, Object key, Object obj) {
			return value % 2 == 0;
		}
	};

	private FilterFn<Object, Integer, Object> oddFilter = invert(evenFilter);

	public class PluckInvokeTest {
		private int i;
		public PluckInvokeTest(int i) {
			this.i = i;
		}
		public String getIndexValue() {
			return "Value: " + this.i;
		}
		public int addedTo(int x) {
			return this.i + x;
		}
	}

	public static <K, V, O> FilterFn<K, V, O> invert(final FilterFn<K, V, O> fn) {
		return new FilterFn<K, V, O>() {
			public boolean call(V value, K key, O obj) {
				return !fn.call(value, key, obj);
			}
		};
	}

	@SuppressWarnings("unchecked")
	@Test
	public void main() {

		final Integer[] oneToTen = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

		Map<String, Integer> m = new LinkedHashMap<String, Integer>();
		m.put("a", 1);
		m.put("b", 2);
		m.put("c", 3);
		m.put("d", 4);
		m.put("e", 5);

		Map<String, PluckInvokeTest> p = _.map(m, new MapFn<String, Integer, Map<String, Integer>, PluckInvokeTest>() {
			public PluckInvokeTest call(Integer value, String key, Map<String, Integer> obj) {
				return new PluckInvokeTest(value);
			}
		});

		Collection<PluckInvokeTest> pv = p.values();
		print(Arrays.toString(_.pluck(pv, "indexValue").toArray()));
		print(Arrays.toString(_.invoke(pv, "addedTo", 2).toArray()));

		JSONObject j = new JSONObject(m);

		print("Any 0", _.any(m, equalTo(0)));
		print("Any 1", _.any(m, equalTo(1)));
		print("Any 2", _.any(m, equalTo(2)));
		print("Any 3", _.any(m, equalTo(3)));
		print("Any 4", _.any(m, equalTo(4)));
		print("Any 5", _.any(m, equalTo(5)));
		print("Any 6", _.any(m, equalTo(6)));
		print("Any even", _.any(m, evenFilter));

		print("All > 0", _.all(m, greaterThan(0)));
		print("All >= 1", _.all(m, greaterThanOrEqualTo(1)));
		print("All 2", _.all(m, equalTo(2)));
		print("All 3", _.all(m, equalTo(3)));
		print("All 4", _.all(m, equalTo(4)));
		print("All 5", _.all(m, equalTo(5)));
		print("All != 6", _.all(m, not(equalTo(6))));
		print("Any Even", _.any(m, evenFilter));
		print("Any Odd", _.any(m, oddFilter));

		print("Reject Even", _.reject(oneToTen, evenFilter));
		print("Select Even", _.select(oneToTen, evenFilter));
		print("Reject Odd", _.reject(oneToTen, oddFilter));
		print("Select Odd", _.select(oneToTen, oddFilter));

		print("Less than 6", _.select(oneToTen, lessThan(6)));

		Integer[] evens = _.filter(oneToTen, evenFilter);
		print("Evens", evens);
		print("All Even", _.all(evens, evenFilter));
		print("Any Odd", _.any(evens, oddFilter));

		print("All 0-6", _.all(m, allOf(greaterThan(0), lessThan(6))));
		print("All 1-5", _.all(m, allOf(greaterThanOrEqualTo(1), lessThanOrEqualTo(5))));
		print("All 2-5", _.all(m, allOf(greaterThanOrEqualTo(2), lessThanOrEqualTo(5))));
		print("All 1-4", _.all(m, allOf(greaterThanOrEqualTo(1), lessThanOrEqualTo(4))));
		print("All 0-7", _.all(m, allOf(greaterThanOrEqualTo(0), lessThanOrEqualTo(7))));

		_.each(m, new EachFn<String, Integer, Map<String, Integer>>() {
			public void call(Integer val, String str, Map<String, Integer> obj) {
				print("ndx " + str + ": " + val);
			}
		});

		Map<String, Double> d = _.map(m, new MapFn<String, Integer, Map<String, Integer>, Double>() {
			public Double call(Integer value, String key, Map<String, Integer> obj) {
				return (double) value / 10.0;
			}
		});

		print("All 0-1: " + _.all(d, allOf(greaterThanOrEqualTo(0.0), lessThanOrEqualTo(1.0))));

		_.each(d, new EachFn<String, Double, Map<String, Double>>() {
			public void call(Double val, String str, Map<String, Double> obj) {
				print("ndx " + str + ": " + val);
			}
		});

		_.each(d, new EachFn<String, Double, Map<String, Double>>() {
			public void call(Double val, String str, Map<String, Double> obj) {
				print("ndx " + str + ": " + val);
			}
		});

		_.each(d, new EachFn<String, Double, Map<String, Double>>() {
			public void call(Double val, String str, Map<String, Double> obj) {
				print("ndx " + str + ": " + val);
			}
		});
	}

	@Test
	public void methods() {
		Collection<String> m = _.functions(_.class);
		_.each(m, new EachFn<Integer, String, Collection<String>>() {
			public void call(String val, Integer str, Collection<String> obj) {
				print("ndx " + str + ": " + val);
			}
		});
	}

	static void print(String s, Collection<?> c) {
		print(s, c.toArray());
	}

	static void print(String s, Object[] o) {
		print(s, Arrays.toString(o));
	}

	static void print(Collection<?> c) {
		print(c.toArray());
	}

	static void print(Object[] o) {
		print(Arrays.toString(o));
	}

	static void print(String s, Object o) {
		print(s + ": " + (o == null ? "null" : o.toString()));
	}

	static void print(String s) {
		System.out.println(s);
	}

}