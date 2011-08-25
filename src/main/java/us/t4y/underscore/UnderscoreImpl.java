package us.t4y.underscore;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hamcrest.Matcher;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import us.t4y.underscore.Range.RangeFactory;
import us.t4y.underscore.fn.*;

class UnderscoreImpl {


	// each

	static <V, O> void _each(O obj, Iterator<V> iter, VoidFn3<? super V, ? super Integer, ? super O> fn) {
		int i = 0;
		while (iter.hasNext()) {
			try {
				fn.call(iter.next(), i, obj);
			} catch (Throwable t) {
				throw new RuntimeException(t);
			}
			i++;
		}
	}

	static <V, K, O> void _each(O obj, MapIterator<K, V> iter, VoidFn3<? super V, ? super K, ? super O> fn) {
		while (iter.hasNext()) {
			try {
				Entry<K, V> e = iter.next();
				fn.call(e.getValue(), e.getKey(), obj);
			} catch (Throwable t) {
				throw new RuntimeException(t);
			}
		}
	}


	// map

	static <K, V, O extends Map<K, V>, R> Map<K, R> _map(O map, Fn3<? extends R, ? super V, ? super K, ? super O> fn) {
		@SuppressWarnings("unchecked")
		Map<K, R> out = (Map<K, R>) makeMeA(map);

		for (Map.Entry<K, V> e : map.entrySet()) {
			try {
				K key = e.getKey();
				R ret = fn.call(e.getValue(), key, map);
				out.put(key, ret);
			} catch (Throwable t) {
				throw new RuntimeException(t);
			}
		}
		return out;
	}


	// reduce

	static <V> V _reduce(Iterator<V> iter, V memo, Fn2<? extends V, ? super V, ? super V> fn) {
		V value = memo;
		if (value == null) {
			if (iter.hasNext()) {
				value = iter.next();
			} else {
				throw new RuntimeException("Reduce without initial value");
			}
		}
		while (iter.hasNext()) {
			value = fn.call(value, iter.next());
		}
		return value;
	}


	// filter

	static <V> V[] _filter(V[] arr, Fn3<Boolean, ? super V, ? super Integer, ? super V[]> fn, boolean desired) {
		List<V> coll = new ArrayList<V>();

		for (int i = 0, l = arr.length; l > i; ++i) {
			try {
				if (fn.call(arr[i], i, arr) == desired) {
					coll.add(arr[i]);
				}
			} catch (Throwable t) {
				throw new RuntimeException(t);
			}
		}
		return coll.toArray(createArray(arr, coll.size()));
	}

	static <V, O extends Collection<V>> O _filter(O coll, Fn3<Boolean, ? super V, ? super Integer, ? super O> fn, boolean desired) {
		O out = makeMeA(coll);

		int i = 0;
		for (V v : coll) {
			try {
				if (fn.call(v, i, coll) == desired) {
					out.add(v);
				}
			} catch (Throwable t) {
				throw new RuntimeException(t);
			}
			i++;
		}
		return out;
	}

	static <K, V, O extends Map<K, V>> O _filter(O map, Fn3<Boolean, ? super V, ? super K, ? super O> fn, boolean desired) {
		O out = makeMeA(map);

		for (Map.Entry<K, V> e : map.entrySet()) {
			try {
				K key = e.getKey();
				V value = e.getValue();
				if (fn.call(value, key, map) == desired) {
					out.put(key, value);
				}
			} catch (Throwable t) {
				throw new RuntimeException(t);
			}
		}
		return out;
	}

	static JSONObject _filter(JSONObject obj, Fn3<Boolean, Object, ? super String, ? super JSONObject> fn, boolean desired) {
		JSONObject out = new JSONObject();
		MapIterator<String, Object> iter = _iterator(obj);

		while (iter.hasNext()) {
			try {
				Map.Entry<String, Object> e = iter.next();
				if (fn.call(e.getValue(), e.getKey(), obj) == desired) {
					out.put(e.getKey(), e.getValue());
				}
			} catch (Throwable t) {
				throw new RuntimeException(t);
			}
		}
		return out;
	}

	static JSONArray _filter(JSONArray arr, Fn3<Boolean, Object, ? super Integer, ? super JSONArray> fn, boolean desired) {
		JSONArray out = new JSONArray();

		for (int i = 0, l = arr.length(); l > i; ++i) {
			try {
				Object v = arr.get(i);
				if (fn.call(v, i, arr) == desired) {
					out.put(v);
				}
			} catch (Throwable t) {
				throw new RuntimeException(t);
			}
			i++;
		}
		return out;
	}


