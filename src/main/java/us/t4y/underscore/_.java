package us.t4y.underscore;

import static us.t4y.underscore.UnderscoreImpl.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.hamcrest.Matcher;
import org.json.JSONArray;
import org.json.JSONObject;

import us.t4y.underscore.fn.EachFn;
import us.t4y.underscore.fn.FilterFn;
import us.t4y.underscore.fn.MapFn;

public class _ {


	// forEach()

	public static <V> void forEach(V[] arr, EachFn<? super Integer, ? super V, ? super V[]> fn) {
		_each(arr, fn);
	}

	public static <V, O extends Iterable<V>> void forEach(O obj, EachFn<? super Integer, ? super V, ? super O> fn) {
		_each(obj, fn);
	}

	public static <V, O extends Collection<V>> void forEach(O coll, EachFn<? super Integer, ? super V, ? super O> fn) {
		_each(coll, fn);
	}

	public static <K, V, O extends Map<K, V>> void forEach(O map, EachFn<? super K, ? super V, ? super O> fn) {
		_each(map, fn);
	}

	public static void forEach(JSONArray arr, EachFn<? super Integer, ? super Object, ? super JSONArray> fn) {
		_each(arr, fn);
	}

	public static void forEach(JSONObject obj, EachFn<? super String, ? super Object, ? super JSONObject> fn) {
		_each(obj, fn);
	}


	// each()

	public static <V> void each(V[] arr, EachFn<? super Integer, ? super V, ? super V[]> fn) {
		_each(arr, fn);
	}

	public static <V, O extends Iterable<V>> void each(O obj, EachFn<? super Integer, ? super V, ? super O> fn) {
		_each(obj, fn);
	}

	public static <V, O extends Collection<V>> void each(O coll, EachFn<? super Integer, ? super V, ? super O> fn) {
		_each(coll, fn);
	}

	public static <K, V, O extends Map<K, V>> void each(O map, EachFn<? super K, ? super V, ? super O> fn) {
		_each(map, fn);
	}

	public static void each(JSONArray arr, EachFn<? super Integer, ? super Object, ? super JSONArray> fn) {
		_each(arr, fn);
	}

	public static void each(JSONObject obj, EachFn<? super String, ? super Object, ? super JSONObject> fn) {
		_each(obj, fn);
	}


	// map()

	public static <K, V, O extends Map<K, V>, R> Map<K, R> map(O map, MapFn<? super K, ? super V, ? super O, ? extends R> fn) {
		return _map(map, fn);
	}

	public static <V, O extends Collection<V>, R> Collection<R> map(O coll, MapFn<? super Integer, ? super V, ? super O, ? extends R> fn) {
		return null;
	}


	// select()

	public static <V> V[] select(V[] arr, Matcher<?> matcher) {
		return filter(arr, matcher);
	}

	public static <V> V[] select(V[] arr, FilterFn<? super Integer, ? super V, ? super V[]> fn) {
		return filter(arr, fn);
	}

	public static <V, O extends Collection<V>> O select(O coll, Matcher<?> matcher) {
		return filter(coll, matcher);
	}

	public static <V, O extends Collection<V>> O select(O coll, FilterFn<? super Integer, ? super V, ? super O> fn) {
		return filter(coll, fn);
	}

	public static <K, V, O extends Map<K, V>> O select(O map, Matcher<?> matcher) {
		return filter(map, matcher);
	}

	public static <K, V, O extends Map<K, V>> O select(O map, FilterFn<? super K, ? super V, ? super O> fn) {
		return filter(map, fn);
	}

	public static JSONArray select(JSONArray arr, Matcher<?> matcher) {
		return filter(arr, matcher);
	}

	public static JSONArray select(JSONArray arr, FilterFn<? super Integer, Object, ? super JSONArray> fn) {
		return filter(arr, fn);
	}

	public static JSONObject select(JSONObject obj, Matcher<?> matcher) {
		return filter(obj, matcher);
	}

	public static JSONObject select(JSONObject obj, FilterFn<? super String, Object, ? super JSONObject> fn) {
		return filter(obj, fn);
	}


	// filter()

	public static <K, V, O extends Map<K, V>> O filter(O map, Matcher<?> matcher) {
		return filter(map, new MatcherFilterAdaptor<K, V, O>(matcher));
	}

	public static <K, V, O extends Map<K, V>> O filter(O map, FilterFn<? super K, ? super V, ? super O> fn) {
		return _filter(map, fn, true);
	}

	public static <V, O extends Collection<V>> O filter(O coll, Matcher<?> matcher) {
		return filter(coll, new MatcherFilterAdaptor<Integer, V, O>(matcher));
	}

