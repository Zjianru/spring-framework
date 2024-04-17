# SPRING 源码阅读笔记

## Spring 资源加载过程简要总结：

Spring 提供了 `Resource` 和 `ResourceLoader` 来统一抽象整个资源及其定位。使得资源与资源的定位有了一个更加清晰的界限，并且提供了合适的 Default 类，使得自定义实现更加方便和清晰。

`AbstractResource` 为 Resource 的默认抽象实现，它对 Resource 接口做了一个统一的实现，子类继承该类后只需要覆盖相应的方法即可，同时对于自定义的 Resource 我们也是继承该类。

`DefaultResourceLoader` 同样也是 `ResourceLoader` 的默认实现，在自定 `ResourceLoader` 的时候我们除了可以继承该类外还可以实现 `ProtocolResolver` 接口来实现自定资源加载协议。

`DefaultResourceLoader` 每次只能返回单一的资源，所以 Spring 针对这个提供了另外一个接口 `ResourcePatternResolver` ，该接口提供了根据指定的 locationPattern 返回多个资源的策略。
其子类 `PathMatchingResourcePatternResolver` 是一个集大成者的 `ResourceLoader` ，不仅实现了 `Resource getResource(String location)` 方法，也实现了 `Resource[] getResources(String locationPattern) `方法

## BeanDefinition 的解析过程
`doLoadBeanDefinitions(InputSource inputSource, Resource resource)` 方法整体时序图

红框部分，就是 BeanDefinition 的解析过程

<img alt="img1.png" src="noteImg/doLoadBeanDefinitions时序图.png"/>

## 加载 bean 阶段
Spring IoC 容器会以某种方式加载 Configuration Metadata，将其解析注册到容器内部，然后回根据这些信息绑定整个系统的对象，最终组装成一个可用的基于轻量级容器的应用系统。

Spring 在实现中将整个流程分为两个阶段：容器初始化阶段 和 加载 bean 阶段。分别如下：

### 容器初始化阶段：

1. 通过某种方式加载 Configuration Metadata (主要是依据 Resource、ResourceLoader 两个体系) 。
2. 容器会对加载的 Configuration MetaData 进行解析和分析，并将分析的信息组装成 BeanDefinition 。
3. 将 BeanDefinition 保存注册到相应的 BeanDefinitionRegistry 中。

至此，Spring IoC 的初始化工作完成

### 加载 Bean 阶段：

经过容器初始化阶段后，应用程序中定义的 bean 信息已经全部加载到系统中了，当我们显示或者隐式地调用 `BeanFactory#getBean(...)` 方法时，则会触发加载 Bean 阶段

在这阶段，容器会首先检查所请求的对象是否已经初始化完成了，如果没有，则会根据注册的 Bean 信息实例化请求的对象，并为其注册依赖，然后将其返回给请求方

至此第二个阶段也已经完成

## 循环依赖
描述下 Spring 解决循环依赖的过程：

首先 A 完成初始化第一步并将自己提前曝光出来（通过 ObjectFactory 将自己提前曝光）， 在 A 初始化的时候，发现自己依赖对象 B，此时就会去尝试 get(B)，这个时候发现 B 还没有被创建出来

然后 B 就走创建流程，在 B 初始化的时候，同样发现自己依赖 C，C 也没有被创建出来

这个时候 C 又开始初始化进程，但是在初始化的过程中发现自己依赖 A，于是尝试 get(A)
这个时候由于 A 已经添加至缓存中（一般都是添加至三级缓存 singletonFactories ），通过 ObjectFactory 提前曝光，所以可以通过 ObjectFactory#getObject() 方法来拿到 A 对象，
C 拿到 A 对象后顺利完成初始化，然后将自己添加到一级缓存中

回到 B ，B 也可以拿到 C 对象，完成初始化

A 可以顺利拿到 B 完成初始化。

<img alt="img2.png" src="noteImg/spring处理bean创建时的依赖循环.png"/>

到这里整个初始化过程就已经完成了

Spring解决循环依赖的核心思想在于提前曝光：

通过构建函数创建A对象（A对象是半成品，还没注入属性和调用init方法）。
A对象需要注入B对象，发现缓存里还没有B对象，将半成品对象A放入半成品缓存。
通过构建函数创建B对象（B对象是半成品，还没注入属性和调用init方法）。
B对象需要注入A对象，从半成品缓存里取到半成品对象A。
B对象继续注入其他属性和初始化，之后将完成品B对象放入完成品缓存。
A对象继续注入属性，从完成品缓存中取到完成品B对象并注入。
A对象继续注入其他属性和初始化，之后将完成品A对象放入完成品缓存。



| 缓存                     | 说明                                                    |
|------------------------|-------------------------------------------------------|
| singletonObjects	      | 第一级缓存，存放可用的成品Bean。                                    |
| earlySingletonObjects	 | 第二级缓存，存放半成品的Bean，半成品的Bean是已创建对象，但是未注入属性和初始化。用以解决循环依赖。 |
| singletonFactories	    | 第三级缓存，存的是Bean工厂对象，用来生成半成品的Bean并放入到二级缓存中。用以解决循环依赖。     |



### 为什么要包装一层ObjectFactory对象？

如果创建的Bean有对应的代理，那其他对象注入时，注入的应该是对应的代理对象；但是Spring无法提前知道这个对象是不是有循环依赖的情况，而正常情况下（没有循环依赖情况），Spring都是在创建好完成品Bean之后才创建对应的代理。这时候Spring有两个选择：

1. 不管有没有循环依赖，都提前创建好代理对象，并将代理对象放入缓存，出现循环依赖时，其他对象直接就可以取到代理对象并注入。
2. 不提前创建好代理对象，在出现循环依赖被其他对象注入时，才实时生成代理对象。这样在没有循环依赖的情况下，Bean就可以按着Spring设计原则的步骤来创建。

Spring选择了第二种方式，那怎么做到提前曝光对象而又不生成代理呢？
Spring就是在对象外面包一层ObjectFactory，提前曝光的是ObjectFactory对象，在被注入时才在`ObjectFactory.getObject`方式内实时生成代理对象，并将生成好的代理对象放入到第二级缓存`Map<String, Object> earlySingletonObjects`

`addSingletonFactory(beanName, () -> getEarlyBeanReference(beanName, mbd, bean));`

### 为什么 Spring 不选择二级缓存方式，而是要额外加一层缓存？

如果要使用二级缓存解决循环依赖，意味着Bean在构造完后就创建代理对象，这样违背了Spring设计原则(延迟实例化)。

Spring 结合 AOP 跟 Bean 的生命周期，是在 Bean 创建完全之后通过`AnnotationAwareAspectJAutoProxyCreator`这个后置处理器来完成的

在这个后置处理的 `postProcessAfterInitialization` 方法中对初始化后的 Bean 完成 AOP 代理。

如果出现了循环依赖，那没有办法，只有给 Bean 先创建代理，但是没有出现循环依赖的情况下，设计之初就是让 Bean 在生命周期的最后一步完成代理而不是在实例化后就立马完成代理。