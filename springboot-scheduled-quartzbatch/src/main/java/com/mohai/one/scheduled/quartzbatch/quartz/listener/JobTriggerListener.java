package com.mohai.one.scheduled.quartzbatch.quartz.listener;

import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.listeners.TriggerListenerSupport;

import java.time.LocalTime;
import java.util.Calendar;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/9/5 23:03
 */
public class JobTriggerListener extends TriggerListenerSupport {

    @Override
    public String getName() {
        String name = getClass().getSimpleName();
        return name;
    }

    /**
     * 打印触发器开始完成时间与结束时间
     */
    @Override
    public void triggerFired(Trigger trigger, JobExecutionContext context) {
        System.out.println(trigger.getJobKey().toString() + " Trigger is fired! ");
    }

    /**
     * 触发器执行完成
     * @param trigger
     * @param context
     * @param triggerInstructionCode
     */
    @Override
    public void triggerComplete(Trigger trigger, JobExecutionContext context,
                                Trigger.CompletedExecutionInstruction triggerInstructionCode) {
        String triggerToString = triggerInstructionCode.toString();
        String name = context.getJobDetail().getKey().getName();
        System.out.println(name + " Trigger is completed! "+ triggerToString);
    }

    @Override
    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
        System.out.println("vetoJobExecution 正在执行 ");
        return false;
    }

}