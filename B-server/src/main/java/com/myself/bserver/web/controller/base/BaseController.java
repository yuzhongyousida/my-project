package com.myself.bserver.web.controller.base;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * @author Administrator on 2016/10/8.
 */
public class BaseController {

    protected static final Logger LOGGER = LoggerFactory.getLogger(BaseController.class);

    public static void addCookie(HttpServletResponse res, String key, String value, int validDays) throws UnsupportedEncodingException {
        if (!StringUtils.isEmpty(key) && !StringUtils.isEmpty(value)) {
            Cookie cookie = new Cookie(key, URLEncoder.encode(value, "UTF-8"));
            cookie.setMaxAge(validDays * 3600 * 24);
            cookie.setPath("/");
            res.addCookie(cookie);
        }
    }

    public String getHeaderValue(String key) {
        if (StringUtils.isEmpty(key)) {
            return null;
        }

        HttpServletRequest request = getHttpServletRequest();
        if (request == null) {
            return null;
        }

        return request.getHeader(key);
    }

    public String getCookieValue(String cookieName, HttpServletRequest request) throws UnsupportedEncodingException {
        Cookie cookie = getCookie(request, cookieName);
        return cookie == null ? null : URLDecoder.decode(cookie.getValue(), "UTF-8");
    }

    protected Cookie getCookie(HttpServletRequest request, String name) {
        if (!StringUtils.isEmpty(name)) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals(name)) {
                        return cookie;
                    }
                }
            }
        }
        return null;
    }


    /**
     * json对象返回
     */
    public void returnJsonObject(HttpServletResponse response, Object bean) {
        String jsonStr = JSON.toJSONString(bean);
        response.setCharacterEncoding("UTF-8");
        PrintWriter pWriter = null;
        try {
            pWriter = response.getWriter();
            pWriter.write(jsonStr);
        } catch (IOException e) {
            LOGGER.error(e.getLocalizedMessage(), e);
        } finally {
            if (pWriter != null) {
                pWriter.flush();
                pWriter.close();
            }
        }
    }


    public HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

}
