package com.cz.demo.test;

import com.cz.demo.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * code desc
 *
 * @author Zjianru
 */
public class UserTest {
	@Test
	public void test() {
		ApplicationContext context = new ClassPathXmlApplicationContext("demoTest.xml");
		User demoTest = (User) context.getBean("demoTest");
		System.out.println(demoTest);
	}
}
