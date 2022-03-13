package com.mohai.one.springbootkafka.controller;

import com.mohai.one.springbootkafka.service.KafkaMessageSendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/10/6 02:02
 */
@RestController
public class KafkaMessageSendController {

    @Autowired
    private KafkaMessageSendService kafkaMessageSendService;

    @RequestMapping(value="/send",method= RequestMethod.GET)
    public String send(){
        try {
            kafkaMessageSendService.sendMessage("Hello World!");
        } catch (Exception e) {
            e.printStackTrace();
            return "FAILED";
        }
        return "SUCCESS";
    }


    @RequestMapping(value="/sendAsync",method= RequestMethod.GET)
    public String sendAsync(@RequestParam String message){
        try {
            kafkaMessageSendService.sendAsync(message);
        } catch (Exception e) {
            e.printStackTrace();
            return "FAILED";
        }
        return "SUCCESS";
    }

    @RequestMapping(value="/sendAsync2",method= RequestMethod.GET)
    public String sendAsync2(@RequestParam String message){
        try {
            kafkaMessageSendService.sendAsync2(message);
        } catch (Exception e) {
            e.printStackTrace();
            return "FAILED";
        }
        return "SUCCESS";
    }


    @RequestMapping(value="/sendSync",method= RequestMethod.GET)
    public String sendSync(@RequestParam String message){
        try {
            kafkaMessageSendService.sendSync(message);
        } catch (Exception e) {
            e.printStackTrace();
            return "FAILED";
        }
        return "SUCCESS";
    }


    @RequestMapping(value="/sendMessageTx",method= RequestMethod.GET)
    public String sendMessageTx(@RequestParam String mak){
        try {
            // 如果发送error则表示事务提交失败
            kafkaMessageSendService.sendMessageTx(mak);
        } catch (Exception e) {
            e.printStackTrace();
            return "FAILED";
        }
        return "SUCCESS";
    }

}