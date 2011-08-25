package us.t4y.underscore;

import static us.t4y.underscore.UnderscoreImpl.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.hamcrest.Matcher;
import org.json.JSONArray;
import org.json.JSONObject;

import us.t4y.underscore.fn.Fn2;
import us.t4y.underscore.fn.VoidFn1;
import us.t4y.underscore.fn.VoidFn3;
import us.t4y.underscore.fn.Fn3;

public class _ {


	// forEach()

	public static <V> void forEach(V[] arr, VoidFn3<? super V, ? super Integer, ? super V[]> fn) {
		each(arr, fn);
	}

	public static <V, O extends Iterable<V>> void forEach(O obj, VoidFn3<? super V, ? super Integer, ? super O> fn) {
		each(obj, fn);
	}

	public static <V, O extends Collection<V>> void forEach(O coll, VoidFn3<? super V, ? super Integer, ? super O> fn) {
		each(coll, fn);
	}

	public static <K, V, O extends Map<K, V>> void forEach(O map, VoidFn3<? super V, ? super K, ? super O> fn) {
		each(map, fn);
	}

	public static void forEach(JSONArray arr, VoidFn3<Object, ? super Integer, ? super JSONArray> fn) {
		each(arr, fn);
	}

	public static void forEach(JSONObject obj, VoidFn3<Object, ? super String, ? super JSONObject> fn) {
		each(obj, fn);
	}


	// each()

	public static <V, O extends Iterable<V>> void each(O obj, VoidFn3<? super V, ? super Integer, ? super O> fn) {
		_each(obj, obj.iterator(), fn);
	}

	public static <V> void each(V[] arr, VoidFn3<? super V, ? super Integer, ? super V[]> fn) {
		_each(arr, _iterator(arr), fn);
	}

	public static <K, V, O extends Map<K, V>> void each(O map, VoidFn3<? super V, ? super K, ? super O> fn) {
		_each(map, _iterator(map), fn);
	}

	public static void each(JSONArray arr, VoidFn3<Object, ? super Integer, ? super JSONArray> fn) {
		_each(arr, _iterator(arr), fn);
	}

	public static void each(JSONObject obj, VoidFn3<Object, ? super String, ? super JSONObject> fn) {
		_each(obj, _iterator(obj), fn);
	}


	// map()

	public static <K, V, O extends Map<K, V>, R> Map<K, R> map(O map, Fn3<? extends R, ? super V, ? super K, ? super O> fn) {
		return _map(map, fn);
	}

	public static <V, O extends Collection<V>, R> Collection<R> map(O coll, Fn3<? extends R, ? super V, ? super Integer, ? super O> fn) {
		return null;
	}


	// select()

	public static <V> V[] select(V[] arr, Matcher<?> matcher) {
		return filter(arr, matcher);
	}

	public static <V> V[] select(V[] arr, Fn3<Boolean, ? super V, ? super Integer, ? super V[]> fn) {
		return filter(arr, fn);
	}

	public static <V, O extends Collection<V>> O select(O coll, Matcher<?> matcher) {
		return filter(coll, matcher);
	}

	public static <V, O extends Collection<V>> O select(O coll, Fn3<Boolean, ? super V, ? super Integer, ? super O> fn) {
		return filter(coll, fn);
	}

	public static <K, V, O extends Map<K, V>> O select(O map, Matcher<?> matcher) {
		return filter(map, matcher);
	}

	public static <K, V, O extends Map<K, V>> O select(O map, Fn3<Boolean, ? super V, ? super K, ? super O> fn) {
		return filter(map, fn);
	}

	public static JSONArray select(JSONArray arr, Matcher<?> matcher) {
		return filter(arr, matcher);
	}

	public static JSONArray select(JSONArray arr, Fn3<Boolean, Object, ? super Integer, ? super JSONArray> fn) {
		return filter(arr, fn);
	}

	public static JSONObject select(JSONObject obj, Matcher<?> matcher) {
		return filter(obj, matcher);
	}

	public static JSONObject select(JSONObject obj, Fn3<Boolean, Object, ? super String, ? super JSONObject> fn) {
		return filter(obj, fn);
	}


	// filter()

