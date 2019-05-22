package com.springcloud.user.service.impl;

import com.springcloud.user.service.IUserService;
import com.springcloud.user.service.dataservice.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author huangl
 * @description
 * @Date 2019/5/22 14:38
 **/
@Service
public class UserService implements IUserService {

    @Autowired
    DataService dataService;

    @Autowired
    RestTemplate restTemplate;

    @Override
    public String getDefaultUser() {
        return dataService.getDefaultUser();
    }

    @Override
    public String getContextUserId() {
        return dataService.getContextUserId();
    }

    @Override
    public List<String> getProviderData() {
        List<String> result = restTemplate.getForObject("http://sc-data-service/getProviderData", List.class);
        return result;
    }
}
