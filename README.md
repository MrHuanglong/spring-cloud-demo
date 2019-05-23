# Spring Cloud 基础整合案列

### 工程介绍

工程名 | 端口 | 描述
----|----|----
spring-cloud-demo | N/A | 父工程
config-server | 9090 | 配置中心
eureka-server | 8761 | 注册中心
zuul-server | 7777 | API Gate Way
hystrix-dashboard | 9099 | hystrix dashboard & Turbine
common | N/A |  公共基础包，方便后台服务引用
user-service | 9091 | 用户服务，对用户数据的操作
data-service |  8099 | 数据服务，提供基础的数据

### 项目介绍
1) 按顺序启动eureka、zuul-service、data-service、user-service，成功后浏览器访问http://localhost:9091/getContextUserId，
发现页面空白，控制台打印“the user is null， please access from gateway or check user info”,
这就说明拦截器起到了作用，对于没有用户信息这样不合法的请求进行了拦截。
``` 
    public class UserContextInterceptor implements HandlerInterceptor {    
       ***
       @Override
       public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
           User user = new User(HttpConvertUtil.httpRequestToMap(request));
           if(StringUtils.isEmpty(user.getUserId()) && StringUtils.isEmpty(user.getUserName())) {
               log.error("the user is null, please access from gateway or check user info");
               return false;
           }
           UserContextHolder.set(user);
           return true;
       }
    } 
   ```
2) 从网关正确的访问http://localhost:7777/sc-user-service/getContextIdUserId这个地址，此时发现报错：

 {"businessId":1,"exceptionType":"cn.springcloud.edu.common.exception.BaseException",
"code":10001,"businessMessage":"the user is null, please check","codeEN":"AuthEmptyError"}

这是自定义了一个异常，没有传用户信息，因为这里在网关做了拦截，如果在请求头里没有
x-customs-user则鉴权不通过，代码如下：
```
    public class AuthFilter extends ZuulFilter {
        public final static String CONTEXT_KEY_USERID = "x-customs-user";
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
```
3) 请求头添加x-customs-user=spring,访问http://localhost:7777/sc-user-service/getContextUserId,
若请求成功，则返回填写的上下文值为spring