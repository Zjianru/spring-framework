package com.cz.demo.beanFactoryPostProcessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.Ordered;

/**
 * code desc
 *
 * @author Zjianru
 */
public class TestBeanFactoryPostProcessor1 implements Ordered, BeanFactoryPostProcessor {
	/**
	 * Modify the application context's internal bean factory after its standard
	 * initialization. All bean definitions will have been loaded, but no beans
	 * will have been instantiated yet. This allows for overriding or adding
	 * properties even to eager-initializing beans.
	 * 在标准初始化后修改应用程序上下文的内部bean工厂
	 * 将加载所有bean定义，但尚未实例化任何bean
	 * 这允许覆盖或添加属性，甚至是对急于初始化的bean
	 *
	 * @param beanFactory the bean factory used by the application context
	 * @throws BeansException in case of errors
	 */
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		System.out.println("调用 BeanFactoryPostProcessor_1 ...");

		System.out.println("容器中有 BeanDefinition 的个数：" + beanFactory.getBeanDefinitionCount());

		// 获取指定的 BeanDefinition
		BeanDefinition bd = beanFactory.getBeanDefinition("demoTest1");

		MutablePropertyValues pvs = bd.getPropertyValues();

		pvs.addPropertyValue("name", getClass().getCanonicalName() + pvs.get("name"));
		pvs.addPropertyValue("age", 15);

	}

	/**
	 * Get the order value of this object.
	 * <p>Higher values are interpreted as lower priority. As a consequence,
	 * the object with the lowest value has the highest priority (somewhat
	 * analogous to Servlet {@code load-on-startup} values).
	 * <p>Same order values will result in arbitrary sort positions for the
	 * affected objects.
	 *
	 * @return the order value
	 * @see #HIGHEST_PRECEDENCE
	 * @see #LOWEST_PRECEDENCE
	 */
	@Override
	public int getOrder() {
		return 1;
	}
}
