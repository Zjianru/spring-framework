package com.cz.demo.aware;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;

/**
 * code desc
 *
 * @author Zjianru
 */
public class TestAware implements BeanNameAware, BeanFactoryAware, BeanClassLoaderAware {

	private String beanName;

	private BeanFactory beanFactory;

	private ClassLoader classLoader;

	private ApplicationContext applicationContext;


	/**
	 * Callback that supplies the bean {@link ClassLoader class loader} to
	 * a bean instance.
	 * <p>Invoked <i>after</i> the population of normal bean properties but
	 * <i>before</i> an initialization callback such as
	 * {@link InitializingBean InitializingBean's}
	 * {@link InitializingBean#afterPropertiesSet()}
	 * method or a custom init-method.
	 * 将bean {@link ClassLoader类加载器} 提供给bean实例的回调。
	 * <p>
	 * 在 [ 填充普通bean属性之后调用 ] ，但在 [ 之前调用 ] 初始化回调
	 * 例如 {@link InitializingBean的} {@link InitializingBeanafterPropertiesSet()} 方法或自定义init方法。
	 * <p>
	 * 将 BeanClassLoader 提供给 bean 实例回调
	 * 在 bean 属性填充之后、初始化回调之前回调，
	 * 例如InitializingBean的InitializingBean.afterPropertiesSet（）方法或自定义init方法
	 *
	 * @param classLoader the owning class loader
	 */
	@Override
	public void setBeanClassLoader(ClassLoader classLoader) {
		System.out.println("调用了 BeanClassLoaderAware 的 setBeanClassLoader 方法");
		this.classLoader = classLoader;
	}

	/**
	 * Callback that supplies the owning factory to a bean instance.
	 * <p>Invoked after the population of normal bean properties
	 * but before an initialization callback such as
	 * {@link InitializingBean#afterPropertiesSet()} or a custom init-method.
	 * 将 BeanFactory 提供给 bean 实例回调
	 * 调用时机和 setBeanClassLoader 一样
	 *
	 * @param beanFactory owning BeanFactory (never {@code null}).
	 *                    The bean can immediately call methods on the factory.
	 * @throws BeansException in case of initialization errors
	 * @see BeanInitializationException
	 */
	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		System.out.println("调用了 BeanFactoryAware 的 setBeanFactory 方法");
		this.beanFactory = beanFactory;
	}

	/**
	 * Set the name of the bean in the bean factory that created this bean.
	 * <p>Invoked after population of normal bean properties but before an
	 * init callback such as {@link InitializingBean#afterPropertiesSet()}
	 * or a custom init-method.
	 *
	 * @param name the name of the bean in the factory.
	 *             Note that this name is the actual bean name used in the factory, which may
	 *             differ from the originally specified name: in particular for inner bean
	 *             names, the actual bean name might have been made unique through appending
	 *             "#..." suffixes. Use the {@link BeanFactoryUtils#originalBeanName(String)}
	 *             method to extract the original bean name (without suffix), if desired.
	 */
	@Override
	public void setBeanName(String name) {
		System.out.println("调用了 BeanNameAware 的 setBeanName 方法");
		this.beanName = name;
	}

	public void display(){
		System.out.println("beanName:" + beanName);
		System.out.println("是否为单例：" + beanFactory.isSingleton(beanName));
		System.out.println("系统环境为：" + applicationContext.getEnvironment());
	}
}
