package com.cz.demo.extendedPoint;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.Map;

/**
 * 扩展 InitPropertySources
 *
 * @author Zjianru
 */
public class InitPropertySourcesApplicationContext extends ClassPathXmlApplicationContext {

	/**
	 * Create a new ClassPathXmlApplicationContext for bean-style configuration.
	 *
	 * @see #setConfigLocation
	 * @see #setConfigLocations
	 * @see #afterPropertiesSet()
	 */
	public InitPropertySourcesApplicationContext(String... configLocations) {
		super(configLocations);
	}

	/**
	 * 自定义扩展点
	 */
	@Override
	protected void initPropertySources() {
		// 当前系统环境
		ConfigurableEnvironment environment = getEnvironment();
		// 当前系统环境变量
		Map<String, Object> systemEnvironment = environment.getSystemProperties();
		// 查看当前所有的系统环境变量
//		systemEnvironment.forEach((k, v) -> System.out.println("k-->" + k + "v-->" + v));
		// 添加必要属性，无此属性将会终止启动容器
//		getEnvironment().setRequiredProperties("testRequiredProperty");
		// 已测试，报错如下，添加系统属性后，不再报错
		// org.springframework.core.env.MissingRequiredPropertiesException: The following properties were declared as required but could not be resolved: [testRequiredProperty]
		// 添加系统属性
		environment.getSystemProperties().put("testCommonProperty", "check");
	}
}