	public static <V, O extends Collection<V>> O filter(O coll, FilterFn<? super Integer, ? super V, ? super O> fn) {
		return _filter(coll, fn, true);
	}

	public static <V> V[] filter(V[] arr, Matcher<?> matcher) {
		return filter(arr, new MatcherFilterAdaptor<Integer, V, V[]>(matcher));
	}

	public static <V> V[] filter(V[] arr, FilterFn<? super Integer, ? super V, ? super V[]> fn) {
		return _filter(arr, fn, true);
	}

	public static JSONArray filter(JSONArray arr, Matcher<?> matcher) {
		return filter(arr, new MatcherFilterAdaptor<Integer, Object, JSONArray>(matcher));
	}

	public static JSONArray filter(JSONArray arr, FilterFn<? super Integer, Object, ? super JSONArray> fn) {
		return _filter(arr, fn, true);
	}

	public static JSONObject filter(JSONObject obj, Matcher<?> matcher) {
		return filter(obj, new MatcherFilterAdaptor<String, Object, JSONObject>(matcher));
	}

	public static JSONObject filter(JSONObject obj, FilterFn<? super String, Object, ? super JSONObject> fn) {
		return _filter(obj, fn, true);
	}


	// reject()

	public static <V> V[] reject(V[] arr, Matcher<?> matcher) {
		return reject(arr, matcher);
	}

	public static <V> V[] reject(V[] arr, FilterFn<? super Integer, ? super V, ? super V[]> fn) {
		return _filter(arr, fn, false);
	}

	public static <V, O extends Collection<V>> O reject(O coll, Matcher<?> matcher) {
		return reject(coll, new MatcherFilterAdaptor<Integer, V, O>(matcher));
	}

	public static <V, O extends Collection<V>> O reject(O coll, FilterFn<? super Integer, ? super V, ? super O> fn) {
		return _filter(coll, fn, false);
	}

	public static <K, V, O extends Map<K, V>> O reject(O map, Matcher<?> matcher) {
		return reject(map, new MatcherFilterAdaptor<K, V, O>(matcher));
	}

	public static <K, V, O extends Map<K, V>> O reject(O map, FilterFn<? super K, ? super V, ? super O> fn) {
		return _filter(map, fn, false);
	}

	public static JSONArray reject(JSONArray arr, Matcher<?> matcher) {
		return reject(arr, new MatcherFilterAdaptor<Integer, Object, JSONArray>(matcher));
	}

	public static JSONArray reject(JSONArray arr, FilterFn<? super Integer, Object, ? super JSONArray> fn) {
		return _filter(arr, fn, false);
	}

	public static JSONObject reject(JSONObject obj, Matcher<?> matcher) {
		return reject(obj, new MatcherFilterAdaptor<String, Object, JSONObject>(matcher));
	}

	public static JSONObject reject(JSONObject obj, FilterFn<? super String, Object, ? super JSONObject> fn) {
		return _filter(obj, fn, false);
	}





	// foldl, inject
	/*public static <V, O extends Collection<V>> R reduce(O coll, R memo, MapFn<? super Integer, ? super V, ? super O, ? extends R> fn) {
		boolean initial = memo != null;
		for (V value : coll) {
			if (!initial) {
				memo = value;
				initial = true;
			} else {
				memo = fn.call(value, key, obj);
			}
		}
		return memo;
	}*/

	// foldr
	public static void reduceRight() {}

	// detect
	public static void find() {}

	// contains
	public static void include() {}


	// some()

	public static <V> boolean some(V[] arr, Matcher<?> matcher) {
		return any(arr, matcher);
	}

	public static <V> boolean some(V[] arr, FilterFn<? super Integer, ? super V, ? super V[]> fn) {
		return _anyAll(arr, fn, true);
	}

	public static <V, O extends Collection<V>> boolean some(O coll, Matcher<?> matcher) {
		return any(coll, new MatcherFilterAdaptor<Integer, V, O>(matcher));
	}

	public static <V, O extends Collection<V>> boolean some(O coll, FilterFn<? super Integer, ? super V, ? super O> fn) {
		return _anyAll(coll, fn, true);
	}

	public static <K, V, O extends Map<K, V>> boolean some(O map, Matcher<?> matcher) {
		return any(map, new MatcherFilterAdaptor<K, V, O>(matcher));
	}

	public static <K, V, O extends Map<K, V>> boolean some(O map, FilterFn<? super K, ? super V, ? super O> fn) {
		return _anyAll(map, fn, true);
	}

