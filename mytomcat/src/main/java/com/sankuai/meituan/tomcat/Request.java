package com.sankuai.meituan.tomcat;

import java.util.Map;

/**
 * 模拟HttpServletRequest
 * @author wangbin58
 */
public class Request {
    private String path;
    private String method;
    private String parameter;
    private Map<String, String> attribute;
}
