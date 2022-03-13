package com.mohai.one.springbootmvc.vo;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

public class RoleVo {

    @JacksonXmlText(false)
    private String name;

    @JacksonXmlText
    private String desc;

    public RoleVo(String name,String desc){
        this.name=name;
        this.desc=desc;
    }

}
