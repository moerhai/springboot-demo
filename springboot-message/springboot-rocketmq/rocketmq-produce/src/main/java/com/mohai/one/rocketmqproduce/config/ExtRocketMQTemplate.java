package com.mohai.one.rocketmqproduce.config;

import org.apache.rocketmq.spring.annotation.ExtRocketMQTemplateConfiguration;
import org.apache.rocketmq.spring.core.RocketMQTemplate;

/**
 * 定义非标准的RocketMQTemplate使用
 * 其value值必须是没有注入到容器中的bean名称
 * 这里的'nameServer'属性必须要定义，默认给的是${rocketmq.name-server:}
 * 如果有必要可定义其他属性。
 * @Auther: moerhai@qq.com
 * @Date: 2020/11/11 01:03
 */
@ExtRocketMQTemplateConfiguration(nameServer="${demo.rocketmq.extNameServer}")
public class ExtRocketMQTemplate extends RocketMQTemplate {
}
