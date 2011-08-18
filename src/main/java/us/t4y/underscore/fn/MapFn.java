package us.t4y.underscore.fn;

public interface MapFn<K, V, O, R> {
	public R call(V value, K key, O obj);
}