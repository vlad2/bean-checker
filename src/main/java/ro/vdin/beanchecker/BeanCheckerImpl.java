package ro.vdin.beanchecker;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class BeanCheckerImpl<T> implements BeanChecker<T> {
	private Set<Method> setters;
	private Set<Method> mandatorySetters;
	private Set<Method> calledSetters;
	private Enhancer enhancer;
	private T beanProxy;
	private static final Logger log = LoggerFactory.getLogger(BeanCheckerImpl.class);
	private static final Comparator<Method> METHOD_COMPARATOR_BY_NAME = new Comparator<Method>() {
		@Override
		public int compare(Method m1, Method m2) {
			return m1.getName().compareTo(m2.getName());
		}

	};

	/**
	 * Constructor.
	 * 
	 * @param clazz - bean class
	 */
	@SuppressWarnings("unchecked")
	public BeanCheckerImpl(Class<T> clazz) {
		setters = computeSetters(clazz);
		mandatorySetters = computeMandatorySetters(setters);
		calledSetters = new TreeSet<>(METHOD_COMPARATOR_BY_NAME);

		enhancer = new Enhancer();
		enhancer.setSuperclass(clazz);
		enhancer.setCallback(new MethodInterceptor() {
			@Override
			public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
				log.debug(String.format("Method intercepted: name=%s, annotations=%s, args=%s",
										method,
										Arrays.asList(method.getAnnotations()),
										Arrays.asList(args)));

				if (isSetter(method)) {
					log.debug(String.format("Setter method intercepted: name=%s, args=%s", method, args));
					calledSetters.add(method);
				}

				return proxy.invokeSuper(obj, args);
			}

		});

		beanProxy = (T) enhancer.create();
	}

	private Set<Method> computeMandatorySetters(Set<Method> allSetters) {
		Set<Method> mandatorySetters = new TreeSet<>(METHOD_COMPARATOR_BY_NAME);

		for (Method method : allSetters) {
			if (!hasOptionalSetterAnnotation(method)) {
				mandatorySetters.add(method);
			}
		}

		return mandatorySetters;
	}

	private boolean hasOptionalSetterAnnotation(Method method) {
		return method.isAnnotationPresent(OptionalSetter.class);
	}

	@Override
	public T getBeanProxy() {
		return beanProxy;
	}

	private boolean isSetter(Method method) {
		return (method.getName().startsWith("set"));
	}

	private Set<Method> computeSetters(Class<?> clazz) {
		Set<Method> setters = new TreeSet<>(METHOD_COMPARATOR_BY_NAME);
		for (Method method : clazz.getDeclaredMethods()) {
			if (isSetter(method)) {
				setters.add(method);
			}
		}

		if (clazz.getSuperclass() != null) {
			setters.addAll(computeSetters(clazz.getSuperclass()));
		}

		return setters;
	}

	@Override
	public Set<Method> getSetters() {
		return setters;
	}

	@Override
	public Set<Method> getCalledSetters() {
		return calledSetters;
	}

	@Override
	public Set<Method> getMissingSetters() {
		Set<Method> missingSetters = new TreeSet<>(METHOD_COMPARATOR_BY_NAME);
		missingSetters.addAll(mandatorySetters);
		missingSetters.removeAll(calledSetters);
		return missingSetters;
	}

	@Override
	public void checkMandatorySettersCalled() throws IllegalStateException {
		if (!mandatorySettersCalled()) {
			throw new IllegalStateException("Not all mandatory setters have been called! Missing setters: "
					+ getMissingSetters());
		}
	}

	@Override
	public boolean mandatorySettersCalled() {
		return calledSetters.containsAll(mandatorySetters);
	}
}
