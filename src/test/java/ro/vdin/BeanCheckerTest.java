package ro.vdin;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import ro.vdin.testbeans.ExtendedBean;
import ro.vdin.testbeans.SimpleBean;

public class BeanCheckerTest {
	@Test
	public void testNormalBeanAllSettersCalled() {
		BeanChecker<SimpleBean> beanChecker = new BeanCheckerImpl<>(SimpleBean.class);
		SimpleBean messageBean = beanChecker.getBeanProxy();

		messageBean.setAuthor("vlad");
		messageBean.setBody("body");
		messageBean.setWhen(new Date());

		Assert.assertEquals("vlad", messageBean.getAuthor());

		Assert.assertTrue(beanChecker.mandatorySettersCalled());
		Assert.assertTrue(beanChecker.allSettersCalled());
	}

	@Test
	public void testNormalBeanMandatorySettersCalled() {
		BeanChecker<SimpleBean> beanChecker = new BeanCheckerImpl<>(SimpleBean.class);
		SimpleBean messageBean = beanChecker.getBeanProxy();

		messageBean.setAuthor("vlad");
		//		messageBean.setBody("body");
		messageBean.setWhen(new Date());

		Assert.assertEquals("vlad", messageBean.getAuthor());

		Assert.assertTrue(beanChecker.mandatorySettersCalled());
		Assert.assertFalse(beanChecker.allSettersCalled());
		Assert.assertEquals(1, beanChecker.getMissingSetters().size());
	}

	@Test
	public void testExtendedBean() {
		BeanChecker<ExtendedBean> beanChecker = new BeanCheckerImpl<>(ExtendedBean.class);
		ExtendedBean bean = beanChecker.getBeanProxy();

		bean.setExtendedProp1(2);
		bean.setExtendedProp2("ext2");

		Assert.assertFalse(beanChecker.allSettersCalled());

		bean.setAuthor("author");
		bean.setBody("body");
		bean.setWhen(new Date());

		Assert.assertTrue(beanChecker.allSettersCalled());
	}

}
