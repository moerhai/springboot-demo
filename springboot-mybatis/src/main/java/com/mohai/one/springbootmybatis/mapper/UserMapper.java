package com.mohai.one.springbootmybatis.mapper;

import com.mohai.one.springbootmybatis.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper//通过xml中配置的namespace指定的接口地址，生成相应的Bean注入到Service层中
//@Repository
public interface UserMapper {

    int insert(User user);

    int update(User user);

    int delete(Integer id);

    List<User> getAllList();

    //可以通过@Select注解实现SQL的绑定
    @Select({"<script>",
            "select",
            "   id as id,",
            "   name as name,",
            "   age as age",
            "from",
            "   user",
            "<where>",
            "   <if test ='id != null'>",
            "   and",
            "       id = #{id}",
            "   </if>",
            "</where>",
            "</script>"})
    User getUserById(@Param("id") Integer id);

}