	public static <K, V, O extends Map<K, V>> O filter(O map, Matcher<?> matcher) {
		return filter(map, _matcherAdaptor(matcher));
	}

	public static <K, V, O extends Map<K, V>> O filter(O map, Fn3<Boolean, ? super V, ? super K, ? super O> fn) {
		return _filter(map, fn, true);
	}

	public static <V, O extends Collection<V>> O filter(O coll, Matcher<?> matcher) {
		return filter(coll, _matcherAdaptor(matcher));
	}

	public static <V, O extends Collection<V>> O filter(O coll, Fn3<Boolean, ? super V, ? super Integer, ? super O> fn) {
		return _filter(coll, fn, true);
	}

	public static <V> V[] filter(V[] arr, Matcher<?> matcher) {
		return filter(arr, _matcherAdaptor(matcher));
	}

	public static <V> V[] filter(V[] arr, Fn3<Boolean, ? super V, ? super Integer, ? super V[]> fn) {
		return _filter(arr, fn, true);
	}

	public static JSONArray filter(JSONArray arr, Matcher<?> matcher) {
		return filter(arr, _matcherAdaptor(matcher));
	}

	public static JSONArray filter(JSONArray arr, Fn3<Boolean, Object, ? super Integer, ? super JSONArray> fn) {
		return _filter(arr, fn, true);
	}

	public static JSONObject filter(JSONObject obj, Matcher<?> matcher) {
		return filter(obj, _matcherAdaptor(matcher));
	}

	public static JSONObject filter(JSONObject obj, Fn3<Boolean, Object, ? super String, ? super JSONObject> fn) {
		return _filter(obj, fn, true);
	}


	// reject()

	public static <V> V[] reject(V[] arr, Matcher<?> matcher) {
		return reject(arr, matcher);
	}

	public static <V> V[] reject(V[] arr, Fn3<Boolean, ? super V, ? super Integer, ? super V[]> fn) {
		return _filter(arr, fn, false);
	}

	public static <V, O extends Collection<V>> O reject(O coll, Matcher<?> matcher) {
		return reject(coll, _matcherAdaptor(matcher));
	}

	public static <V, O extends Collection<V>> O reject(O coll, Fn3<Boolean, ? super V, ? super Integer, ? super O> fn) {
		return _filter(coll, fn, false);
	}

	public static <K, V, O extends Map<K, V>> O reject(O map, Matcher<?> matcher) {
		return reject(map, _matcherAdaptor(matcher));
	}

	public static <K, V, O extends Map<K, V>> O reject(O map, Fn3<Boolean, ? super V, ? super K, ? super O> fn) {
		return _filter(map, fn, false);
	}

	public static JSONArray reject(JSONArray arr, Matcher<?> matcher) {
		return reject(arr, _matcherAdaptor(matcher));
	}

	public static JSONArray reject(JSONArray arr, Fn3<Boolean, Object, ? super Integer, ? super JSONArray> fn) {
		return _filter(arr, fn, false);
	}

	public static JSONObject reject(JSONObject obj, Matcher<?> matcher) {
		return reject(obj, _matcherAdaptor(matcher));
	}

	public static JSONObject reject(JSONObject obj, Fn3<Boolean, Object, ? super String, ? super JSONObject> fn) {
		return _filter(obj, fn, false);
	}


	// foldl

	public static <V> V foldl(V[] arr, Fn2<? extends V, ? super V, ? super V> fn) {
		return reduce(arr, null, fn);
	}

	public static <V> V foldl(V[] arr, V memo, Fn2<? extends V, ? super V, ? super V> fn) {
		return reduce(arr, memo, fn);
	}

	public static Object foldl(JSONArray arr, Fn2<? extends Object, Object, Object> fn) {
		return reduce(arr, null, fn);
	}

	public static Object foldl(JSONArray arr, Object memo, Fn2<? extends Object, Object, Object> fn) {
		return reduce(arr, memo, fn);
	}

	public static Object foldl(JSONObject obj, Fn2<? extends Object, Object, Object> fn) {
		return reduce(obj, null, fn);
	}

	public static Object foldl(JSONObject obj, Object memo, Fn2<? extends Object, Object, Object> fn) {
		return reduce(obj, memo, fn);
	}

