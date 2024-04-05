package java8;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import java8.utility.Person;

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
//		streamCreation();
//		streamOperation();
//		mapToStream();
//		readFilesUsingStreams();
//		infiniteStreams();

//		 terminalOperations();
//		 collect_TerminalOperation();

		intermediateOperators();

//		 snippets();
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

		/* Creating int stream from 'm' to 'n' */
		IntStream intStream2 = IntStream.range(1, 5); // inclusive,exclusive
		System.out.println("***intStream2***");
		intStream2.forEach(Streams::log); // 1 2 3 4

		IntStream intStream3 = IntStream.rangeClosed(7, 10); // inclusive,inclusive
		System.out.println("***intStream3***");
		intStream3.forEach(Streams::log); // 7 8 9 10

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
		// <name, age>
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

	/**
	 * <li>Terminal operations can be performed without any intermediate operations
	 * but not the other way around <br>
	 * <li><b>Reduction operations </b>are special terminal operations that reduce
	 * the entire stream to a single value or object <br>
	 * <ol>
	 * <li>{@code long count()}<br>
	 * <li>{@code Optional<T> min()}<br>
	 * <li>{@code Optional<T> max()}<br>
	 * <li>{@code reduce()}<br>
	 * <li>{@code collect()}
	 * </ol>
	 * <li>{@code min()} and {@code max()} return {@code Optional<T>} as the stream
	 * might be empty at the time of operation <br>
	 * <li>Following aren't considered as <b>short-circuitingterminal operation</b>
	 * as they might not need to iterate over the entire stream.
	 * <ol>
	 * <li>{@code boolean allMatch()} ,
	 * <li>{@code boolean anyMatch()} ,
	 * <li>{@code boolean noneMatch()} ,
	 * <li>{@code Optional<T> findFirst()}
	 * <li>{@code Optional<T> findAny()}
	 * </ol>
	 * <br>
	 * <li><b> {@code void forEach()} is not considered as reduction because it
	 * returns void, and is used when you want something to happen inside the
	 * Consumer (as a <i>side-effect</i>).</b> The Iterable interface also has a
	 * default forEach() that accepts a Consumer.</li>
	 * <li>Following snippet can create a mess as it does not stop at 100000 but it
	 * keeps going on and once the value over flows it reaches -ve and then it
	 * starts to print again<br>
	 * {@code Stream.iterate(0, n -> n + 7).filter(num -> num <
	 * 100000).forEach(System.out::println);}</li>
	 * <li>As illustrated follows Streams can not be used in for each loop because
	 * Stream does not implement Iterable.
	 * {@code Stream<Integer> stream2 = Stream.iterate(0, n -> n + 7); for(int num :
	 * stream2) {})</li>
	 * <li><b>reduce() is over ridden</b>
	 * <ol>
	 * <li><u>Optional<T> reduce(BinaryOperator<T> accumulator)</u>
	 * <li><u>T reduce(T identity, BinaryOperator<T> accumulator)</u>: is used when
	 * there is some operation to be done on the stream while reducing</br>
	 * <li><u><M> M reduce(M identity, BiFunction<M, ? super T, M> accumulator,
	 * BinaryOperator<M> combiner)</u> : This is used when the return type of the
	 * reduction is not same as stream type. Combiner is only used for parallel
	 * processing</li>
	 * </ol>
	 * 
	 * <strong>If your reduce operation involves adding elements to a collection,
	 * then every time your accumulator function processes an element, it creates a
	 * new collection that includes the element, which is inefficient. It would be
	 * more efficient for you to update an existing collection instead.</strong>
	 */
	public static void terminalOperations() {
		int[] arr = { 3, -2, 34, 40, 12, 1, 2, 2, 432, -53, 65, -46, 4 };
		List<String> animals = new LinkedList<>();
		animals.add("Lion");
		animals.add("ReinDeer");
		animals.add("Yak");
		animals.add("Tiger");

		/* .max() */
		System.out.println("largest: " + Arrays.stream(arr).max().getAsInt()); // 432
		System.out.println("largest -ve: " + Arrays.stream(arr).filter(num -> num <= 0).max().getAsInt()); // -2

		Optional<String> longestAnimalName = animals.stream().max((animal1,
				animal2) -> (animal1.length() > animal2.length()) ? 1 : (animal1.length() < animal2.length()) ? -1 : 0);

		if (longestAnimalName.isPresent())
			System.out.println("longest animal name: " + longestAnimalName.get()); // ReinDeer

		else
			System.out.println("longest animal name: NOT FOUND!!!");

		/* .min() */
		System.out.println("smallest: " + Arrays.stream(arr).min().getAsInt()); // -53
		System.out.println("smallest +ve: " + Arrays.stream(arr).filter(num -> num >= 0).min().getAsInt()); // 1

		Optional<String> shortestAnimalName = animals.stream().min((animal1,
				animal2) -> (animal1.length() > animal2.length()) ? 1 : (animal1.length() < animal2.length()) ? -1 : 0);

		if (shortestAnimalName.isPresent())
			System.out.println("sortest animal name: " + shortestAnimalName.get()); // Yak
		else
			System.out.println("sortest animal name: NOT FOUND!!!");

		/* .count() */
		long allTwoDigitNumberCount = Arrays.stream(arr).filter(number -> number > 9 || number < -9).count();
		long possitiveTwoDigitNumberCount = Arrays.stream(arr).filter(number -> number > 9).count();
		long smallNameAnimals = animals.stream().filter(animal -> animal.length() < 7).count();
		System.out.println("Number of 2 digit numbers: " + allTwoDigitNumberCount); // 7
		System.out.println("Number of possitive 2 digit numbers: " + possitiveTwoDigitNumberCount); // 5
		System.out.println("Number of animals names with less than 7 alaphabets: " + smallNameAnimals);// 3

		/* .findAny() */
		Optional<String> any = animals.stream().findAny();
		any.ifPresent(System.out::println); // Lion (usually)

		/* .findFirst() */
		Optional<String> first = animals.stream().filter(name -> name.length() > 4).findFirst();
		first.ifPresent(System.out::println); // ReinDeer

		Predicate<String> startsWithR = name -> name.startsWith("R");
		System.out.println(animals.stream().anyMatch(startsWithR)); // true (contains 'ReinDeer')
		System.out.println(animals.stream().allMatch(startsWithR)); // false ('Lion', 'Yak', 'Tiger' do not)
		System.out.println(animals.stream().noneMatch(startsWithR)); // false ('ReinDeer' starts with 'R')

		/* .forEach() */
		Stream<Integer> stream = Stream.iterate(0, n -> n + 7);
		stream.limit(100000).forEach(System.out::println);

		/*
		 * .reduce() :: T reduce(T identity, BinaryOperator<T> accumulator)
		 * 
		 * takes in 'identity' as the initial value of the reduction, and the same will
		 * be returned if the stream is empty
		 */
		int limit = 10;
		int from = 1;
		Stream<Integer> numStream = Stream.iterate(from, num -> num + 1);
		long nEvenSum = numStream.filter(num -> num % 2 == 0).limit(limit).reduce(0, (m, n) -> m + n);
		System.out.println(nEvenSum);

		Stream<String> strStream = Stream.of("a", "b", "o", "d", "e");
		String finalResult = strStream.reduce("final value : ",
				(initialValue, currentValue) -> initialValue + currentValue);
		System.out.println(finalResult);

		/*
		 * .reduce() :: Optional<T> reduce(BinaryOperator<T> accumulator). Whenever used
		 * without 'identity' the return will be an Optional<T>
		 */
		Stream<String> strStream1 = Stream.of("a", "b", "o", "d", "e");
		Stream<String> strStream2 = Stream.of("a", "7", "o", "d", "e");

		Optional<String> result1, result2 = null;
		Predicate<String> isDigitFilter = str -> Character.isDigit(str.charAt(0));
		BinaryOperator<String> strAccumulator = (str1, str2) -> str1 + str2;
		Supplier<Optional<String>> notFoundSupplier = () -> Optional.of("not found!!!");

		result1 = strStream1.filter(isDigitFilter).reduce(strAccumulator);
		result2 = strStream2.filter(isDigitFilter).reduce(strAccumulator);

		System.out.println(result1.or(notFoundSupplier).get()); // not found!!!
		System.out.println(result2.or(notFoundSupplier).get()); // 7

		/*
		 * .reduce() :: <U> U reduce(U identity, BiFunction<U, ? super T, U>
		 * accumulator, BinaryOperator<U> combiner).
		 * 
		 * It processes in parallel using threads. And is not restricted to sequentially
		 * execution.
		 * 
		 * IDENTITY: The identity element is both the initial value of the reduction and
		 * the default result if there are no elements in the stream.
		 * 
		 * ACCUMULATOR: The accumulator function takes two parameters: a partial result
		 * of the reduction and the next element of the stream. Used for synchronous
		 * operations
		 * 
		 * COMBINER: for combining two values(identity, accumulator returned value),
		 * When the reduction is performed in parallel, this function combines the
		 * results of two parallel reductions by adding em together.
		 * 
		 * Since the stream is not processed in parallel, the combiner is not being used
		 * here. A combiner must be compatible with the accumulator functions return
		 * type.
		 */
		Stream<String> stringStream = Stream.of("auto", "bus", "ola", "ducati", "electric");
		Integer totalLength = stringStream.reduce(0, (n, str) -> {
			System.out.println(str);
			return n + str.length();
		}, (n1, n2) -> {
			System.out.println("n1: " + n1 + " n2: " + n2);
			return n1 + n2;
		});
		System.out.println(totalLength);

	}

	/**
	 * <b>syntax:
	 * <ul>
	 * <li>
	 * {@code <R> R collect(Supplier<R> supplier, BiConsumer<R, ? super T> accumulator,
	 * BiConsumer<R, R> combiner)}</li>
	 * <li>{@code <R, A> R collect(Collector<? super T, A, R> collector)}</li> </b>
	 * </ul>
	 * <li>.collect() is a special type of reduction called as <b><i>mutable
	 * reduction</i></b> where we accumulate the data in same mutable object during
	 * 'accumulation'.</li>
	 * <li>It allows us to get data out of streams in other forms, List, Set,
	 * Map</li>
	 * <li>There are two ways to collect data from Streams using .collect()</li>
	 * <li>Combiner or accumulator may entirely skipped for optimizations</li>
	 * <li><u>For parallel streams, a <b>identity</b> is made for each element and
	 * current current value is appended into that StringBuilder. Only after all the
	 * elements are processed by the <b>accumulator</b>, then the <b>identities</b>
	 * are processed by <b>combiner</b> in pair one after the other</u></li>
	 * 
	 * <li></li>
	 */
	public static void collect_TerminalOperation() {

		/**
		 * for the below in single threaded situation, a single 'StringBuider' is used
		 * and the 'accumulator' iterates over all the elements and appends them to the
		 * same 'StringBuiler' object thus there is not need to use the 'combiner'.<br>
		 * sb: "" str: "Mc" <br>
		 * sb: "Mc" str: "." <br>
		 * sb: "Mc." str: "Donald" <br>
		 * sb: "Mc.Donald" str: "'s" <br>
		 * Mc.Donald's <br>
		 */
		/*
		 * ###########################single-threaded stream###########################
		 */
		StringBuilder sBuilder1 = Stream.of("Mc", ".", "Donald", "'s").collect(() -> new StringBuilder() // supplier
				, (sb, str) -> { // accumulator
					System.out.println("sb: \"" + sb + "\" str: \"" + str + "\"");
					sb.append(str);
				}, (sb1, sb2) -> { // combiner
					System.out.println("sb1: \"" + sb1 + "\" sb2: \"" + sb2 + "\"");
					sb1.append(sb2);
				});
		System.out.println("Using single-threaded stream: " + sBuilder1);

		/**
		 * while in multi-threaded situations, there is a new 'StringBuilder' created
		 * per elements, the 'accumulator' appends the current element to the newly
		 * created StringBuilder object. Once 'accumulator' is done with all the
		 * elements, these accumulated 'StringBuilder' objects are used by the
		 * 'combiner' two at a time in parallel to append them. At last, when all the
		 * 'StringBuilder's are combined as a single SingleBuilder it is returned by the
		 * 'combiner'. <br>
		 * sb: "" str: "F" sb: "" str: "." <br>
		 * sb: "" str: "." <br>
		 * sb: "" str: "C" <br>
		 * sb: "" str: "K" <br>
		 * sb1: "K" sb2: "." <br>
		 * sb1: "." sb2: "C" <br>
		 * sb1: "F" sb2: ".C" <br>
		 * sb1: "K." sb2: "F.C" <br>
		 * Mc.Donald's <br>
		 */
		/*
		 * #########################multi-threaded stream#########################
		 */
		StringBuilder sBuilder2 = Stream.of("K", ".", "F", ".", "C").parallel().collect(() -> new StringBuilder() // supplier
				, (sb, str) -> { // accumulator
					System.out.println("sb: \"" + sb + "\" str: \"" + str + "\"");
					sb.append(str);
				}, (sb1, sb2) -> { // combiner
					System.out.println("sb1: \"" + sb1 + "\" sb2: \"" + sb2 + "\"");
					sb1.append(sb2);
				});
		System.out.println("Using multi-threaded streams: " + sBuilder2);

		/*
		 * ############.collect() using Collectors interface static methods############
		 */

		List<String> sweetList = List.of("cake", "pastry", "pies", "tarts", "wafer", "timbale");
		List<Integer> intList = List.of(10, 20, 30, 40, 50, 60, 70, 80, 90);
		List<Integer> randomIntList = List.of(10, 20, 80, 40, 20, 60, 40, 80, 10);
		List<Long> longList = List.of(100l, 200l, 300l, 400l, 500l, 600l, 700l, 800l, 900l);
		List<Double> doubleList = List.of(10.3, 19.7, 30.2, 39.8, 50.1, 60.0, 69.9, 80.0, 90.0);

		/*
		 * ##########################Collectors.joining()##########################
		 */
		String sweetTooth1 = sweetList.stream().collect(Collectors.joining());
		System.out.println(sweetTooth1); // cakepastrypiestartswafertimbale

		/*
		 * #####################Collectors.joining(delimiter)#####################
		 */
		String sweetTooth2 = sweetList.stream().collect(Collectors.joining(" -> "));
		System.out.println(sweetTooth2); // cake -> pastry -> pies -> tarts -> wafer -> timbale

		/*
		 * #############Collectors.joining(delimiter, prefix, suffix)#############
		 */
		String sweetTooth3 = sweetList.stream().collect(Collectors.joining(", ", ">>", "<<"));
		System.out.println(sweetTooth3); // >>cake, pastry, pies, tarts, wafer, timbale<<

		/*
		 * ###################<Double> Collectors.averagingInt()###################
		 */
		Double averageLength = sweetList.stream().collect(Collectors.averagingInt(name -> name.length()));
		System.out.println("averageLength of sweetList: " + averageLength); // 5.166666666666667

		/*
		 * ##################<Integer> Collectors.averagingInt()##################
		 */
		Double averageInt = intList.stream().collect(Collectors.averagingInt(num -> num));
		System.out.println("averageValue of intList: " + averageInt); // 50.0

		/*
		 * ####################<Long> Collectors.averagingLong()###################
		 */
		Double averageLong = longList.stream().collect(Collectors.averagingLong(num -> num));
		System.out.println("averageValue of longList: " + averageLong); // 500.0

		/*
		 * #################<Double> Collectors.averagingDouble()#################
		 */
		Double averageDouble = doubleList.stream().collect(Collectors.averagingDouble(num -> num));
		System.out.println("averageValue of doubleList: " + averageDouble); // 50.0

		/*
		 * ###########################Collectors.toMap()##########################
		 */

		/**
		 * Collectors.toMap( <br>
		 * Function<? super T, ? extends K> keyMapper, <br>
		 * Function<? super T, ? extends U> valueMapper <br>
		 * )
		 * 
		 * NOTE:: The returned map can be of any type unless specified using
		 * mapFactory(example given in later code)
		 */
		Map<String, Integer> sweetWithLength = sweetList.stream()
				.collect(Collectors.toMap(str -> str, str -> str.length()));
		System.out.println(sweetWithLength); // {pastry=6, pies=4, wafer=5, tarts=5, cake=4, timbale=7}

		/**
		 * Collectors.toMap( <br>
		 * Function<? super T, ? extends K> keyMapper, <br>
		 * Function<? super T, ? extends U> valueMapper, <br>
		 * BinaryOperator<U> mergeFunction <br>
		 * )
		 * 
		 * NOTE:: <T> mergeFunction(<T> oldValue, <T> newValue) <br>
		 * merge function is a BiConsumer<T>
		 */
		Map<Integer, String> lengthWithSweet = sweetList.stream().collect(
				Collectors.toMap(name -> name.length(), name -> name, (name1, name2) -> name1 + " and " + name2));
		System.out.println(lengthWithSweet); // {4=cake and pies, 5=tarts and wafer, 6=pastry, 7=timbale}
		System.out.println(lengthWithSweet.getClass()); // class java.util.HashMap

		/**
		 * Get the specified type of resulting Map
		 * 
		 * Collectors.toMap( <br>
		 * Function<? super T, ? extends K> keyMapper, <br>
		 * Function<? super T, ? extends U> valueMapper, <br>
		 * BinaryOperator<U> mergeFunction, <br>
		 * Supplier<M> mapFactory <br>
		 * )
		 */
		LinkedHashMap<String, Integer> sweetWithTotalLength = Stream.of("tart", "pastry", "tart", "cake", "ice cream")
				.collect(Collectors.toMap(name -> name, name -> name.length(), (length1, length2) -> length1 + length2,
						() -> new LinkedHashMap<String, Integer>())); // mapFactory, "LinkedHashMap::new" would also
																		// work
		System.out.println(sweetWithTotalLength); // {tart=8, pastry=6, cake=4, ice cream=9}
		System.out.println(sweetWithTotalLength.getClass()); // class java.util.LinkedHashMap

		/*
		 * ########################Collectors.groupingBy()########################
		 */
		/**
		 * Collectors.groupingBy( <br>
		 * Function<? super T, ? extends K> classifier <br>
		 * )
		 * 
		 * Collectors.groupingBy( <br>
		 * Function<? super T, ? extends K> classifier, <br>
		 * Collector<? super T, A, D> downstream <br>
		 * )
		 * 
		 * Collectors.groupingBy( <br>
		 * Function<? super T, ? extends K> classifier, <br>
		 * Supplier<M> mapFactory, <br>
		 * Collector<? super T, A, D> downstream <br>
		 * )
		 * 
		 * returns a Map<K, List<T>>
		 * 
		 * By default List is returned as key but it can be changed(refer later code)
		 */
//		grouping by length of string
		Map<Integer, List<String>> sweetListGroupedBy = sweetList.stream()
				.collect(Collectors.groupingBy(name -> name.length()));
		System.out.println(sweetListGroupedBy); // {4=[cake, pies], 5=[tarts, wafer], 6=[pastry], 7=[timbale]}

//		grouping by 1st character
		Map<Character, List<String>> sweetListGroupedByFirstLetter = sweetList.stream()
				.collect(Collectors.groupingBy(name -> name.charAt(0)));
		System.out.println(sweetListGroupedByFirstLetter); // {p=[pastry, pies], c=[cake], t=[tarts, timbale],
															// w=[wafer]}
		System.out.println(sweetListGroupedByFirstLetter.getClass()); // class java.util.HashMap

//		grouping by numerical value, and getting the map value as Set
		Map<Integer, Set<Integer>> intGroupedByValueAsSet = randomIntList.stream()
				.collect(Collectors.groupingBy(number -> number, Collectors.toSet()));
		System.out.println(intGroupedByValueAsSet.get(80).getClass()); // 'class java.util.HashSet' instead of 'class
																		// java.util.ArrayList'

//		Getting Map as 'TreeMap' while grouping by numerical value, and getting the map's 'value' as Set
		TreeMap<Integer, Set<Integer>> intGroupedByValue_asTreeSet = randomIntList.stream()
				.collect(Collectors.groupingBy(number -> number, TreeMap::new, Collectors.toSet()));
		System.out.println(intGroupedByValue_asTreeSet.getClass()); // class java.util.TreeMap

		/*
		 * ########################Collectors.partitionBy()########################
		 */
		/**
		 * partitions the data for 'true' as key or 'false' as key based on the
		 * predicate passed.
		 * 
		 * Map<Boolean, List<T>> partitioningBy(Predicate<? super T> predicate)
		 * 
		 * returns value type List<T> by default but can be changed(refer later code)
		 */
//		partitioning based on value > 50
		Map<Boolean, List<Integer>> integerValuesOver50 = randomIntList.stream()
				.collect(Collectors.partitioningBy(number -> number > 50));
		System.out.println(integerValuesOver50); // {false=[10, 20, 40, 20, 40, 10], true=[80, 60, 80]}

//		getting Set<T> as Map's value
		Map<Boolean, Set<Integer>> integerValuesOver50_set = randomIntList.stream()
				.collect(Collectors.partitioningBy(number -> number > 50, Collectors.toSet()));
		System.out.println(integerValuesOver50_set); // {false=[20, 40, 10], true=[80, 60]} instead of
														// {false=[10, 20, 40, 20, 40, 10], true=[80, 60, 80]}

	}

	/**
	 * Always return a Stream
	 */
	public static void intermediateOperators() {
		List<String> sweetList = List.of("cake", "pastry", "pies", "tarts", "wafer", "timbale");
		List<String> names = List.of("Pat", "Kate", "Jim", "James", "Ron", "Romilda", "Jane", "Kate", "Joe", "Choe",
				"Ginny");
		List<Integer> intList = List.of(10, 20, 30, 40, 50, 60, 70, 80, 90);
		List<Integer> randomIntList = List.of(10, 20, 80, 40, 20, 60, 40, 80, 10);
		List<Long> longList = List.of(100l, 200l, 300l, 400l, 500l, 600l, 700l, 800l, 900l);
		List<Double> doubleList = List.of(10.3, 19.7, 30.2, 39.8, 50.1, 60.0, 69.9, 80.0, 90.0, 100.00);

//		 * ########################Collectors.partitionBy()########################

		/*
		 * #########################.filter(Predicate<T>)#########################
		 */
		/**
		 * returns the streams of elements for whom the predicate returns 'true'
		 * 
		 * Stream<T> filter(Predicate<? super T> predicate)
		 */
//		Filtering stream to contain only sweet names with length > 4
		List<String> sweetNames_moreThan4length = sweetList.stream().filter((sweet) -> sweet.length() > 4)
				.collect(Collectors.toList());
		System.out.println(sweetNames_moreThan4length); // [pastry, tarts, wafer, timbale]

		/*
		 * ##############################.distinct()##############################
		 */
		/**
		 * distinct() is a stateful intermediate operation that behaves similar to a
		 * filter. <br>
		 * It internally uses .equals() and thus, it is case-sensitive.
		 * 
		 * Stream<T> distinct()
		 */
//		Getting all the distinct values of the Stream<Integer>
		System.out.println(randomIntList.stream().distinct().collect(Collectors.toList())); // [10, 20, 80, 40, 60];

		/*
		 * ################################.limit()################################
		 */
		/**
		 * limit() is a stateful short-circuiting intermediate operation.
		 * 
		 * Stream<T> limit(long maxSize)
		 */
//		Getting only first 2 even number in the List<Double>
		List<Double> first2EvenNumbers = doubleList.stream().filter(num -> num % 2 == 0).limit(2)
				.collect(Collectors.toList());
		System.out.println("first2EvenNumbers: " + first2EvenNumbers); // first2EvenNumbers: [60.0, 80.0]

		/*
		 * ##################################.map()##################################
		 */
		/**
		 * map() is used to 'Transform data'
		 * 
		 * <R> Stream<R> map(Function<? super T, ? extends R> mapper)
		 */
//		mapping/replacing the sweet names with the initial alphabet
		List<Character> sweetIntitals = sweetList.stream().map(sweet -> sweet.charAt(0)).collect(Collectors.toList());
		System.out.println(sweetIntitals); // [c, p, p, t, w, t]

		/*
		 * ################################.flatMap()################################
		 */
		/**
		 * Returns a stream containing all the underlying stream elements
		 */
//		Creating a stream that has all the elements of underlying Streams in 'List<Stream<Integer>>'
		List<Integer> allIntList = Stream.of(intList, randomIntList).flatMap(list -> list.stream())
				.collect(Collectors.toList());
		System.out.println(allIntList); // [10, 20, 30, 40, 50, 60, 70, 80, 90, 10, 20, 80, 40, 20, 60, 40, 80, 10]

		/*
		 * #################################.sorted()#################################
		 */
		/**
		 * sorts the stream based on the natural order.
		 * 
		 * sorted() is a stateful intermediate operation as it needs to have all the
		 * elements in order to sort them.
		 * 
		 * Stream<T> sorted()
		 * 
		 * Above uses the Comparable.compareTo(<T>) on the elements to compare. Thus, if
		 * the elements of this stream are not {@code Comparable}, a
		 * {@code java.lang.ClassCastException} may be thrown when the terminal
		 * operation is executed.
		 * 
		 * Stream<T> sorted(Comparator<? super T> comparator)
		 * 
		 * Comparator can be passed, make sure that the <type> of 'comparing variable'
		 * is Comparable and has proper implementation of .compareTo() <br>
		 * i.e. <String> in Person example down below, if we were to compare by <DOB>
		 * the DOB class would have been expected to be a Comparable with a proper
		 * .compareTo() implemented.
		 * 
		 */
//		Sorting 'List<Integer>' based on Natural sorting order by value(in asc. order)
		List<Integer> randomIntList_sorted = randomIntList.stream().sorted().collect(Collectors.toList());
		System.out.println(randomIntList_sorted); // [10, 10, 20, 20, 40, 40, 60, 80, 80]

//		Sorting 'List<String>' in desc. order by length
		List<String> sweetList_sortedReverseByLength = sweetList.stream()
				.sorted((num1, num2) -> (num1.length() < num2.length()) ? 1 : (num1.length() == num2.length()) ? 0 : -1)
				.collect(Collectors.toList());
		System.out.println(sweetList_sortedReverseByLength); // [timbale, pastry, tarts, wafer, cake, pies]

//		Sorting 'List<Integer>' in reverse of Natural sorting order by value
		List<Integer> randoList_sortedReverse = randomIntList.stream().sorted(Comparator.reverseOrder())
				.collect(Collectors.toList());
		System.out.println(randoList_sortedReverse); // [80, 80, 60, 40, 40, 20, 20, 10, 10]

//		Sorting 'List<Person>' using
//		Collectors.comparing(Function<? super T, ? extends U> keyExtractor) 
//		passed to the Stream.sorted(), we pass the lambda to get the sorting parameter
		List<Person> people = Stream
				.of(new Person("Molly", 46), new Person("Harry", 18), new Person("Patrisha", 24),
						new Person("Albus", 59), new Person("Brian", 18))
				.sorted(Comparator.comparing(Person::getName)).collect(Collectors.toList());
		System.out.println(people);

//		Sorting the names based on lexicographical order, but also limiting them to 2 count.
//		The filtering happens first because next is sort() and sort() needs all the data to perform sorting.
//		Then the sort is performed and first element is passed to limit, since no elements passed limit yet limit's 
//		state is updates to '1' and allows 'Joe' to pass, then 'same happends for 'Jim' 
//		but since the count() state is '2' now the operation is short-circuited and the pipeline is shut down. The last 3 sysouts may change the order
//		filtered: Jim
//		filtered: Joe
//		filtered: Pat
//		filtered: Ron
//		sorted: Joe
//		limit: Joe
//		sorted: Jim
//		limit: Jim
		names.stream().parallel().filter(name -> name.length() <= 3)
				.peek(name -> System.out.println("filtered: " + name)) // Pat, Jim, Ron, Joe
				.sorted().peek(name -> System.out.println("sorted: " + name)) // Jim, Joe
				.limit(2).forEach(name -> System.out.println("limit: " + name)); // Jim, Joe

	}

	/**
	 * Integer::compare .reversed() can be used to compare 2 int/Integers in acs. or
	 * desc. order
	 * 
	 * Remember that stream operations use internal iteration when processing
	 * elements of a stream. Also, when you execute a stream in parallel, the Java
	 * compiler and runtime determine the order of execution to maximize the
	 * benefits of parallel computing unless specified by the stream operation.
	 * 
	 * All intermediate operations are lazy. An expression, method, or algorithm is
	 * lazy if its value is evaluated only when it is asked for. (eager when
	 * evaluated or processed immediately.) Intermediate operations are lazy as they
	 * do not start any intermediate operations on the stream until the terminal
	 * operation encountered. Processing streams lazily allows the Java compiler and
	 * runtime to look at the complete pipeline before optimization of streams
	 * processing.
	 * 
	 * Remember, all intermediate operations are lazy.
	 * 
	 * The operation forEachOrdered processes elements in the order the are in
	 * stream , regardless of whether the stream is executed in serial or parallel
	 * fashion. However, when a stream is executed in parallel, the map operation
	 * processes elements of the stream specified by the Java runtime and compiler.
	 * Consequently, the order in which the lambda expression e -> {
	 * parallelStorage.add(e); return e; } adds elements to the List parallelStorage
	 * can vary every time the code is run. For deterministic and predictable
	 * results, ensure that lambda expression parameters in stream operations are
	 * not stateful.
	 * 
	 * Arrays.toString(array).length() would give the length including the ' ' and
	 * ',' and '[]'
	 */
	public static void snippets() {
		List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
		List<String> strings = Arrays.asList("hello", "world", "java", "stream");

//		max value in numbers
		Optional<Integer> max = numbers.stream().reduce(Integer::max);
		System.out.println("max value in numbers: " + max.get()); // 5

//		sum of all numbers
		Optional<Integer> sum = numbers.stream().reduce(Integer::sum);
		System.out.println("sum of all numbers: " + sum.get()); // 15

//		Concatenate all the strings
		String concatenated = strings.stream().reduce("", String::concat);
		System.out.println("Concatenate all the strings: " + concatenated); // helloworldjavastream

//		total length of all the strings
		Integer totalLength = strings.stream().reduce(0,
				(accuumulatedLength, currentString) -> accuumulatedLength + currentString.length(), Integer::sum);
		System.out.println("total length of all the strings: " + totalLength); // 20

//		longest length string
		String longestString = strings.stream().reduce((s1, s2) -> (s1.length() > s2.length()) ? s1 : s2).get();
		System.out.println("longest length string: " + longestString); // stream

//		length of longest string
		Integer longestStringLength = strings.stream().reduce((s1, s2) -> (s1.length() > s2.length()) ? s1 : s2).get()
				.length();
		System.out.println("length of longest string: " + longestStringLength); // 6

//		product of all the numbers
		long product = numbers.stream().reduce(1, (currentProduct, currentValue) -> currentProduct * currentValue);
		System.out.println("product of all the numbers: " + product); // 120

//		average of number
		double average = numbers.stream().mapToDouble(Integer::doubleValue).average().getAsDouble();
		System.out.println("average of number: " + average); // 3.0

//		combining multiple lists
		List<List<Integer>> lists = Arrays.asList(Arrays.asList(1, 2, 3), Arrays.asList(4, 5, 6),
				Arrays.asList(7, 8, 9));
		List<Integer> combined = lists.stream().reduce(new ArrayList<>(), (acc, list) -> {
			acc.addAll(list);
			return acc;
		}, (l1, l2) -> {
			return l1;
		});
		System.out.println("combined list: " + combined); // 1 2 3 4 5 6 7 8 9

//		factorial of integer
		int n = 5;
		int factorial = IntStream.rangeClosed(1, n).reduce(1,
				(currentFactorial, currentNum) -> currentFactorial * currentNum);
		System.out.println("factorial of " + n + ": " + factorial); // 120
	}
}
