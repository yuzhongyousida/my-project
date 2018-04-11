package com.etrip.dynamicQuartz;

import com.etrip.util.common.CommonUtil;
import org.apache.log4j.Logger;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
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
            JobDetail jobDetail = new JobDetail(jobName, jobGroup, jobClazz);

            // 生成触发器实例
            CronTrigger trigger = new CronTrigger(jobName, triggerGroup);

            // 触发器时间设置
            trigger.setCronExpression(expression);

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
    @SuppressWarnings("rawtypes")
    public static boolean modifyJobExpression(Scheduler scheduler, String jobName, String expression, String jobGroupName, String triggerGroupName) {
        boolean result = false;

        // 入参校验
        if (!CommonUtil.checkParamBlank(scheduler, jobName, expression)){
            return result;
        }

        String jobGroup = StringUtils.isEmpty(jobGroupName)? JOB_GROUP_NAME : jobGroupName;
        String triggerGroup = StringUtils.isEmpty(triggerGroupName)? TRIGGER_GROUP_NAME : triggerGroupName;

        try {
            // 校验触发器是否存在
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(jobName, triggerGroup);
            if (trigger == null) {
                return false;
            }

            String oldExpression = trigger.getCronExpression();
            if (!oldExpression.equalsIgnoreCase(expression)) {
                JobDetail jobDetail = scheduler.getJobDetail(jobName, jobGroup);
                Class objJobClass = jobDetail.getJobClass();

                // 移除
                removeJob(scheduler, jobName, jobGroup, triggerGroup);

                // 新增
                addJob(scheduler, jobName, objJobClass, expression, jobGroup, triggerGroup);
            }

            result = true;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }

        return result;
    }

    /**
     * 修改一个任务的触发时间
     * @param scheduler
     * @param triggerName
     * @param triggerGroupName
     * @param expression
     */
    public static boolean modifyJobTime(Scheduler scheduler, String triggerName, String triggerGroupName, String expression) {
        boolean result = false;

        // 入参校验
        if (!CommonUtil.checkParamBlank(scheduler, triggerName, triggerGroupName, expression)){
            return result;
        }

        try {
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerName, triggerGroupName);
            if (trigger == null) {
                return result;
            }

            String oldExpression = trigger.getCronExpression();

            if (!oldExpression.equalsIgnoreCase(expression)) {
                CronTrigger ct = (CronTrigger) trigger;

                // 修改时间
                ct.setCronExpression(expression);

                // 重启触发器
                scheduler.resumeTrigger(triggerName, triggerGroupName);
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
            // 停止触发器
            scheduler.pauseTrigger(jobName, triggerGroup);

            // 移除触发器
            scheduler.unscheduleJob(jobName, triggerGroup);

            // 删除任务
            scheduler.deleteJob(jobName, jobGroup);

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
