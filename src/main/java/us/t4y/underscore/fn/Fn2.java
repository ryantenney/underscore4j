package us.t4y.underscore.fn;

public abstract class Fn2<R, T1, T2> implements Fn3<R, T1, T2, Object> {

	public abstract R call(T1 arg1, T2 arg2);

	public final R call(T1 arg1, T2 arg2, Object arg3) {
		return call(arg1, arg2);
	}

}