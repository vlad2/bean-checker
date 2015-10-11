package ro.vdin;

import java.lang.reflect.Method;
import java.util.Set;

public interface BeanChecker<T> {
	
	/**
	 * @return the bean to be used
	 */
	T getBeanProxy();

	/**
	 * Checks if all setters have been called, and it throws an exception if not.
	 * @throws IllegalStateException
	 */
	void checkAllSettersCalled() throws IllegalStateException;
	
	/**
	 * Checks if mandatory setters have been called, and it throws an exception if not.
	 * (Mandatory setters are those who are not marked with the @OptionalSetter annotation).
	 * @throws IllegalStateException
	 */
	void checkMandatorySettersCalled() throws IllegalStateException;

	boolean allSettersCalled();
	
	boolean mandatorySettersCalled();

	Set<Method> getSetters();

	Set<Method> getCalledSetters();

	Set<Method> getMissingSetters();

}