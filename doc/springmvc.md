# 一、SpringMVC概述

- Spring为展现层提供的基于MVC设计理念的优秀的Web框架，是目前最主流的MVC框架之一
- Spring3.0之后全面超越Struts2，成为最优秀的MVC框架
- SpringMVC通过一套MVC注解，让POJO成为处理请求的控制器，而无须实现任何接口
- 支持REST风格的URL请求
- 采用了松散耦合可挺拔组件结构，比其他MVC框架更具扩展性和灵活性

# 二、HelloWorld

步骤：

1. maven依赖

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <project xmlns="http://maven.apache.org/POM/4.0.0"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
       <modelVersion>4.0.0</modelVersion>
   
       <groupId>com.meituan.spring</groupId>
       <artifactId>mvc</artifactId>
       <version>1.0-SNAPSHOT</version>
   
       <dependencies>
           <dependency>
               <groupId>org.springframework</groupId>
               <artifactId>spring-web</artifactId>
               <version>4.3.14.RELEASE</version>
           </dependency>
   
           <dependency>
               <groupId>org.springframework</groupId>
               <artifactId>spring-core</artifactId>
               <version>4.3.14.RELEASE</version>
           </dependency>
   
           <dependency>
               <groupId>org.springframework</groupId>
               <artifactId>spring-context</artifactId>
               <version>4.3.14.RELEASE</version>
           </dependency>
   
           <dependency>
               <groupId>org.springframework</groupId>
               <artifactId>spring-webmvc</artifactId>
               <version>4.3.14.RELEASE</version>
           </dependency>
   
       </dependencies>
   
       <build>
           <plugins>
               <!--  添加编译插件 -->
               <plugin>
                   <groupId>org.apache.maven.plugins</groupId>
                   <artifactId>maven-compiler-plugin</artifactId>
                   <version>3.5.1</version>
                   <configuration>
                       <source>1.8</source>
                       <target>1.8</target>
                       <encoding>UTF-8</encoding>
                   </configuration>
               </plugin>
               <plugin>
                   <groupId>org.apache.maven.plugins</groupId>
                   <artifactId>maven-war-plugin</artifactId>
               </plugin>
           </plugins>
       </build>
   </project>
   ```

   在Module Settings->Project Settings->Artifacts->output layout中，找到WEB-INF目录，确认在其下存在lib目录，不存在则创建，将library files添加进去

2. web.xml

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
            version="4.0">
       <context-param>
           <param-name>contextConfigLocation</param-name>
           <param-value>/WEB-INF/applicationContext.xml</param-value>
       </context-param>
       <listener>
           <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
       </listener>
       <servlet>
           <servlet-name>dispatcher</servlet-name>
           <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
           <load-on-startup>1</load-on-startup>
       </servlet>
       <servlet-mapping>
           <servlet-name>dispatcher</servlet-name>
           <url-pattern>/</url-pattern>
       </servlet-mapping>
   </web-app>
   ```

