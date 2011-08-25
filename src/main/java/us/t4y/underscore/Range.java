package us.t4y.underscore;

import java.lang.reflect.Array;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;

public abstract class Range<T, S> implements Iterable<T> {

	protected final T start;
	protected final T stop;
	protected final S step;
	protected final boolean inclusive;

	protected transient Class<?> genericType;

	public Range(final T start, final T stop, final S step, final boolean inclusive) {
		super();

		this.start = start;
		this.stop = stop;
		this.step = step;
		this.inclusive = inclusive;
	}

	public Iterator<T> iterator() {
		return RangeIteratorFactory.create(getGenericType(), start, stop, step, inclusive);
	}

	public final T getStart() {
		return start;
	}

	public final T getStop() {
		return stop;
	}

	public final S getStep() {
		return step;
	}

	public final boolean getInclusive() {
		return inclusive;
	}

	public final List<T> asList() {
		List<T> list = new ArrayList<T>();
		Iterator<T> iter = iterator();
		while (iter.hasNext()) {
			list.add(iter.next());
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public final T[] asArray() {
		Collection<T> list = asList();
		return list.toArray((T[]) Array.newInstance(getGenericType(), list.size()));
	}

	public final JSONArray asJSONArray() {
		return new JSONArray(asList());
	}

	private final Class<?> getGenericType() {
		if (genericType == null) {
			genericType = getGenericType(getClass());
		}
		return genericType;
	}

	private static final Class<?> getGenericType(Class<?> clazz) {
		Type superClass = clazz.getGenericSuperclass();
		if (superClass instanceof Class) {
			throw new RuntimeException("Missing type parameter.");
		}
		Type typeArg = ((ParameterizedType) superClass).getActualTypeArguments()[0];
		if (typeArg instanceof Class) {
			return (Class<?>) typeArg;
		}
		return null;
	}

	public static final class RangeFactory {

		@SuppressWarnings("unchecked")
		public static <T, S> Range<T, S> create(T start, T stop, S step, boolean inclusive) {
			if (test(start, stop, step, Integer.class, Integer.class)) {
				return (Range<T, S>) new Range<Integer, Integer>((Integer) start, (Integer) stop, (Integer) step, inclusive) {};
			} else if (test(start, stop, step, Double.class, Double.class)) {
				return (Range<T, S>) new Range<Double, Double>((Double) start, (Double) stop, (Double) step, inclusive) {};
			} else if (test(start, stop, step, Float.class, Float.class)) {
				return (Range<T, S>) new Range<Float, Float>((Float) start, (Float) stop, (Float) step, inclusive) {};
			} else if (test(start, stop, step, Character.class, Integer.class)) {
				return (Range<T, S>) new Range<Character, Integer>((Character) start, (Character) stop, (Integer) step, inclusive) {};
			}
			return null;
		}

		private static <T, S> boolean test(T start, T stop, S step, Class<?> clazzT, Class<?> clazzS) {
			return clazzT.isAssignableFrom(stop.getClass())
					&& (start == null || clazzT.isAssignableFrom(start.getClass()))
					&& (step == null || clazzS.isAssignableFrom(step.getClass()));
		}

	}

	private static final class RangeIteratorFactory {

		@SuppressWarnings("unchecked")
		public static <T, S> Iterator<T> create(Class<?> type, T start, T stop, S step, boolean inclusive) {
			if (type == Integer.class) {
				return (Iterator<T>) new IntegerRangeIterator((Integer) start, (Integer) stop, (Integer) step, inclusive);
			} else if (type == Double.class) {
				return (Iterator<T>) new DoubleRangeIterator((Double) start, (Double) stop, (Double) step, inclusive);
			} else if (type == Float.class) {
				return (Iterator<T>) new FloatRangeIterator((Float) start, (Float) stop, (Float) step, inclusive);
			} else if (type == Character.class) {
				return (Iterator<T>) new CharacterRangeIterator((Character) start, (Character) stop, (Integer) step, inclusive);
			}
			return null;
		}

	}

	public static abstract class AbstractRangeIterator<T, S> implements Iterator<T>, Cloneable {

		private final T start;
		private final T stop;
		private final S step;
		private final boolean inclusive;

		private T current;

		private transient Class<?> type;

		public AbstractRangeIterator(final T start, final T stop, final S step, final boolean inclusive) {
			this.stop = stop;
			this.start = start != null ? start : getDefaultStart();
			this.step = step != null ? step : getDefaultStep(this.start, this.stop);
			this.current = this.start;
			this.inclusive = inclusive;
		}

		public synchronized final T next() {
			try {
				return current;
			} finally {
				current = step(current, step);
			}
		}

		public synchronized boolean hasNext() {
			return hasNext(current, stop, step, inclusive);
		}

		public final void remove() {
			throw new UnsupportedOperationException();
		}

		public final Class<?> forType() {
			if (type == null) {
				type = getGenericType(getClass());
			}
			return type;
		}

		public final void reset() {
			current = start;
		}

		@Override
		public Object clone() throws CloneNotSupportedException {
			return super.clone();
		}

		protected abstract boolean hasNext(T current, T stop, S step, boolean inclusive);

		protected abstract T step(T current, S step);
		protected abstract T getDefaultStart();
		protected abstract S getDefaultStep(T start, T stop);

	}

	private static abstract class AbstractNumericalRangeIterator<T extends Number> extends AbstractRangeIterator<T, T> {

		private transient T zero;

		public AbstractNumericalRangeIterator(final T start, final T stop, final T step, final boolean inclusive) {
			super(start, stop, step, inclusive);
		}

		@Override
		public boolean hasNext(final T current, final T stop, final T step, final boolean inclusive) {
			int compare = compare(current, stop);
			return (inclusive && compare == 0) || compare == compare(zero(), step);
		}

		@Override
		protected T getDefaultStart() {
			return zero();
		}

		@Override
		protected T getDefaultStep(final T start, final T stop) {
			return castNumber(compare(start, stop) < 0 ? 1 : -1);
		}

		private T zero() {
			if (zero == null) {
				zero = castNumber(0);
			}
			return zero;
		}
		@SuppressWarnings({ "unchecked", "rawtypes" })
		private int compare(final T a, final T b) {
			if (a instanceof Comparable) {
				return ((Comparable) a).compareTo(b);
			}
			throw new UnsupportedOperationException("Non-comparable types " + (a == null ? a : a.getClass().getCanonicalName()) + ", " + (b == null ? b : b.getClass().getCanonicalName()));
		}

		private T castNumber(final Number value) {
			return castNumber(value, forType());
		}

		@SuppressWarnings("unchecked")
		private T castNumber(final Number value, final Class<?> toType) {
			if (toType == Byte.class) {
				return (T) Byte.valueOf(value.byteValue());
			} else if (toType == Double.class) {
				return (T) Double.valueOf(value.doubleValue());
			} else if (toType == Integer.class) {
				return (T) Integer.valueOf(value.intValue());
			} else if (toType == Float.class) {
				return (T) Float.valueOf(value.floatValue());
			} else if (toType == Long.class) {
				return (T) Long.valueOf(value.longValue());
			} else if (toType == Short.class) {
				return (T) Short.valueOf(value.shortValue());
			}
			throw new UnsupportedOperationException("Unable to convert to " + toType.getCanonicalName());
		}

	}

	private static class IntegerRangeIterator extends AbstractNumericalRangeIterator<Integer> {

		public IntegerRangeIterator(final Integer start, final Integer stop, final Integer step, final boolean inclusive) {
			super(start, stop, step, inclusive);
		}

		@Override
		protected Integer step(final Integer current, final Integer step) {
			return current + step;
		}

	}

	private static class DoubleRangeIterator extends AbstractNumericalRangeIterator<Double> {

		public DoubleRangeIterator(final Double start, final Double stop, final Double step, final boolean inclusive) {
			super(start, stop, step, inclusive);
		}

		@Override
		protected Double step(final Double current, final Double step) {
			return current + step;
		}

	}

	private static class FloatRangeIterator extends AbstractNumericalRangeIterator<Float> {

		public FloatRangeIterator(final Float start, final Float stop, final Float step, final boolean inclusive) {
			super(start, stop, step, inclusive);
		}

		@Override
		protected Float step(final Float current, final Float step) {
			return current + step;
		}

	}

	private static class CharacterRangeIterator extends AbstractRangeIterator<Character, Integer> {

		public CharacterRangeIterator(final Character start, final Character stop, final Integer step, final boolean inclusive) {
			super(start, stop, step, inclusive);
		}

		@Override
		protected boolean hasNext(final Character current, final Character stop, final Integer step, final boolean inclusive) {
			return (inclusive && current == stop) || current < stop;
		}

		@Override
		protected Character step(final Character current, final Integer step) {
			return Character.valueOf((char)(current.charValue() + step));
		}

		@Override
		protected Character getDefaultStart() {
			return 'a';
		}

		@Override
		protected Integer getDefaultStep(final Character start, final Character stop) {
			return start < stop ? 1 : -1;
		}

	}

}