	public static <K, V> V foldl(Map<K, V> map, Fn2<? extends V, ? super V, ? super V> fn) {
		return reduce(map, null, fn);
	}

	public static <K, V> V foldl(Map<K, V> map, V memo, Fn2<? extends V, ? super V, ? super V> fn) {
		return reduce(map, memo, fn);
	}

	public static <V> V foldl(Iterable<V> coll, Fn2<? extends V, ? super V, ? super V> fn) {
		return reduce(coll, null, fn);
	}

	public static <V> V foldl(Iterable<V> coll, V memo, Fn2<? extends V, ? super V, ? super V> fn) {
		return reduce(coll, memo, fn);
	}


	// reduce()

	public static <V> V reduce(V[] arr, Fn2<? extends V, ? super V, ? super V> fn) {
		return _reduce(_iterator(arr), null, fn);
	}

	public static <V> V reduce(V[] arr, V memo, Fn2<? extends V, ? super V, ? super V> fn) {
		return _reduce(_iterator(arr), memo, fn);
	}

	public static Object reduce(JSONArray arr, Fn2<? extends Object, Object, Object> fn) {
		return _reduce(_iterator(arr), null, fn);
	}

	public static Object reduce(JSONArray arr, Object memo, Fn2<? extends Object, Object, Object> fn) {
		return _reduce(_iterator(arr), memo, fn);
	}

	public static Object reduce(JSONObject obj, Fn2<? extends Object, Object, Object> fn) {
		return _reduce(_iterator(obj).values(), null, fn);
	}

	public static Object reduce(JSONObject obj, Object memo, Fn2<? extends Object, Object, Object> fn) {
		return _reduce(_iterator(obj).values(), memo, fn);
	}

	public static <K, V> V reduce(Map<K, V> map, Fn2<? extends V, ? super V, ? super V> fn) {
		return _reduce(_iterator(map).values(), null, fn);
	}

	public static <K, V> V reduce(Map<K, V> map, V memo, Fn2<? extends V, ? super V, ? super V> fn) {
		return _reduce(_iterator(map).values(), memo, fn);
	}

	public static <V> V reduce(Iterable<V> coll, Fn2<? extends V, ? super V, ? super V> fn) {
		return _reduce(coll.iterator(), null, fn);
	}

	public static <V> V reduce(Iterable<V> coll, V memo, Fn2<? extends V, ? super V, ? super V> fn) {
		return _reduce(coll.iterator(), memo, fn);
	}


	// foldr
	public static void reduceRight() {}


	// detect

	public static <K, V, O extends Map<K, V>> V detect(O map, Matcher<?> matcher) {
		return detect(map, _matcherAdaptor(matcher));
	}

	public static <K, V, O extends Map<K, V>> V detect(O map, Fn3<Boolean, ? super V, ? super K, ? super O> fn) {
		return _detect(map, _iterator(map), fn);
	}

	public static <V, O extends Collection<V>> V detect(O coll, Matcher<?> matcher) {
		return detect(coll, _matcherAdaptor(matcher));
	}

	public static <V, O extends Collection<V>> V detect(O coll, Fn3<Boolean, ? super V, ? super Integer, ? super O> fn) {
		return _detect(coll, _iterator(coll), fn);
	}

	public static <V> V detect(V[] arr, Matcher<?> matcher) {
		return detect(arr, _matcherAdaptor(matcher));
	}

	public static <V> V detect(V[] arr, Fn3<Boolean, ? super V, ? super Integer, ? super V[]> fn) {
		return _detect(arr, _iterator(arr), fn);
	}

	public static Object detect(JSONArray arr, Matcher<?> matcher) {
		return detect(arr, _matcherAdaptor(matcher));
	}

	public static Object detect(JSONArray arr, Fn3<Boolean, Object, ? super Integer, ? super JSONArray> fn) {
		return _detect(arr, _iterator(arr), fn);
	}

	public static Object detect(JSONObject obj, Matcher<?> matcher) {
		return detect(obj, _matcherAdaptor(matcher));
	}

	public static Object detect(JSONObject obj, Fn3<Boolean, Object, ? super String, ? super JSONObject> fn) {
		return _detect(obj, _iterator(obj), fn);
	}


