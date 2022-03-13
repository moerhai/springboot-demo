package com.mohai.one.springbootdatamongodb.service;

import com.mohai.one.springbootdatamongodb.domain.UserDTO;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/7/12 00:44
 */
@Service
public class UserService {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 新增
     * @param userDTO
     * @return
     */
    public UserDTO insert(UserDTO userDTO){
        //insert方法并不提供级联类的保存，所以级联类需要先自己先保存
        return mongoTemplate.insert(userDTO,"user");
    }

    /**
     * 修改
     * @param userDTO
     * @return
     */
    public UpdateResult updateResult(UserDTO userDTO){
        //构造更新条件，根据主键id更新
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(userDTO.getId()));
        //设置更新内容
        Update update = new Update();
        update.set("name",userDTO.getName());
        update.set("age",userDTO.getAge());
        update.set("sex",userDTO.getSex());
        update.set("update_time",userDTO.getUpdateTime());
        //指定查询collectionName集合中的记录，相当于表名区分大小写，然后根据id更新数据
        return mongoTemplate.updateFirst(query,update,UserDTO.class,"user");
    }

    /**
     * 删除
     * @param id
     * @return
     */
    public DeleteResult deleteResult(Integer id){
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        //指定查询collectionName集合中的记录，相当于表名区分大小写，然后根据id删除数据
        return mongoTemplate.remove(query,UserDTO.class,"user");
    }

    /**
     * 查询所有
     * @return
     */
    public List<UserDTO> findAll() {
        //指定查询collectionName集合中的记录，相当于表名区分大小写
        //如果没有collectionName会先到缓存中根据classname获取集合名，如果还没有则新建一个集合名（类名首字母小写）
        return mongoTemplate.findAll(UserDTO.class,"user");
    }

    /**
     * 获取单条
     * @param id
     * @return
     */
    public UserDTO getOneById(Integer id) {
        return mongoTemplate.findById(id,UserDTO.class,"user");
    }
}