package com.meituan.springmvc.handler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.servlet.View;

import java.util.Map;

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

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        System.out.println(id);
        return "redirect:/user/list.action";
    }

    @RequestMapping(value = "/testRest", method = RequestMethod.DELETE)
    public String testDelete() {
        System.out.println("delete rest");
        ModelAndView modelAndView = new ModelAndView();
        View view = new View() {
            @Override
            public String getContentType() {
                return null;
            }

            @Override
            public void render(Map<String, ?> map, javax.servlet.http.HttpServletRequest httpServletRequest, javax.servlet.http.HttpServletResponse httpServletResponse) throws Exception {

            }
        };
        modelAndView.setView(view);
        return "success";
    }


}
