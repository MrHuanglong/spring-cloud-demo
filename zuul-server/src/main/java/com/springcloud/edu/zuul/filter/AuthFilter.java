package com.springcloud.edu.zuul.filter;

import com.alibaba.fastjson.JSONObject;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.springcloud.edu.dataservice.config.common.exception.BaseException;
import com.springcloud.edu.dataservice.config.common.exception.BaseExceptionBody;
import com.springcloud.edu.dataservice.config.common.exception.CommonError;
import com.springcloud.edu.dataservice.config.common.vo.User;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author huangl
 * @description
 * @Date 2019/5/22 14:49
 **/
public class AuthFilter extends ZuulFilter {

    private static final Logger logger = LoggerFactory.getLogger(AuthFilter.class);

    @Override
    public boolean shouldFilter() {
        // 判断是否需要进行处理
        return true;
    }

    @Override
    public Object run() {
        RequestContext rc = RequestContext.getCurrentContext();
        authUser(rc);
        return null;
    }

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    private static Map<String, String> httpRequestToMap(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        Map<String, String> headers = new HashMap<>();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.put(headerName, request.getHeader(headerName));
        }
        return headers;
    }

    public static void authUser(RequestContext ctx) {
        HttpServletRequest request = ctx.getRequest();
        Map<String, String> header = httpRequestToMap(request);
        String userId = header.get(User.CONTEXT_KEY_USERID);
        if(StringUtils.isEmpty(userId)) {
            try {
                BaseException BaseException = new BaseException(CommonError.AUTH_EMPTY_ERROR.getCode(),CommonError.AUTH_EMPTY_ERROR.getCodeEn(),CommonError.AUTH_EMPTY_ERROR.getMessage(),1L);
                BaseExceptionBody errorBody = new BaseExceptionBody(BaseException);
                ctx.setSendZuulResponse(false);
                ctx.setResponseStatusCode(401);
                ctx.setResponseBody(JSONObject.toJSON(errorBody).toString());
            } catch (Exception e) {
                logger.error("println message error",e);
            }
        }else {
            for (Map.Entry<String, String> entry : header.entrySet()) {
                ctx.addZuulRequestHeader(entry.getKey(), entry.getValue());
            }
        }
    }
}