	// detect()

	static <V, O> V _detect(O obj, Iterator<V> iter, Fn3<? extends Boolean, ? super V, ? super Integer, ? super O> fn) {
		int i = 0;
		while (iter.hasNext()) {
			try {
				V v = iter.next();
				if (fn.call(v, i, obj)) {
					return v;
				}
			} catch (Throwable t) {
				throw new RuntimeException(t);
			}
			i++;
		}
		return null;
	}

	static <K, V, O> V _detect(O obj, MapIterator<K, V> iter, Fn3<? extends Boolean, ? super V, ? super K, ? super O> fn) {
		while (iter.hasNext()) {
			try {
				Entry<K, V> e = iter.next();
				if (fn.call(e.getValue(), e.getKey(), obj)) {
					return e.getValue();
				}
			} catch (Throwable t) {
				throw new RuntimeException(t);
			}
		}
		return null;
	}


	// anyAll()

	static <V, O> boolean _anyAll(O obj, Iterator<V> iter, Fn3<Boolean, ? super V, ? super Integer, ? super O> fn, boolean any) {
		int i = 0;
		while (iter.hasNext()) {
			try {
				if (fn.call(iter.next(), i, obj) == any) {
					return any;
				}
			} catch (Throwable t) {
				throw new RuntimeException(t);
			}
			i++;
		}
		return !any;
	}

	static <V, K, O> boolean _anyAll(O obj, MapIterator<K, V> iter, Fn3<Boolean, ? super V, ? super K, ? super O> fn, boolean any) {
		while (iter.hasNext()) {
			try {
				Map.Entry<K, V> e = iter.next();
				if (fn.call(e.getValue(), e.getKey(), obj) == any) {
					return any;
				}
			} catch (Throwable t) {
				throw new RuntimeException(t);
			}
		}
		return !any;
	}


	// _min()

	public static <V> V _minMax(Iterator<V> coll, Fn2<? extends V, ? super V, ? super V> fn, boolean max) {
		return null;
	}

	public static <V> V _minMax(Iterator<V> coll, Comparator<? super V> cmp, boolean max) {
		return null;
	}

	public static <V extends Comparable<V>> V _minMax(Iterator<V> coll, boolean max) {
		return null;
	}


	// invoke()

	@SuppressWarnings("unchecked")
	public static <R> Collection<R> _invoke(Collection<?> coll, String name, Class<R> clazz, Object... args) {
		Collection<R> out = new ArrayList<R>();
		for (Object obj : coll) {
			for (Method method : obj.getClass().getMethods()) {
				if (name.equals(method.getName())) {
					try {
						Object ret = method.invoke(obj, args);
						if (ret == null || clazz.isAssignableFrom(ret.getClass())) {
							out.add((R) ret);
						}
						break;
					} catch (IllegalArgumentException e) {
						throw new RuntimeException(e);
					} catch (IllegalAccessException e) {
						throw new RuntimeException(e);
					} catch (InvocationTargetException e) {
						throw new RuntimeException(e);
					}
				}
			}
		}
		return out;
	}


	// pluck()

	static <T> Collection<T> _pluck(Collection<?> coll, String name, Class<T> clazz) {
		Collection<T> out = new ArrayList<T>();
		for (Object obj : coll) {
			out.add(getProperty(obj, name, clazz));
		}
		return out;
	}


	// range()

	public static <T, S> Range<T, S> _range(T start, T stop, S step, boolean inclusive) {
		return RangeFactory.create(start, stop, step, inclusive);
	}


	// reverse()

	public static <T> T[] _reverse(T[] arr) {
		T tmp;
		for (int i = 0, l = arr.length, m = l / 2; m > i; ++i) {
			tmp = arr[i];
			arr[i] = arr[l - i - 1];
			arr[l - i - 1] = tmp;
		}
		return arr;
	}

	public static <T> List<T> _reverse(List<T> list) {
		Collections.reverse(list);
		return list;
	}

