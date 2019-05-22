package com.springcloud.edu.common.context;

import com.springcloud.edu.common.vo.User;

/**
 * @author huangl
 * @description
 * @Date 2019/5/21 16:43
 **/
public class UserContextHolder {

    public static ThreadLocal<User> context = new ThreadLocal<>();

    public static User currentUser(){
        return context.get();
    }

    public static void set(User user){
        context.set(user);
    }

    public static void shutdown(){
        context.remove();
    }
}
