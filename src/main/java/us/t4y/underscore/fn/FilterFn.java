package us.t4y.underscore.fn;

public interface FilterFn<K, V, O> {
	public boolean call(V value, K key, O obj);
}