	// detect

	public static <K, V, O extends Map<K, V>> V find(O map, Matcher<?> matcher) {
		return find(map, _matcherAdaptor(matcher));
	}

	public static <K, V, O extends Map<K, V>> V find(O map, Fn3<Boolean, ? super V, ? super K, ? super O> fn) {
		return _detect(map, _iterator(map), fn);
	}

	public static <V, O extends Collection<V>> V find(O coll, Matcher<?> matcher) {
		return find(coll, _matcherAdaptor(matcher));
	}

	public static <V, O extends Collection<V>> V find(O coll, Fn3<Boolean, ? super V, ? super Integer, ? super O> fn) {
		return _detect(coll, _iterator(coll), fn);
	}

	public static <V> V find(V[] arr, Matcher<?> matcher) {
		return find(arr, _matcherAdaptor(matcher));
	}

	public static <V> V find(V[] arr, Fn3<Boolean, ? super V, ? super Integer, ? super V[]> fn) {
		return _detect(arr, _iterator(arr), fn);
	}

	public static Object find(JSONArray arr, Matcher<?> matcher) {
		return find(arr, _matcherAdaptor(matcher));
	}

	public static Object find(JSONArray arr, Fn3<Boolean, Object, ? super Integer, ? super JSONArray> fn) {
		return _detect(arr, _iterator(arr), fn);
	}

	public static Object find(JSONObject obj, Matcher<?> matcher) {
		return find(obj, _matcherAdaptor(matcher));
	}

	public static Object find(JSONObject obj, Fn3<Boolean, Object, ? super String, ? super JSONObject> fn) {
		return _detect(obj, _iterator(obj), fn);
	}


	// contains
	public static void include() {}


	// some()

	public static <V> boolean some(V[] arr, Matcher<?> matcher) {
		return any(arr, matcher);
	}

	public static <V> boolean some(V[] arr, Fn3<Boolean, ? super V, ? super Integer, ? super V[]> fn) {
		return _anyAll(arr, _iterator(arr), fn, true);
	}

	public static <V, O extends Collection<V>> boolean some(O coll, Matcher<?> matcher) {
		return any(coll, _matcherAdaptor(matcher));
	}

	public static <V, O extends Collection<V>> boolean some(O coll, Fn3<Boolean, ? super V, ? super Integer, ? super O> fn) {
		return _anyAll(coll, _iterator(coll), fn, true);
	}

	public static <K, V, O extends Map<K, V>> boolean some(O map, Matcher<?> matcher) {
		return any(map, _matcherAdaptor(matcher));
	}

	public static <K, V, O extends Map<K, V>> boolean some(O map, Fn3<Boolean, ? super V, ? super K, ? super O> fn) {
		return _anyAll(map, _iterator(map), fn, true);
	}

	public static boolean some(JSONArray arr, Matcher<?> matcher) {
		return any(arr, _matcherAdaptor(matcher));
	}

	public static boolean some(JSONArray arr, Fn3<Boolean, Object, ? super Integer, ? super JSONArray> fn) {
		return _anyAll(arr, _iterator(arr), fn, true);
	}

	public static boolean some(JSONObject obj, Matcher<?> matcher) {
		return any(obj, _matcherAdaptor(matcher));
	}

	public static boolean some(JSONObject obj, Fn3<Boolean, Object, ? super String, ? super JSONObject> fn) {
		return _anyAll(obj, _iterator(obj), fn, true);
	}


	// any()

	public static <V> boolean any(V[] arr, Matcher<?> matcher) {
		return any(arr, matcher);
	}

	public static <V> boolean any(V[] arr, Fn3<Boolean, ? super V, ? super Integer, ? super V[]> fn) {
		return _anyAll(arr, _iterator(arr), fn, true);
	}

	public static <V, O extends Collection<V>> boolean any(O coll, Matcher<?> matcher) {
		return any(coll, _matcherAdaptor(matcher));
	}

	public static <V, O extends Collection<V>> boolean any(O coll, Fn3<Boolean, ? super V, ? super Integer, ? super O> fn) {
		return _anyAll(coll, _iterator(coll), fn, true);
	}

