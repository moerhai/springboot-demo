package com.mohai.one.springboothadoophbase.config;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.hadoop.hbase.HbaseTemplate;

import java.io.IOException;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/7/14 01:28
 */
@Configuration
public class HBaseConfig {

    @Value("${hbase.zookeeper.quorum}")
    private String zookeeperQuorum;

    @Value("${hbase.zookeeper.property.clientPort}")
    private String clientPort;

    @Value("hbase.zookeeper.property.dataDir")
    private String dataDir;

    @Value("${zookeeper.znode.parent}")
    private String znodeParent;

    @Bean
    public HbaseTemplate hbaseTemplate() {
        org.apache.hadoop.conf.Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", zookeeperQuorum);
        conf.set("hbase.zookeeper.property.clientPort", clientPort);
        conf.set("hbase.zookeeper.property.dataDir", dataDir);
        conf.set("zookeeper.znode.parent", znodeParent);
        return new HbaseTemplate(conf);
    }

}