package us.t4y.underscore.fn;

public abstract class VoidFn1<T1> implements VoidFn3<T1, Object, Object> {

	public abstract void call(T1 arg1);

	public final void call(T1 arg1, Object arg2, Object arg3) {
		call(arg1);
	}

}