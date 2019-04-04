package com.myself.util.log4j;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Layout;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.spi.LoggingEvent;
import org.springframework.util.StringUtils;

/**
 * Created by Administrator on 2017/4/17.
 */
public class Log4jAppender extends AppenderSkeleton{
    private String appName;//web应用的名称



    public Log4jAppender(){

    }


    /**
     * Derived appenders should override this method if option structure requires it.
     */
    public void activateOptions() {
        init();
    }


    @Override
    protected void append(LoggingEvent event) {
        try {

            // 格式化
            String msg = this.getLayout().format(event);

            // 传输
            System.out.println(msg);


        }catch (Exception e){
            LogLog.error("exception : Log4jAppender.append() exception ,message is : "+e);
        }

    }


    @Override
    public synchronized void close() {
        if(closed){
            return;
        }

        // 关闭连接
        try {

            closed = true;
        }catch (Exception e){
            LogLog.error("Exception:Log4jAppender.close(),Exception error message is: "+e);
        }


    }

    @Override
    public boolean requiresLayout() {
        return true;
    }



    private void init(){
        try {
            // 获取host等配置信息，进行解析和配置
            if(StringUtils.isEmpty(this.appName)){
                this.appName = "web-app";
            }

            // 判断LayOut是否为空，为空则进行默认值设置
            Layout layout = this.getLayout();
            if(layout==null){
                LogLog.debug("The Layout is not seted, so set it default value.");
                String pattern = "%d{yyyy-MM-dd HH:mm:ss} %p [%t] %C.%M(%L) | %m %n";
                this.setLayout(new org.apache.log4j.PatternLayout(pattern));
            }

            // 将appName补充在ConversionPattern格式前面
            if(layout instanceof PatternLayout){
                PatternLayout patternLayout = (PatternLayout) layout;
                String conversionPattern = patternLayout.getConversionPattern();
                if(!StringUtils.isEmpty(conversionPattern)){
                    conversionPattern = "【" + appName + "】 " + conversionPattern;
                }
                patternLayout.setConversionPattern(conversionPattern);
            }

        }catch (Exception e){
            LogLog.error("Exception:Log4jAppender.init(),Exception error message is: "+e);
        }
    }








    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

}
