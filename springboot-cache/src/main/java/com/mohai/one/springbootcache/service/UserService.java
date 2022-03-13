package com.mohai.one.springbootcache.service;

import com.mohai.one.springbootcache.dao.UserRepository;
import com.mohai.one.springbootcache.domain.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/8/1 22:06
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * 注意key的取值是从userDTO里取得
     * @param userDTO
     * @return
     */
    @CachePut(value = "user", key = "#userDTO.id")
    public UserDTO save(UserDTO userDTO) {
        return userRepository.save(userDTO);
    }

    @CacheEvict(value = "user")
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Cacheable(value = "user", key = "#id")
    public UserDTO findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }


    @Caching(
            //根据name查询
            cacheable = {
                    @Cacheable(value = "user",key = "#name")
            },
            //根据id查询缓存，以另一种key将结果进行缓存，如果返回结果为null则#result.id将会取值报错
            //想要不报错可以写成key="#result!=null?#result.id:#name"
            put = {
                    @CachePut(value = "user",key = "#result.id")
            }
    )
    public UserDTO getUserByName(String name) {
        return userRepository.findByName(name);
    }

}