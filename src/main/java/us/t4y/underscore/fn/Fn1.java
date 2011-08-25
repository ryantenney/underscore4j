package us.t4y.underscore.fn;

public abstract class Fn1<R, T1> implements Fn3<R, T1, Object, Object> {

	public abstract R call(T1 arg1);

	public final R call(T1 arg1, Object arg2, Object arg3) {
		return call(arg1);
	}

}