3. dispatcher-servlet.xml

   - 可以通过contextConfigLocation来指定SpringMVC配置文件的位置
   - 使用默认规则：/WEB-INF/<servlet-name>-servlet.xml

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <beans xmlns="http://www.springframework.org/schema/beans"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xmlns:context="http://www.springframework.org/schema/context"
          xmlns:mvc="http://www.springframework.org/schema/mvc"
          xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
              http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd
              http://www.springframework.org/schema/mvc
              http://www.springframework.org/schema/mvc/spring-mvc.xsd
   ">
   
       <context:component-scan base-package="com.meituan.springmvc"/>
   
       <mvc:annotation-driven/>
   
       <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
           <property name="prefix" value="/WEB-INF/views/"/>
           <property name="suffix" value=".jsp"/>
       </bean>
   </beans>
   ```

   注意：前后缀一定不要出错

4. Controller类

   ```java
   import org.springframework.stereotype.Controller;
   import org.springframework.web.bind.annotation.RequestMapping;
   import org.springframework.web.bind.annotation.ResponseBody;
   
   /**
    * 描述:
    * hello world
    *
    * @author Alcalde
    * @create 2018-07-07 下午9:50
    */
   @Controller
   public class HelloWorld {
   
       @RequestMapping("/helloworld")
       public String hello() {
           System.out.println("hello world, hello springmvc !");
           return "success";
       }
   }
   ```

5. index.jsp文件

   ```jsp
   <%@ page contentType="text/html;charset=UTF-8" language="java" %>
   <html>
     <head>
       <title>$Title$</title>
     </head>
     <body>
       <a href="/helloworld">hello world!</a>
     </body>
   </html>
   ```

6. 视图jsp文件

   ```jsp
   <%@ page contentType="text/html;charset=UTF-8" language="java" %>
   <html>
     <head>
       <title>$Title$</title>
     </head>
     <body>
       <h4>success page!</h4>
     </body>
   </html>
   ```

7. 配置好Tomcat

   值得注意的是，在deployment->Deploy at the server startup中添加artifact

8. 启动Tomcat即可

# 三、使用@RequestMapping映射请求

源码

```java
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Mapping
public @interface RequestMapping
```

## 3.1 使用@RequestMapping映射请求

- 可以用来修饰方法，还可以用来修饰类
  - 类定义处：提供初步的请求映射信息，相对于WEB应用的根目录
  - 方法处：提供进一步的细分映射信息，相对于类定义处的URL，若类定义处未标@RequestMapping，则方法处标记的URL相对于WEB应用的根目录
- DispatcherServlet截获请求后，就通过控制器上@RequestMapping提供的映射信息确定请求所对应的处理方法

## 3.2 映射请求参数、请求方法或请求头

- @Re􏰇􏳅􏳆􏳇􏰀questMapping除了可以使用请求URL映射请求外，还可以使用请求方法、请求参数及请求头映射请求
- @RequestMapping的value、method、params及heads分别表示请求URL、请求方法、请求参数及请求头的映射条件，它们之间是与的关系，联合使用多个条件可让请求映射更加精确化
- params和headers支持简单的表达式
  - param1：表示请求必须包含名为param1的请求参数
  - !param1：表示请求不能包含名为param1的请求参数
  - param1!=value1表示请求包含名为param1的请求参数，但其值不能为value1
  - {"param1=value1", "param2"}：请求必须包含名为param1和param2的两个请求参数，且param1参数的值必须为value1
- Ant风格资源地址支持3种匹配符：
  - ？：匹配文件名中的一个字符 
  - \* :匹配文件名中的任意字符 
  - \*\*:匹配多层路径 

## 3.3 支持Ant风格（了解即可）

- @RequestMapping 还支持 Ant 风格的 URL : 
  - /user/*/createUser:匹配 /user/aaa/createUser、/user/bbb/createUser 等 URL 
  - /user/**/createUser:匹配 /user/createllser、/user/aaa/bbb/createUser 等 URL
  - /user/createllser??:匹配 /user/createllseraa、/user/createllserbb 等 URL   

## 3.4 **@PathVariable**映射URL綁定的占位符 

- 带 占 位 符 的 URL是Spring3.0新增的功能，该功能在SpringMVC向REST目标挺进发展过程中具有里程碑的意义
- 通过@PathVariable可以将URL中占位符参数绑定到控制器处理方法的入参中:URL中的{xxx丨占位符可以通过 @PathVariable("xxx")綁定到操作方法的入参中。

```java
    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        System.out.println(id);
        return "redirect:/user/list.action";
    }
