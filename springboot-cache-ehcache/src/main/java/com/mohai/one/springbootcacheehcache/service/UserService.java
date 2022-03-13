package com.mohai.one.springbootcacheehcache.service;


import com.mohai.one.springbootcacheehcache.dao.UserRepository;
import com.mohai.one.springbootcacheehcache.domain.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/8/1 22:06
 */
@Service
@CacheConfig(cacheNames = "testUserCache")
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * 注意key的取值是从userDTO里取得
     * @param userDTO
     * @return
     */
    @CachePut(value = "testUserCache", key = "#userDTO.id")
    public UserDTO save(UserDTO userDTO) {
        return userRepository.save(userDTO);
    }

    @CacheEvict(value = "testUserCache")
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Cacheable(value = "testUserCache", key = "#id")
    public UserDTO findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }


    @Caching(
            //根据name查询
            cacheable = {
                    @Cacheable(value = "testUserCache",key = "#name")
            },
            //根据id查询缓存，以另一种key将结果进行缓存，如果返回结果为null则#result.id将会取值报错
            //想要不报错可以写成key="#result!=null?#result.id:#name"
            put = {
                    @CachePut(value = "testUserCache",key = "#result.id")
            }
    )
    public UserDTO getUserByName(String name) {
        return userRepository.findByName(name);
    }

}