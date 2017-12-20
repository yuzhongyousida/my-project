package com.etrip.util.log4j;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.net.ZeroConfSupport;
import org.apache.log4j.spi.ErrorCode;
import org.apache.log4j.spi.LoggingEvent;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by Administrator on 2017/4/18.
 */
public class EtripSocketAppender extends AppenderSkeleton {

    public static final int DEFAULT_PORT                 = 4560;
    public static final int DEFAULT_RECONNECTION_DELAY   = 30000;
    public static final String ZONE = "_log4j_obj_tcpconnect_appender.local.";

    private String remoteHost;
    private InetAddress address;
    private ObjectOutputStream oos;
    private String application;
    private Connector connector;
    private int counter = 0;
    private boolean advertiseViaMulticastDNS;
    private ZeroConfSupport zeroConf;

    private String appName;//web应用的名称


    private boolean locationInfo = false;
    private int port = DEFAULT_PORT;//连接端口
    private int reconnectionDelay = DEFAULT_RECONNECTION_DELAY;//连接延时毫秒数
    private static final int RESET_FREQUENCY = 3;//oos的flush之后，reset的频率（该值意味着每3次flush之后，oos进行一次reset）

    public EtripSocketAppender() {

    }

    public EtripSocketAppender(InetAddress address, int port) {
        this.address = address;
        this.remoteHost = address.getHostName();
        this.port = port;
        connect(address, port);
    }

    public EtripSocketAppender(String host, int port) {
        this.port = port;
        this.address = getAddressByName(host);
        this.remoteHost = host;
        connect(address, port);
    }

    public void activateOptions() {
        if (advertiseViaMulticastDNS) {
            zeroConf = new ZeroConfSupport(ZONE, port, getName());
            zeroConf.advertise();
        }
        connect(address, port);
    }

    /**
     * 关闭appender
     */
    public synchronized void close() {
        if(closed){
            return;
        }

        this.closed = true;
        if (advertiseViaMulticastDNS) {
            zeroConf.unadvertise();
        }

        cleanUp();
    }


    /**
     * 清除工作：断开远程连接，如果有潜在的连接线程创建了，则解除他们，以便让GC回收资源
     */
    public void cleanUp() {
        if(oos != null) {
            try {
                oos.close();
            } catch(IOException e) {
                if (e instanceof InterruptedIOException) {
                    Thread.currentThread().interrupt();
                }
                LogLog.error("Could not close oos.", e);
            }
            oos = null;
        }
        if(connector != null) {
            connector.interrupted = true;
            connector = null;  // allow gc
        }
    }

    /**
     * 连接远程
     * @param address
     * @param port
     */
    public void connect(InetAddress address, int port) {
        if(this.address == null)
            return;
        try {
            cleanUp();
            Socket socket = new Socket(address, port);
            oos = new ObjectOutputStream(socket.getOutputStream());
        } catch(IOException e) {
            if (e instanceof InterruptedIOException) {
                Thread.currentThread().interrupt();
            }
            String msg = "Could not connect to remote log4j server at ["
                    +address.getHostName()+"].";
            if(reconnectionDelay > 0) {
                msg += " We will try again later.";
                fireConnector(); // 重连
            } else {
                msg += " We are not retrying.";
                errorHandler.error(msg, e, ErrorCode.GENERIC_FAILURE);
            }
            LogLog.error(msg);
        }
    }


