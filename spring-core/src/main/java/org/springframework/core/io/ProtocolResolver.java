/*
 * Copyright 2002-2016 the original author or authors.
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

package org.springframework.core.io;

import org.springframework.lang.Nullable;

/**
 * A resolution strategy for protocol-specific resource handles.
 *
 * <p>Used as an SPI for {@link DefaultResourceLoader}, allowing for
 * custom protocols to be handled without subclassing the loader
 * implementation (or application context implementation).
 *
 * @author Juergen Hoeller
 * @see DefaultResourceLoader#addProtocolResolver
 * 用户自定义协议资源解决策略
 * 作为 DefaultResourceLoader 的 SPI：它允许用户自定义资源加载协议，而不需要继承 ResourceLoader 的子类
 * 不需要直接继承 DefaultResourceLoader，改为实现 ProtocolResolver 接口也可以实现自定义的 ResourceLoader
 * <p>
 * 调用 DefaultResourceLoader#addProtocolResolver(ProtocolResolver) 方法
 * 即可把自定义的 Resolver 如何加入 Spring 体系
 * <p>
 * @since 4.3
 */
@FunctionalInterface
public interface ProtocolResolver {

	/**
	 * Resolve the given location against the given resource loader
	 * if this implementation's protocol matches.
	 *
	 * @param location       the user-specified resource location
	 * @param resourceLoader the associated resource loader
	 * @return a corresponding {@code Resource} handle if the given location
	 * matches this resolver's protocol, or {@code null} otherwise
	 * 使用指定的 ResourceLoader ，解析指定的 location
	 * 若成功，则返回对应的 Resource
	 */
	@Nullable
	Resource resolve(String location, ResourceLoader resourceLoader);

}
