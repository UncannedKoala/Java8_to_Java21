package java8;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * <ul>
 * <li>A Stream is a sequence of data that can be processed with operations
 * <li>Stream is not a collection of data or a way to organize data, but the
 * flow of data itself.
 * <li>Stream implements AutoClosable.
 * <li>Streams power comes from its capability to perform <i>multiple
 * intermediate</i> operations.
 * <li>A Stream pipeline is a sequence of operations done on a stream of data to
 * produce a result.
 * <li>There are three parts of a Stream pipeline:
 * <ol>
 * <li>Source: Where the data come from.</br>
 * i.e. </br>
 * <ul>
 * <li>{@code default Stream<E> Collection.stream()}, inherited by all the
 * collection interfaces/classes</br>
 * <li>{@code static Stream<T> Arrays.stream(T[] array)},</br>
 * <li>{@code static IntStream Arrays.stream(int[] array)},
 * <li>{@code static LongStream Arrays.stream(long[] array)},
 * <li>{@code static DoubleStream Arrays.stream(double[] array)},
 * <li>{@code static Stream<T> Stream.of(T t)}</br>
 * <li>{@code static Stream<T> Stream.of(T... values)},</br>
 * </ul>
 * <li>Intermediate operations: Operations done on the stream in order to
 * convert it into another Stream. Can be as few or as many as needed.
 * <li>Terminal Operation: Required to start the whole process, and produces the
 * result.
 * </ol>
 * <li>Streams are lazy, meaning operations are not executed until the terminal
 * operation is called. This allows for efficient processing. <br>
 * <br>
 * <b>Streams passes one element of data at a time through the pipeline, this
 * helps in reducing the number of operations whenever the required data is
 * already found (when using a short circuit terminal operation).</b> </br>
 * </br>
 * <b>Collection.stream()</b></br>
 * The Collection interface has a default .stream() which is inherited by all
 * the child interfaces and their implementing classes </br>
 * Note that Map is not included as it is not a part of Collections
 * framework.</br>
 * </br>
 * <b> Arrays.stream() </b> </br>
 * The {@code Arrays.stream()} is overridden to accept primitive int[], long[],
 * double[], or Object[]
 * <ul>
 * <li>{@code Arrays.stream(int[])}
 * <li>{@code Arrays.stream(long[])}
 * <li>{@code Arrays.stream(double[])}
 * <li>{@code Arrays.stream(T[] array)}
 * </ul>
 * For each of the above methods there is an overloaded method that accepts 2
 * additional integers as starting(inclusive) and ending(exclusive) indexes of
 * the array</br>
 * </br>
 * <b>Stream.of() </b> </br>
 * The {@code Stream.of()} is overridden to accept var-args(...), or single
 * argument
 * <ul>
 * <li>{@code Stream.empty()}
 * <li>{@code Stream.of("")}
 * <li>{@code Stream.of(2,3,4,5,6,7,43,3,3,54,5,6)}
 * </ul>
 * <b>Stream.generate()</b> </br>
 * The {@code Stream.generate()} which accepts a {@code Supplier<T>} <br>
 * <br>
 * <b>Stream.iterate()</b> </br>
 * The {@code Stream.iterate()} which accepts a {@code T Seed} and a
 * {@code UnaryOperator<T>} <br>
 * <br>
 * <b>NOTE : A Stream can only be used once. After a terminal operation is
 * performed on a stream,</br>
 * it can not be used again</b>
 * </ul>
 */
public class Streams {
	public static void main(String[] args) {
		streamCreation();
		streamOperation();
		mapToStream();
		readFilesUsingStreams();
		infiniteStreams();
	}