```

## 3.5 REST

REST 即Representational State Transfer。 (资源)表现层状态转化。是目前最流行的一种互联网软件架构。它结构清晰、符合标准、易于理解、扩展方便， 所以正得到越来越多网站的采用 

- 资 源 ( Resources):网络上的一个实体，或者说是网络上的一个具体信息。它 可以是一段文本、 一 张图片、一首歌曲、一种服务，总之就是一个具体的存在。 可以用一个URI (统一资源定位符)指向它，每种资源对应一个特定的URU要 获取这个资源，访问它的URI就可以，因此<u>URI即为每一个资源的独一无二的识别符</u>。 

- 表现层 ( Representation):把资源具体呈现出来的形式，叫做它的表现层 (Representation) ^比如，文本可以用txt格式表现，也可以用HTML格式、XML格式、JSON格式表现，甚至可以采用二进制格式

- 状态转化 ( StateTransfer):每发出一个请求，就代表了客户端和服务器的一 次交互过程。HTTP协议，是一个无状态协议，即所有的状态都保存在服务器端。因此，如果客户端想要操作服务器，必须通过某种手段，让服务器端发生“状态转化”( State Transfer)。而这种转化是建立在表现层之上的，所以就是“ 表现层状态转化”。具体说，就是<u>HTTP协议里面，四个表示操作方式的动词:GET、POST、PUT、DELETE。它们分别对应四种基本操作：GET用来获取资源，POST用来新建资源，PUT用来更新资源，DELETE用来删除资源。</u> 

- HiddenHttpMethodFilter: 浏览器 form 表单只支持 GET 与 POST请求，而 DELETE、PUT等method并不支持，Spring3.0添加了一个过滤器，可以将这些请求转换为标准的http方法，使得支持GET、POST、PUT与DELETE请求

  - web.xml

    ```xml
        <!--配置org.springframework.web.filter.HiddenHttpMethodFilter：可以把POST请求转换为DELETE或PUT请求-->
        <filter>
            <filter-name>HiddenHttpMethodFilter</filter-name>
            <filter-class>org.springframework.web.filter.HiddenHttpMethodFilter</filter-class>
        </filter>
        
        <filter-mapping>
            <filter-name>HiddenHttpMethodFilter</filter-name>
            <url-pattern>/*</url-pattern>
        </filter-mapping>
    ```

  - jsp

    添加一个form，增加一个隐藏域name="_method"，指明请求的类型，值取PUT或DELETE

    ```jsp
        <form action="/testRest" method="post">
          <input type="hidden" name="_method" value="DELETE">
          <input type="submit" value="TestRest DELETE">
        </form>
    ```

  - java

    ```java
        @RequestMapping("/delete/{id}")
        public String delete(@PathVariable("id") Integer id) {
            userDao.deleteUserById(id);
            return "success";
        }
    ```

  在Tomcat8以上版本，会出现问题，需要在jsp文件中添加

  ```jsp
  <%@ page contentType="text/html;charset=UTF-8" language="java"  isErrorPage="true"%>
  ```

  指定属性isErrorPage="true"

# 四、映射请求参数 & 请求参数

## 4.1 请求处理方法签名 

- Spring MVC通过分析处理方法的签名，将HTTP请求信息绑定到处理方法的相应入参中。 
- Spring MVC对控制器处理方法签名的限制是很宽松的， 几乎可以按喜欢的任何方式对方法进行签名。 
- 必要时可以对方法及方法入参标注相应的注解(@PathVariable、@RequestParam、 @RequestHeader等)、Spring MVC框架会将HTTP请求的信息绑定到相应的方法入参中，并根据方法的返回值类型做出相应的后续处理。 

## 4.2 使用@RequestParam绑定请求参数值

在处理方法入参处使用@RequestParam可以把请求参数传递给请求方法

- value：参数名 
- required：是否必须。默认为true,表示请求参数中必须包含对应 的参数，若不存在，将抛出异常
- defaultValue：请求参数的默认值 

## 4.3 使用@RequestHeader绑定请求报头的属性值（了解即可） 

请求头包含了若干个属性，服务器可据此获知客户端的信息，通过@RequestHeader即可将请求头中的属性值绑定到处理方法的入参中

![image](https://user-images.githubusercontent.com/16509581/42418238-94caeb7e-82ce-11e8-8f26-15637d718e84.png)



## 4.4 使用CookieValue绑定请求中的cookie值（了解即可）

@CookieValue可让处理方法入参绑定某个cookie值

![image](https://user-images.githubusercontent.com/16509581/42418269-19e9db58-82cf-11e8-9bac-5e3add5f7859.png)

## 4.5 使用POJO对象绑定请求参数值

SpringMVC会按请求参数名和POJO属性名进行自动匹配，自动为该对象填充属性值，支持级联属性

如：dept.deptId、dept.address.tel等

![image](https://user-images.githubusercontent.com/16509581/42418295-8769e1fa-82cf-11e8-9972-7eed88069bf9.png)

## 4.6 使用Servlet API作为入参

MVC的handler访求可以接受以下Servlet API类型的参数

- HttpServletRequest
- HttpServletResponse
- HttpSession
- java.security.Principal
- Locale
- InputStream
- OutputStream 
- Reader
- Writer 

# 五、处理模式数据

Spring MVC提供了以下几种途径输出模型数据: 

- ModelAndView:处理方法返回值类型为ModelAndView 时，方法体即可通过该对象添加模型数据 
- Map及Model: 入参为org.springframework.ui.Model、 org.springframework.ui.ModelMap或 java.util.Map时，处理 方法返回时，Map中的数据会自动添加到模型中。 
- @SessionAttributes:将模型中的某个属性暂存到 HttpSession中，以便多个请求之间可以共享这个属性 
- @ModelAttribute:方法入参标注该注解后，入参的对象就会放到数据模型中 

## 5.1 ModelAndView

SpringMVC会把 ModelAndView 里model 中的数据放入到request域对象( requestScope )中

- 控制器处理方法的返回值如果为ModelAndView，则其既包含视图信息，也包含模型数据信息。 
- 添加模型数据: 
  - MoelAndView addObject(String attributeName, Object attributeValue) 
  - ModelAndView addAIIObject(Map<String, ?> modelMap) 
- 设置视图: 
  - void setView(View view)
  - void setViewName(String viewName)

## 5.2 Map及Model

- Spring MVC在内部使用了一个 org.springframework.ui.Model 接口存储模型数据

- 具体步骤 
  - Spring MVC在调用方法前会创建一个隐 含的模型对象作为模型数据的存储容器。 
  - 如果方法的入参为Map或Model类型，Spring MVC会将隐含模型的引用传递给这些入参。在方法体内，开发者可以通过这个入参对象访问到模型中的所有数 据，也可以向模型中添加新的属性数据。

![image](https://user-images.githubusercontent.com/16509581/42418611-b79bd21e-82d6-11e8-8395-7fdd9561cab3.png)

## 5.3 将模型数据存入Session中

- 若希望在多个请求之间共用某个模型属性数据，则可以在**控制器<u>类</u>**上标注一个 @SessionAttributes，Spring MVC 将在模型中对应的属性暂存到HttpSession中。
- @SessionAttributes除了可以通过<u>属性名</u>指定需要放到会话中的属性外，还可以通过模型属性的<u>对象类型</u>指定哪些模型属性需要放到session会话中 
  - @SessionAttributes(types=User.class)会将隐含模型中所有类型 为User.class的属添加到会话中。 
  - @SessionAttributes(value={“user1”， “user2”}) 
  - @SessionAttributes(types={User.class, Dept.class}) 
  - @SessionAttributes(value={“user1”， “user2” } ， types={Dept.class}) 

## 5.4 @ModelAttribute 

- 在方法定义上使用@ModelAttribute注解: Spring MVC 在调用目标处理方法前，会先逐个调用在方法级上标注了@ModelAttribute 的方法，因此对于一个controller映射多个URL的用法来说，要谨慎使用。 
- 在方法的入参前使用@ModelAttribute注解: 
  - 可以从隐含对象中获取隐含的模型数据中获取对象，再将请求参数绑定到对象中，再传入入参
  - 将方法入参对象添加到模型中
- @ModelAttribute 注解也可以来修饰目标方法POJO类型的入参，其 value 属性值有如下的作用：
  - SpringMVC会使用 value 属性值在implicitModel中查找对应的对象，若存在则会直接传入到目标方法的入参中
  - SpringMVC会以value为key，POJO类型的对象为value，存入到request中

### 5.4.1 @ModelAttribute运行原理 

```java
@ModelAttribute
public void getUser(@RequestParam(value="id", required=false) Integer id, Map<String, Object> map) {
    System.out.println("modelAttribute method");
    
    if (id != null) {
        //模拟从数据库中获取对象
        User user = new User(1, "Tom", "123456", "Tom@meituan.com", 12);
        System.out.println("从数据库中获取一个对象: " + user);
        map.put("user", user);
    }
}
```

运行流程：

1、 执行@ModelAttribute注解修饰的方法；从数据库中取出对象，把对象放入到Map中，键为user

2、SpringMVC从Map中取出User对象，并把表单的请求参数赋值给该User对象对应的属性

3、SpringMVC把上述对象传入目标方法的参数

注意：在@ModelAttribute修饰的方法中，放入到Map时的键需要和目标方法入参类型的第一个字母小写的字符串一致！

### 5.4.2 源码分析的流程

1. 调用@ModelAttribute注解修饰的方法，实际上把@ModelAttribute方法中Map中的数据放在了implicitModel中

2. 解析请求处理器的目标参数，实际上该目标参数来自于WebDataBinder对象的target属性，

   1. 创建WebDataBinder对象：

      - 确定objectName属性：若传入的attrName属性值为""，则objectName为类名第一个字母小写

        注意：attrName，若目标方法的POJO属性使用了@ModelAttribute来修饰，则attrName值即为@ModelAttribute的value属性

      - 确定target的值

        在implicitModel中查找attrName对应的属性值。若存在，ok；若不存在，则验证当前handler是否使用了@SessionAttributes进行修饰，若使用了，则尝试从Session中获取attrName所对应的属性值，若session中没有对应的属性值，则抛出异常；若handler没有使用@SessionAttribute进行修饰，或@SessionAttributes中没有使用value值指定的key和attrName相匹配，则通过反射创建POJO对象

   2. SpringMVC把表单的请求参数赋给了WebDataBinder的target对应的属性

   3. SpringMVC会把WebDataBinder的attrName和target赋值给implicitModel

3. 把WebDataBinder的target作为参数传递给目标参数的入参

### 5.4.3 如何确定目标方法POJO类型的入参

1. 确定一个key

   1. 若目标方法的POJO类型的参数没有使用@ModelAttribute作为修饰，则key为POJO类名第一个字母的小写
   2. 若使用了@ModelAttribute来修饰，则key为@ModelAttribute注解的value属性值

2. 在implicitModel中查找key对应的对象，若存在，则作为入参传入

   若@ModelAttribute标记的方法在Map中保存过，且key和1确定的key一致，则会获取到

3. 若implicitModel中不存在key对应的对象，则检查当前的handler是否使用@SessionAttributes注解修饰，若使用了该注解，且@SessionAttributes注解的value属性值中包含了key，则会从HttpSession中来获取key所对应的value值，若存在则直接传入目标方法的入参中；若不存在则将抛出异常

4. 若handler没有标识@SessionAttributes注解的value值中不包含key，则会通过反射来创建POJO类型的参数，传入为目标方法的参数

5. SpringMVC会把key和POJO类型的对象保存到implicitModel，进而保存到request中

## 5.5 @SessionAttributes 注解引发的异常

```java
org.springframework.web.HttpSessionRequiredException: Session attribute 'user' required - not found in session
```

如果在处理类定义处标注了@SessionAttributes(“xxx”)， 则尝试从session会话中获取该属性，并将其赋给该入参，然后再用请求消息填充该入参对象。如果在会话中找不到对应的属性，则拋出HttpSessionRequiredException 异常

## 5.6 视图和视图解析器

### 5.6.1 视图解析流程分析

![image](https://user-images.githubusercontent.com/16509581/42732417-d288a3e0-8853-11e8-8986-2e26d3089c06.png)

- 请求处理方法执行完成后，最终返回一个ModelAndView 对象。对于那些返回String, View或ModeMap等类型的处理方法，**Spring MVC也会在内部将它们装配成一个 ModelAndView对象**，它包含了逻辑名和模型对象的视图 
- SpringMVC借助视图解析器（ViewResolver)得到最终 白七视图对象（View)，最终的视图可以是JSP，也可能是 Excel、JFreeChart等各种表现形式的视图 
- 对于最终究竟采取何种视图对象对模型数据进行渲染，处 理器并不关心，处理器工作重点聚焦在生产模型数据的工 作上，从而实现MVC的充分解耦

### 5.6.2 视图

- 视图的作用是渲染模型数据，将模型里的数据以某种形式呈现给客 户。
- 为了实现视图模型和具体实现技术的解耦，Spring在org.springframework.web.servlet 包中定义了一个高度抽象的 View接口
- 视图对象由视图解析器负责实例化。由于视图是无状态的，所以他们 不会有线程安全的问题

常用的视图实现类： 

![image](https://user-images.githubusercontent.com/16509581/43031553-76af90b0-8cd6-11e8-9b4b-7214ee5395a0.png)

### 5.6.3 视图解析器（ViewResolver）

- 视图解析器的作用是将逻辑视图转为物理视图，所有的视图解析器都必须实现ViewResolver接口。 
- 视图解析器的作用比较单一：将逻辑视图解析为一个具体的视图对象
- SpringMVC为逻辑视图名的解析提供了不同的策略，可以在Spring WEB上下文中**配置一种或多种解析策略，并指定他们之间的先后顺序**。每一种映射策略对应一个具体的视图解析器实现类。
- 程序员可以选择一种视图解析器或混用多种视图解析器。可以通过order属性指定解析器的优先顺序，order越小优先级越高
- SpringMVC会按视图解析器顺序的优先顺序对逻辑视图名进行解析，直到解析成功并返回视图对象，否则抛出ServletException异常。 

![image](https://user-images.githubusercontent.com/16509581/43031776-3ee43f8c-8cdb-11e8-9c36-58d87649e71c.png)

### 5.6.4 使用mvc:view-controller不经控制器直接跳转到页面

若希望直接响应通过SpringMVC渲染的页面，可以使用mvc:view-controller标签实现：

```xml
　<!-- 配置直接转发的页面 -->      
<!-- 可以直接相应转发的页面, 而无需再经过 Handler 的方法.  -->  
    <mvc:view-controller path="/success" view-name="success"/>  
```

那么现在可以直接在某一页面中通过请求路径”success”访问到/WEB-INF/views/success.jsp页面（因为我们上面配置了视图解析器将逻辑视图解析为前缀为/WEB-INF/views/，后缀为.jsp的物理视图）。但是，这种情况下通过控制器就无法映射到请求了，需要再进行如下配置：

```xml
 <!-- 在实际开发中通常都需配置 mvc:annotation-driven 标签，  之前的页面才不会因为配置了直接转发页面而受到影响 -->  
    <mvc:annotation-driven></mvc:annotation-driven>  
```

### 5.6.5 关于重定向

一般情况下，控制器方法返回字符串类型的值会被当成逻辑视图名处理，但如果返回的字符串中带forward:或redirect:前缀时，SpringMVC会对它们进行特殊处理：将forward: 和redirect: 当成指示符，其后的字符串作为URL 来处理。示例如下： 

index.jsp：

```jsp
<a href="${pageContext.request.contextPath }/springmvc/testRedirect">Test Redirect</a>

```

controller:

```java
@Controller
@RequestMapping("/springmvc")
public class SpringMVCTest {

    @RequestMapping("/testRedirect")
    public String testRedirect() {
        System.out.println("testRedirect");
        return "redirect:/index.jsp";
    }
}
```

即可重定向到index.jsp。也可在redirect:/后添加控制器方法的映射路径，重定向到该目标方法。 

# 6 SpringMVC表单标签&处理静态资源

通过SpringMVC的表单标签可以实现将模型数据 中的属性和HTML表单元素相綁定，以实现表单 

数据更便捷编辑和表单值的回显 

## 6.1 表单标签

form:input、 form:passwordN form:hidden、 form:textarea :对应 HTML 表車的 text、password、hidden、 textarea 标签 

- form:radiobutton :单选框组件标签，当表单bean对应的属性值和value值相等时，单选框破选中 

- form:radiobuttons :单选框组标签，用于构造多个单选 
  - items :可以是一个 List、String[]或 Map 
  - item Value :指定radio 的value值。可以是集合中bean的一个属性值
  - itemLabel :指定 radio 的 label 值
  - delimiter :多个单选框可以通过delimiter指定分隔符 
- form:checkbox :复选框组件。用于构造单个复选框
- form:checkboxs :用于构造多个复选框。使用方式同form:radiobuttons 标签
- form:select :用于@造下拉框组件。使用方式同form:radiobuttons 标签 
- form:option :下拉框选项组件标签。使用方式同 form:radiobuttons 标签 
- form:errors :显示表单组件或数据校验所对应的错误

## 6.2 处理静态资源

- 优雅的REST风格的资源URL不希望带.html或.do等后缀 
- 若将DispatcherServlet请求映射配置为/，则Spring MVC将捕获 WEB容器的所有请求，包括静态资源的请求，SpringMVC会将他 们当成一个普通请求处理，因找不到对应处理器将导致错误。
- 可以在SpringMVC的配置文件中配置的方式解决静态资源的问题： 
  - 将在 SpringMVC 上下文中定义一个DefaultServletHttpRequestHandler，它会对进入DispatcherServlet 的请求进行筛查，如果发现是没有经过映射的请求，就将该请求交由WEB 应用服务器默认的Servlet处理，如果不是静态资源的请求，才由DispatcherServlet 继续处理 
  - 一般WEB应用服务器默认的Servlet的名称都是default。若所使用的 WEB服务器的默认Servlet名称不是default,则需要通过default-servlet-name 属性显式指定

# 7、数据转换 & 数据格式化 & 数据校验

## 7.1 数据转换

### 7.1.1 数据綁定流程

1. Spring MVC主框架将ServletRequest对象及目标方法的入参实例传递给WebDataBinderFactory实例，以创建DataBinder实例对象

2.	DataBinder调用装配在Spring MVC上下文中的
  ConversionService组件进行数据类型转换、数据格式化工作。将Servlet中的请求信息填充到入参对象中

3.	调用Validator组件对已经綁定了请求消息的入参对象进行数据合法性校验，并最终生成数据绑定结果BindingData 对象

4.	Spring MVC抽取BindingResult中的入参对象和校验错误对象，将它们赋给处理方法的响应入参

SpringMVC通过反射机制对目标处理方法进行解析，将请求消息绑定到处理方法的入参中。数据绑定的核心部件是DataBinder，运行机制如下：

![image](https://user-images.githubusercontent.com/16509581/43034169-03560822-8d0a-11e8-9b7f-eb5f07b72d91.png)

### 7.1.2 关于mvc:annotation-driven

- <mvc:annotation-driven /> 会自动注册RequestMappingHandlerMapping、RequestMappingHandlerAdapter 与ExceptionHandlerExceptionResolver 三个bean。
- 还将提供以下支持：
  - 支持使用ConversionService实例对表单参数进行类型转换
  - 支持使用 @NumberFormat annotation、@DateTimeFormat注解完成数据类型的格式化
  - 支持使用@Valid注解对JavaBean实例进行JSR 303验证
  - 支持使用 @RequestBody和 @ResponseBody 注解

### 7.1.3 @InitBinder注解

由@lnitBinder标识的方法，可以对WebDataBinder对

象进行初始化。WebDataBinder是DataBinder的子类，用
于完成由表单字段到JavaBean属性的綁定

@lnitBinder方法不能有返回值，它必须声明为void。

@lnitBinder方法的参数通常是是WebDataBinder

## 7.2 数据格式化

- 对属性对象的输入/输出进行格式化，从其本质上讲依然属于“类型转换”的范畴。

- Spring在格式化模块，定义了一个实现ConversionService接口的FormattingConversionService 实现类，该实现类扩展了 GenericConversionService，因此它既具有类型转换的功能，又具有格式化的功能

- FormattingConversionService 拥有一个FormattingConversionServiceFactroyBean 工厂类，后者用于在 Spring上下文中构造前者

- FormattingConversionServiceFactroyBean 内部已经注册了：

  - NumberFormatAnnotationFormatterFactroy :支持对数字类型的属性，使用 @NumberFormat 注解
  - JodaDateTimeFormatAnnotationFormatterFactroy :支持对日期类型的属性使用@DateTimeFormat注解

- 装配了 FormattingConversionServiceFactroyBean 后，就可

  以在Spring MVC入参绑定及模型数据输出时使用注解驱动
  了。**`<mvc:annotation-driven/>` 默认创建的ConversionService 实例即为FormattingConversionServiceFactroyBean**

### 7.2.1 日期格式化

@DateTimeFormat 注解可对java.util.Date、java.util.Calendar、java.long.Long 时间类型进行标注：

- pattern属性：类型为字符串。指定解析/格式化字段数据的模式，如：”yyyy-MM-dd hh:mm:ss”
- iso属性：类型为DateTimeFormat.lSO。指定解析/格式化字段数据的ISO模式，包括四种MSO.NONE (不使用）默认、ISO.DATE(yyyy-MM-dd)、IS〇.TIME(hh:mm:ss.SSSZ)、ISO.DATE_TIME(yyyy-MM-dd hh:mm:ss.SSSZ)

- style属性：字符串类型。通过样式指定日期时间的格式，由两位字符组成，第一位表示日期的格式，第二位表示时间的格式：S :短日期/时间格式、M :中日期/时间格式、L :长日期/时间格式、F :完整日期/时间格式、忽略日期或时间格式

### 7.2.2

@NumberFormat可对类似数字类型的属性进行标注，它拥有两个互斥的属性：

- style :类型为NumberFormat.Style。用于指定样式类型，包括三种：Style.NUMBER (正常数字类型)、Style.CURRENCY (货币类型）、Style.PERCENT (百分数类型）
- pattern :类型为String,自定义样式，如patter="#，###";

## 7.3 数据校验

### 7.3.1 JSR 303

- JSR 303是Java为Bean数据合法性校验提供的标准框架，它已经包含在JavaEE 6.0中
- JSR 303通过在Bean属性上标注类似于@NotNull、@Max等标准的注解指定校验规则，并通过标准的验证接口对Bean进行验证

![wx20180721-183913 2x](https://user-images.githubusercontent.com/16509581/43034901-a568e122-8d17-11e8-8e0e-c4d71bf8f98e.png)

### 7.3.2 SpringMVC数据校验

- Spring 4.0拥有自己独立的数据校验框架，同时支持JSR 303标准的校验框架。
- Spring在进行数据绑定时，可同时调用校验框架完成数据校验工作。在Spring MVC中，可直接通过注解驱动的方式进行数据校验
- Spring 的 LocalValidatorFactroyBean 既实现了 Spring 的Validator 接口，也实现了 JSR 303 的 Validator 接口。只要在Spring容器中定义了一个LocalValidatorFactoryBean，即可将其注入到需要数据校验的Bean中。
- Spring本身并没有提供JSR303的实现，所以必须将JSR303的实现者的jar包放到类路径下。
- <mvc:annotation-driven/＞会默认装配好一个 LocalValidatorFactoryBean ，通过在处理方法的入参上标注＠valid 注解即可让 Spring MVC 在完成数据绑定后执行数据校验的工作
- 在已经标注了 JSR303 注解的表单／命令对象前标注一个 @Valid , Spring MVC 框架在将请求参数绑定到该入参对象后，就会调用校验框架根据注解声明的校验规则实施校验
- Spring MVC 是通过对处理方法签名的规约来保存校验结果的：前一个表单/命令对象的校验结果保存到随后的入参中，这个保存校验结果的入参必须是 BindingResult 或 Errors 类型，这两个类都位于 org.springframework.validation 包中
- **需校验的Bean对象和其绑定结果对象或错误对象时成对出现的，它们之间不允许声明其他的入参**
- Errors接口提供了获取错误信息的方法，如getErrorCount()或getFieldErrors(String field)
- BindingResult扩展了Errors接口

### 7.3.3 在目标方法中获取校验结果

- 在表单/命令对象类的属性中标校验注解，在处理方法对应的入参前添加@Valid，SpringMVC就会实施校验并将校验结果保存在被校验入参对象之后的BindingResult或Errors入参中

### 7.3.4 在页面上显示错误

- Spring MVC 除了会将表单/命令对象的校验结果保存到对 应的 BindingResu|t或 Errors对象中外,还会将所有校验结果保存到“隐含模型” 
- 即使处理方法的签名中没有对应于表单/命合对象的结果入参,校验结果也会保存在“隐含对象”中。 
- 隐含模型中的所有数据最终将通过 Http Servletrequest的属性列表暴露给 JSP 视图对象,因此在 JSP 中可以获取错误信息 
- 在JSP页面上可通过`<form:errors path="userName">`显示错误消息

