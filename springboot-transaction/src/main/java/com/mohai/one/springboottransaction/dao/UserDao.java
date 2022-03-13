package com.mohai.one.springboottransaction.dao;

import com.mohai.one.springboottransaction.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Repository
public class UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int updateUserEntity(UserEntity userEntity){
        //定义一个某个框架平台的TransactionManager，这里使用JDBC
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(jdbcTemplate.getDataSource()); // 设置数据源
        DefaultTransactionDefinition transDef = new DefaultTransactionDefinition(); // 定义事务属性
        transDef.setPropagationBehavior(DefaultTransactionDefinition.PROPAGATION_REQUIRED); // 设置传播行为属性
        TransactionStatus status = dataSourceTransactionManager.getTransaction(transDef); // 获得事务状态
        try {
            // 数据库操作


            dataSourceTransactionManager.commit(status);// 提交
        } catch (Exception e) {
            dataSourceTransactionManager.rollback(status);// 回滚
        }
        return 1;
    }


    public UserEntity getUserEntity(int id){

        return null;
    }

}