	/*
	 * final variables can not be reassigned but assigned object can be modified
	 */
	public static void streamCreation() {
		/* Using Collection.stream() */
		List<String> list = new LinkedList<>();
		list.add("ten");
		list.add("twenty");
		list.add("thirty");
		list.add("fourty");
		Stream<String> stream1 = list.stream();
		stream1.filter(str -> str.length() < 5).forEach(Streams::log);

		Set<Integer> set = new HashSet<>();
		set.add(5);
		set.add(12);
		set.add(30);
		set.add(45);
		Stream<Integer> stream2 = set.stream();
		stream2.filter(num -> num % 3 == 0 && num % 5 == 0).forEach(Streams::log);

		/* Using Arrays.stream() */
		int[] arr = { 2, 3, 4, 43, 65, 42, 243, 398 };
		IntStream evenIntStream = Arrays.stream(arr);
		evenIntStream.filter(num -> num % 2 == 0).forEach(Streams::log);

		long[] arr1 = { 2, 3, 4, 43, 65, 42, 243, 398 };
		LongStream oddIntStream = Arrays.stream(arr1, 2, 7); // int[], inclusiveStart, exclusiveEnd
		oddIntStream.filter(num -> num % 2 != 0).forEach(Streams::log);

		/* Using Stream.of() */
		Stream.empty(); // empty stream
		Stream.of("Hello"); // single element stream
		Stream.of(2, 4, 7, 43, 2, 2, 3, 4, 6, 89); // var-args of type <T>

		/* Using Stream.generate(Supplier<T>) */
		Stream<Integer> intStream = Stream.generate(() -> {
			return (int) (Math.random() * 10);
		});
		intStream.limit(12).forEach(randomNum -> System.out.println("random num() :" + randomNum));

		/* Using Stream.iterate(T Seed, UnaryOperator<T>) */
		Stream<Integer> intStream1 = Stream.iterate(2, num -> {
			return num + 2;
		});

		intStream1.limit(30).forEach(input -> System.out.println("intStream() : " + input));
	}

	public static void log(Number i) {
		System.out.println(i);
	}

	public static void log(String i) {
		System.out.println(i);
	}

	/**
	 * <li>Stream takes 1st element and passes it through the pipeline,<br>
	 * then takes 2nd and passes it through the pipeline, and this keeps going on.
	 * <li>IntStream is for primitives while Stream<Integers> is for the stream of
	 * Integers wrapper class
	 */
	public static void streamOperation() {
		int[] arr = { 3, 458, 423, 334, 423, 2, 3, 6, 6, 0 };
		IntStream intStream = Arrays.stream(arr);
		intStream.filter(num -> {
			System.out.println("even filter: " + num);
			return num % 2 == 0;
		}).filter(num -> {
			System.out.println(" > 5 filter: " + num);
			return num > 5;
		}).forEach(Streams::log);

		/* peek() to perform non-interfering operations */
		Stream.of("Nick", "Pia", "Roy", "Zain", "Paulo", "Robbie").peek(System.out::println)
				.filter(s -> s.startsWith("P") || s.startsWith("R"))
				.peek(s -> System.out.println("passed starts check: " + s)).filter(s -> s.length() > 3)
				.peek(s -> System.out.println("passed length check: " + s)).limit(1)
				.forEach(s -> System.out.println("Answer :" + s));
	}

	public static void mapToStream() {
//		<name, age>
		Map<String, Integer> map = new HashMap<>();
		map.put("Rena", 21);
		map.put("Sean", 43);
		map.put("Kieth", 19);
		map.put("Laur", 29);

		Map<String, Integer> youngstersMap = map.entrySet().stream()
				.filter(entry -> (entry.getValue() < 30 && entry.getValue() > 20))
				.collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue()));

		System.out.println(youngstersMap);
	}

	public static void readFilesUsingStreams() {
		String path = "C:\\Users\\piyus\\OneDrive\\Desktop\\apple interview prep.txt";
		List<String> lines = new LinkedList<>();
		try {
			Files.lines(Path.of(path)).forEach(lines::add);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("total number of lines :: " + lines.size());
		System.out.println("FILE CONTENTS : ");
		lines.forEach(System.out::println);
	}

	/*
	 * limit() is a stateful intermediate operation that maintains some state to
	 * count the current result size
	 */
	public static void infiniteStreams() {

		/* static Stream<T> generate(Supplier<? extends T>) */
		Stream<Integer> intStream = Stream.generate(() -> {
			return (int) (Math.random() * 10);
		});
		intStream.forEach(System.out::println); // keeps going one unless the supply is cut off

		Stream<Integer> intStream1 = Stream.iterate(2, n -> n + 2);
		intStream1.forEach(System.out::println);
	}
}