package com.myself.dynamicQuartz;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: wangteng
 * @description:  定时器job类
 * @date:2018/4/11
 */
@DisallowConcurrentExecution
public class QuartzJob implements Job{

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 定时器逻辑执行方法
     * @param jobExecutionContext
     * @throws JobExecutionException
     */
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String name = jobExecutionContext.getJobDetail().getKey().getName();
        Long id = jobExecutionContext.getJobDetail().getJobDataMap().getLong("id");
        System.out.println("定时器:" + name + ",ID: "+ id + ", 执行中-------" + sdf.format(new Date()));

//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        String str = httpGet("http://www.baidu.com");
        System.out.println(str);
    }


    public String httpGet(String url) {
        String responseStr =  null;
        HttpClient client = new HttpClient();
        HttpMethod method = new GetMethod(url);
        try {
            client.executeMethod(method);
            System.out.println(method.getStatusLine());
            responseStr = method.getResponseBodyAsString();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            method.releaseConnection();
        }
        return responseStr;
    }





}
