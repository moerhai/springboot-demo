package com.mohai.one.springbootscheduled.service.jdk;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/8/25 00:53
 */
@Slf4j
@Component
public class MyTriggerTask {

    public void execute(){
        MyTask myTask = new MyTask();
        Timer timer = new Timer();
        //延迟3秒之后执行一次
        timer.scheduleAtFixedRate(myTask,1000,3000);
    }

    public void execute1(){
        MyTask myTask = new MyTask();
        Timer timer = new Timer();
        //延迟3秒之后执行一次
        timer.schedule(myTask,3000,1000);
    }

    public static void main(String[] args) {
        MyTriggerTask myTriggerTask = new MyTriggerTask();
        myTriggerTask.execute();
    }


    static class MyTask extends TimerTask {
        @Override
        public void run() {
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("MyTask 正在执行的时间 {}", LocalDateTime.now().toLocalTime());
        }
    }
}