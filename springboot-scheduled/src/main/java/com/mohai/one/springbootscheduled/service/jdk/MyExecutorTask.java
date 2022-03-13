package com.mohai.one.springbootscheduled.service.jdk;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/8/24 00:38
 */
@Slf4j
@Component
public class MyExecutorTask {

    //JAVA 1.5 后新增的定时任务接口
    private static ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    public void execute() {
        //延迟3秒后执行一次
       // executorService.schedule(()->run() , 3, TimeUnit.SECONDS);

       //立即执行，每隔3秒执行一次 固定的时间执行，如果任务执行时间大于间隔时间，则真正的执行时间以任务执行时间为准
       // executorService.scheduleAtFixedRate(()->run(), 0,3, TimeUnit.SECONDS);

        //立即执行，任务执行结束后每隔3秒执行一次，真正的执行时间是任务执行时间+间隔时间
        executorService.scheduleWithFixedDelay(()->run(), 0,3, TimeUnit.SECONDS);
        System.out.println("任务执行结束！！！");
    }

    private void run(){
        System.out.println("MyExecutorTask正在执行。。。"+ LocalDateTime.now().toLocalTime());
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        //立即执行，任务执行结束后每隔3秒执行一次，真正的执行时间是任务执行时间+间隔时间
        executorService.scheduleWithFixedDelay(()-> {
            System.out.println("["+LocalDateTime.now().toLocalTime()+"]任务执行在执行...");
        }, 0,3, TimeUnit.SECONDS);
        System.out.println("任务执行结束！！！");

   }

}
