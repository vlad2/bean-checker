package ro.vdin.beanchecker;

import java.lang.reflect.Method;
import java.util.Set;

/**
 * 
 * Sample usage:
 * 
 *		BeanChecker<SimpleBean> beanChecker = BeanCheckerFactory.createBeanChecker(SimpleBean.class);
 *      // get the bean
 * 	    SimpleBean messageBean = beanChecker.getBeanProxy();
 *
 *      // call setters, getters, other methods on the bean
 *		messageBean.setAuthor("vlad");
 *		messageBean.setBody("text body");
 *		messageBean.setDate(new Date());
 *
 *		// check that mandatory setters have been called
 *		beanChecker.mandatorySettersCalled());
 * 
 */
public interface BeanChecker<T> {

	/**
	  * @return the bean to be used
	  */

	T getBeanProxy();

	/**
	 * Checks if mandatory setters have been called, and it throws an exception if not.
	 * (Mandatory setters are those who are not marked with the @OptionalSetter annotation).
	 * 
	 * @throws IllegalStateException
	 */

	void checkMandatorySettersCalled() throws IllegalStateException;

	/**
	 * Tells if all mandatory settings have been called.
	 * (Mandatory setters are those who are not marked with the @OptionalSetter annotation).
	 */

	boolean mandatorySettersCalled();

	/**
	 * @return all setters of the bean class
	 */

	Set<Method> getSetters();

	/**
	 * @return called setters for that bean
	 */

	Set<Method> getCalledSetters();

	/**
	 * @return missing(uncalled) setters for that bean
	 */

	Set<Method> getMissingSetters();

}