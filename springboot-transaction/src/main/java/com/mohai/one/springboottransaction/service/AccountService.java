package com.mohai.one.springboottransaction.service;

import com.mohai.one.springboottransaction.dao.AccountDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

@Service
public class AccountService {

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private AccountDao accountDao;

    public Object getAccount(){
        //设置事务传播属性
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        // 设置事务的隔离级别,设置为读已提交（默认是ISOLATION_DEFAULT:使用的是底层数据库的默认的隔离级别）
        transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        // 设置是否只读，默认是false
        transactionTemplate.setReadOnly(true);
        // 默认使用的是数据库底层的默认的事务的超时时间
        transactionTemplate.setTimeout(30000);

        /*
         *  执行带有返回值<Object>的事务管理
         */
        Object obj = transactionTemplate.execute(new TransactionCallback<Object>() {
            @Override
            public Object doInTransaction(TransactionStatus transactionStatus) {

                try {

                    //.......   业务代码

                    return new Object();
                } catch (Exception e) {
                    //回滚
                    transactionStatus.setRollbackOnly();
                    return null;
                }
            }
        });
        return obj;
    }


    public void updateAccount(){

        //设置事务传播属性
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        // 设置事务的隔离级别,设置为读已提交（默认是ISOLATION_DEFAULT:使用的是底层数据库的默认的隔离级别）
        transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        // 设置是否只读，默认是false
        transactionTemplate.setReadOnly(true);
        // 默认使用的是数据库底层的默认的事务的超时时间
        transactionTemplate.setTimeout(30000);

        /*
         *  执行无返回值的事务管理
         */
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            public void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                try {

                    //.......   业务代码


                } catch (Exception e) {
                    //回滚
                    transactionStatus.setRollbackOnly();
                }
            }
        });
    }

}
