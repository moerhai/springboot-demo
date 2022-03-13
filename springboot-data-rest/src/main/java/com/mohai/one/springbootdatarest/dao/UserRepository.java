package com.mohai.one.springbootdatarest.dao;

import com.mohai.one.springbootdatarest.domain.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/7/22 01:02
 */
@RepositoryRestResource(collectionResourceRel = "user", path = "user",excerptProjection = UserDtoExcerpt.class)
public interface UserRepository extends JpaRepository<UserDTO,Long> {

    @RestResource(path = "name",rel = "name")
    List<UserDTO> findByFirstName(@Param("name") String name);

}
