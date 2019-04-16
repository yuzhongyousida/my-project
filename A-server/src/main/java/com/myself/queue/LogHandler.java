package com.myself.queue;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author: wangteng
 * @description:
 * @date:2019/4/10
 */
@Repository
public class LogHandler{

    private  boolean isStop = false;
    static int TEMP_LIST_CAPACITY = 100;
    ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<String>(1000, true);
    List<String> tempList = new ArrayList<>(TEMP_LIST_CAPACITY);

    /**
     * 添加
     * @param s
     * @return
     */
    public boolean addLog(String s){
        System.out.println("添加元素：" + s);

        boolean result = false;
        try {
            result = queue.add(s);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }


    /**
     * 消费
     */
    public void consume(){
        System.out.println("消费方法");

        // 先启动消费逻辑
        Thread t = new Thread(){
            @Override
            public void run() {
                while (!isStop) {
                    try {
                        int preSize = queue.size();

                        // 取出队列中的元素
                        String s = queue.poll();

                        int afterSize = queue.size();

                        System.out.println(preSize + "——>" + afterSize);

                        if (!StringUtils.isEmpty(s)){
                            tempList.add(s);
                        }

                        if (tempList.size()>=TEMP_LIST_CAPACITY){
                            // 处理list
                            System.out.println(tempList.toString());

                            // clear
                            tempList.clear();
                        }

                        Thread.sleep(50);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        t.start();

    }

}
