package com.etrip.util.common;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/27.
 * 请求上线文
 */
public class ThreadContext {

    /**
     * 请求上下文全局变量持有者
     */
    private final static ThreadLocal<Map<String,Object>> CONTEXT_HOLDER = new ThreadLocal<Map<String,Object>>();

    public static final String SESSION_KEY = "sessionId";


    static{
        CONTEXT_HOLDER.set(new HashMap<String, Object>());
    }

    /**
     * 添加内容
     * @param key
     * @param value
     */
    public final static void putContext(String key, Object value) {
        Map<String, Object> ctx = CONTEXT_HOLDER.get();
        if (ctx == null) {
            return;
        }
        ctx.put(key, value);
    }

    /**
     * 获取内容
     * @param key
     */
    @SuppressWarnings("unchecked")
    public final static <T extends Object> T getContext(String key) {
        Map<String, Object> ctx = CONTEXT_HOLDER.get();
        if (ctx == null) {
            return null;
        }
        return (T) ctx.get(key);
    }

    /**
     * 获取上下文所有全局内容
     */
    public final static Map<String, Object> getContext() {
        Map<String, Object> ctx = CONTEXT_HOLDER.get();
        if (ctx == null) {
            return null;
        }
        return ctx;
    }

    /**
     * 删除内容
     * @param key
     */
    public final static void remove(String key) {
        Map<String, Object> ctx = CONTEXT_HOLDER.get();
        if (ctx != null) {
            ctx.remove(key);
        }
    }

    /**
     * 上下文中是否包含此key
     */
    public final static boolean contains(String key) {
        Map<String, Object> ctx = CONTEXT_HOLDER.get();
        if (ctx != null) {
            return ctx.containsKey(key);
        }
        return false;
    }

    /**
     * 清空上下文
     */
    public final static void clean() {
        CONTEXT_HOLDER.set(null);
    }

    /**
     * 初始化线程上下文
     */
    public final static void init() {
        CONTEXT_HOLDER.set(new HashMap<String, Object>());
    }

    /**
     * 设置会话ID数据
     */
    public final static void putSessionId(String sessionId) {
        putContext(SESSION_KEY, sessionId);
    }

    /**
     * 获取会话ID数据
     */
    public final static String getSessionId() {
        return getContext(SESSION_KEY);
    }

    /**
     * 清空会话ID数据
     */
    public final static void removeSessionId() {
        remove(SESSION_KEY);
    }

}
