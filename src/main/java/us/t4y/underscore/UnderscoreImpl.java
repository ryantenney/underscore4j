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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import us.t4y.underscore.fn.EachFn;
import us.t4y.underscore.fn.FilterFn;
import us.t4y.underscore.fn.MapFn;

class UnderscoreImpl {

	static <V> void _each(V[] arr, EachFn<? super Integer, ? super V, ? super V[]> fn) {
		for (int i = 0, l = arr.length; l > i; ++i) {
			try {
				fn.call(arr[i], i, arr);
			} catch (Throwable t) {
				throw new RuntimeException(t);
			}
		}
	}

	static <V, O extends Iterable<V>> void _each(O obj, EachFn<? super Integer, ? super V, ? super O> fn) {
		Iterator<V> iter = obj.iterator();
		int i = 0;
		while (iter.hasNext()) {
			try {
				V value = iter.next();
				fn.call(value, i, obj);
			} catch (Throwable t) {
				throw new RuntimeException(t);
			}
		}
	}

	static <V, O extends Collection<V>> void _each(O coll, EachFn<? super Integer, ? super V, ? super O> fn) {
		int i = 0;
		for (V v : coll) {
			try {
				fn.call(v, i, coll);
			} catch (Throwable t) {
				throw new RuntimeException(t);
			}
			i++;
		}
	}

	static <K, V, O extends Map<K, V>> void _each(O map, EachFn<? super K, ? super V, ? super O> fn) {
		for (Map.Entry<K, V> e : map.entrySet()) {
			try {
				fn.call(e.getValue(), e.getKey(), map);
			} catch (Throwable t) {
				throw new RuntimeException(t);
			}
		}
	}

	static void _each(JSONArray arr, EachFn<? super Integer, ? super Object, ? super JSONArray> fn) {
		for (int i = 0, l = arr.length(); l > i; ++i) {
			try {
				fn.call(arr.get(i), i, arr);
			} catch (Throwable t) {
				throw new RuntimeException(t);
			}
		}
	}

	static void _each(JSONObject obj, EachFn<? super String, ? super Object, ? super JSONObject> fn) {
		Iterator<?> keys = obj.keys();
		while (keys.hasNext()) {
			try {
				String key = (String) keys.next();
				Object value = obj.get(key);
				fn.call(value, key, obj);
			} catch (Throwable t) {
				throw new RuntimeException(t);
			}
		}
	}


	// map

	static <K, V, O extends Map<K, V>, R> Map<K, R> _map(O map, MapFn<? super K, ? super V, ? super O, ? extends R> fn) {
		@SuppressWarnings("unchecked")
		Map<K, R> out = (Map<K, R>) makeMeA(map);

		for (Map.Entry<K, V> e : map.entrySet()) {
			try {
				K key = e.getKey();
				out.put(key, fn.call(e.getValue(), key, map));
			} catch (Throwable t) {
				throw new RuntimeException(t);
			}
		}
		return out;
	}


	// filter

	static <V> V[] _filter(V[] arr, FilterFn<? super Integer, ? super V, ? super V[]> fn, boolean desired) {
		List<V> out = new ArrayList<V>();
		for (int i = 0, l = arr.length; l > i; ++i) {
			try {
				if (fn.call(arr[i], i, arr) == desired) {
					out.add(arr[i]);
				}
			} catch (Throwable t) {
				throw new RuntimeException(t);
			}
		}
		return toArray(out);
	}