	public static <K, V, O extends Map<K, V>> boolean any(O map, Matcher<?> matcher) {
		return any(map, _matcherAdaptor(matcher));
	}

	public static <K, V, O extends Map<K, V>> boolean any(O map, Fn3<Boolean, ? super V, ? super K, ? super O> fn) {
		return _anyAll(map, _iterator(map), fn, true);
	}

	public static boolean any(JSONArray arr, Matcher<?> matcher) {
		return any(arr, _matcherAdaptor(matcher));
	}

	public static boolean any(JSONArray arr, Fn3<Boolean, Object, ? super Integer, ? super JSONArray> fn) {
		return _anyAll(arr, _iterator(arr), fn, true);
	}

	public static boolean any(JSONObject obj, Matcher<?> matcher) {
		return any(obj, _matcherAdaptor(matcher));
	}

	public static boolean any(JSONObject obj, Fn3<Boolean, Object, ? super String, ? super JSONObject> fn) {
		return _anyAll(obj, _iterator(obj), fn, true);
	}


	// every()

	public static <V> boolean every(V[] arr, Matcher<?> matcher) {
		return all(arr, matcher);
	}

	public static <V> boolean every(V[] arr, Fn3<Boolean, ? super V, ? super Integer, ? super V[]> fn) {
		return _anyAll(arr, _iterator(arr), fn, false);
	}

	public static <V, O extends Collection<V>> boolean every(O coll, Matcher<?> matcher) {
		return all(coll, _matcherAdaptor(matcher));
	}

	public static <V, O extends Collection<V>> boolean every(O coll, Fn3<Boolean, ? super V, ? super Integer, ? super O> fn) {
		return _anyAll(coll, _iterator(coll), fn, false);
	}

	public static <K, V, O extends Map<K, V>> boolean every(O map, Matcher<?> matcher) {
		return all(map, _matcherAdaptor(matcher));
	}

	public static <K, V, O extends Map<K, V>> boolean every(O map, Fn3<Boolean, ? super V, ? super K, ? super O> fn) {
		return _anyAll(map, _iterator(map), fn, false);
	}

	public static boolean every(JSONArray arr, Matcher<?> matcher) {
		return all(arr, _matcherAdaptor(matcher));
	}

	public static boolean every(JSONArray arr, Fn3<Boolean, Object, ? super Integer, ? super JSONArray> fn) {
		return _anyAll(arr, _iterator(arr), fn, false);
	}

	public static boolean every(JSONObject obj, Matcher<?> matcher) {
		return all(obj, _matcherAdaptor(matcher));
	}

	public static boolean every(JSONObject obj, Fn3<Boolean, Object, ? super String, ? super JSONObject> fn) {
		return _anyAll(obj, _iterator(obj), fn, false);
	}


	// all()

	public static <V> boolean all(V[] arr, Matcher<?> matcher) {
		return all(arr, matcher);
	}

	public static <V> boolean all(V[] arr, Fn3<Boolean, ? super V, ? super Integer, ? super V[]> fn) {
		return _anyAll(arr, _iterator(arr), fn, false);
	}

	public static <V, O extends Collection<V>> boolean all(O coll, Matcher<?> matcher) {
		return all(coll, _matcherAdaptor(matcher));
	}

	public static <V, O extends Collection<V>> boolean all(O coll, Fn3<Boolean, ? super V, ? super Integer, ? super O> fn) {
		return _anyAll(coll, _iterator(coll), fn, false);
	}

	public static <K, V, O extends Map<K, V>> boolean all(O map, Matcher<?> matcher) {
		return all(map, _matcherAdaptor(matcher));
	}

	public static <K, V, O extends Map<K, V>> boolean all(O map, Fn3<Boolean, ? super V, ? super K, ? super O> fn) {
		return _anyAll(map, _iterator(map), fn, false);
	}

	public static boolean all(JSONArray arr, Matcher<?> matcher) {
		return all(arr, _matcherAdaptor(matcher));
	}

	public static boolean all(JSONArray arr, Fn3<Boolean, Object, ? super Integer, ? super JSONArray> fn) {
		return _anyAll(arr, _iterator(arr), fn, false);
	}

	public static boolean all(JSONObject obj, Matcher<?> matcher) {
		return all(obj, _matcherAdaptor(matcher));
	}

