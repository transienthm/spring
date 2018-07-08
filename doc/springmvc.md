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