package jdk;

import base.JunitBaseTest;
import com.myself.dynamicQuartz.QuartzJob;
import com.myself.dynamicQuartz.QuartzManager;
import org.junit.Test;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * @author: wangteng
 * @description:
 * @date:2018/4/11
 */
public class QuartzTest extends JunitBaseTest {

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    @Test
    public void test(){
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();

            String jobName1 = "动态任务调度1";
            String jobName2 = "动态任务调度2";

            String expression1 = "0/1 * * * * ?";
            System.out.println("【添加定时任务】开始(每1秒输出一次)...");
            QuartzManager.addJob(scheduler, jobName1, QuartzJob.class, expression1, QuartzManager.JOB_GROUP_NAME, QuartzManager.TRIGGER_GROUP_NAME);


            Thread.sleep(30000);
//
//            System.out.println("【修改时间】开始(每2秒输出一次)...");
//            String expression2 = "0/2 * * * * ?";
//            QuartzManager.modifyJobExpression(scheduler, jobName1, expression2, QuartzManager.TRIGGER_GROUP_NAME);
//
//            Thread.sleep(6000);
//
//            System.out.println("【再次添加定时任务】开始(每10秒输出一次)...");
//            String expression3 = "0/10 * * * * ?";
//            QuartzManager.addJob(scheduler, jobName2, QuartzJob.class, expression3, QuartzManager.JOB_GROUP_NAME, QuartzManager.TRIGGER_GROUP_NAME);
//
//            Thread.sleep(30000);
//
//            System.out.println("【移除定时1】开始...");
//            QuartzManager.removeJob(scheduler, jobName1, QuartzManager.JOB_GROUP_NAME, QuartzManager.TRIGGER_GROUP_NAME);
//            System.out.println("【移除定时1】成功");
//
//            Thread.sleep(30000);
//
//            System.out.println("【移除定时2】开始...");
//            QuartzManager.removeJob(scheduler, jobName2, QuartzManager.JOB_GROUP_NAME, QuartzManager.TRIGGER_GROUP_NAME);
//            System.out.println("【移除定时2】成功");

        }catch (Exception e){
            e.printStackTrace();
        }
    }



}
