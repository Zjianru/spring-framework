package com.cz.demo.BeanProcessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.Assert;

/**
 * TestBeanPostProcessor
 *
 * @author Zjianru
 */
public class TestBeanPostProcessor implements BeanPostProcessor {

	/**
	 * Custom bean before-processing
	 * 自定义 bean 前置处理
	 *
	 * @param bean     the new bean instance
	 * @param beanName the name of the bean
	 * @return the bean instance to use, either the original or a replacement
	 * @throws BeansException exception
	 */
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

		Assert.notNull(bean, "bean is null !");
		Assert.notNull(beanName, "beanName is null !");

		System.out.println("Bean [" + beanName + "] 开始初始化");
		// 这里一定要返回 bean，不能返回 null
		return bean;

	}

	/**
	 * Custom bean post-processing
	 * 自定义 bean 后置处理
	 *
	 * @param bean     the new bean instance
	 * @param beanName the name of the bean
	 * @return the bean instance to use, either the original or a replacement
	 * @throws BeansException exception
	 */
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		System.out.println("Bean [" + beanName + "] 完成初始化");
		return bean;
	}

	public void display() {
		System.out.println("hello BeanPostProcessor!!!");
	}

}
