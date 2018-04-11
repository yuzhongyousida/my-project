package com.etrip.web.interceptor.session;

import com.etrip.util.common.ThreadContext;
import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * Created by Administrator on 2017/7/27.
 */
public class SessionInterceptor extends HandlerInterceptorAdapter {

    protected static final Logger logger = Logger.getLogger(SessionInterceptor.class);


    /**
     * 预处理（按照interceptor的顺序所有interceptor的预处理都返回true的时候，请求才会走到controller）
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("SessionInterceptor.preHandle()方法执行");

        String trackNo = (String)request.getAttribute("track_no");

        if(StringUtils.isEmpty(trackNo)){
            trackNo = ""+UUID.randomUUID().toString();
        }
        request.setAttribute("track_no",trackNo);

        // 初始化会话
        ThreadContext.init();
        ThreadContext.putContext("track_no",trackNo);
        return true;
    }

    /**
     * 返回处理（当preHandle方法返回全为true时，执行下一个拦截器,直到所有拦截器执行完。再运行被拦截的Controller。然后进入拦截器链，运行所有拦截器的postHandle方法）
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        logger.info("SessionInterceptor.postHandle()方法执行");
    }

    /**
     * 后处理（被拦截的controller逻辑执行完毕之后，从最后一个拦截器往回执行所有拦截器的afterCompletion方法）
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        logger.info("SessionInterceptor.afterCompletion()方法执行");
        ThreadContext.clean();
    }

}
