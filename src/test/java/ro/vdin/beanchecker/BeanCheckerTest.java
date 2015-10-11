package ro.vdin.beanchecker;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import ro.vdin.beanchecker.testbeans.ExtendedBean;
import ro.vdin.beanchecker.testbeans.SimpleBean;

public class BeanCheckerTest {
	@Test
	public void testNormalBeanMandatorySettersCalled() {
		BeanChecker<SimpleBean> beanChecker = BeanCheckerFactory.createBeanChecker(SimpleBean.class);
		SimpleBean messageBean = beanChecker.getBeanProxy();

		Assert.assertFalse(beanChecker.mandatorySettersCalled());

		messageBean.setAuthor("vlad");
		//		messageBean.setBody("body");

		Assert.assertFalse(beanChecker.mandatorySettersCalled());
		try {
			beanChecker.checkMandatorySettersCalled();
			fail();
		} catch (IllegalStateException e) {
			// expected
		}
		Assert.assertEquals(1, beanChecker.getMissingSetters().size());
		messageBean.setWhen(new Date());

		Assert.assertEquals("vlad", messageBean.getAuthor());

		Assert.assertTrue(beanChecker.mandatorySettersCalled());
		Assert.assertEquals(0, beanChecker.getMissingSetters().size());

		beanChecker.checkMandatorySettersCalled();
	}

	@Test
	public void testExtendedBean() {
		BeanChecker<ExtendedBean> beanChecker = BeanCheckerFactory.createBeanChecker(ExtendedBean.class);
		ExtendedBean bean = beanChecker.getBeanProxy();

		bean.setExtendedProp1(2);
		bean.setExtendedProp2("ext2");

		Assert.assertFalse(beanChecker.mandatorySettersCalled());
		Assert.assertEquals(2, beanChecker.getMissingSetters().size());

		bean.setAuthor("author");
		bean.setBody("body");
		bean.setWhen(new Date());

		Assert.assertTrue(beanChecker.mandatorySettersCalled());
	}

}
