package java8to15;

import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * {@code Optionals} can be defined as a container of the object, this container
 * may or may not be empty.<br>
 * {@code Optionals} makes it clear that returned value may be null and
 * indecated user to handle that situation
 * <ul>
 * 
 * <b>java 8</b>
 * <li>There is <b>{@code Optional<T>}</b> class and for primitives there is <b>
 * {@code OptionalInt, OptionalLong, and OptionalDouble } </b></li>
 * <li>{@code Optional<T> Optional.of()}<br>
 * returns an {@code Optional.empty()}</li>
 * <li>{@code Optional<T> Optional.of(T t)}<br>
 * return an Option containing the passed value</li>
 * <li>{@code Optional<T> Optional.ofNullable(T t)}<br>
 * returns an Optional with given non-null value, if null is passed
 * {@code Optional.empty} is returned</li>
 * <li>{@code <T> get()}<br>
 * returns the contained value, will throw {@code NoSuchElementException } if no
 * value is present</li>
 * <li>{@code boolean isPresent()}<br>
 * returns true if there is a value in the Optional, else false</li>
 * 
 * <li>{@code void ifPresent(Consumer<? super T>)}<br>
 * calls the passed {@code Consumer} if there is a value else does not do
 * anything</li>
 * <li>{@code Optional<T> filter(Predicated<? super T>)}<br>
 * returns an {@code Optional} with value if the value passes the Predicate,
 * else {@code Optional.empty()}</li>
 * <li>{@code Optional<U> map(Function<? super T, ? extends U> mapper)}<br>
 * if has value then applies the {@code Function} on the value and returns as an
 * {@code Optional} otherwise returns {@code Optional.empty()}</li>
 * <li>{@code T orElse(T)}<br>
 * returns the non-null value or returns back the passed value</li>
 * <li>{@code T orElseThrow()}<br>
 * returns the value else throws {@code NoSuchElementException}</li>
 * 
 * <b>java 9</b>
 * <li>
 * {@code Optional<T> or(Supplier<? extends Optional<? extends T>> supplier)}<br>
 * if value is present, returns an {@code Optional} having the value, otherwise
 * returns an {@code Optional} produced by the passed {@code Function}</li>
 * <li>{@code void ifPresentOrElse(Consumer<? super T>, Runable)}<br>
 * if has value executes {@code Consumer} else executes passed
 * {@code Runnable}</li>
 * <li>{@code Stream<T> stream()}</li>
 * 
 * <b>java 10</b>
 * <li>
 * {@code <X extends Throwable>T orElseThrow(Supplier<? extends X> exceptionSupplier)}<br>
 * returns the non-null value or throws the {@code Exception} returned by
 * {@code Supplier}</li>
 * 
 * <b>java 11</b>
 * <li>{@code boolean isEmpty()}<br>
 * reutrns {@code true} if a value is not present, otherwise {@code false}</li>
 * <ul>
 */
public class Optionals {
	public static void main(String[] args) {
		Optionals.optionalCreation();
		Optionals.optionalMethods();
	}

	public static void optionalCreation() {
//		java 8
		Optional<String> empty = Optional.empty();
		Optional<Integer> optionalInteger = Optional.of(Integer.valueOf(3));
		Optional<String> optionalString = Optional.of("Sample String value");
		Optional<StringBuilder> optionalStringBuilder = Optional.of(new StringBuilder("sample StringBuilder vlaue"));
		Optional<Long> optionalLong = Optional.of(Long.valueOf(3));
		Optional<Double> optionalDouble = Optional.of(Double.valueOf(3.0));

//		PRIMITIVE OPTIONAL
//		java 8
		OptionalInt optionalInt = OptionalInt.of(5);
		OptionalLong optionalLong2 = OptionalLong.of(23);
		OptionalDouble optionalDouble2 = OptionalDouble.of(23.5);
	}

