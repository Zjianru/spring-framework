package com.cz.demo.test;

import com.cz.demo.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

/**
 * code desc
 *
 * @author Zjianru
 */
public class UserTest {
	@Test
	public void contextTest() {
		ApplicationContext context = new ClassPathXmlApplicationContext("demoTest.xml");
		// 显示或者隐式地调用 BeanFactory#getBean(String name) 方法时，则会触发加载 Bean 阶段
		// 假设配置了一个 FactoryBean 的名字为 "abc" ，那么获取 FactoryBean 创建的 Bean 时，使用 "abc" ，如果获取 FactoryBean 本身，使用 "$abc"
		User demoTest = (User) context.getBean("demoTest");
		// bean 实例需要实现 FactoryBean 接口
//		Object factoryBean = context.getBean("&demoTest");
//		System.out.println(factoryBean);
		System.out.println(demoTest);
	}

	@Test
	public void resourceTest() {
		DefaultResourceLoader defaultResourceLoader = new DefaultResourceLoader();
		Resource resource = defaultResourceLoader.getResource("http://www.baidu.com");
		System.out.println("urlResource1 is urlResource:" + (resource instanceof UrlResource));
	}

	@Test
	public void loadContainerTest() {
		// <1> 获取资源
		ClassPathResource resource = new ClassPathResource("bean.xml");
		// <2> 获取 BeanFactory
		DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
		// <3> 根据新建的 BeanFactory 创建一个 BeanDefinitionReader 对象，该 Reader 对象为资源的解析器
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
		// <4> 装载资源
		reader.loadBeanDefinitions(resource);
	}


}