	public static JSONArray _reverse(JSONArray arr) {
		try {
			Object tmp;
			for (int i = 0, l = arr.length() / 2; l > i; ++i) {
				tmp = arr.get(i);
				arr.put(i, arr.get(l - i));
				arr.put(l - i, tmp);
			}
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
		return arr;
	}


	// times()

	public static void _times(int times, VoidFn1<Integer> fn) {
		for (int i = 0; times > i; ++i) {
			fn.call(i);
		}
	}


	// functions()

	static Collection<String> _functions(Class<?> clazz) {
		Collection<String> out = new HashSet<String>();
		for (Method method : clazz.getMethods()) {
			out.add(method.getName());
		}
		return out;
	}


	// isEqual()

	static boolean _isEqual(Object a, Object b) {
		if (a == b) {
			return true;
		} else if (a == null || b == null) {
			return false;
		} else {
			return a.equals(b);
		}
	}


	// _matcherAdaptor()

	static <K, V, O> Fn3<Boolean, V, K, O> _matcherAdaptor(final Matcher<?> matcher) {
		return new Fn3<Boolean, V, K, O>() {
			public Boolean call(V value, K key, O obj) {
				return matcher.matches(value);
			}
		};
	}


	// _iterator()

	static <V> Iterator<V> _iterator(final V[] arr) {
		return new Iterator<V>() {

			private int ndx = 0;
			private int len = arr.length;

			public boolean hasNext() {
				return ndx < len;
			}

			public V next() {
				return arr[ndx++];
			}

			public void remove() {
				throw new UnsupportedOperationException();
			}

		};
	}

	static <V> Iterator<V> _iterator(final Collection<V> coll) {
		return new Iterator<V>() {

			private final Iterator<V> inner = coll.iterator();
			
			public boolean hasNext() {
				return inner.hasNext();
			}

			public V next() {
				return inner.next();
			}

			public void remove() {
				throw new UnsupportedOperationException();
			}

		};
	}

	static Iterator<Object> _iterator(final JSONArray arr) {
		// NOTE: WILL NOT ALERT ON CONCURRENT MODIFICATION
		return new Iterator<Object>() {

			private int ndx = 0;

			public boolean hasNext() {
				return ndx < arr.length();
			}

			public Object next() {
				try {
					return arr.get(ndx++);
				} catch (JSONException e) {
					throw new RuntimeException(e);
				}
			}

			public void remove() {
				throw new UnsupportedOperationException("JSONArray iterator is readonly");
			}

		};
	}

	static <K, V> MapIterator<K, V> _iterator(final Map<K, V> map) {
		return new MapIterator<K, V>() {

			private final Iterator<Entry<K, V>> inner = map.entrySet().iterator();

			public boolean hasNext() {
				return inner.hasNext();
			}

			public Entry<K, V> next() {
				return inner.next();
			}

			public void remove() {
				throw new UnsupportedOperationException();
			}

		};
	}

	static MapIterator<String, Object> _iterator(final JSONObject obj) {
		// NOTE: WILL NOT ALERT ON CONCURRENT MODIFICATION
		return new MapIterator<String, Object>() {

			private Iterator<?> keys = obj.keys();

			public boolean hasNext() {
				return keys.hasNext();
			}

			public Entry<String, Object> next() {
				final String key = (String) keys.next();

				return new Entry<String, Object>() {
		
					public String getKey() {
						return key;
					}
		
					public Object getValue() {
						try {
							return obj.get(key);
						} catch (JSONException e) {
							throw new RuntimeException(e);
						}
					}
		
					public Object setValue(Object value) {
						throw new UnsupportedOperationException("JSONObject iterator is readonly");
					}
		
				};
			}

			public void remove() {
				throw new UnsupportedOperationException("JSONObject iterator is readonly");
			}

		};
	}


	@SuppressWarnings("unchecked")
	private static <T> T getProperty(final Object object, final String name, final Class<T> clazz) {
		try {
			Method m = getReadMethod(object, name);
			if (m.getReturnType().isAssignableFrom(clazz)) {
				return (T) m.invoke(object);
			} else {
				//TODO do something about an invalid type
				return null;
			}
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

	private static Method getReadMethod(final Object object, final String name) {
		Class<?> clazz = object.getClass();
		try {
			BeanInfo info = Introspector.getBeanInfo(clazz);
			PropertyDescriptor[] pds = info.getPropertyDescriptors();
			for (PropertyDescriptor pd : pds) {
				if (name.equals(pd.getName())) {
					return pd.getReadMethod();
				}
			}
		} catch (IntrospectionException e) {
			throw new RuntimeException("Error getting bean info on object of type " + clazz.getCanonicalName(), e);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	private static <T> T[] createArray(T[] arr, int size) {
		return (T[]) Array.newInstance(arr.getClass().getComponentType(), size);
	}

	@SuppressWarnings("unchecked")
	private static <T> T makeMeA(final T o) {
		Class<?> clazz = o.getClass();
		Object out = null;

		try {
			out = clazz.newInstance();
		} catch (Throwable t) {
			throw new RuntimeException("Error attempting to instantiate object of type " + clazz.getCanonicalName(), t);
		}

		return (T) out;
	}


}