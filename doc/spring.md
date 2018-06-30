# 一、Spring简介

## 1.1 Spring是什么

Spring 是一个开源框架.

Spring 为简化企业级应用开发而生. 使用 Spring 可以使简单的 JavaBean 实现以前只有 EJB 才能实现的功能.

Spring 是一个 IOC(DI) 和 AOP 容器框架.

**具体描述 Spring:**

- 轻量级：Spring 是非侵入性的 - 基于 Spring 开发的应用中的对象可以不依赖于 Spring 的 API

- 依赖注入(DI --- dependency injection、IOC)

- 面向切面编程(AOP --- aspect oriented programming)

- 容器: Spring 是一个容器, 因为它包含并且管理应用对象的生命周期

- 框架: Spring 实现了使用简单的组件配置组合成一个复杂的应用. 在 Spring 中可以使用 XML 和 Java 注解组合这些对象

- 一站式：在 IOC 和 AOP 的基础上可以整合各种企业应用的开源框架和优秀的第三方类库 （实际上 Spring 自身也提供了展现层的 SpringMVC 和 持久层的 Spring JDBC）

## 1.2 Spring模块

![image](https://user-images.githubusercontent.com/16509581/42025690-c718e2ea-7af7-11e8-8716-d21235b15aed.png)

# 二、Spring中的Bean配置

## 2.1 IOC和DI

**IOC(Inversion of Control)**：其思想是**反转资源获取的方向**. 传统的资源查找方式要求组件向容器发起请求查找资源. 作为回应, 容器适时的返回资源. 而应用了 IOC 之后, 则是<u>容器主动地将资源推送给它所管理的组件, 组件所要做的仅是选择一种合适的方式来接受资源</u>. 这种行为也被称为查找的被动形式

DI(Dependency Injection) — IOC 的另一种表述方式：即<u>组件以一些预先定义好的方式(例如: setter 方法)接受来自如**容器**的资源注入.</u> 相对于 IOC 而言，这种表述更直接。

**IOC的演化过程**

需求: 生成 HTML 或 PDF 格式的不同类型的报表. 

1、分离接口与实现 



![image](https://user-images.githubusercontent.com/16509581/42026760-93b7d91c-7afa-11e8-8701-6f5c4388a1dc.png)

2、采用工厂设计模式 

![image](https://user-images.githubusercontent.com/16509581/42026847-c51844ec-7afa-11e8-86bf-d92a3b5fa059.png)

3、采用反转控制 

![image](https://user-images.githubusercontent.com/16509581/42026855-c860906e-7afa-11e8-8bce-ddb42fe6aae4.png)

## 2.2 配置Bean

**配置 bean**

-  配置形式：基于 XML 文件的方式；基于注解的方式

- Bean 的配置方式：通过全类名（反射）、通过工厂方法（静态工厂方法 & 实例工厂方法）、FactoryBean

- IOC 容器 BeanFactory & ApplicationContext 概述

- 依赖注入的方式：属性注入；构造器注入

- 注入属性值细节

- 自动转配

- bean 之间的关系：继承；依赖

- bean 的作用域：singleton；prototype；WEB 环境作用域

- 使用外部属性文件

- spEL 

- IOC 容器中 Bean 的生命周期

- Spring 4.x 新特性：泛型依赖注入

### 2.2.1 Spring容器

在 Spring IOC 容器读取 Bean 配置创建 Bean 实例之前, 必须对它进行实例化. 只有在容器实例化后, 才可以从 IOC 容器里获取 Bean 实例并使用.

Spring 提供了两种类型的 IOC 容器实现. 

- BeanFactory: IOC 容器的基本实现.

- ApplicationContext: 提供了更多的高级特性. 是 BeanFactory 的子接口.

- BeanFactory 是 Spring 框架的基础设施，面向 Spring 本身；ApplicationContext 面向使用 Spring 框架的开发者，**几乎所有的应用场合都直接使用 ApplicationContext** 而非底层的 BeanFactory

- 无论使用何种方式, 配置文件时相同的.

### 2.2.2 ApplicationContext 

![image](https://user-images.githubusercontent.com/16509581/42067535-3d6c7f98-7b79-11e8-809f-f04faba010e4.png)

ApplicationContext 的主要实现类：

- ClassPathXmlApplicationContext：从类路径下加载配置文件
- FileSystemXmlApplicationContext: 从文件系统中加载配置文件

**ConfigurableApplicationContext** 扩展于 ApplicationContext，新增加两个主要方法：refresh() 和 close()， 让 ApplicationContext 具有**启动、刷新和关闭上下文**的能力

ApplicationContext 在初始化上下文时就实例化所有单例的 Bean。

WebApplicationContext 是专门为 WEB 应用而准备的，它允许从相对于 WEB 根目录的路径中完成初始化工作

### 2.2.3 从IOC容器中获取Bean的方法

调用 ApplicationContext 的 getBean() 方法 

![image](https://user-images.githubusercontent.com/16509581/42067628-af762f12-7b79-11e8-8ae7-5769887c367c.png)

### 2.2.4 依赖注入的方式

- 属性注入
  - 属性注入即通过 setter 方法注入Bean 的属性值或依赖的对象
  - 属性注入使用 <property> 元素, 使用 name 属性指定 Bean 的属性名称，value 属性或 <value> 子节点指定属性值 
  - 属性注入是实际应用中最常用的注入方式
- 构造器注入
  - 通过构造方法注入Bean 的属性值或依赖的对象，它保证了 Bean 实例在实例化后就可以使用。
  - 构造器注入在 <constructor-arg> 元素里声明属性, <constructor-arg> 中没有 name 属性

### 2.2.5 注入属性值的细节

- 字面值

  - 字面值：可用字符串表示的值，可以通过 <value> 元素标签或 value 属性进行注入。
  - 基本数据类型及其封装类、String 等类型都可以采取字面值注入的方式
  - 若字面值中包含特殊字符，可以使用 <![CDATA[]]> 把字面值包裹起来。

- 引用其他Bean

  - ref引用

  ![image](https://user-images.githubusercontent.com/16509581/42068140-d8fd6df2-7b7c-11e8-83b2-ec2f12f0cc9e.png)

  - 内部Bean
    - 当 Bean 实例仅仅给一个特定的属性使用时, 可以将其声明为内部 Bean. 内部 Bean 声明直接包含在 <property> 或 <constructor-arg> 元素里, 不需要设置任何 id 或 name 属性
    - 内部 Bean 不能使用在任何其他地方，即不能被外部引用
  - 注入参数详解：null 值和级联属性 
    - 可以使用专用的 <null/> 元素标签为 Bean 的字符串或其它对象类型的属性注入 null 值
    - 和 Struts、Hiberante 等框架一样，Spring 支持级联属性的配置。

- 集合属性

  - List及Set
    - 在 Spring中可以通过一组内置的 xml 标签(例如: <list>, <set> 或 <map>) 来配置集合属性.
    - 配置 java.util.List 类型的属性, 需要指定 <list>  标签, 在标签里包含一些元素. 这些标签可以通过 <value> 指定简单的常量值, 通过 <ref> 指定对其他 Bean 的引用. 通过<bean> 指定内置 Bean 定义. 通过 <null/> 指定空元素. 甚至可以内嵌其他集合.
    - 数组的定义和 List 一样, 都使用 <list>
    - 配置 java.util.Set 需要使用 <set> 标签, 定义元素的方法与 List 一样.
  - Map
    - Java.util.Map 通过 <map> 标签定义, <map> 标签里可以使用多个 <entry> 作为子标签. 每个条目包含一个键和一个值. 
    - 必须在 <key> 标签里定义键
    - 因为键和值的类型没有限制, 所以可以自由地为它们指定 <value>, <ref>, <bean> 或 <null> 元素. 
    - 可以将 Map 的键和值作为 <entry> 的属性定义: 简单常量使用 key 和 value 来定义; Bean 引用通过 key-ref 和 value-ref 属性定义
    - 使用 <props> 定义 java.util.Properties, 该标签使用多个 <prop> 作为子标签. 每个 <prop> 标签必须定义 key 属性. 

- 使用 utility scheme 定义集合 

  - 使用基本的集合标签定义集合时, 不能将集合作为独立的 Bean 定义, 导致其他 Bean 无法引用该集合, 所以无法在不同 Bean 之间共享集合.
  - 可以使用 util schema 里的集合标签定义独立的集合 Bean. 需要注意的是, 必须在 <beans> 根元素里添加 util schema 定义

- 使用 p 命名空间 

  - 为了简化 XML 文件的配置，越来越多的 XML 文件采用属性而非子元素配置信息。
  - Spring 从 2.5 版本开始引入了一个新的 p 命名空间，可以通过 <bean> 元素属性的方式配置 Bean 的属性。
  - 使用 p 命名空间后，基于 XML 的配置方式将进一步简化

### 2.2.6 自动装配

- XML 配置里的 Bean 自动装配 
  - Spring IOC 容器可以自动装配 Bean. 需要做的仅仅是在 <bean> 的 autowire 属性里指定自动装配的模式
  - byType(根据类型自动装配): 若 IOC 容器中有多个与目标 Bean 类型一致的 Bean. 在这种情况下, Spring 将无法判定哪个 Bean 最合适该属性, 所以不能执行自动装配.
  - byName(根据名称自动装配): 必须将目标 Bean 的名称和属性名设置的完全相同.
  - constructor(通过构造器自动装配): 当 Bean 中存在多个构造器时, 此种自动装配方式将会很复杂. 不推荐使用
- XML 配置里的 Bean 自动装配的缺点 
  - 在 Bean 配置文件里设置 autowire 属性进行自动装配将会装配 Bean 的所有属性. 然而, 若只希望装配个别属性时, autowire 属性就不够灵活了. 
  - autowire 属性要么根据类型自动装配, 要么根据名称自动装配, 不能两者兼而有之.
  - 一般情况下，在实际的项目中很少使用自动装配功能，因为和自动装配功能所带来的好处比起来，明确清晰的配置文档更有说服力一些

### 2.2.7 Bean之间的关系：继承、依赖

- 继承 Bean 配置 

  - Spring 允许继承 bean 的配置, 被继承的 bean 称为父 bean. 继承这个父 Bean 的 Bean 称为子 Bean

    ```xml
    <bean id="address" class="com.meituan.spring.bean.Address" p:city="BeiJing" p:street="WuDaoKou"></bean>
    
    <bean id="address2" parent="address" p:street="DaZhongSi"></bean>
    ```

    

  - 子 Bean 从父 Bean 中继承配置, 包括 Bean 的属性配置

  - 子 Bean 也可以覆盖从父 Bean 继承过来的配置

  - 父 Bean 可以作为配置模板, 也可以作为 Bean 实例. 若只想把父 Bean 作为模板, 可以设置 <bean> 的abstract 属性为 true, 这样 Spring 将不会实例化这个 Bean

  - 并不是 <bean> 元素里的所有属性都会被继承. 比如: autowire, abstract 等.

  - 也可以忽略父 Bean 的 class 属性, 让子 Bean 指定自己的类, 而共享相同的属性配置. 但此时 abstract 必须设为 true

- 依赖 Bean 配置 

  - Spring 允许用户通过 depends-on 属性设定 Bean 前置依赖的Bean，前置依赖的 Bean 会在本 Bean 实例化之前创建好
  - 如果前置依赖于多个 Bean，则可以通过逗号，空格或的方式配置 Bean 的名称

### 2.2.8 Bean的作用域

- 在 Spring 中, 可以在 <bean> 元素的 scope 属性里设置 Bean 的作用域. 

- **默认情况下, Spring 只为每个在 IOC 容器里声明的 Bean 创建唯一一个实例, 整个 IOC 容器范围内都能共享该实例**：所有后续的 getBean() 调用和 Bean 引用都将返回这个唯一的 Bean 实例.该作用域被称为 **singleton**, 它是所有 Bean 的默认作用域.

  ![image](https://user-images.githubusercontent.com/16509581/42071615-cc32aa08-7b8e-11e8-9936-bac20d40ac45.png)

### 2.2.9 使用外部属性文件

- 在配置文件里配置 Bean 时, 有时需要在 Bean 的配置里混入系统部署的细节信息(例如: 文件路径, 数据源配置信息等). 而这些部署细节实际上需要和 Bean 配置相分离

- Spring 提供了一个 PropertyPlaceholderConfigurer 的 BeanFactory 后置处理器, 这个处理器允许用户将 Bean 配置的部分内容外移到属性文件中. 可以在 Bean 配置文件里使用形式为 ${var} 的变量, PropertyPlaceholderConfigurer 从属性文件里加载属性, 并使用这些属性来替换变量.

- Spring 还允许在属性文件中使用 ${propName}，以实现属性之间的相互引用。

- 注册 PropertyPlaceholderConfigurer 

  - Spring 2.5 之后: 可通过 <context:property-placeholder> 元素简化:

    <beans> 中添加 context Schema 定义

    在配置文件中加入如下配置: 

    ![image](https://user-images.githubusercontent.com/16509581/42071963-c3c3de44-7b90-11e8-8e9f-a34a786711cf.png)

### 2.2.10 Spring表达式语言：SpEL

- 简介
  - Spring 表达式语言（简称SpEL）：是一个支持运行时查询和操作对象图的强大的表达式语言。
  - 语法类似于 EL：SpEL 使用 #{…} 作为定界符，所有在大框号中的字符都将被认为是 SpEL
  - SpEL 为 bean 的属性进行动态赋值提供了便利
  - 通过 SpEL 可以实现：
    - 通过 bean 的 id 对 bean 进行引用
    - 调用方法以及引用对象中的属性
    - 计算表达式的值
    - 正则表达式的匹配

- SpEL表示字面量

  - 字面量的表示：
    - 整数：`<property name="count" value="#{5}"/>`

  - 小数：`<property name="frequency" value="#{89.7}"/>`

  - 科学计数法：`<property name="capacity" value="#{1e4}"/>`

  - String可以使用单引号或者双引号作为字符串的定界符号：`<property name=“name” value="#{'Chuck'}"/>` 或 `<property name='name' value='#{"Chuck"}'/>`

  - Boolean：`<property name="enabled" value="#{false}"/>`

- SpEL引用 Bean、属性和方法 

  - 引用其他对象： 

    ![image](https://user-images.githubusercontent.com/16509581/42076179-8f9f9e9e-7ba6-11e8-8313-c8fc25e84783.png)

  - 引用其他对象的属性 

    ![image](https://user-images.githubusercontent.com/16509581/42076180-8fa3ddb0-7ba6-11e8-9a50-7cd88ac99a4c.png)

  - 调用其他方法，还可以链式操作 ![image](https://user-images.githubusercontent.com/16509581/42076182-90fad632-7ba6-11e8-9223-83e34d216f79.png)

    ![image](https://user-images.githubusercontent.com/16509581/42076187-945dff52-7ba6-11e8-994b-24a32a67baa4.png)

  - 调用静态方法或静态属性：通过 T() 调用一个类的静态方法，它将返回一个 Class Object，然后再调用相应的方法或属性： 

    ![image](https://user-images.githubusercontent.com/16509581/42076287-1c74d226-7ba7-11e8-8583-e130a74a2aeb.png)

- SpEL还支持运算符号

### 2.2.11 IOC 容器中 Bean 的生命周期 

- IOC 容器中 Bean 的生命周期方法 

  - Spring IOC 容器可以管理 Bean 的生命周期, Spring 允许在 Bean 生命周期的特定点执行定制的任务. 
  - Spring IOC 容器对 Bean 的生命周期进行管理的过程:
    - 通过构造器或工厂方法创建 Bean 实例
    - 为 Bean 的属性设置值和对其他 Bean 的引用
    - **调用 Bean 的初始化方法**
    - Bean 可以使用了
    - **当容器关闭时, 调用 Bean 的销毁方法**
  - 在 Bean 的声明里设置 **init-method** 和 **destroy-method** 属性, 为 Bean **指定初始化和销毁方法.**

- 创建 Bean 后置处理器 

  - Bean 后置处理器允许在**调用初始化方法前后**对 Bean 进行额外的处理.

  - Bean 后置处理器对 IOC 容器里的所有 Bean 实例逐一处理, 而非单一实例. 其典型应用是: 检查 Bean 属性的正确性或根据特定的标准更改 Bean 的属性.

  - 对Bean 后置处理器而言, 需要实现`org.springframework.beans.factory.config.BeanPostProcessor`接口. 在**初始化方法被调用前后**, Spring 将把每个 Bean 实例分别传递给上述接口的以下两个方法:

    ![image](https://user-images.githubusercontent.com/16509581/42078057-aa975ece-7bad-11e8-9a9b-098d75854c36.png)

- 添加 Bean 后置处理器后 Bean 的生命周期 

  Spring IOC 容器对 Bean 的生命周期进行管理的过程:

  - 通过构造器或工厂方法创建 Bean 实例

  - 为 Bean 的属性设置值和对其他 Bean 的引用

  - 将 Bean 实例传递给 Bean 后置处理器的 postProcessBeforeInitialization 方法

  - 调用 Bean 的初始化方法

  - 将 Bean 实例传递给 Bean 后置处理器的 postProcessAfterInitialization方法

  - Bean 可以使用了

  - 当容器关闭时, 调用 Bean 的销毁方法

### 2.2.12 通过工厂方法配置Bean

- 通过调用静态工厂方法创建 Bean 

  - 调用静态工厂方法创建 Bean是将对象创建的过程封装到静态方法中. 当客户端需要对象时, 只需要简单地调用静态方法, 而不同关心创建对象的细节.
  - 要声明通过静态方法创建的 Bean, 需要在 Bean 的 class 属性里指定拥有该工厂的方法的类, 同时在 `factory-method`属性里指定工厂方法的名称. 最后, 使用 <constrctor-arg> 元素为该方法传递方法参数.

  ```xml
  <!--
  	class属性：指向静态工厂方法的全类名
  	factory-method：指向静态工厂方法的名字
  	constructor-arg：如果工厂方法需要传入参数，则使用constructor-arg来配置参数
  -->
  <bean id="car" class="com.meituan.spring.beans.factory.StaticCarFacotory" factory-method="getCar">
  	<constructor-arg value="audi"></constructor-arg>
  </bean>
  ```

- 通过调用实例工厂方法创建 Bean 

  - 实例工厂方法: 将对象的创建过程封装到另外一个对象实例的方法里. 当客户端需要请求对象时, 只需要简单的调用该实例方法而不需要关心对象的创建细节.

  - 要声明通过实例工厂方法创建的 Bean

    - 在 bean 的 factory-bean 属性里指定拥有该工厂方法的 Bean

    - 在 factory-method 属性里指定该工厂方法的名称

    - 使用 construtor-arg 元素为工厂方法传递方法参数

    ```xml
    <!--配置工厂的实例-->
    <bean id="carFactory" class="com.meituan.spring.beans.facotry.InstanceCarFactory"></bean>
    
    <!--通过实例工厂方法来配置bean-->
    <!--
    	factory-bean：指向实例工厂方法的bean
    	factory-method：指向静态工厂方法的名字
    	constructor-arg：如果工厂方法需要传入参数，则使用constructor-arg来配置参数
    -->
    <bean id="car" factory-bean="carFactory" factory-method="getCar">
    	<constructor-arg value="ford"></constructor-arg>
    </bean>
    ```

    

### 2.2.13 通过注解配置bean

- 在 classpath 中扫描组件 
  - 组件扫描(component scanning):  Spring 能够从 classpath 下自动扫描, 侦测和实例化具有特定注解的组件. 

  - 特定组件包括:
     - @Component: 基本注解, 标识了一个受 Spring 管理的组件
    - @Respository: 标识持久层组件
    - @Service: 标识服务层(业务层)组件
    - @Controller: 标识表现层组件

  - 对于扫描到的组件, Spring 有默认的命名策略: 使用非限定类名, 第一个字母小写. 也可以在注解中通过 value 属性值标识组件的名称

  - 当在组件类上使用了特定的注解之后, 还需要在 Spring 的配置文件中声明` <context:component-scan> `：
    - base-package 属性指定一个需要扫描的基类包，Spring 容器将会扫描这个基类包里及其子包中的所有类. 

    - 当需要扫描多个包时, 可以使用逗号分隔.

    - 如果仅希望扫描特定的类而非基包下的所有类，可使用 resource-pattern 属性过滤特定的类，示例：

      ![image](https://user-images.githubusercontent.com/16509581/42079614-bd69ca0a-7bb2-11e8-9a62-4f34ea53eea3.png)

    - `<context:include-filter>` 子节点表示要包含的目标类

    - `<context:exclude-filter>` 子节点表示要排除在外的目标类

    - `<context:component-scan> `下可以拥有若干个 `<context:include-filter>` 和` <context:exclude-filter> `子节点

- 组件装配

  `<context:component-scan>` 元素还会自动注册 AutowiredAnnotationBeanPostProcessor 实例, 该实例可以自动装配具有 @Autowired 和 @Resource 、@Inject注解的属性.  

- 使用 @Autowired 自动装配 Bean 

  @Autowired 注解自动装配具有兼容类型的单个 Bean属性

  - 构造器, 普通字段(即使是非 public), 一切具有参数的方法都可以应用@Authwired 注解

  - 默认情况下, 所有使用 @Authwired 注解的属性都需要被设置. 当 Spring 找不到匹配的 Bean 装配属性时, 会抛出异常, 若某一属性允许不被设置, 可以设置 @Authwired 注解的 required 属性为 false

  - 默认情况下, 当 IOC 容器里存在多个类型兼容的 Bean 时, 通过类型的自动装配将无法工作. 此时可以在 @Qualifier 注解里提供 Bean 的名称. Spring 允许对方法的入参标注 @Qualifiter 已指定注入 Bean 的名称

  -  @Authwired 注解也可以应用在数组类型的属性上, 此时 Spring 将会把所有匹配的 Bean 进行自动装配.

  - @Authwired 注解也可以应用在集合属性上, 此时 Spring 读取该集合的类型信息, 然后自动装配所有与之兼容的 Bean. 

  - @Authwired 注解用在 java.util.Map 上时, 若该 Map 的键值为 String, 那么 Spring 将自动装配与之 Map 值类型兼容的 Bean, 此时 Bean 的名称作为键值

- 使用 @Resource 或 @Inject   自动装配 Bean 

  - Spring 还支持 @Resource 和 @Inject 注解，这两个注解和 @Autowired 注解的功用类似
  - @Resource 注解要求提供一个 Bean 名称的属性，若该属性为空，则自动采用标注处的变量或方法名作为 Bean 的名称
  - @Inject 和 @Autowired 注解一样也是按类型匹配注入的 Bean， 但没有 reqired 属性
  - 建议使用 @Autowired 注解

### 2.2.14 泛型依赖注入

![image](https://user-images.githubusercontent.com/16509581/42085307-a46f9290-7bc2-11e8-92da-10be7e496d87.png)



# 三、AOP

![image](https://user-images.githubusercontent.com/16509581/42085850-1c1f2db8-7bc4-11e8-8c1c-cb0d0d5d35af.png)

需求1-日志：在程序执行期间追踪正在发生的活动

需求2-验证：希望计算器只能处理正数的运算

![image](https://user-images.githubusercontent.com/16509581/42086336-57180664-7bc5-11e8-8970-2354f4d37d8e.png)

问题：

- 代码混乱：越来越多的非业务需求(日志和验证等)加入后, 原有的业务方法急剧膨胀.  每个方法在处理核心逻辑的同时还必须兼顾其他多个关注点. 
- 代码分散: 以日志需求为例, 只是为了满足这个单一需求, 就不得不在多个模块（方法）里多次重复相同的日志代码. 如果日志需求发生变化, 必须修改所有模块.

## 3.1 使用动态代理解决上述问题

代理设计模式的原理: **使用一个代理将对象包装起来**, 然后用该代理对象取代原始对象. 任何对原始对象的调用都要通过代理. 代理对象决定是否以及何时将方法调用转到原始对象上. 

![image](https://user-images.githubusercontent.com/16509581/42086439-a984b7b2-7bc5-11e8-8c50-a168c6363e90.png)

代理类

```java
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class ArithmeticCalculatorLoggingProxy {
    private ArithmeticCalculator target;

    public void setTarget(ArithmeticCalculator target) {
        this.target = target;
    }

    public ArithmeticCalculatorLoggingProxy(ArithmeticCalculator target) {
        this.target = target;
    }

    public ArithmeticCalculator getLogginProxy() {
        ArithmeticCalculator proxy = null;

        //代理对象由哪一个类加载器负责加载
        ClassLoader loader = target.getClass().getClassLoader();
        //代理对象的类型，即其中有哪些方法
        Class[] interfaces = new Class[]{ArithmeticCalculator.class};
        //当调用代理对象中期的方法时，该执行的代码
        InvocationHandler h = new InvocationHandler() {
            /**
             * @param proxy  正在返回的那个代理对象，一般情况下，在invoke方法中ftjbn 不使用该对象
             * @param method 正在被调用的方法
             * @param args   调用方法时，传入的参数
             * @return
             * @throws Throwable
             */
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                String methodName = method.getName();
                System.out.println("The method " + methodName + "() begins with " + Arrays.asList(args));
                Object result = method.invoke(target, args);
                System.out.println("The method " + methodName + "() ends with " + result);
                return result;
            }
        };

        proxy = (ArithmeticCalculator) Proxy.newProxyInstance(loader, interfaces, h);

        return proxy;
    }
}
```

测试类

```java
    public static void main(String[] args) {
        ArithmeticCalculator target = new ArithmeticCalculatorImpl();
        ArithmeticCalculatorLoggingProxy proxy = new ArithmeticCalculatorLoggingProxy(target);
        ArithmeticCalculator logginProxy = proxy.getLogginProxy();
        logginProxy.add(1, 2);
    }
```

## 3.2 AOP简介

- AOP(Aspect-Oriented Programming, 面向切面编程): 是一种新的方法论, 是对传统 OOP(Object-Oriented Programming, 面向对象编程) 的补充.

- AOP 的主要编程对象是切面(aspect), 而切面模块化横切关注点.

- 在应用 AOP 编程时, 仍然需要定义公共功能, 但可以明确的定义这个功能在哪里, 以什么方式应用, 并且不必修改受影响的类. 这样一来横切关注点就被模块化到特殊的对象(切面)里.

- AOP 的好处:

  - 每个事物逻辑位于一个位置, 代码不分散, 便于维护和升级
  - 业务模块更简洁, 只包含核心业务代码.

  ![image](https://user-images.githubusercontent.com/16509581/42092902-252ee574-7bdd-11e8-8b24-67c5664bf13d.png)

## 3.3 AOP术语

- 切面(Aspect):  横切关注点(跨越应用程序多个模块的功能)被模块化的特殊对象

- 通知(Advice):  切面必须要完成的工作

- 目标(Target): 被通知的对象

- 代理(Proxy): 向目标对象应用通知之后创建的对象

- 连接点（Joinpoint）：程序执行的某个特定位置：如类某个方法调用前、调用后、方法抛出异常后等。连接点由两个信息确定：方法表示的程序执行点；相对点表示的方位。例如 ArithmethicCalculator#add() 方法执行前的连接点，执行点为 ArithmethicCalculator#add()； 方位为该方法执行前的位置

- 切点（pointcut）：每个类都拥有多个连接点：例如 ArithmethicCalculator 的所有方法实际上都是连接点，即连接点是程序类中客观存在的事务。**AOP 通过切点定位到特定的连接点。**类比：连接点相当于数据库中的记录，切点相当于查询条件。切点和连接点不是一对一的关系，一个切点匹配多个连接点，切点通过 org.springframework.aop.Pointcut 接口进行描述，它使用类和方法作为连接点的查询条件。

## 3.4 Spring AOP

- AspectJ：Java 社区里最完整最流行的 AOP 框架.

  在 Spring2.0 以上版本中, 可以使用基于 AspectJ 注解或基于 XML 配置的 AOP

- 在 Spring 中启用 AspectJ 注解支持 

  - 要在 Spring 应用中使用 AspectJ 注解, 必须在 classpath 下包含 AspectJ 类库: aopalliance.jar、aspectj.weaver.jar 和 spring-aspects.jar

  - 将 aop Schema 添加到 <beans> 根元素中.

  - 要在 Spring IOC 容器中启用 AspectJ 注解支持, 只要在 Bean 配置文件中定义一个空的 XML 元素 <aop:aspectj-autoproxy>

  - 当 Spring IOC 容器侦测到 Bean 配置文件中的 <aop:aspectj-autoproxy> 元素时, 会自动为与 AspectJ 切面匹配的 Bean 创建代理.

## 3.5 用 AspectJ 注解声明切面 

- 要在 Spring 中声明 AspectJ 切面, 只需要在 IOC 容器中将切面声明为 Bean 实例. 当在 Spring IOC 容器中初始化 AspectJ 切面之后, Spring IOC 容器就会为那些与 AspectJ 切面相匹配的 Bean 创建代理.

- 在 AspectJ 注解中, 切面只是一个带有 @Aspect 注解的 Java 类. 

- 通知是标注有某种注解的简单的 Java 方法.

- AspectJ 支持 5 种类型的通知注解: 

  - @Before: 前置通知, 在方法执行之前执行

  - @After: 后置通知, 在方法执行之后执行 

  - @AfterRunning: 返回通知, 在方法返回结果之后执行
  - @AfterThrowing: 异常通知, 在方法抛出异常之后
  - @Around: 环绕通知, 围绕着方法执行

## 3.6 实现步骤

1、添加依赖

```xml
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-aop</artifactId>
            <version>4.3.13.RELEASE</version>
        </dependency>

        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-aspects</artifactId>
            <version>4.3.2.RELEASE</version>
        </dependency>
```

2、在配置文件中添加aop的命名空间

```xml
xmlns:aop="http://www.springframework.org/schema/aop"
xsi:schemaLocation="http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop.xsd">
```

3、加入配置

```xml
    <!--使AspectJ注解起作用：自动为匹配的类生成代理对象-->
    <aop:aspectj-autoproxy/>
```

4、创建切面

- 切面首先是IOC中的bean，即加上@Component注解
- 切面还需要加上@Aspect注解

5、在类中声明各种通知

AspectJ 支持 5 种类型的通知注解: 

- @Before: 前置通知, 在方法执行之前执行

- @After: 后置通知, 在方法执行之后执行 

- @AfterRunning: 返回通知, 在方法返回结果之后执行

- @AfterThrowing: 异常通知, 在方法抛出异常之后

- @Around: 环绕通知, 围绕着方法执行

以前置通知为例：

```java
//把这个类声明为一个切面
@Component
@Aspect
public class LoggingAspect {

    //声明该方法是一个前置通知：在目标方法开始之前执行
    @Before("execution(public int com.meituan.spring.aop.helloworld.ArithmeticCalculatorImpl.*(int, int))")
    public void beforeMethod(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        List<Object> args = Arrays.asList(joinPoint.getArgs());
        System.out.println("The method " + signature.getName()  + args + " begins");
    }
}
```

## 3.7 后置通知、返回通知、异常通知、环绕通知

一个切面可以包括一个或者多个通知.

- 后置通知

  后置通知是在连接点完成之后执行的, 即**连接点返回结果或者抛出异常**的时候, 下面的后置通知记录了方法的终止. 

  后置通知不能访问目标方法执行的结果

- 返回通知

  - 无论连接点是正常返回还是抛出异常, 后置通知都会执行. 如果只想在连接点返回的时候记录日志, 应使用返回通知代替后置通知. 
  - 在返回通知中访问连接点的返回值 
    - 在返回通知中, 只要将 returning 属性添加到 @AfterReturning 注解中, 就可以访问连接点的返回值. 该属性的值即为用来传入返回值的参数名称. 
    - 必须在通知方法的签名中添加一个同名参数. 在运行时, Spring AOP 会通过这个参数传递返回值.
    - 原始的切点表达式需要出现在 pointcut 属性中

- 异常通知

  - 只在连接点抛出异常时才执行异常通知

  - **将 throwing 属性添加到 @AfterThrowing 注解中**, 也可以访问连接点抛出的异常. Throwable 是所有错误和异常类的超类. 所以在异常通知方法可以捕获到任何错误和异常.

  - **如果只对某种特殊的异常类型感兴趣**, 可以将参数声明为其他异常的参数类型. 然后通知就只在抛出这个类型及其子类的异常时才被执行.

    ![image](https://user-images.githubusercontent.com/16509581/42119664-18edfb4c-7c41-11e8-874a-137008e7fa99.png)

- 环绕通知
  - 环绕通知是所有通知类型中功能最为强大的, 能够全面地控制连接点. 甚至可以控制是否执行连接点.
  - 对于环绕通知来说, 连接点的参数类型必须是 ProceedingJoinPoint . 它是 JoinPoint 的子接口, 允许控制何时执行, 是否执行连接点.

  - 在环绕通知中需要明确调用 ProceedingJoinPoint 的 proceed() 方法来执行被代理的方法. 如果忘记这样做就会导致通知被执行了, 但目标方法没有被执行.

  - 注意: 环绕通知的方法需要返回目标方法执行之后的结果, 即调用 joinPoint.proceed(); 的返回值, 否则会出现空指针异常

    ![image](https://user-images.githubusercontent.com/16509581/42119696-65b7a39c-7c41-11e8-8fc4-f5c67bedce00.png)

## 3.8 切面的优先级

- 在同一个连接点上应用不止一个切面时, 除非明确指定, 否则它们的优先级是不确定的.
- 切面的优先级可以通过实现 Ordered 接口或利用 @Order 注解指定.
- 实现 Ordered 接口, getOrder() 方法的**返回值越小, 优先级越高**.
- 若使用 @Order 注解, 序号出现在注解中

## 3.9 重用切入点定义

- 在编写 AspectJ 切面时, 可以直接在通知注解中书写切入点表达式. 但同一个切点表达式可能会在多个通知中重复出现.

- 在 AspectJ 切面中, 可以通过 @Pointcut 注解将一个切入点声明成简单的方法. 切入点的方法体通常是空的, 因为将切入点定义与应用程序逻辑混在一起是不合理的. 

- 切入点方法的访问控制符同时也控制着这个切入点的可见性. 如果切入点要在多个切面中共用, 最好将它们集中在一个公共的类中. 在这种情况下, 它们必须被声明为 public. 在引入这个切入点时, 必须将类名也包括在内. 如果类没有与这个切面放在同一个包中, 还必须包含包名.

- 其他通知可以通过方法名称引入该切入点.

  ![image](https://user-images.githubusercontent.com/16509581/42119933-089935dc-7c45-11e8-8657-3cce6cd85608.png)

# 四、Spring对JDBC的支持

## 4.1 JdbcTemplate 简介 

- 为了使 JDBC 更加易于使用, Spring 在 JDBC API 上定义了一个抽象层, 以此建立一个 JDBC 存取框架.
- 作为 Spring JDBC 框架的核心, JDBC 模板的设计目的是为不同类型的 JDBC 操作提供模板方法. 每个模板方法都能控制整个过程, 并允许覆盖过程中的特定任务. 通过这种方式, 可以在尽可能保留灵活性的情况下, 将数据库存取的工作量降到最低.

## 4.2 简化JDBC模板查询

- 每次使用都创建一个 JdbcTemplate 的新实例, 这种做法效率很低下.

- JdbcTemplate 类被设计成为线程安全的, 所以可以再 IOC 容器中声明它的单个实例, 并将这个实例注入到所有的 DAO 实例中.

- JdbcTemplate 也利用了 Java 1.5 的特定(自动装箱, 泛型, 可变长度等)来简化开发

- Spring JDBC 框架还提供了一个 JdbcDaoSupport 类来简化 DAO 实现. 该类声明了 jdbcTemplate 属性, 它可以从 IOC 容器中注入, 或者自动从数据源中创建.

## 4.3 注入JDBC模板示例代码

![image](https://user-images.githubusercontent.com/16509581/42120079-b7d2531a-7c47-11e8-8916-abe605b1658d.png)

## 4.4 在 JDBC 模板中使用具名参数 

- 在经典的 JDBC 用法中, SQL 参数是用占位符 ? 表示,并且受到位置的限制. 定位参数的问题在于, 一旦参数的顺序发生变化, 就必须改变参数绑定. 

- 在 Spring JDBC 框架中, **绑定 SQL 参数的另一种选择是使用具名参数(named parameter)**. 

- 具名参数: SQL 按名称(以冒号开头)而不是按位置进行指定. 具名参数更易于维护, 也提升了可读性. 具名参数由框架类在运行时用占位符取代

- 具名参数只在 NamedParameterJdbcTemplate 中得到支持 

- 在 SQL 语句中使用具名参数时, 可以在一个 Map 中提供参数值, 参数名为键

- 也可以使用 SqlParameterSource 参数

- 批量更新时可以提供 Map 或 SqlParameterSource 的数组

  ![image](https://user-images.githubusercontent.com/16509581/42120120-6205f4f4-7c48-11e8-9adf-780124ef8d8a.png)

# 五、Spring中的事务管理 

## 5.1 事务简介

- 事务管理是企业级应用程序开发中必不可少的技术,  用来确保数据的完整性和一致性. 

- 事务就是一系列的动作, 它们被当做一个单独的工作单元. 这些动作要么全部完成, 要么全部不起作用

- 事务的四个关键属性(ACID)
  - 原子性(atomicity): 事务是一个原子操作, 由一系列动作组成. 事务的原子性确保动作要么全部完成要么完全不起作用.

  - 一致性(consistency): 一旦所有事务动作完成, 事务就被提交. 数据和资源就处于一种满足业务规则的一致性状态中.

  - 隔离性(isolation): 可能有许多事务会同时处理相同的数据, 因此每个事物都应该与其他事务隔离开来, 防止数据损坏.

  - 持久性(durability): 一旦事务完成, 无论发生什么系统错误, 它的结果都不应该受到影响. 通常情况下, 事务的结果被写到持久化存储器中.

## 5.2 事务管理中的问题

- 必须为不同的方法重写类似的样板代码

- 这段代码是特定于 JDBC 的, 一旦选择类其它数据库存取技术, 代码需要作出相应的修改

  ![image](https://user-images.githubusercontent.com/16509581/42120144-eba31a66-7c48-11e8-919b-c747e1bd1c76.png)

获取连接、开启事务==》前置通知

提交事务==》返回通知

回滚事务==》异常通知

finally部分==》后置通知

## 5.3 Spring的事务管理

- 作为企业级应用程序框架, Spring 在不同的事务管理 API 之上定义了一个抽象层. 而应用程序开发人员不必了解底层的事务管理 API, 就可以使用 Spring 的事务管理机制.

- Spring 既支持1）编程式事务管理（即编程）, 也支持2）声明式的事务管理（即配置文件）. 

- 编程式事务管理: 将事务管理代码嵌入到业务方法中来控制事务的提交和回滚. 在编程式管理事务时, 必须在每个事务操作中包含额外的事务管理代码. 

- **声明式事务管理**: <u>大多数情况下比编程式事务管理更好用</u>. 它将事务管理代码从业务方法中分离出来, 以声明的方式来实现事务管理. 事务管理作为一种横切关注点, 可以通过 AOP 方法模块化. Spring 通过 Spring AOP 框架支持声明式事务管理.

## 5.3 Spring中的事务管理器

- Spring 从不同的事务管理 API 中抽象了一整套的事务机制. 开发人员不必了解底层的事务 API, 就可以利用这些事务机制. 有了这些事务机制, 事务管理代码就能独立于特定的事务技术了.
- Spring 的核心事务管理抽象是`org.springframework.transaction.PlatformTransactionManager;`它为事务管理封装了一组独立于技术的方法. 无论使用 Spring 的哪种事务管理策略(编程式或声明式), 事务管理器都是必须的.

## 5.4 实现步骤

1. 配置事务管理器

```xml
<bean id="transactionManager" 
      class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
	<property name="dataSource" ref="dataSource"></property>
</bean>
```

2. 启动事务注解

```xml
<tx:annotation-driven transaction-manager="transactionManager" />
```

3. 在对应的方法上添加注解`@Transactional`

## 5.5 事务的传播行为

- 当事务方法被另一个事务方法调用时, 必须指定事务应该如何传播. 例如: 方法可能继续在现有事务中运行, 也可能开启一个新事务, 并在自己的事务中运行.

- 事务的传播行为可以由传播属性指定. Spring 定义了 7  种类传播行为.

![image](https://user-images.githubusercontent.com/16509581/42120860-39b9047a-7c55-11e8-98db-57cec6dfa7b6.png)

- 使用方法

  ```java
  @Transactional(propagation=Propagation.REQUIRED)
  ```

  

### 5.5.1 REQUIRED 传播行为 

- 当 bookService 的 purchase() 方法被另一个事务方法 checkout() 调用时, 它默认会在现有的事务内运行. 这个默认的传播行为就是 REQUIRED. 因此在 checkout() 方法的开始和终止边界内只有一个事务. 这个事务只在 checkout() 方法结束的时候被提交, 结果用户一本书都买不了
- 事务传播属性可以在 @Transactional 注解的 propagation 属性中定义

![image](https://user-images.githubusercontent.com/16509581/42120898-0cb04550-7c56-11e8-92c9-4b4d342c6079.png)

### 5.5.2 REQUIRES_NEW 传播行为 

另一种常见的传播行为是 REQUIRES_NEW. 它表示该方法必须启动一个新事务, 并在自己的事务内运行. 如果有事务在运行, 就应该先挂起它. 

![image](https://user-images.githubusercontent.com/16509581/42120903-26ca4972-7c56-11e8-8658-d30f3087a8ba.png)



## 5.6 事务的隔离级别

### 5.6.1 并发事务所导致的问题 

- 当同一个应用程序或者不同应用程序中的多个事务在同一个数据集上并发执行时, 可能会出现许多意外的问题

- 并发事务所导致的问题可以分为下面三种类型:
  - 脏读: 对于两个事物 T1, T2, T1  读取了已经被 T2 更新但 还没有被提交的字段. 之后, 若 T2 回滚, T1读取的内容就是临时且无效的.

  - 不可重复读:对于两个事物 T1, T2, T1  读取了一个字段, 然后 T2 更新了该字段. 之后, T1再次读取同一个字段, 值就不同了.

  - 幻读:对于两个事物 T1, T2, T1  从一个表中读取了一个字段, 然后 T2 在该表中插入了一些新的行. 之后, 如果 T1 再次读取同一个表, 就会多出几行.

### 5.6.2 隔离级别

- 从理论上来说, 事务应该彼此完全隔离, 以避免并发事务所导致的问题. 然而, 那样会对性能产生极大的影响, 因为事务必须按顺序运行. 
- 在实际开发中, 为了提升性能, 事务会以较低的隔离级别运行.
- 事务的隔离级别可以通过隔离事务属性指定

### 5.6.3 Spring 支持的事务隔离级别 

![image](https://user-images.githubusercontent.com/16509581/42120939-b125678c-7c56-11e8-90f9-e7235c3163eb.png)

- 事务的隔离级别要得到底层数据库引擎的支持, 而不是应用程序或者框架的支持.

- Oracle 支持的 2 种事务隔离级别：READ_COMMITED , SERIALIZABLE

- Mysql 支持 4 中事务隔离级别.

### 5.6.4 设置隔离事务属性 

用 @Transactional 注解声明式地管理事务时可以在 @Transactional 的 isolation 属性中设置隔离级别. 

![image](https://user-images.githubusercontent.com/16509581/42120955-0222b34c-7c57-11e8-89a3-b0b3a3d2e927.png)

### 5.6.5 设置回滚事务属性 

- 默认情况下只有未检查异常(RuntimeException和Error类型的异常)会导致事务回滚. 而受检查异常不会.

- 事务的回滚规则可以通过 @Transactional 注解的 rollbackFor 和 noRollbackFor 属性来定义. 这两个属性被声明为 Class[] 类型的, 因此可以为这两个属性指定多个异常类.
  - rollbackFor:  遇到时必须进行回滚
  - noRollbackFor: 一组异常类，遇到时必须不回滚

## 5.7 超时和只读属性 

- 由于事务可以在行和表上获得锁,  因此长事务会占用资源, 并对整体性能产生影响. 

- 如果一个事物只读取数据但不做修改, 数据库引擎可以对这个事务进行优化.

- 超时事务属性: 事务在强制回滚之前可以保持多久. 这样可以防止长期运行的事务占用资源.

- 只读事务属性: 表示这个事务只读取数据但不更新数据, 这样可以帮助数据库引擎优化事务.

