package com.meituan.springmvc.handler;

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
