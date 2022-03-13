package com.mohai.one.springbootsecurity.service;

import com.mohai.one.springbootsecurity.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 自定义一个 UserDetailsService实现类进行用户认证信息封装
 * @Auther: moerhai@qq.com
 * @Date: 2020/9/13 19:14
 */
//@Service
public class UserAuthDetailsService implements UserDetailsService {

    @Autowired
    CustomerService customerService;

    /**
     * 模拟用户登录 根据用户名获取用户 - 用户的角色、权限等信息
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 通过业务方法获取用户权限信息
        List<GrantedAuthority> authorities = getAuthorities();
        // 对用户权限进行封装
        List<SimpleGrantedAuthority> list = authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthority()))
                .collect(Collectors.toList());
        Customer customer = new Customer();
        // 返回封装的UserDetails用户详情类
        if (customer != null) {
            UserDetails userDetails = new User(customer.getUserName(),customer.getPassword(),list);
            return userDetails;
        } else {
            // 如果查询的用户不存在（用户名不存在），必须抛出异常
            throw new UsernameNotFoundException("当前用户不存在！");
        }
    }

    /**
     * 获取用户的角色权限,为了降低实验的难度，这里去掉了根据用户名获取角色的步骤
     * @return
     */
    private List<GrantedAuthority> getAuthorities(){
        List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();
        authList.add(new SimpleGrantedAuthority("ROLE_USER"));
        authList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        return authList;
    }
}