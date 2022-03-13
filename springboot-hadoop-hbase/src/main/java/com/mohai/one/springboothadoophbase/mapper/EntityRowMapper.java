package com.mohai.one.springboothadoophbase.mapper;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.data.hadoop.hbase.RowMapper;

import java.util.Map;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/7/14 01:15
 */
public class EntityRowMapper<T> implements RowMapper<T> {

    private String columnFamily = "columns";

    private Class<T> beanType;

    public EntityRowMapper(final Class<T> beanType){
        this.beanType = beanType;
    }

    public String getColumnFamily() {
        return columnFamily;
    }

    public void setColumnFamily(String columnFamily) {
        this.columnFamily = columnFamily;
    }

    @Override
    public T mapRow(Result result, int rowNum) throws Exception {
        Map<byte[], byte[]> map = result.getFamilyMap(Bytes.toBytes(columnFamily));
        T t = beanType.newInstance();
        BeanWrapper beanWrapper = new BeanWrapperImpl(t);
        for(Map.Entry<byte[], byte[]> entry : map.entrySet()){
            beanWrapper.setPropertyValue(Bytes.toString(entry.getKey()),Bytes.toString(entry.getValue()));
        }
        return t;
    }
}
