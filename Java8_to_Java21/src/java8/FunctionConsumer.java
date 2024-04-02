package java8;

@FunctionalInterface
public interface FunctionConsumer {
	
	public boolean isNull(Object obj);

	/**
	 * <li><b>Using CurrentThread</b><br>
	 * {@code return Thread.currentThread() 
	    .getStackTrace()[1] 
	    .getClassName();}<br><br>
	 * 
	 * <li><b>current method name at 0th index</b><br>
	 * {@code return new Exception().getStackTrace()[0].getClassName();}<br><br>
	 * 
	 * <li><b>We can use Class.getEnclosingMethod(), this method returns a Method
	 * object representing the instantly enclosing method of the prime class. But
	 * this come with a expressive overhead as it involves creating a new anonymous
	 * inner class behind the scenes.</b><br>
	 * {@code return new
	 * Object(){}.getClass().getEnclosingMethod().getDeclaringClass().getName();}<br><br>
	 * 
	 * <li><b>Using Inner Class</b><br>
	 * {@code class Inner {};<br>
	 * return Inner.class.getEnclosingClass().getName();}<br><br>
	 * 
	 * <li><b>Using StackWalker(Java 9)</b><br>
	 * {@code return StackWalker.getInstance().walk(frames -> frames.skip(0).findFirst().map(StackWalker.StackFrame::getClassName)).orElse("Anything");}<br><br>
	 * 
	 * @apiNote StackWalked was introduced in java 9
	 */
	public static String getName() {
		/* Using CurrentThread */
		return Thread.currentThread().getStackTrace()[1].getClassName();

		/* current method name at 0th index */
//		return new Throwable().getStackTrace()[0].getClassName();

		/* Using Exception */
//		return new Exception().getStackTrace()[0].getClassName();

		/*
		 * We can use Class.getEnclosingMethod(), this method returns a Method object
		 * representing the instantly enclosing method of the prime class. But this come
		 * with a expressive overhead as it involves creating a new anonymous inner
		 * class behind the scenes.
		 */
//		return new Object(){}.getClass().getEnclosingMethod().getDeclaringClass().getName();

		/* Using Inner Class */
//		class Inner {};
//		return Inner.class.getEnclosingClass().getName();

		/* Using StackWalker */
//		return StackWalker.getInstance()
//		.walk(frames -> frames.skip(0).findFirst().map(StackWalker.StackFrame::getClassName))
//		.orElse("Anything");
	}
}
