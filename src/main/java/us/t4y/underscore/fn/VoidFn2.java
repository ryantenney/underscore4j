package us.t4y.underscore.fn;

public abstract class VoidFn2<T1, T2> implements VoidFn3<T1, T2, Object> {

	public abstract void call(T1 arg1, T2 arg2);

	public final void call(T1 arg1, T2 arg2, Object arg3) {
		call(arg1, arg2);
	}

}