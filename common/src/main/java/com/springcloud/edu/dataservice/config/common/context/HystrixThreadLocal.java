package com.springcloud.edu.dataservice.config.common.context;

/**
 * @author huangl
 * @description
 * @Date 2019/5/21 17:37
 **/
public class HystrixThreadLocal {

    public static ThreadLocal<String> threadLocal = new ThreadLocal<>();

}
