package com.cz.demo.InitializingBean;

import org.springframework.beans.factory.InitializingBean;

/**
 * code desc
 *
 * @author Zjianru
 */
public class TestInitializingBean implements InitializingBean {

	private String beanName;


	/**
	 * Invoked by the containing {@code BeanFactory} after it has set all bean properties
	 * and satisfied {@link BeanFactoryAware}, {@code ApplicationContextAware} etc.
	 * <p>This method allows the bean instance to perform validation of its overall
	 * configuration and final initialization when all bean properties have been set.
	 *
	 * @throws Exception in the event of misconfiguration (such as failure to set an
	 *                   essential property) or if initialization fails for any other reason
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("InitializingBeanTest initializing...");
		this.beanName = "codez 2 号";
	}

	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}
}
