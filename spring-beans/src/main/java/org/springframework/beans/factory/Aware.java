/*
 * Copyright 2002-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.beans.factory;

/**
 * A marker superinterface indicating that a bean is eligible to be notified by the
 * Spring container of a particular framework object through a callback-style method.
 * The actual method signature is determined by individual subinterfaces but should
 * typically consist of just one void-returning method that accepts a single argument.
 *
 * <p>
 * Note that merely implementing {@link Aware} provides no default functionality.
 * Rather, processing must be done explicitly, for example in a
 * {@link org.springframework.beans.factory.config.BeanPostProcessor}.
 * Refer to {@link org.springframework.context.support.ApplicationContextAwareProcessor}
 * for an example of processing specific {@code *Aware} interface callbacks.
 * <p>
 * 一个标记超级接口，指示 bean 有资格通过回调样式的方法由特定框架对象的 Spring 容器通知
 * 实际的方法签名由各个子接口确定，但通常应该只包含一个接受单个参数的 void 返回方法
 * <p>
 * 请注意，仅实现 {@link Aware} 不提供默认功能。相反，处理必须显式完成，例如在 {@link org.springframework.beans.factory.config.BeanPostProcessor} 中
 * 有关处理特定 {@code Aware} 接口回调的示例，请参阅 {@link org.springframework.context.support.ApplicationContextAwareProcessor}。
 * Aware 接口是一个空接口，实际的方法签名由各个子接口来确定，且该接口通常只会有一个接收单参数的 set 方法
 * 该 set 方法的命名方式为 set + 去掉接口名中的 Aware 后缀，即 XxxAware 接口，则方法定义为 setXxx()
 * 例如 BeanNameAware（setBeanName），ApplicationContextAware（setApplicationContext）。 * @author Chris Beams
 *
 * @author Juergen Hoeller
 * @since 3.1
 */
public interface Aware {

}
