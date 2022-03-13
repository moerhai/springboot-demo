package com.mohai.one.springbootmultisource.multi.annotation;

import com.mohai.one.springbootmultisource.multi.DataSourceTypeEnum;

import java.lang.annotation.*;

/**
 * 切换数据注解，可以用于类或者方法上
 * 方法级别优先级大于类级别
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSourceType {
    DataSourceTypeEnum value() default DataSourceTypeEnum.MASTER;
}
