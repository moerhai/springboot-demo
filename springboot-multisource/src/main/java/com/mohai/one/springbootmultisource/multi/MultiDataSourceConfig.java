package com.mohai.one.springbootmultisource.multi;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class MultiDataSourceConfig {

    @Bean("master")
    @ConfigurationProperties(prefix = "spring.datasource.one")
    public DataSource masterDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean("slave")
    @ConfigurationProperties(prefix = "spring.datasource.two")
    public DataSource slaveDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }


    /**
     * 动态数据源
     * @param masterDataSource
     * @param slaveDataSource
     * @return
     */
    //@Primary
    @Bean(name = "managerRoutingDataSource")
    public ManagerRoutingDataSource managerRoutingDataSource(@Qualifier("master") DataSource masterDataSource, @Qualifier("slave") DataSource slaveDataSource) {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DataSourceTypeEnum.MASTER.name(), masterDataSource);
        targetDataSources.put(DataSourceTypeEnum.SLAVE.name(), slaveDataSource);
        return new ManagerRoutingDataSource(masterDataSource, targetDataSources);
    }

    /**
     * 事务管理
     * @return
     */
//    @Bean
//    public PlatformTransactionManager platformTransactionManager() {
//        return new DataSourceTransactionManager();
//    }

}