package com.springcloud.edu.dataservice.config.common.intercepter;

import com.springcloud.edu.dataservice.config.common.context.UserContextHolder;
import com.springcloud.edu.dataservice.config.common.vo.User;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.util.Map;

/**
 * @author huangl
 * @description RestTemplate传递用户上下文
 * @Date 2019/5/22 9:29
 **/
public class RestTemplateUserContextInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {
        User user = UserContextHolder.currentUser();
        Map<String, String> headers = user.toHttpHeaders();
        for (Map.Entry<String, String> header : headers.entrySet()) {
            httpRequest.getHeaders().add(header.getKey(), header.getValue());
        }
        return execution.execute(httpRequest, body);
    }
}