	public static boolean some(JSONArray arr, Matcher<?> matcher) {
		return any(arr, new MatcherFilterAdaptor<Integer, Object, JSONArray>(matcher));
	}

	public static boolean some(JSONArray arr, FilterFn<? super Integer, Object, ? super JSONArray> fn) {
		return _anyAll(arr, fn, true);
	}

	public static boolean some(JSONObject obj, Matcher<?> matcher) {
		return any(obj, new MatcherFilterAdaptor<String, Object, JSONObject>(matcher));
	}

	public static boolean some(JSONObject obj, FilterFn<? super String, Object, ? super JSONObject> fn) {
		return _anyAll(obj, fn, true);
	}


	// any()

	public static <V> boolean any(V[] arr, Matcher<?> matcher) {
		return any(arr, matcher);
	}

	public static <V> boolean any(V[] arr, FilterFn<? super Integer, ? super V, ? super V[]> fn) {
		return _anyAll(arr, fn, true);
	}

	public static <V, O extends Collection<V>> boolean any(O coll, Matcher<?> matcher) {
		return any(coll, new MatcherFilterAdaptor<Integer, V, O>(matcher));
	}

	public static <V, O extends Collection<V>> boolean any(O coll, FilterFn<? super Integer, ? super V, ? super O> fn) {
		return _anyAll(coll, fn, true);
	}

	public static <K, V, O extends Map<K, V>> boolean any(O map, Matcher<?> matcher) {
		return any(map, new MatcherFilterAdaptor<K, V, O>(matcher));
	}

	public static <K, V, O extends Map<K, V>> boolean any(O map, FilterFn<? super K, ? super V, ? super O> fn) {
		return _anyAll(map, fn, true);
	}

	public static boolean any(JSONArray arr, Matcher<?> matcher) {
		return any(arr, new MatcherFilterAdaptor<Integer, Object, JSONArray>(matcher));
	}

	public static boolean any(JSONArray arr, FilterFn<? super Integer, Object, ? super JSONArray> fn) {
		return _anyAll(arr, fn, true);
	}

	public static boolean any(JSONObject obj, Matcher<?> matcher) {
		return any(obj, new MatcherFilterAdaptor<String, Object, JSONObject>(matcher));
	}

	public static boolean any(JSONObject obj, FilterFn<? super String, Object, ? super JSONObject> fn) {
		return _anyAll(obj, fn, true);
	}


	// every()

	public static <V> boolean every(V[] arr, Matcher<?> matcher) {
		return all(arr, matcher);
	}

	public static <V> boolean every(V[] arr, FilterFn<? super Integer, ? super V, ? super V[]> fn) {
		return _anyAll(arr, fn, false);
	}

	public static <V, O extends Collection<V>> boolean every(O coll, Matcher<?> matcher) {
		return all(coll, new MatcherFilterAdaptor<Integer, V, O>(matcher));
	}

	public static <V, O extends Collection<V>> boolean every(O coll, FilterFn<? super Integer, ? super V, ? super O> fn) {
		return _anyAll(coll, fn, false);
	}

	public static <K, V, O extends Map<K, V>> boolean every(O map, Matcher<?> matcher) {
		return all(map, new MatcherFilterAdaptor<K, V, O>(matcher));
	}

	public static <K, V, O extends Map<K, V>> boolean every(O map, FilterFn<? super K, ? super V, ? super O> fn) {
		return _anyAll(map, fn, false);
	}

	public static boolean every(JSONArray arr, Matcher<?> matcher) {
		return all(arr, new MatcherFilterAdaptor<Integer, Object, JSONArray>(matcher));
	}

	public static boolean every(JSONArray arr, FilterFn<? super Integer, Object, ? super JSONArray> fn) {
		return _anyAll(arr, fn, false);
	}

	public static boolean every(JSONObject obj, Matcher<?> matcher) {
		return all(obj, new MatcherFilterAdaptor<String, Object, JSONObject>(matcher));
	}

	public static boolean every(JSONObject obj, FilterFn<? super String, Object, ? super JSONObject> fn) {
		return _anyAll(obj, fn, false);
	}


	// all()

	public static <V> boolean all(V[] arr, Matcher<?> matcher) {
		return all(arr, matcher);
	}

	public static <V> boolean all(V[] arr, FilterFn<? super Integer, ? super V, ? super V[]> fn) {
		return _anyAll(arr, fn, false);
	}

	public static <V, O extends Collection<V>> boolean all(O coll, Matcher<?> matcher) {
		return all(coll, new MatcherFilterAdaptor<Integer, V, O>(matcher));
	}

	public static <V, O extends Collection<V>> boolean all(O coll, FilterFn<? super Integer, ? super V, ? super O> fn) {
		return _anyAll(coll, fn, false);
	}

