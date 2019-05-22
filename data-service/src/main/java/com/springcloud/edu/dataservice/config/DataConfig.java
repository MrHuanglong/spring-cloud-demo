package com.springcloud.edu.dataservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author huangl
 * @description
 * @Date 2019/5/22 11:08
 **/
@Component
@ConfigurationProperties(prefix = "cn.springcloud.boot")
public class DataConfig {

    private String defaultUser;

    public String getDefaultUser() {
        return defaultUser;
    }

    public void setDefaultUser(String defaultUser) {
        this.defaultUser = defaultUser;
    }
}
