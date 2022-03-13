package com.mohai.one.springbootmvc.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.text.SimpleDateFormat;

@Configuration
public class WebMvcConfig {

    @Bean
    @Qualifier("json")
    public ObjectMapper objectMapper(){
        ObjectMapper om = new ObjectMapper();
        om.setDateFormat(new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss"));
        return om;
    }

    @Bean
    @Qualifier("xml")
    public XmlMapper xmlMapper(Jackson2ObjectMapperBuilder builder) {
        XmlMapper mapper = builder.createXmlMapper(true)
                .build();
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        return mapper;
    }

}