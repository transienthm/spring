package com.sankuai.meituan.servlet;

import com.sankuai.meituan.tomcat.Request;
import com.sankuai.meituan.tomcat.Response;

import java.io.IOException;

/**
 * @author
 * @see(功能介绍) 模拟Servlet
 */
public abstract class HttpServlet {

    public void doGet(Request request, Response response) throws IOException {
        this.service(request, response);
    }

    public void doPost(Request request, Response response) throws IOException {
        this.service(request, response);
    }


    public void service(Request request, Response response) throws IOException {
        if ("GET".equals(request.getMethod())) {
            doGet(request, response);
        } else {
            doPost(request, response);
        }
    }
}
