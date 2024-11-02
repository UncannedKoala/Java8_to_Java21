package java8to15.java8.intresting;

import java.util.stream.Stream;

public class Fibonacci_tuples {
	public static void main(String[] args) {
		approach1(30);
	}

	public static void approach1(int target) {
		Stream.iterate(new int[] { 0, 1 }, arr -> new int[] { arr[1], arr[0] + arr[1] }).limit(target)
				.forEach(tuple -> System.out.println("[" + tuple[0] + ", " + tuple[1] + "]"));
	}
}
