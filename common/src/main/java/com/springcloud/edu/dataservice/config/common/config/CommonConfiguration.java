package com.springcloud.edu.dataservice.config.common.config;

import com.springcloud.edu.dataservice.config.common.context.SpringCloudHystrixConcurrencyStrategy;
import com.springcloud.edu.dataservice.config.common.intercepter.FeignUserContextInterceptor;
import com.springcloud.edu.dataservice.config.common.intercepter.RestTemplateUserContextInterceptor;
import com.springcloud.edu.dataservice.config.common.intercepter.UserContextInterceptor;
import feign.Feign;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author huangl
 * @description
 * @Date 2019/5/21 16:39
 **/
@Configuration
@EnableWebMvc
public class CommonConfiguration extends WebMvcConfigurerAdapter {

    /**
     * 请求拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserContextInterceptor());
    }

    /**
     * 创建Feign请求拦截器，在发送请求前设置认证的用户上下文信息
     */
    @Bean
    @ConditionalOnClass(Feign.class)
    public FeignUserContextInterceptor feignTokenInterceptor() {
        return new FeignUserContextInterceptor();
    }

    /**
     * RestTemplate拦截器
     * @return
     */
    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(new RestTemplateUserContextInterceptor());
        return restTemplate;
    }

    @Bean
    public SpringCloudHystrixConcurrencyStrategy springCloudHystrixConcurrencyStrategy() {
        return new SpringCloudHystrixConcurrencyStrategy();
    }

}