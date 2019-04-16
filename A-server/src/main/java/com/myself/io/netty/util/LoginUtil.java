package com.myself.io.netty.util;


import io.netty.channel.Channel;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * @author: wangteng
 * @description:
 * @date:2019/4/10
 */

public class LoginUtil {

    private static AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");

    public static void markLogin(Channel channel){
        channel.attr(LOGIN).set(true);
    }

    public static boolean isLogin(Channel channel){
        Attribute<Boolean> loginAttr = channel.attr(LOGIN);
        return loginAttr.get()!=null;
    }


}
