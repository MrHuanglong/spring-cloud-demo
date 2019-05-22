package com.springcloud.edu.dataservice.config.common.context;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.concurrent.Callable;

/**
 * @author huangl
 * @description
 * @Date 2019/5/21 17:38
 **/
public class HystrixThreadCallable<S> implements Callable<S>{
    private final RequestAttributes requestAttributes;
    private final Callable<S> delegate;
    private String params;

    public HystrixThreadCallable(Callable<S> callable, RequestAttributes requestAttributes,String params) {
        this.delegate = callable;
        this.requestAttributes = requestAttributes;
        this.params = params;
    }

    @Override
    public S call() throws Exception {
        try {
            RequestContextHolder.setRequestAttributes(requestAttributes);
            HystrixThreadLocal.threadLocal.set(params);
            return delegate.call();
        } finally {
            RequestContextHolder.resetRequestAttributes();
            HystrixThreadLocal.threadLocal.remove();
        }
    }
}
