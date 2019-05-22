package com.springcloud.user.service.fallback;

import com.springcloud.user.service.dataservice.DataService;
import org.springframework.stereotype.Component;

/**
 * @author huangl
 * @description
 * @Date 2019/5/22 14:34
 **/
@Component
public class UserClientFallback implements DataService {
    @Override
    public String getDefaultUser() {
        return new String("get getDefaultUser failed");
    }

    @Override
    public String getContextUserId() {
        return new String("get getContextUserId failed");
    }
}
