package com.myself.io.nettyHeartBeat;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: wangteng
 * @description:
 * @date:2018/6/10
 */
public final class Header {

    /**
     * netty消息的校验码：有三部分组成 0xABEF（2字节） + 主版本号（1字节） + 次版本号（1字节）
     */
    private int crcCode = 0xabef0101;

    /**
     * 消息头长度
     */
    private int length;

    /**
     * 全局唯一会话ID
     */
    private long sessionId;

    /**
     * 消息类型（0：业务请求消息，1：业务响应消息，2：业务open way消息（即使请求又是响应消息），3：握手请求消息，4：握手响应消息，5：心跳请求消息，6：心跳响应消息）
     */
    private byte type;

    /**
     * 消息优先级
     */
    private byte priority;

    /**
     * 可选字段（用于扩展消息头）
     */
    private Map<String, Object> attachment = new HashMap<>();


    public int getCrcCode() {
        return crcCode;
    }

    public void setCrcCode(int crcCode) {
        this.crcCode = crcCode;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public long getSessionId() {
        return sessionId;
    }

    public void setSessionId(long sessionId) {
        this.sessionId = sessionId;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public byte getPriority() {
        return priority;
    }

    public void setPriority(byte priority) {
        this.priority = priority;
    }

    public Map<String, Object> getAttachment() {
        return attachment;
    }

    public void setAttachment(Map<String, Object> attachment) {
        this.attachment = attachment;
    }
}
