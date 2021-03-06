package us.t4y.underscore;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;

import static org.hamcrest.Matchers.*;

import us.t4y.underscore.fn.*;

public class Tests {

	private Fn3<Boolean, Integer, Object, Object> evenFilter = new Fn1<Boolean, Integer>() {
		public Boolean call(Integer value) {
			return value % 2 == 0;
		}
	};

	private Fn3<Boolean, Integer, Object, Object> oddFilter = invert(evenFilter);

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

	public static <V, K, O> Fn3<Boolean, V, K, O> invert(final Fn3<Boolean, V, K, O> fn) {
		return new Fn3<Boolean, V, K, O>() {
			public Boolean call(V value, K key, O obj) {
				return !fn.call(value, key, obj);
			}
		};
	}

	@SuppressWarnings("unchecked")
	@Test
	public void main() {

		Integer[] oneToTen = _.range(0, 10).asArray();
		final Map<String, Integer> m = new LinkedHashMap<String, Integer>();

		print("a-e", _.range('a', 'e'));
		_.each(_.range('a', 'e'), new VoidFn2<Character, Integer>() {
			public void call(Character chr, Integer ndx) {
				m.put(chr.toString(), ndx + 1);
			}
		});

		Map<String, PluckInvokeTest> p = _.map(m, new Fn1<PluckInvokeTest, Integer>() {
			public PluckInvokeTest call(Integer value) {
				return new PluckInvokeTest(value);
			}
		});

		Collection<PluckInvokeTest> pv = _.values(p);
		print(_.pluck(pv, "indexValue", String.class));
		print(_.invoke(pv, "addedTo", Integer.class, 2));

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

		_.each(m, new VoidFn2<Integer, String>() {
			public void call(Integer val, String str) {
				print("ndx " + str + ": " + val);
			}
		});

		Map<String, Double> d = _.map(m, new Fn1<Double, Integer>() {
			public Double call(Integer value) {
				return (double) value / 10.0;
			}
		});

		print("All 0-1: " + _.all(d, allOf(greaterThanOrEqualTo(0.0), lessThanOrEqualTo(1.0))));

		VoidFn3<Double, String, Object> printFn = new VoidFn2<Double, String>() {
			public void call(Double val, String str) {
				print("ndx " + str + ": " + val);
			}
		};

		_.each(d, printFn);
	}

	@Test
	public void methods() {
		Collection<String> m = _.functions(_.class);
		_.each(m, new VoidFn2<String, Integer>() {
			public void call(String name, Integer ndx) {
				print("ndx " + ndx + ": " + name);
			}
		});
	}

	static void print(String s, Collection<?> c) {
		print(s, c.toArray());
	}

	static void print(String s, Object[] o) {
		print(s, Arrays.toString(o));
	}

	static void print(String s, Range<?, ?> o) {
		print(s, Arrays.toString(o.asArray()));
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