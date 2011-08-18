package us.t4y.underscore.fn;

public interface EachFn<K, V, O> {
	public void call(V value, K key, O obj);
}