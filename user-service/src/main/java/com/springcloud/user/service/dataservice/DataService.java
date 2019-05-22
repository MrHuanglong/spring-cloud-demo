package com.springcloud.user.service.dataservice;

import com.springcloud.user.service.fallback.UserClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author huangl
 * @description
 * @Date 2019/5/22 14:33
 **/
@FeignClient(name = "sc-data-service", fallback=UserClientFallback.class)
public interface DataService {

    @RequestMapping(value = "/getDefaultUser", method = RequestMethod.GET)
    public String getDefaultUser();

    @RequestMapping(value = "/getContextUserId", method = RequestMethod.GET)
    public String getContextUserId();
}
