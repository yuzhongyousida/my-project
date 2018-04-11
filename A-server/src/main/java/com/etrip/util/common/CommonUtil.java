package com.etrip.util.common;

import org.apache.commons.lang3.StringUtils;

/**
 * @author: wangteng
 * @description:
 * @date:2018/4/11
 */
public class CommonUtil {


    /**
     * @title: checkParamBlank
     * @description: 判断是否存在空，String类型包括""、" "
     * @param args
     * @return
     */
    public static boolean checkParamBlank(Object... args) {
        for (Object arg : args) {
            if (arg == null || (arg.getClass().equals(String.class) && StringUtils.isEmpty((String) arg))) {
                return false;
            }
        }
        return true;
    }
}
