package java8;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

/**
 * Any local variable in order to be used by a lambda expression must be final
 * or effectively final(not marked 'final', but are not being modified by the
 * method), as lambda takes the snapshot of the local variables. LAMBDA IT SELF
 * MAY CHANGE THE VARIABLE BUT NOT OTHERS
 */
public class FunctionalInterfaces {
	public static void main(String[] args) {

//		customPredicate();
//		predicate();
//		consumer();
//		supplier();
//		function();
//		unaryOperator();
//		binaryOperator();
		lambdaUsingLocalVariables();

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

		// SAM: boolean test(T t, U u)
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

		// SAM: void accept(T t, U u)
		BiConsumer<String, String> printTogether = (str1, str2) -> System.out.println(str1 + str2);
		Map<String, String> names = new HashMap<>();
		names.put("Mr.", "Ron Wiesley");
		names.put("Ms.", "Jinny Wiesley");
		names.put("Prof.", "Dumbledore");
		names.forEach(printTogether);
	}

	public static void supplier() {
		// SAM: T get()
		Supplier<String> isEvenSupplier = () -> "is even: ";
		System.out.println("2 " + isEvenSupplier.get() + (2 % 2 == 0));
		System.out.println("13 " + isEvenSupplier.get() + (13 % 2 == 0));
		System.out.println("14 " + isEvenSupplier.get() + (14 % 2 == 0));
	}

	public static void function() {
		// SAM: R apply(T t)
		Function<String, Integer> actualLength = str -> str.trim().length();
		System.out.println(actualLength.apply("   cat ")); // 3
		System.out.println(actualLength.apply("    ")); // 0
		System.out.println(actualLength.apply("1    ")); // 1
		System.out.println(actualLength.apply("1234")); // 4

		// SAM: R apply(T t, U u)
		BiFunction<String, Integer, String> firstXLetters = (str, index) -> str.substring(0, index);
		System.out.println(firstXLetters.apply("Speedster", Integer.valueOf(5))); // Speed
		System.out.println(firstXLetters.apply("Inflamation", Integer.valueOf(2))); // In
		System.out.println(firstXLetters.apply("Dangerous", Integer.valueOf(6))); // Danger
	}

//	the input type will remain same for output type
	public static void unaryOperator() {
		// SAM: R apply(T t)
		UnaryOperator<Integer> findSmallestDivisor = input -> {
			// if even
			if (input % 2 == 0)
				return 2;

			// iterate from 3 to sqrt(n)
			for (int i = 3; i * i <= input; i += 2) {
				if (input % i == 0)
					return i;
			}
			// if none found, the number is prime. Return the input itself.
			return input;
		};

		System.out.println("smallest divisor for 21 is: " + findSmallestDivisor.apply(21));
		System.out.println("smallest divisor for 23 is: " + findSmallestDivisor.apply(23));
		System.out.println("smallest divisor for 74 is: " + findSmallestDivisor.apply(74));
	}

//	the input type will remain same for output type
	public static void binaryOperator() {
		// SAM: R apply(T t, U u)
		BinaryOperator<String> toLowerCaseAndConcat = (str1, str2) -> new StringBuilder(str1).append(str2).toString()
				.toLowerCase();

		System.out.println(toLowerCaseAndConcat.apply("Super ", "MAN")); // super man
		System.out.println(toLowerCaseAndConcat.apply("ToM ", "RiDDle")); // tom riddle
	}

	/***
	 * <li>Here the variable 'x' is not final but it is effectively final(not marked
	 * 'final' but behaves as final) because it is being used by the lambda
	 * expression than thus once initialized its value can not be changed, neither
	 * in the lambda nor outside it.<br>
	 * <li>Local variable x defined in an enclosing scope must be final or effectively
	 * final :: is given by the lambda expression when the value of used variable by the lambda is changed
	 */
	public static void lambdaUsingLocalVariables() {
		int x = 12;
//		x = 51; // !!!!

		Predicate<String> lengthPrinter = s -> {
//			x++; // !!!!
			System.out.println("counter:" + x);
			return false;
		};

//		x++; // !!!! 
		lengthPrinter.test("sample string");

		return;
	}
}