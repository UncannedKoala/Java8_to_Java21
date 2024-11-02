package java8to15.java8;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class MethodReferencing {
	public static void main(String[] args) {
		methodReferences();
	}

	/**
	 * Method References are of four kinds:<br>
	 * <ol>
	 * <li><b>Reference to a constructor</b> : <i>ClassName::new</i><br>
	 * HashSet::new
	 * <li><b>Reference to a static method <i>(considered as unbound)</i></b> :
	 * <i>ContainingClass::staticMethodName</i><br>
	 * Person::compareByAge<br>
	 * MethodReferencesExamples::appendStrings
	 * <li><b>Bound method referencing : <br>
	 * <i>Reference to an instance method of a particular object</b> :
	 * containingObject::instanceMethodName</i><br>
	 * myComparisonProvider::compareByName<br>
	 * myApp::appendStrings2
	 * <li><b>Unbound method referencing : <br>
	 * <i>Reference to an instance method of an arbitrary object of a particular
	 * type</b> : ContainingType::methodName</i><br>
	 * String::compareToIgnoreCase<br>
	 * String::concat
	 * </ol>
	 */
	public static void methodReferences() {

		List<String> names = Arrays.asList("Jenny", "Tom", "Dobby", "Harry");
		names.forEach(str -> System.out.println(str)); // using lambda
		System.out.println("-----");
		names.forEach(System.out::println); // using method referencing

		/*
		 * CONSTRUCTOR REFERENCING (calling a static method of the class)
		 */
		Supplier<StringBuilder> lambda_sbSupplier = () -> new StringBuilder(); // using lambda
		Supplier<StringBuilder> constructorRef_sbSupplier = StringBuilder::new; // using constructor referencing
		System.out.println(lambda_sbSupplier.get().append("with lambda"));
		System.out.println(constructorRef_sbSupplier.get().append("with constructor referencing"));

		Function<String, StringBuilder> lambda_sbFunction = str -> new StringBuilder(str); // using lambda
		Function<String, StringBuilder> constructorRef_sbFunction = StringBuilder::new; // using constructor referencing
		System.out.println(lambda_sbFunction.apply("new StringBuilder Obj using").append("lambda"));
		System.out.println(
				constructorRef_sbFunction.apply("new StringBuilder Obj using").append(" Constructor referencing"));

		/*
		 * STATIC METHOD REFERENCING (calling a static method of the class)
		 */
		List<Integer> list = Arrays.asList(44, 12, 6, 46, 0, 45, 15, 16, 4, 654, 54, 6, 51, 565, 1);
		Consumer<List<Integer>> lambda_listSortConsumer = lis -> Collections.sort(lis); // using lambda
		Consumer<List<Integer>> methodRef_listSortConsumer = Collections::sort; // using static method referencing
		System.out.print("list before sorting: " + list);
		lambda_listSortConsumer.accept(list);
		System.out.println(" -->> " + list);
		Collections.shuffle(list);
		System.out.print("list before sorting: " + list);
		methodRef_listSortConsumer.accept(list);
		System.out.println(" -->> " + list);

		/*
		 * BOUND METHOD REFERENCING (calling the instance method using the object
		 * reference). We bind the instance method to the instance of the object, it is
		 * resolved at runtime using DMD(dynamic method dispatch)
		 */
		String name = "Persey Jackson";

		Supplier<String> lambda_nameSupplier = () -> name.toLowerCase(); // using lambda
		Supplier<String> methodRef_nameSupplier = name::toUpperCase; // using bound method referencing
		System.out.println(lambda_nameSupplier.get());
		System.out.println(methodRef_nameSupplier.get());

		Predicate<String> nameStartsWith = name::startsWith; // using bound method referencing
		System.out.println(nameStartsWith.test("Per"));

		/*
		 * UNBOUND METHOD REFERENCING (calling the instance method using the Class name)
		 * we do not associate instance to the instance method , it is resolved at
		 * runtime using DMD(dynamic method dispatch)
		 */
		String movie = "The 5th element";

		Function<String, String> lambda_movieSupplier = str -> str.toLowerCase(); // using lambda
		Function<String, String> methodRef_movieSupplier = String::toUpperCase; // using bound method referencing
		System.out.println(lambda_movieSupplier.apply(movie)); // the 5th element
		System.out.println(methodRef_movieSupplier.apply(movie)); // THE 5TH ELEMENT

		BiFunction<String, String, String> movieConcat = String::concat; // using bound method referencing
		System.out.println(movieConcat.apply(movie, ", Harry Potter")); // The 5th element, Harry Potter
	}
}