	public static boolean all(JSONObject obj, Fn3<Boolean, Object, ? super String, ? super JSONObject> fn) {
		return _anyAll(obj, _iterator(obj), fn, false);
	}


	// invoke()

	public static <R> Collection<R> invoke(Collection<?> coll, String name, Class<R> clazz, Object... args) {
		return _invoke(coll, name, clazz, args);
	}


	// pluck()

	public static Collection<Object> pluck(Object[] arr, String name) {
		return pluck(arr, name, Object.class);
	}

	public static Collection<Object> pluck(Collection<?> coll, String name) {
		return pluck(coll, name, Object.class);
	}

	public static <T> Collection<T> pluck(Object[] arr, String name, Class<T> clazz) {
		return _pluck(Arrays.asList(arr), name, clazz);
	}

	public static <T> Collection<T> pluck(Collection<?> coll, String name, Class<T> clazz) {
		return _pluck(coll, name, clazz);
	}

	public static <T> Collection<Object> pluck(Map<?, Map<? super String, T>> map, String name) {
		//return _pluck(map, name);
		return null;
	}

	public static JSONArray pluck(JSONArray arr, String name) {
		//return _pluck(arr, name);
		return null;
	}

	public static JSONArray pluck(JSONObject obj, String name) {
		//return _pluck(obj., name);
		return null;
	}


	// max()

	public static <V> V max(V[] arr, Fn2<? extends V, ? super V, ? super V> fn) {
		return _minMax(_iterator(arr), fn, true);
	}

	public static <V> V max(V[] arr, Comparator<? super V> cmp) {
		return _minMax(_iterator(arr), cmp, true);
	}

	public static <V extends Comparable<V>> V max(V[] arr) {
		return _minMax(_iterator(arr), true);
	}

	public static Object max(JSONArray arr, Fn2<? extends Object, Object, Object> fn) {
		return _minMax(_iterator(arr), fn, true);
	}

	public static Object max(JSONArray arr, Comparator<Object> cmp) {
		return _minMax(_iterator(arr), cmp, true);
	}

	public static Object max(JSONObject obj, Fn2<? extends Object, Object, Object> fn) {
		return _minMax(_iterator(obj).values(), fn, true);
	}

	public static Object max(JSONObject obj, Comparator<Object> cmp) {
		return _minMax(_iterator(obj), cmp, true);
	}

	public static <K, V> V max(Map<K, V> map, Fn2<? extends V, ? super V, ? super V> fn) {
		return _minMax(_iterator(map).values(), fn, true);
	}

	public static <K, V> V max(Map<K, V> map, Comparator<? super V> cmp) {
		return _minMax(_iterator(map).values(), cmp, true);
	}

	public static <K, V extends Comparable<V>> V max(Map<K, V> map) {
		return _minMax(_iterator(map).values(), true);
	}

	public static <V> V max(Iterable<V> coll, Fn2<? extends V, ? super V, ? super V> fn) {
		return _minMax(coll.iterator(), fn, true);
	}

	public static <V> V max(Iterable<V> coll, Comparator<? super V> cmp) {
		return _minMax(coll.iterator(), cmp, true);
	}

	public static <V extends Comparable<V>> V max(Iterable<V> coll) {
		return _minMax(coll.iterator(), true);
	}


	// min()

	public static <V> V min(V[] arr, Fn2<? extends V, ? super V, ? super V> fn) {
		return _minMax(_iterator(arr), fn, false);
	}

	public static <V> V min(V[] arr, Comparator<? super V> cmp) {
		return _minMax(_iterator(arr), cmp, false);
	}

	public static <V extends Comparable<V>> V min(V[] arr) {
		return _minMax(_iterator(arr), false);
	}

	public static Object min(JSONArray arr, Fn2<? extends Object, Object, Object> fn) {
		return _minMax(_iterator(arr), fn, false);
	}

	public static Object min(JSONArray arr, Comparator<Object> cmp) {
		return _minMax(_iterator(arr), cmp, false);
	}

	public static Object min(JSONObject obj, Fn2<? extends Object, Object, Object> fn) {
		return _minMax(_iterator(obj).values(), fn, false);
	}