	public static void optionalMethods() {
		Optional<String> optionalEmpty = Optional.empty();
		Optional<Integer> optionalInteger = Optional.of(Integer.valueOf(3));
		Optional<String> optionalString = Optional.of("Sample String value");
		Optional<StringBuilder> optionalStringBuilder = Optional.of(new StringBuilder("sample StringBuilder vlaue"));
		Optional<Long> optionalLong = Optional.of(Long.valueOf(3));
		Optional<Double> optionalDouble = Optional.of(Double.valueOf(3.0));

//		java 8
		OptionalInt optionalInt = OptionalInt.of(5);
		OptionalLong optionalLong2 = OptionalLong.of(23);
		OptionalDouble optionalDouble2 = OptionalDouble.of(23.5);

		System.out.println(optionalInt.getAsInt());
		System.out.println(optionalLong2.getAsLong());
		System.out.println(optionalDouble2.getAsDouble());

		System.out.println(optionalEmpty);

//		java 8
		String empty = optionalEmpty.get();// throws Exception
		System.out.println(empty);

//		java 8
		/* •T get() */
		String str = optionalString.get();
		System.out.println(str); // prints "Sample String value"

//		java 8
		/* •T orElse(T other) */
		String orElse = optionalEmpty.orElse("or else value");
		System.out.println(orElse); // prints "or else value"

//		java 8
		/* •T orElseGet(Supplier<? extends T> supplier) */
		String elseGet = optionalEmpty.orElseGet(() -> "default vlaue");
		System.out.println(elseGet); // prints "default vlaue"

//		java 8
		/*
		 * •<X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier)
		 * throws X
		 */
//		throws exception from given Supplier for empty optional 
//		String a = optionalEmpty.orElseThrow(NullPointerException::new); 

//		java 8
		/* •ifPresent(Consumer<T>) */
//		executes the passed Consumer if optional contains a value, else nothing
		optionalString.ifPresent(System.out::println); // nothing happens
		optionalString.ifPresent(System.out::println); // prints "Sample String value"

//		java 8
		/* •Optional<T> Optional.ofNullable(T t) */
//		returns an Optional with given non-null value, if null is passed Optional.empty is returned
		Optional<Object> optionalNullable = Optional.ofNullable(null);
		System.out.println(optionalNullable); // Optional.empty
		System.out.println(Optional.ofNullable("temp")); // prints "Optional[temp]"

		double value = OptionalDouble.empty().orElseGet(() -> Double.NaN);
		System.out.println(value); // NaN

//		java 8
		/* •Optional<T> filter(Predicate<? super T> predicate) */
//		returns Optional with value if value passes filter else Optional.empty()
		System.out.println(optionalDouble.filter(val -> val > 0.8).get());
		optionalInteger.filter(val -> val > 2).ifPresent(Streams::log);

//		#################################################################
//		###########.filter unavailable for primitive Optionals###########
//		#################################################################
//		System.out.println(optionalDouble2.filter(val -> val > 20).get());

//		java 9
		/* •Optional<T> or(Supplier<? extends Optional<? extends T>> supplier) */
//		returns an Optional having the value if(value!=null), otherwise returns an Optional produced by the passed Function
		optionalString.or(() -> Optional.of(""));

//		java 9
		/* •void ifPresentOrElse(Consumer<? super T> action, Runnable emptyAction) */
//		NOTE :: the 'emptyAction' is executed by the same thread
		Optional.empty().ifPresentOrElse(System.out::println,
				() -> System.out.println(Thread.currentThread().getName()));

//		java 9
		/* •Stream<T> stream() */
		Stream<String> stringStream = optionalString.stream();
		IntStream intStream = optionalInt.stream();
		Stream<Integer> integerStream = optionalInteger.stream();

		stringStream.forEach(e -> System.out.println(e));
		intStream.forEach(e -> System.out.println(e));
		integerStream.forEach(e -> System.out.println(e));

//		java 10
		/* •T orElseThrow() */
// 		throws NoSuchElementException for empty optional
		String a = optionalEmpty.orElseThrow(); // throws NoSuchElementException

		System.out.println(a);
	}
}