	static <V, O extends Collection<V>> O _filter(O coll, FilterFn<? super Integer, ? super V, ? super O> fn, boolean desired) {
		@SuppressWarnings("unchecked")
		O out = (O) makeMeA(coll);

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

	static <K, V, O extends Map<K, V>> O _filter(O map, FilterFn<? super K, ? super V, ? super O> fn, boolean desired) {
		@SuppressWarnings("unchecked")
		O out = (O) makeMeA(map);

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

	static JSONObject _filter(JSONObject obj, FilterFn<? super String, Object, ? super JSONObject> fn, boolean desired) {
		JSONObject out = new JSONObject();
		Iterator<?> keys = obj.keys();
		while (keys.hasNext()) {
			try {
				String key = (String) keys.next();
				Object value = obj.get(key);
				if (fn.call(value, key, obj) == desired) {
					out.put(key, value);
				}
			} catch (Throwable t) {
				throw new RuntimeException(t);
			}
		}
		return out;
	}

	static JSONArray _filter(JSONArray arr, FilterFn<? super Integer, Object, ? super JSONArray> fn, boolean desired) {
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


	// anyAll()

	static <K, V, O extends Map<K, V>> boolean _anyAll(O map, FilterFn<? super K, ? super V, ? super O> fn, boolean any) {
		for (Map.Entry<K, V> e : map.entrySet()) {
			try {
				if (fn.call(e.getValue(), e.getKey(), map) == any) {
					return any;
				}
			} catch (Throwable t) {
				throw new RuntimeException(t);
			}
		}
		return !any;
	}

	static <V, O extends Iterable<V>> boolean _anyAll(O obj, FilterFn<? super Integer, ? super V, ? super O> fn, boolean any) {
		Iterator<V> iter = obj.iterator();
		int i = 0;
		while (iter.hasNext()) {
			try {
				V v = iter.next();
				if (fn.call(v, i, obj) == any) {
					return any;
				}
			} catch (Throwable t) {
				throw new RuntimeException(t);
			}
			i++;
		}
		return !any;
	}

	static <V, O extends Collection<V>> boolean _anyAll(O coll, FilterFn<? super Integer, ? super V, ? super O> fn, boolean any) {
		int i = 0;
		for (V v : coll) {
			try {
				if (fn.call(v, i, coll) == any) {
					return any;
				}
			} catch (Throwable t) {
				throw new RuntimeException(t);
			}
			i++;
		}
		return !any;
	}

	static <V> boolean _anyAll(V[] arr, FilterFn<? super Integer, ? super V, ? super V[]> fn, boolean any) {
		for (int i = 0, l = arr.length; l > i; ++i) {
			try {
				if (fn.call(arr[i], i, arr) == any) {
					return any;
				}
			} catch (Throwable t) {
				throw new RuntimeException(t);
			}
		}
		return !any;
	}

	static boolean _anyAll(JSONObject obj, FilterFn<? super String, Object, ? super JSONObject> fn, boolean any) {
		Iterator<?> keys = obj.keys();
		while (keys.hasNext()) {
			try {
				String key = (String) keys.next();
				Object value = obj.get(key);
				if (fn.call(value, key, obj) == any) {
					return any;
				}
			} catch (Throwable t) {
				throw new RuntimeException(t);
			}
		}
		return !any;
	}

	static boolean _anyAll(JSONArray arr, FilterFn<? super Integer, Object, ? super JSONArray> fn, boolean any) {
		for (int i = 0, l = arr.length(); l > i; ++i) {
			try {
				Object v = arr.get(i);
				if (fn.call(v, i, arr) == any) {
					return any;
				}
			} catch (Throwable t) {
				throw new RuntimeException(t);
			}
			i++;
		}
		return !any;
	}


	// pluck()

	public static Collection<Object> _pluck(Collection<?> coll, String name) {
		Collection<Object> out = new ArrayList<Object>();
		for (Object obj : coll) {
			out.add(getProperty(obj, name));
		}
		return out;
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

	private static Object getProperty(final Object object, final String name) {
		try {
			return getReadMethod(object, name).invoke(object);
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

	private static <T> T[] toArray(Collection<? extends T> coll) {
		T[] out = (T[]) Array.newInstance(commonSuperclass(coll), coll.size());
		coll.toArray(out);
		return out;
	}

	private static Class<?> commonSuperclass(Collection<?> coll) {
		Iterator<?> iter = coll.iterator();
		Class<?> common = Object.class;
		try {
			if (iter.hasNext()) {
				common = nextNonNull(iter).getClass();
			}
			while (iter.hasNext()) {
				common = commonSuperclass(common, nextNonNull(iter).getClass());
			}
		} catch (NullPointerException e) {}
		return common;
	}

	private static Object nextNonNull(Iterator<?> iter) throws NullPointerException {
		while (iter.hasNext()) {
			Object o = iter.next();
			if (o != null) {
				return o;
			}
		}
		throw new NullPointerException();
	}

	private static Class<?> commonSuperclass(Class<?> c1, Class<?> c2) {
		if (c1.isAssignableFrom(c2)) return c1;
		if (c2.isAssignableFrom(c1)) return c2;
		return commonSuperclass(c1.getSuperclass(), c2.getSuperclass());
	}

	private static Object makeMeA(final Object o) {
		Class<?> clazz = o.getClass();
		Object out = null;

		try {
			out = clazz.newInstance();
		} catch (Throwable t) {
			throw new RuntimeException("Error getting bean info on object of type " + clazz.getCanonicalName(), t);
		}

		return out;
	}


}