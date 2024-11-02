package java8to15.java8;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleFunction;
import java.util.function.DoublePredicate;
import java.util.function.DoubleSupplier;
import java.util.function.DoubleToIntFunction;
import java.util.function.DoubleToLongFunction;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntSupplier;
import java.util.function.IntToDoubleFunction;
import java.util.function.IntToLongFunction;
import java.util.function.IntUnaryOperator;
import java.util.function.LongBinaryOperator;
import java.util.function.LongConsumer;
import java.util.function.LongFunction;
import java.util.function.LongPredicate;
import java.util.function.LongSupplier;
import java.util.function.LongToDoubleFunction;
import java.util.function.LongToIntFunction;
import java.util.function.LongUnaryOperator;
import java.util.function.ObjDoubleConsumer;
import java.util.function.ObjIntConsumer;
import java.util.function.ObjLongConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToDoubleBiFunction;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntBiFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongBiFunction;
import java.util.function.ToLongFunction;
import java.util.function.UnaryOperator;

/**
 * Any local variable in order to be used by a lambda expression must be final
 * or effectively final(not marked 'final', but are not being modified by the
 * method), as lambda takes the snapshot of the local variables. LAMBDA IT SELF
 * MAY CHANGE THE VARIABLE BUT NOT OTHERS
 */
