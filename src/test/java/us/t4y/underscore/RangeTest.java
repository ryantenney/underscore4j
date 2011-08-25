package us.t4y.underscore;

import java.util.Arrays;

import org.junit.Test;

public class RangeTest {

	@Test
	public void intRangeTest() {
		try {

			print(_.range(0, 10, 1));
			print(_.reverse(_.range(0, 10, 1).asArray()));
			print(_.range(0, 10, 2));
			print(_.range(0, 10, 5));
			print(_.range(0, 10, 10));
			print(_.range(0, 10));
			print(_.range(10));
			print(_.range(10, 0));
			print(_.range(10, 0, -1));
			print(_.range(10, 0, -2));
			print(_.range(10, 0, -5));
			print(_.range('a', 'z'));
			print(_.range('0', '9'));
			print(_.range(0.0, 10.0, 0.1));
			print(_.range(0.0f, 10.0f, 0.1f));

			print(_.until(0, 10, 1));
			print(_.reverse(_.until(0, 10, 1).asArray()));
			print(_.until(0, 10, 2));
			print(_.until(0, 10, 5));
			print(_.until(0, 10, 10));
			print(_.until(0, 10));
			print(_.until(10));
			print(_.until(10, 0));
			print(_.until(10, 0, -1));
			print(_.until(10, 0, -2));
			print(_.until(10, 0, -5));
			print(_.until('a', 'z'));
			print(_.until('0', '9'));
			print(_.until(0.0, 10.0, 0.1));
			print(_.until(0.0f, 10.0f, 0.1f));

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public void print(Range<?, ?> range) {
		System.out.println("Range: " + range.getStart() + ", " + range.getStop() + ", " + range.getStep());
		print(range.asArray());
	}

	public void print(Object[] arr) {
		System.out.println(Arrays.toString(arr));
	}

}