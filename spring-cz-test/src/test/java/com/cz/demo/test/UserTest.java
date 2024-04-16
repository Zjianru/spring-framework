package com.cz.demo.test;

import com.cz.demo.pojo.User;
import org.junit.jupiter.api.Test;
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
		User demoTest = (User) context.getBean("demoTest");
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