	public static Object min(JSONObject obj, Comparator<Object> cmp) {
		return _minMax(_iterator(obj).values(), cmp, false);
	}

	public static <K, V> V min(Map<K, V> map, Fn2<? extends V, ? super V, ? super V> fn) {
		return _minMax(_iterator(map).values(), fn, false);
	}

	public static <K, V> V min(Map<K, V> map, Comparator<? super V> cmp) {
		return _minMax(_iterator(map).values(), cmp, false);
	}

	public static <K, V extends Comparable<V>> V min(Map<K, V> map) {
		return _minMax(_iterator(map).values(), false);
	}

	public static <V> V min(Iterable<V> coll, Fn2<? extends V, ? super V, ? super V> fn) {
		return _minMax(coll.iterator(), fn, false);
	}

	public static <V> V min(Iterable<V> coll, Comparator<? super V> cmp) {
		return _minMax(coll.iterator(), cmp, false);
	}

	public static <V extends Comparable<V>> V min(Iterable<V> coll) {
		return _minMax(coll.iterator(), false);
	}


	// sortBy()

	public static <V> V[] sortBy(V[] arr, Fn3<? extends Comparable<?>, ? super V, ? super Integer, ? super V[]> fn) {
		//return _sortBy(arr);
		return null;
	}

	public static <V, O extends Collection<V>> O sortBy(O arr, Fn3<? extends Comparable<?>, ? super V, ? super Integer, ? super O> fn) {
		//return _sortBy(list);
		return null;
	}

	public static JSONArray sortBy(JSONArray arr, Fn3<? extends Comparable<?>, Object, ? super Integer, ? super JSONArray> fn) {
		//return _sortBy(arr);
		return null;
	}


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
	public static void memoize() {}
	public static void keys() {}


	// values()

	public static <T> Collection<T> values(Map<?, T> map) {
		return map.values();
	}


	// range()

	public static <T> Range<T, ?> range(T stop) {
		return _range(null, stop, null, true);
	}

	public static <T> Range<T, ?> range(T start, T stop) {
		return _range(start, stop, null, true);
	}

	public static <T, S> Range<T, S> range(T start, T stop, S step) {
		return _range(start, stop, step, true);
	}


	// until()

	public static <T> Range<T, ?> until(T stop) {
		return _range(null, stop, null, false);
	}

	public static <T> Range<T, ?> until(T start, T stop) {
		return _range(start, stop, null, false);
	}

	public static <T, S> Range<T, S> until(T start, T stop, S step) {
		return _range(start, stop, step, false);
	}


	// methods()

	public static Collection<String> methods(Object obj) {
		return _functions(obj.getClass());
	}

	public static Collection<String> methods(Class<?> clazz) {
		return _functions(clazz);
	}

	public static Collection<String> functions(Object obj) {
		return _functions(obj.getClass());
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


//	public static boolean isArray() { return false; } 
//	public static boolean isObject() { return false; }
//	public static boolean isString() { return false; }
//	public static boolean isNumber() { return false; }
//	public static boolean isNaN() { return false; }
//	public static boolean isBoolean() { return false; }
//	public static boolean isDate() { return false; }
//	public static boolean isNull() { return false; }


	// times()

	public static void times(int times, VoidFn1<Integer> fn) {
		_times(times, fn);
	}


	public static void pop() {
		
	}

	public static void push() {}


	// reverse()

	public static <T> T[] reverse(T[] arr) {
		return _reverse(arr);
	}

	public static <T> List<T> reverse(List<T> list) {
		return _reverse(list);
	}

	public static JSONArray reverse(JSONArray arr) {
		return _reverse(arr);
	}


	// sort()

	public static <T> T[] sort(T[] arr) {
		//return _sort(arr);
		return null;
	}

	public static <T> List<T> sort(List<T> list) {
		//return _sort(list);
		return null;
	}

	public static JSONArray sort(JSONArray arr) {
		//return _sort(arr);
		return null;
	}


	public static void shift() {}
	public static void splice() {}
	public static void unshift() {}


	public static void concat() {}
	public static void join() {}
	public static void slice() {}


	public static void chain() {}
	public static void value() {}
	public static void tap (){}


}