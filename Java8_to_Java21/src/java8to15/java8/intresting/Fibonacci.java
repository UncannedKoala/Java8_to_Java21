package java8to15.java8.intresting;

import java.util.stream.Stream;

public class Fibonacci {
	public static void main(String[] args) {
//		fabinacciTuples(30);
		fabinacciSeries(30);		
	}

	public static void fabinacciTuples(int target) {
		Stream.iterate(new int[] { 0, 1 }, arr -> new int[] { arr[1], arr[0] + arr[1] }).limit(target)
				.forEach(tuple -> System.out.println("[" + tuple[0] + ", " + tuple[1] + "]"));
	}

	public static void fabinacciSeries(int target) {
		Stream.iterate(new int[] { 0, 1 }, arr -> new int[] { arr[1], arr[0] + arr[1] }).limit(target)
		.forEach(tuple -> System.out.print(tuple[0]+", "));
	}
}
