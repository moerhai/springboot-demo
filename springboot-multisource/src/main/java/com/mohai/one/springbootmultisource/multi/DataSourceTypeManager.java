package com.mohai.one.springbootmultisource.multi;

/**
 * 数据源类别管理
 * 通过ThreadLocal来保存每个线程选择的数据源类别
 * ThreadLocal用于提供线程局部变量，在多线程环境可以保证各个线程里的变量独立于其它线程里的变量。
 */
public class DataSourceTypeManager {

    /**
     * 默认类别为MASTER
     */
    private static final ThreadLocal<DataSourceTypeEnum> dataSourceTypes = new ThreadLocal<DataSourceTypeEnum>(){
        @Override
        protected DataSourceTypeEnum initialValue(){
            return DataSourceTypeEnum.MASTER;
        }
    };

    public static DataSourceTypeEnum getDataSource(){
        return dataSourceTypes.get();
    }

    public static void setDataSource(DataSourceTypeEnum dataSourceType){
        dataSourceTypes.set(dataSourceType);
    }

    /**
     * 清空数据库连接池
     */
     public static void clearDataSource(){
         dataSourceTypes.remove();
     }
}