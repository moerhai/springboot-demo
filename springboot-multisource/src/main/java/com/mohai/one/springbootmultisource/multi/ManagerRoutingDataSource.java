package com.mohai.one.springbootmultisource.multi;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.Map;

/**
 * 扩展Spring的AbstractRoutingDataSource抽象类，重写determineCurrentLookupKey方法
 * 调用determineCurrentLookupKey()方法动态使用哪个数据源
 */
public class ManagerRoutingDataSource extends AbstractRoutingDataSource {

    /**
     *
     * @param defaultTargetDataSource  默认数据源
     * @param targetDataSources        目标数据源映射
     */
    public ManagerRoutingDataSource(DataSource defaultTargetDataSource, Map<Object, Object> targetDataSources){
        super.setDefaultTargetDataSource(defaultTargetDataSource);
        super.setTargetDataSources(targetDataSources);
        super.afterPropertiesSet();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceTypeManager.getDataSource();
    }
}
