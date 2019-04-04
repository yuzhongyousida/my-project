package com.myself.instrument;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.instrument.Instrumentation;
import java.lang.management.ManagementFactory;
import java.util.List;

/**
 * @author: wangteng
 * @description: 代理类
 * @date:2018/7/9
 */
public class LoadedAgent {
    public static void agentmain(String args, Instrumentation inst){
        // 获取所有已加载的类
        Class[] classes = inst.getAllLoadedClasses();

        // 打印类名
        for (Class clazz : classes){
            System.out.println(clazz.getName());
        }
    }


    public static void main(String[] args) {
        // 获取当前Java进程名称
        String name = ManagementFactory.getRuntimeMXBean().getName();
        System.out.println(name);

        // 从Java进程的名称中解析出pid
        String pid = name.split("@")[0];
        System.out.println("Pid is:" + pid);

        try {
            System.out.println("getPid() pid is : " + getPid());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static String getPid() throws IOException {
        Process p = Runtime.getRuntime().exec("/Library/Java/JavaVirtualMachines/jdk1.7.0_71.jdk/Contents/Home/bin/jps");
        InputStream in = p.getInputStream();
        List<String> jpsLines = IOUtils.readLines(in);
        IOUtils.closeQuietly(in);
        for (String line : jpsLines) {
            System.out.println(line);
            if (line.contains(LoadedAgent.class.getSimpleName())) {
                return line.split("\\s")[0];
            }
        }
        throw new IllegalStateException("拿不到pid");
    }
}
