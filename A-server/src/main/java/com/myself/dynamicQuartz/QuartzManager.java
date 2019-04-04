package com.myself.dynamicQuartz;

import com.myself.util.common.CommonUtil;
import org.apache.log4j.Logger;
import org.quartz.*;
import org.springframework.util.StringUtils;

/**
 * @author: wangteng
 * @description: 定时器管理类
 * @date:2018/4/11
 */
public class QuartzManager {

    private static final Logger LOGGER = Logger.getLogger(QuartzManager.class);

    /**
     * 默认的job分组名称
     */
    public static String JOB_GROUP_NAME = "EXTJWEB_JOBGROUP_NAME";

    /**
     * 默认的触发器分组名称
     */
    public static String TRIGGER_GROUP_NAME = "EXTJWEB_TRIGGERGROUP_NAME";



    /**
     * 添加job（使用默认的job分组名称和触发器分组名称）
     * @param scheduler  调度器
     * @param jobName  job名称
     * @param jobClazz job执行类
     * @param expression job执行expression
     * @param jobGroupName job分组名称
     * @param triggerGroupName 触发器分组名称
     * @return
     */
    public static boolean addJob(Scheduler scheduler, String jobName, Class jobClazz, String expression, String jobGroupName, String triggerGroupName){
        boolean result = false;

        // 入参校验
        if (!CommonUtil.checkParamBlank(scheduler, jobName, jobClazz, expression)){
            return result;
        }

        String jobGroup = StringUtils.isEmpty(jobGroupName)? JOB_GROUP_NAME : jobGroupName;
        String triggerGroup = StringUtils.isEmpty(triggerGroupName)? TRIGGER_GROUP_NAME : triggerGroupName;

        try {

            // 生成JobDetail实例
            JobDetail jobDetail = JobBuilder.newJob(jobClazz)
                    .withIdentity(jobName, jobGroup)
                    .usingJobData("id", 1L)
                    .build();

            // 生成trigger实例
            CronTrigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(jobName+"_trigger", triggerGroup)
                    .withSchedule(CronScheduleBuilder.cronSchedule(expression))
                    .startNow()
                    .build();

            // 加入调度器
            scheduler.scheduleJob(jobDetail, trigger);

            // 启动
            if(!scheduler.isShutdown()){
                scheduler.start();
            }

            result = true;
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
        }

        return result;
    }


    /**
     * 修改一个job的触发时间表达式
     * @param scheduler
     * @param jobName
     * @param expression
     */
    public static boolean modifyJobExpression(Scheduler scheduler, String jobName, String expression, String triggerGroupName) {
        boolean result = false;

        // 入参校验
        if (!CommonUtil.checkParamBlank(scheduler, jobName, expression)){
            return result;
        }

        String triggerGroup = StringUtils.isEmpty(triggerGroupName)? TRIGGER_GROUP_NAME : triggerGroupName;

        try {
            // 校验触发器是否存在
            TriggerKey triggerKey = TriggerKey.triggerKey(jobName+"_trigger", triggerGroup);
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            if (trigger == null) {
                return false;
            }

            // 时间表达式是否被修改
            String oldExpression = trigger.getCronExpression();
            if (!oldExpression.equalsIgnoreCase(expression)) {

                // 新生成触发器
                trigger = TriggerBuilder.newTrigger()
                        .withIdentity(jobName+"_trigger", triggerGroup)
                        .withSchedule(CronScheduleBuilder.cronSchedule(expression))
                        .startNow()
                        .build();

                // 替换
                scheduler.rescheduleJob(triggerKey, trigger);
            }

            result = true;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }

        return result;
    }


    /**
     * 移除一个任务
     * @param scheduler
     * @param jobName
     * @param jobGroupName
     * @param triggerGroupName
     * @return
     */
    public static boolean removeJob(Scheduler scheduler, String jobName, String jobGroupName, String triggerGroupName) {
        boolean result = false;

        // 入参校验
        if (!CommonUtil.checkParamBlank(scheduler, jobName)){
            return result;
        }

        String jobGroup = StringUtils.isEmpty(jobGroupName)? JOB_GROUP_NAME : jobGroupName;
        String triggerGroup = StringUtils.isEmpty(triggerGroupName)? TRIGGER_GROUP_NAME : triggerGroupName;

        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(jobName+"_trigger", triggerGroup);

            // 停止触发器
            scheduler.pauseTrigger(triggerKey);

            // 移除触发器
            scheduler.unscheduleJob(triggerKey);

            // 删除任务
            scheduler.deleteJob(JobKey.jobKey(jobName, jobGroup));

            result = true;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }

        return result;
    }


    /**
     * 启动所有定时任务
     * @param scheduler
     */
    public static void startJobs(Scheduler scheduler) {
        try {
            scheduler.start();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    /**
     * 关闭所有定时任务
     * @param scheduler
     */
    public static void shutdownJobs(Scheduler scheduler) {
        try {
            if (!scheduler.isShutdown()) {
                scheduler.shutdown();
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }





}
