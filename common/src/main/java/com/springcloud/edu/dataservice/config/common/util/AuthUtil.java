package com.springcloud.edu.dataservice.config.common.util;

import com.springcloud.edu.dataservice.config.common.vo.User;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;

/**
 * @author huangl
 * @description
 * @Date 2019/5/21 18:11
 **/
public class AuthUtil {

    public static boolean authUser(User user, HttpServletResponse response) throws Exception {
        if (StringUtils.isEmpty(user.getUserId())){
            return false;
        }
        return true;
    }
}
