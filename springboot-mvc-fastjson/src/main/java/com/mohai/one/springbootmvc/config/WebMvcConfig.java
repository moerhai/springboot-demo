package com.mohai.one.springbootmvc.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mohai.one.springbootmvc.converter.MyMessageConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Configuration
@ConditionalOnMissingBean(WebMvcConfigurationSupport.class)
public class WebMvcConfig {

    public WebMvcConfig(){
        System.out.println("WebMvcConfig初始化");
    }

//    @Bean
//    public MyMessageConverter myMessageConverter(){
//        MyMessageConverter converter  = new MyMessageConverter();
//        return converter;
//    }



//    @Bean
//    public StringHttpMessageConverter stringHttpMessageConverter(){
//        StringHttpMessageConverter converter  = new StringHttpMessageConverter(Charset.forName("UTF-8"));
//        return converter;
//    }
//
//
//



    @Bean
    public HttpMessageConverter fastJsonHttpMessageConverter() {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        FastJsonConfig config = new FastJsonConfig();
        config.setSerializerFeatures(
                // 是否将结果格式化,默认为false，配置后则格式化
                SerializerFeature.PrettyFormat,
                // 是否输出值为null的字段,默认为false 配置后则输出map中空字段
                SerializerFeature.WriteMapNullValue,
                // 将String类型的null返回""
                SerializerFeature.WriteNullStringAsEmpty,
                // 将Number类型的null返回0
                SerializerFeature.WriteNullNumberAsZero,
                // 将List类型的空集合null返回[]
                SerializerFeature.WriteNullListAsEmpty,
                // 将Boolean类型的null返回false
                SerializerFeature.WriteNullBooleanAsFalse,
                // 避免循环引用
                SerializerFeature.DisableCircularReferenceDetect);

        converter.setFastJsonConfig(config);
        // 解决中文乱码问题
        converter.setDefaultCharset(Charset.forName("UTF-8"));
        List<MediaType> mediaTypeList = new ArrayList<>();
        // 添加支持媒体类型，相当于在@RequestMapping中加了个属性produces = "application/json"
        mediaTypeList.add(MediaType.APPLICATION_JSON);
        converter.setSupportedMediaTypes(mediaTypeList);
        return converter;
    }

}