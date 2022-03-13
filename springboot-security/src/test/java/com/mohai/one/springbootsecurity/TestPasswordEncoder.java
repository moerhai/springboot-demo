package com.mohai.one.springbootsecurity;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/9/24 13:33
 */
public class TestPasswordEncoder {

    @Test
    public void testOne(){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
        String result = encoder.encode("myPassword");
        assertTrue(encoder.matches("myPassword", result));
    }

    @Test
    public void testTwo(){
        Pbkdf2PasswordEncoder encoder = new Pbkdf2PasswordEncoder();
        String result = encoder.encode("myPassword");
        System.out.println(result);
        assertTrue(encoder.matches("myPassword", result));
    }

}
