package ro.vdin.beanchecker;

public class BeanCheckerFactory {
	public static <T> BeanChecker<T> createBeanChecker(Class<T> clazz) {
		return new BeanCheckerImpl<T>(clazz);
	}
}
