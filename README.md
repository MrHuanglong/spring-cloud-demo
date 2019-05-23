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
按顺序启动eureka、zuul-service、data-service、user-service，成功后浏览器访问http://localhost:9091/getContextUserId，
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