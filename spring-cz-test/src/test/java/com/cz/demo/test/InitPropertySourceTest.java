package com.cz.demo.test;

import com.cz.demo.extendedPoint.InitPropertySourcesApplicationContext;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;

/**
 * InitPropertySource 扩展点测试
 *
 * @author Zjianru
 */
public class InitPropertySourceTest {

	/**
	 * 测试是否可以取到自定义的系统属性
	 */
	@Test
	public void test() {
		InitPropertySourcesApplicationContext context = new InitPropertySourcesApplicationContext("demoTest.xml");
		Object addedProperty = context.getEnvironment().getSystemProperties().get("testCommonProperty");
		System.out.println(addedProperty);
	}
}
