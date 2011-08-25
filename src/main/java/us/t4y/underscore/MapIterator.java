package us.t4y.underscore;

import java.util.Iterator;
import java.util.Map;

public abstract class MapIterator<K, V> implements Iterator<Map.Entry<K, V>> {

	public Iterator<V> values() {
		return new Iterator<V>() {

			public boolean hasNext() {
				return MapIterator.this.hasNext();
			}

			public V next() {
				return MapIterator.this.next().getValue();
			}

			public void remove() {
				throw new UnsupportedOperationException();
			}

		};
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}	

}