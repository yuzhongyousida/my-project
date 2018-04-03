package com.etrip.util.common;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author: wangteng
 * @description:
 * @date:2018/4/2
 */
public class SocketUtil {

    private static final Logger LOGGER = Logger.getLogger(SocketUtil.class);



    public static String getInputStreamStr(InputStream inputStream) throws IOException {
        if(inputStream==null){
            return null;
        }

        byte[] bytes = new byte[2048];
        int len;
        StringBuilder sb = new StringBuilder();
        while ((len = inputStream.read(bytes)) != -1) {
            sb.append(new String(bytes, 0, len,"UTF-8"));
        }

        return sb.toString();
    }




}