	public static <K, V, O extends Map<K, V>> boolean all(O map, Matcher<?> matcher) {
		return all(map, new MatcherFilterAdaptor<K, V, O>(matcher));
	}

	public static <K, V, O extends Map<K, V>> boolean all(O map, FilterFn<? super K, ? super V, ? super O> fn) {
		return _anyAll(map, fn, false);
	}

	public static boolean all(JSONArray arr, Matcher<?> matcher) {
		return all(arr, new MatcherFilterAdaptor<Integer, Object, JSONArray>(matcher));
	}

	public static boolean all(JSONArray arr, FilterFn<? super Integer, Object, ? super JSONArray> fn) {
		return _anyAll(arr, fn, false);
	}

	public static boolean all(JSONObject obj, Matcher<?> matcher) {
		return all(obj, new MatcherFilterAdaptor<String, Object, JSONObject>(matcher));
	}

	public static boolean all(JSONObject obj, FilterFn<? super String, Object, ? super JSONObject> fn) {
		return _anyAll(obj, fn, false);
	}


	// invoke()

	public static Collection<Object> invoke(Collection<?> coll, String name, Object... args) {
		Collection<Object> out = new ArrayList<Object>();
		for (Object obj : coll) {
			Object ret = null;
			Method[] methods = obj.getClass().getMethods();
			for (Method method : methods) {
				if (name.equals(method.getName())) {
					try {
						ret = method.invoke(obj, args);
					} catch (IllegalArgumentException e) {
						throw new RuntimeException(e);
					} catch (IllegalAccessException e) {
						throw new RuntimeException(e);
					} catch (InvocationTargetException e) {
						throw new RuntimeException(e);
					}
				}
			}
			out.add(ret);
		}
		return out;
	}


	// pluck()

	public static Collection<Object> pluck(Collection<?> coll, String name) {
		return _pluck(coll, name);
	}


	public static void max() {}

	public static void min() {}

	public static void sortBy() {}

	public static void groupBy() {}

	public static void sortedIndex() {}

	public static void toArray() {}

	public static void size() {}

	// head
	public static void first() {}

	// tail
	public static void rest() {}

	public static void last() {}

	public static void compact() {}

	public static void flatten() {}

	public static void without() {}

	// unique
	public static void uniq() {}

	public static void union() {}

	// intersection
	public static void intersect() {}

	public static void difference() {}

	public static void zip() {}

	public static void indexOf() {}

	public static void lastIndexOf() {}
	public static void range() {}
	public static void memoize() {}
	public static void keys() {}
	public static void values() {}


	public static Collection<String> methods(Object obj) {
		return functions(obj);
	}

	public static Collection<String> methods(Class<?> clazz) {
		return _functions(clazz);
	}

	public static Collection<String> functions(Object obj) {
		return functions(obj.getClass());
	}

	public static Collection<String> functions(Class<?> clazz) {
		return _functions(clazz);
	}


	// isEqual()

	public static boolean isEqual(Object a, Object b) {
		return _isEqual(a, b);
	}


	// size()

	public static int size(Object[] arr) {
		return arr.length;
	}

	public static int size(Collection<?> coll) {
		return coll.size();
	}

	public static int size(Map<?, ?> map) {
		return map.size();
	}

	public static int size(JSONObject obj) {
		return obj.length();
	}

	public static int size(JSONArray arr) {
		return arr.length();
	}


	// isEmpty()

	public static boolean isEmpty(Object[] arr) {
		return arr.length == 0;
	}

	public static boolean isEmpty(Collection<?> coll) {
		return coll.size() == 0;
	}

	public static boolean isEmpty(Map<?, ?> map) {
		return map.size() == 0;
	}

	public static boolean isEmpty(JSONObject obj) {
		return obj.length() == 0;
	}

	public static boolean isEmpty(JSONArray arr) {
		return arr.length() == 0;
	}


	public static boolean isArray() { return false; } 
	public static boolean isObject() { return false; }
	public static boolean isString() { return false; }
	public static boolean isNumber() { return false; }
	public static boolean isNaN() { return false; }
	public static boolean isBoolean() { return false; }
	public static boolean isDate() { return false; }
	public static boolean isNull() { return false; }


	public static void times() {}
	public static void pop() {}
	public static void push() {}
	public static void reverse() {}
	public static void shift() {}
	public static void sort() {}
	public static void splice() {}
	public static void unshift() {}


	public static void concat() {}
	public static void join() {}
	public static void slice() {}


	public static void chain() {}
	public static void value() {}
	public static void tap (){}

}