    /**
     * 日志append方法
     * @param event
     */
    public void append(LoggingEvent event) {
        if(event == null)
            return;

        if(address==null) {
            errorHandler.error("No remote host is set for EtripSocketAppender named \"" + this.name+"\".");
            return;
        }

        if(oos != null) {
            try {
                if(locationInfo) {
                    event.getLocationInformation();
                }
                if (application != null) {
                    event.setProperty("application", application);
                }
                event.getNDC();
                event.getThreadName();
                event.getMDCCopy();
                event.getRenderedMessage();
                event.getThrowableStrRep();

                // 格式化
                String pattern = "%d{yyyy-MM-dd HH:mm:ss} %p [%t] %C.%M(%L) | %m %n";
                PatternLayout patternLayout = new PatternLayout(pattern);
                String conversionPattern = patternLayout.getConversionPattern();
                conversionPattern = "【" + appName + "】 " + conversionPattern;
                patternLayout.setConversionPattern(conversionPattern);
                String msg = patternLayout.format(event);
                System.out.println(msg);
                event.setProperty("message",msg);

                oos.writeObject(event);
                oos.flush();
                if(++counter >= RESET_FREQUENCY) {
                    counter = 0;
                    oos.reset();
                }
            } catch(IOException e) {
                if (e instanceof InterruptedIOException) {
                    Thread.currentThread().interrupt();
                }
                oos = null;
                LogLog.warn("Detected problem with connection: "+e);
                if(reconnectionDelay > 0) {
                    fireConnector();
                } else {
                    errorHandler.error("Detected problem with connection, not reconnecting.", e,
                            ErrorCode.GENERIC_FAILURE);
                }
            }
        }
    }

    public void setAdvertiseViaMulticastDNS(boolean advertiseViaMulticastDNS) {
        this.advertiseViaMulticastDNS = advertiseViaMulticastDNS;
    }

    public boolean isAdvertiseViaMulticastDNS() {
        return advertiseViaMulticastDNS;
    }

    /**
     * 连接动作
     */
    public void fireConnector() {
        if(connector == null) {
            LogLog.debug("Starting a new connector thread.");
            connector = new Connector();
            connector.setDaemon(true);
            connector.setPriority(Thread.MIN_PRIORITY);
            connector.start();
        }
    }

    /**
     * 根据host获取address对象
     * @param host
     * @return
     */
    public static InetAddress getAddressByName(String host) {
        try {
            return InetAddress.getByName(host);
        } catch(Exception e) {
            if (e instanceof InterruptedIOException || e instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }
            LogLog.error("Could not find address of ["+host+"].", e);
            return null;
        }
    }

    /**
     * 是否需要layout是个问题
     * @return
     */
    public boolean requiresLayout() {
        return false;
    }


    /**
     * socket连接线程内部类
     */
    public class Connector extends Thread {
        boolean interrupted = false;

        public void run() {
            Socket socket;
            while(!interrupted) {
                try {
                    sleep(reconnectionDelay);// 休眠30秒之后再reConnect
                    LogLog.debug("Attempting connection to "+address.getHostName());
                    socket = new Socket(address, port);
                    synchronized(this) {//需要线程安全的，因为日志是大量的，防止并发过大时，出现很多连接将服务器资源占用完
                        oos = new ObjectOutputStream(socket.getOutputStream());
                        connector = null;
                        LogLog.debug("Connection established. Exiting connector thread.");
                        break;
                    }
                } catch(InterruptedException e) {
                    LogLog.debug("Connector interrupted. Leaving loop.");
                    return;
                } catch(java.net.ConnectException e) {
                    LogLog.debug("Remote host "+address.getHostName() + " refused connection.");
                } catch(IOException e) {
                    if (e instanceof InterruptedIOException) {
                        Thread.currentThread().interrupt();
                    }
                    LogLog.debug("Could not connect to " + address.getHostName()+ ". Exception is " + e);
                }
            }
        }

    }




    public void setRemoteHost(String host) {
        address = getAddressByName(host);
        remoteHost = host;
    }
    public String getRemoteHost() {
        return remoteHost;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getPort() {
        return port;
    }

    public void setLocationInfo(boolean locationInfo) {
        this.locationInfo = locationInfo;
    }

    public boolean getLocationInfo() {
        return locationInfo;
    }

    public void setApplication(String lapp) {
        this.application = lapp;
    }

    public String getApplication() {
        return application;
    }

    public void setReconnectionDelay(int delay) {
        this.reconnectionDelay = delay;
    }

    public int getReconnectionDelay() {
        return reconnectionDelay;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }
}