public class FunctionalInterfaces {
	public static void main(String[] args) {

		customPredicate();
		predicate();
		consumer();
		supplier();
		function();
		unaryOperator();
		binaryOperator();
		lambdaUsingLocalVariables();

		intConsumer();
		intFunction();
		intPredicate();
		intSupplier();
		intBinaryOperator();
		intUnaryOperator();
		intToLongFunction();
		intToDoubleFunction();

		longConsumer();
		longFunction();
		longPredicate();
		longSupplier();
		longBinaryOperator();
		longUnaryOperator();
		longToIntFunction();
		longToDoubleFunction();

		doubleConsumer();
		doubleFunction();
		doublePredicate();
		doubleSupplier();
		doubleBinaryOperator();
		doubleUnaryOperator();
		doubleToIntFunction();
		doubleToLongFunction();

		toIntFunction();
		toIntBiFunction();
		toLongFunction();
		toLongBiFunction();
		toDoubleFunction();

		objIntConsumer();
		objLongConsumer();
		objDoubleConsumer();
	}

//	java 8
	public static void customPredicate() {
		FunctionConsumer f = obj -> obj == null;
		System.out.println(f.isNull(null));
		System.out.println(f.isNull("Stri"));
		System.out.println(FunctionConsumer.getName());
	}

//	java 8
	/**
	 * simillarly all the FinctionalInterfaces have the following methods<br>
	 * <ul>
	 * <li>static and()</li>
	 * <li>static or()</li>
	 * <li>static negate()</li>
	 * <li>default not() <i>since java 11</i></li>
	 * </ul>
	 * 
	 * @implNote the {@code default not()} method introduced in java 8 internally
	 *           calls {@code negate()} but wraps the call around
	 *           {@code Objects.requireNonNull} check. If null is passed
	 *           {@code not()} throws an exception, whereas {@code negate()} does
	 *           not.
	 */
	public static void predicate() {
		// SAM: boolean test(T t)
		Predicate<Integer> isEven = number -> number % 2 == 0;
		System.out.println(isEven.test(2)); // true
		System.out.println(isEven.test(13)); // false
		System.out.println(isEven.test(14)); // true

		// SAM: static Predicate<T> not(Predicate<? super T> target) :: java 11
		Predicate<Integer> isOdd = Predicate.not(isEven);
		Predicate<Integer> isPositive = number -> number > -1;
		Predicate<Integer> isNegative = Predicate.not(isPositive);
		System.out.println(isOdd.test(14)); // false
		System.out.println(isOdd.test(17)); // true

		// SAM: static Predicate<T> not(Predicate<? super T> target)
		Predicate<Integer> isPositiveEven = isPositive.and(isEven);
		System.out.println(isPositiveEven.test(14)); // true
		System.out.println(isPositiveEven.test(15)); // false
		System.out.println(isPositiveEven.test(-14)); // false
		System.out.println(isPositiveEven.test(-15)); // false

		// SAM: static Predicate<T> not(Predicate<? super T> target)
		Predicate<Integer> isOddOrNegative = isOdd.or(isNegative);
		System.out.println(isOddOrNegative.test(14)); // false
		System.out.println(isOddOrNegative.test(15)); // true
		System.out.println(isOddOrNegative.test(-14)); // true
		System.out.println(isOddOrNegative.test(-15)); // true

		// SAM: boolean test(T t, U u)
		BiPredicate<Integer, Integer> areEven = (num1, num2) -> (num1 % 2 == 0 && num2 % 2 == 0);
		System.out.println(areEven.test(3, 4)); // false
		System.out.println(areEven.test(12, 40)); // true
		System.out.println(areEven.test(12, 41)); // false
	}

//	java 8
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

//	java 8
	public static void supplier() {
		// SAM: T get()
		Supplier<String> isEvenSupplier = () -> "is even: ";
		System.out.println("2 " + isEvenSupplier.get() + (2 % 2 == 0));
		System.out.println("13 " + isEvenSupplier.get() + (13 % 2 == 0));
		System.out.println("14 " + isEvenSupplier.get() + (14 % 2 == 0));
	}

//	java 8
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

//	java 8 :: the input type will remain same for output type
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

//	java 8 :: the input type will remain same for output type
	public static void binaryOperator() {
		// SAM: R apply(T t, U u)
		BinaryOperator<String> toLowerCaseAndConcat = (str1, str2) -> new StringBuilder(str1).append(str2).toString()
				.toLowerCase();

		System.out.println(toLowerCaseAndConcat.apply("Super ", "MAN")); // super man
		System.out.println(toLowerCaseAndConcat.apply("ToM ", "RiDDle")); // tom riddle
	}

//	java 8
	public static void intConsumer() {
		IntConsumer logIt = a -> System.out.println(a);
		logIt.accept(2); // 2
	}

//	java 8
	public static void intFunction() {
		IntFunction<String> isEven = num -> "num value: " + num;
		System.out.println(isEven.apply(23)); // num value: 23
		System.out.println(isEven.apply(24)); // num value: 24
	}

//	java 8
	public static void intPredicate() {
		IntPredicate isEven = num -> num % 2 == 0;
		System.out.println(isEven.test(23)); // false
		System.out.println(isEven.test(24)); // true
	}

//	java 8
	public static void intSupplier() {
		IntSupplier supplier = () -> (int) (Math.random() * 100);
		System.out.println(supplier.getAsInt());
		System.out.println(supplier.getAsInt());
	}

//	java 8
	public static void intBinaryOperator() {
		IntBinaryOperator multiplyThem = (a, b) -> a * b;
		System.out.println(multiplyThem.applyAsInt(12, 5)); // 60
	}

//	java 8
	public static void intUnaryOperator() {
		IntUnaryOperator doubleIt = num -> num * 2;
		System.out.println(doubleIt.applyAsInt(72)); // 144
	}

//	java 8
	public static void intToLongFunction() {
		IntToLongFunction doubleIt = num -> (long) num * num;
		System.out.println(doubleIt.applyAsLong(12)); // 144
	}

//	java 8
	public static void intToDoubleFunction() {
		IntToDoubleFunction getRoot = Math::sqrt;
		System.out.println(getRoot.applyAsDouble(74)); // 8.602325267042627
	}

//	java 8
	public static void longConsumer() {
		LongConsumer logIt = a -> System.out.println(a);
		logIt.accept(546); // 546
	}

//	java 8
	public static void longFunction() {
		LongFunction<String> isEven = num -> "num value: " + num;
		System.out.println(isEven.apply(23)); // num value: 23
		System.out.println(isEven.apply(24)); // num value: 24
	}

//	java 8
	public static void longPredicate() {
		LongPredicate isEven = num -> num % 2 == 0;
		System.out.println(isEven.test(23)); // false
		System.out.println(isEven.test(24)); // true
	}

//	java 8
	public static void longSupplier() {
		LongSupplier supplier = () -> (int) (Math.random() * 100);
		System.out.println(supplier.getAsLong());
		System.out.println(supplier.getAsLong());
	}

//	java 8
	public static void longBinaryOperator() {
		LongBinaryOperator doubleIt = (l1, l2) -> l1 + l2;
		System.out.println(doubleIt.applyAsLong(23, 65)); // 88
	}

//	java 8
	public static void longUnaryOperator() {
		LongUnaryOperator squareIt = val -> val * val;
		System.out.println(squareIt.applyAsLong(23)); // 529
	}

//	java 8
	public static void longToIntFunction() {
		LongToIntFunction devideby12 = num -> (int) num / 12;
		System.out.println(devideby12.applyAsInt(36)); // 3
	}

//	java 8
	public static void longToDoubleFunction() {
		LongToDoubleFunction getCubeRoot = Math::cbrt;
		System.out.println(getCubeRoot.applyAsDouble(27)); // 3.0
	}

//	java 8
	public static void doubleConsumer() {
		DoubleConsumer logDouble = System.out::println;
		logDouble.accept(28.956); // 28.956
	}

//	java 8
	public static void doubleFunction() {
		DoubleFunction<String> decorateDoubleValue = val -> "double value is " + val;
		System.out.println(decorateDoubleValue.apply(56.293)); // double value is 56.293
		System.out.println(decorateDoubleValue.apply(9.784)); // double value is 9.784
	}

//	java 8
	public static void doublePredicate() {
		DoublePredicate hasDecimal = num -> (num * 10) % 10 == 0;
		System.out.println(hasDecimal.test(17.0)); // true
		System.out.println(hasDecimal.test(298.415)); // false
	}

//	java 8
	public static void doubleSupplier() {
		DoubleSupplier random = Math::random;
		System.out.println(random.getAsDouble());
		System.out.println(random.getAsDouble());
	}

//	java 8
	public static void doubleBinaryOperator() {
		DoubleBinaryOperator devide = (d1, d2) -> d1 / d2;
		System.out.println(devide.applyAsDouble(9.6, 3.2)); // 2.9999999999999996
	}

//	java 8
	public static void doubleUnaryOperator() {
		DoubleUnaryOperator increase = num -> num + 12.28;
		System.out.println(increase.applyAsDouble(20)); // 32.28
	}

//	java 8
	public static void doubleToIntFunction() {
		DoubleToIntFunction include1decimal = val -> (int) (val * 10);
		System.out.println(include1decimal.applyAsInt(12.35)); // 123
		System.out.println(include1decimal.applyAsInt(792.281)); // 7922
	}

//	java 8
	public static void doubleToLongFunction() {
		DoubleToLongFunction toLong = val -> (long) val;
		System.out.println(toLong.applyAsLong(153.3546)); // 153
	}

//	java 8
	public static void toIntFunction() {
		ToIntFunction<String> stringLength = str -> str.length();
		System.out.println(stringLength.applyAsInt("")); // 0
		System.out.println(stringLength.applyAsInt("potato")); // 6
		System.out.println(stringLength.applyAsInt(" , ")); // 3
	}

//	java 8
	public static void toIntBiFunction() {
		ToIntBiFunction<String, String> lengthSum = (str1, str2) -> str1.length() + str2.length();
		System.out.println(lengthSum.applyAsInt("A", "kbar")); // 5
	}

//	java 8
	public static void toLongFunction() {
		ToLongFunction<String> lengthSquared = str -> (long) str.length() * str.length();
		System.out.println("length -> " + lengthSquared.applyAsLong("")); // 0
		System.out.println(lengthSquared.applyAsLong("cat")); // 9
		System.out.println(lengthSquared.applyAsLong("Boat")); // 16
	}

//	java 8
	public static void toLongBiFunction() {
		ToLongBiFunction<String, Integer> multiplyLength = (str, multiplyBy) -> (long) str.length() * multiplyBy;
		System.out.println(multiplyLength.applyAsLong("cat", 4)); // 12
	}

//	java 8
	public static void toDoubleFunction() {
		ToDoubleFunction<StringBuffer> rootOfLength = strBuff -> Math.cbrt(strBuff.length());
		StringBuffer sb = new StringBuffer();
		System.out.println(rootOfLength.applyAsDouble(sb.append("1"))); // 1
		System.out.println(rootOfLength.applyAsDouble(sb.append("2345678"))); // 2
		System.out.println(rootOfLength.applyAsDouble(sb.append("9012345678901234567")));// 3
	}

//	java 8
	public static void toDoubleBiFunction() {
		ToDoubleBiFunction<Integer, Double> toDoubleBiFunction = (intVal, longVal) -> intVal * longVal * 1.0;
		System.out.println(toDoubleBiFunction.applyAsDouble(2, 45.0));
		;
	}

//	java 8
	/**
	 * A {@code Consumer} that takes in an {@code Object} <T> with a primitive {@code int} value
	 */
	public static void objIntConsumer() {
		ObjIntConsumer<String> printScore = (stringTeam, doubleScore) -> System.out
				.println("Team " + stringTeam + " scored " + doubleScore + " runs.");
		printScore.accept("India", 48);
	}

//	java 8
	/**
	 * A {@code Consumer} that takes in an {@code Object} <T> with a primitive {@code long} value
	 */
	public static void objLongConsumer() {
		ObjLongConsumer<String> printScore = (stringTeam, doubleScore) -> System.out
				.println("With this "+stringTeam + " bags a total of " + doubleScore + " runs in his entire career.");
		printScore.accept("Dhoni", 23490);
	}

//	java 8
	/**
	 * A {@code Consumer} that takes in an {@code Object} <T> with a primitive {@code double} value
	 */
	public static void objDoubleConsumer() {
		ObjDoubleConsumer<String> printScore = (stringTeam, runRate) -> System.out
				.println("Team " + stringTeam + " has run rate of " + runRate + " runs per over.");
		printScore.accept("Pakistan", 2);
	}

	/***
	 * <li>Here the variable 'x' is not final but it is effectively final(not marked
	 * 'final' but behaves as final) because it is being used by the lambda
	 * expression than thus once initialized its value can not be changed, neither
	 * in the lambda nor outside it.<br>
	 * <li>Local variable x defined in an enclosing scope must be final or
	 * effectively final :: is given by the lambda expression when the value of used
	 * variable by the lambda is changed
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