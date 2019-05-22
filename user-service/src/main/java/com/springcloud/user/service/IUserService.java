package com.springcloud.user.service;

import java.util.List;

/**
 * @author huangl
 * @description
 * @Date 2019/5/22 14:32
 **/
public interface IUserService {
    String getDefaultUser();
    String getContextUserId();
    List<String> getProviderData();
}
