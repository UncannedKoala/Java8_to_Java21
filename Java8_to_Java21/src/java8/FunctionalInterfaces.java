package java8;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class FunctionalInterfaces {
	public static void main(String[] args) {

//		customPredicate();
//		predicate();
		consumer();
//		supplier();

//		IntPredicate isEven = number -> number%2==0;	
	}

	public static void customPredicate() {
		FunctionConsumer f = obj -> obj == null;
		System.out.println(f.isNull(null));
		System.out.println(FunctionConsumer.getName());
	}

	public static void predicate() {
		// SAM: boolean test(T t)
		Predicate<Integer> isEven = number -> number % 2 == 0;
		System.out.println(isEven.test(2)); // true
		System.out.println(isEven.test(13)); // false
		System.out.println(isEven.test(14)); // true

		BiPredicate<Integer, Integer> areEven = (num1, num2) -> (num1 % 2 == 0 && num2 % 2 == 0);
		System.out.println(areEven.test(3, 4)); // false
		System.out.println(areEven.test(12, 40)); // true
		System.out.println(areEven.test(12, 41)); // false
	}

	public static void consumer() {
		// SAM: void accept(T t)
		Consumer<Integer> printEvenOrOdd = number -> System.out
				.println(number + " is " + ((number % 2 == 0) ? "even" : "odd"));
		printEvenOrOdd.accept(2); // even
		printEvenOrOdd.accept(13); // odd
		printEvenOrOdd.accept(14); // even

		BiConsumer<String, String> printTogether = (str1, str2) -> System.out.println(str1 + str2);
		Map<String, String> names = new HashMap<>();
		names.put("Mr.", "Ron Wiesley");
		names.put("Ms.", "Jinny Wiesley");
		names.put("Prof.", "Dumbledore");
		names.forEach(printTogether);
	}

	public static void supplier() {
		// SAM: T get();
		Supplier<String> isEvenSupplier = () -> "is even: ";
		System.out.println("2 " + isEvenSupplier.get() + (2 % 2 == 0));
		System.out.println("13 " + isEvenSupplier.get() + (13 % 2 == 0));
		System.out.println("14 " + isEvenSupplier.get() + (14 % 2 == 0));
	}
}